package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.EnergyNodeBlock;
import net.mcreator.crustychunks.block.entity.EnergyNodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;

@Mixin(EnergyNodeBlockEntity.class)
public class EnergyNodeBlockEntityMixin {

    private final int limit = Config.SERVER.cableLimit.get();
    private int lastTransfer;
    private int count;
    
    private final EnergyStorage energyStats = new EnergyStorage(limit) {
        @Override
        public int getEnergyStored() {
            if (count > 6)
                this.energy = 0;
            else count++;
            
            return this.energy;
        }
    };

    @Inject(
        method = "load",
        at = @At("TAIL")
    )
    private void injectRead(CompoundTag compound, CallbackInfo ci) {
        this.energyStats.deserializeNBT(compound.get("energyStorage"));
        this.count = 0;
    }

    @Inject(
        method = "saveAdditional",
        at = @At("TAIL")
    )
    private void injectSave(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("energyStorage", this.lastTransfer);
    }

    private EnergyStorage getEnergyStorage(IEnergyStorage storage) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
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
                
                int received = storage.receiveEnergy(Math.min(maxReceive, this.capacity), simulate);
                
                if (!simulate && received > 0) {
                    lastTransfer = received;
                    blockEntity.setChanged();
                    
                    BlockState state = blockEntity.getBlockState();
                    blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), state, state, Block.UPDATE_CLIENTS);
                }

                return received;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                if (!canExtract())
                    return 0;

                int extracted = storage.extractEnergy(Math.min(maxExtract, this.capacity), simulate);
                
                if (!simulate && extracted > 0) {
                    lastTransfer = extracted;
                    blockEntity.setChanged();
                    
                    BlockState state = blockEntity.getBlockState();
                    blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), state, state, Block.UPDATE_CLIENTS);
                }

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
        if (capability == ForgeCapabilities.ENERGY) {
            BlockEntity blockEntity = (BlockEntity)(Object)this;
            Level level = blockEntity.getLevel();

            if (level.isClientSide)
                cir.setReturnValue(LazyOptional.of(() -> this.energyStats).cast());

            else {
                Direction nodeFacing = blockEntity.getBlockState().getValue(EnergyNodeBlock.FACING);

                if (capFacing == null) {
                    BlockEntity connectedBlock = level
                    .getBlockEntity(
                        blockEntity
                        .getBlockPos()
                        .relative(nodeFacing.getOpposite())
                    );

                    if (connectedBlock != null) {
                        IEnergyStorage storage = connectedBlock.getCapability(ForgeCapabilities.ENERGY, nodeFacing).orElse(null);

                        if (storage != null)
                            cir.setReturnValue(LazyOptional.of(() -> getEnergyStorage(storage)).cast());
                    }
                }

                else if (capFacing == nodeFacing.getOpposite()) {
                    CompoundTag data = blockEntity.getPersistentData();

                    if (data.contains("PowerX")) {
                        BlockEntity connectedNode = level.getBlockEntity(new BlockPos(
                            data.getInt("PowerX"),
                            data.getInt("PowerY"),
                            data.getInt("PowerZ")
                        ));

                        if (connectedNode != null && connectedNode instanceof EnergyNodeBlockEntity) {
                            IEnergyStorage storage = connectedNode.getCapability(ForgeCapabilities.ENERGY).orElse(null);

                            if (storage != null)
                                cir.setReturnValue(LazyOptional.of(() -> getEnergyStorage(storage)).cast());
                        }
                    }
                }
            }
        }
    }
}
