package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.EnergyNodeBlock;
import net.mcreator.crustychunks.block.entity.EnergyNodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.IEnergyNodeAccessor;

@Mixin(EnergyNodeBlockEntity.class)
public class EnergyNodeBlockEntityMixin implements IEnergyNodeAccessor {
    private final int limit = Config.SERVER.energyTransferLimit.get();
    private int lastReceived;
    private int lastExtracted;
    private int counter;
    private final int[] history = new int[40];

    @Override
    public void updateTransferinfo() {
        System.arraycopy(this.history, 0, this.history, 1, 39);
        int transfer = Math.max(this.lastReceived, this.lastExtracted);
        this.history[0] = transfer;
        this.lastReceived = 0;
        this.lastExtracted = 0;

        if (counter++ > 10) {
            counter = 0;
            BlockEntity blockEntity = (BlockEntity)(Object)this;
            BlockState state = blockEntity.getBlockState();
            blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), state, state, Block.UPDATE_CLIENTS);
        }
    }
    
    @Unique
    private final EnergyStorage energyStorage = new EnergyStorage(limit);

    @Redirect(
        method = "load",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/energy/EnergyStorage;deserializeNBT(Lnet/minecraft/nbt/Tag;)V",
            remap = false
        )
    )
    private void redirectDeserializeNBT(EnergyStorage storage, Tag tag) {
        this.energyStorage.deserializeNBT(tag);
    }

    @Redirect(
        method = "saveAdditional",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;put(Ljava/lang/String;Lnet/minecraft/nbt/Tag;)Lnet/minecraft/nbt/Tag;"
        )
    )
    private Tag redirectCompoundPut(CompoundTag compound, String key, Tag tag) {
        int sum = 0;

        for (int value : this.history)
            sum += value;

        return compound.put(key, IntTag.valueOf(Math.round(sum / 40)));
    }

    private EnergyStorage getEnergyStorage(IEnergyStorage storage) {
        return new EnergyStorage(this.limit) {
            @Override
            public int getEnergyStored() {
                return storage.getEnergyStored();
            }

            @Override
            public boolean canExtract() {
                return storage.canExtract();
            }

            @Override
            public boolean canReceive() {
                return storage.canReceive();
            }

            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                if (!canReceive())
                    return 0;
                
                int received = storage.receiveEnergy(Math.min(maxReceive, this.capacity - lastExtracted), simulate);
                
                if (!simulate && received > 0)
                    lastReceived += received;

                return received;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                if (!canExtract())
                    return 0;

                int extracted = storage.extractEnergy(Math.min(maxExtract, this.capacity - lastExtracted), simulate);
                
                if (!simulate && extracted > 0)
                    lastExtracted += extracted;

                return extracted;
            }
        };
    }

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectEnergyCapability(Capability<?> capability, Direction capFacing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability != ForgeCapabilities.ENERGY)
            return;

        BlockEntity blockEntity = (BlockEntity)(Object)this;
        Level level = blockEntity.getLevel();

        if (level.isClientSide) {
            cir.setReturnValue(LazyOptional.of(() -> this.energyStorage).cast());
            return;
        }

        Direction nodeFacing = blockEntity.getBlockState().getValue(EnergyNodeBlock.FACING);

        if (capFacing == null) {
            BlockEntity connectedBlock = level
            .getBlockEntity(
                blockEntity
                .getBlockPos()
                .relative(nodeFacing.getOpposite())
            );

            if (connectedBlock != null) {
                connectedBlock.getCapability(ForgeCapabilities.ENERGY, nodeFacing).resolve().ifPresentOrElse(
                    storage -> cir.setReturnValue(LazyOptional.of(() -> getEnergyStorage(storage)).cast()),
                    () -> cir.setReturnValue(LazyOptional.empty())
                );
                return;
            }
        } else if (capFacing == nodeFacing.getOpposite()) {
            CompoundTag data = blockEntity.getPersistentData();

            if (data.contains("PowerX")) {
                BlockEntity connectedNode = level.getBlockEntity(new BlockPos(
                    data.getInt("PowerX"),
                    data.getInt("PowerY"),
                    data.getInt("PowerZ")
                ));

                if (connectedNode != null && connectedNode instanceof EnergyNodeBlockEntity) {
                    connectedNode.getCapability(ForgeCapabilities.ENERGY).resolve().ifPresentOrElse(
                        storage -> cir.setReturnValue(LazyOptional.of(() -> getEnergyStorage(storage)).cast()),
                        () -> cir.setReturnValue(LazyOptional.empty())
                    );
                    return;
                }
            }
        }

        cir.setReturnValue(LazyOptional.empty());
    }
}
