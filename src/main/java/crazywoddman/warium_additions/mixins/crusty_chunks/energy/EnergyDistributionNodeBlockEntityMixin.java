package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.entity.EnergyDistributionNodeBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;

@Mixin(EnergyDistributionNodeBlockEntity.class)
public class EnergyDistributionNodeBlockEntityMixin {
    private final int transLimit = Config.SERVER.energyTransferLimit.get();

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectEnergyCapability(Capability<?> capability, Direction capFacing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.ENERGY && capFacing != null) {
            BlockEntity blockEntity = (BlockEntity)(Object)this;
            cir.setReturnValue(LazyOptional.of(() -> new EnergyStorage(transLimit, transLimit, transLimit, transLimit) {
                @Override
                public int receiveEnergy(int maxReceive, boolean simulate) {
                    List<IEnergyStorage> validStorages = new ArrayList<>(6);
                    List<Integer> receiveAmount = new ArrayList<>(6);

                    for (Direction direction : Direction.values())
                        if (direction != capFacing) {
                            BlockEntity sideBlock = blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos().relative(direction));

                            if (sideBlock != null) {
                                IEnergyStorage cap = sideBlock.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).orElse(null);

                                if (cap != null && cap.canReceive()) {
                                    int received = cap.receiveEnergy(Math.min(capacity, maxReceive), true);

                                    if (received > 0) {
                                        validStorages.add(cap);
                                        receiveAmount.add(received);
                                    }
                                }
                            }
                        }

                    if (!validStorages.isEmpty()) {

                        int totalReceive = 0;

                        for (int amount : receiveAmount)
                            totalReceive = Math.min(Math.min(this.maxReceive, maxReceive), totalReceive + amount);
                    
                        int receiveLeft = totalReceive;
                        int sides = validStorages.size();

                        for (int i = 0; i < sides; i++)
                            receiveLeft -= validStorages.get(i).receiveEnergy(Math.round(receiveLeft / (sides - i)), simulate);
                            
                        return totalReceive - receiveLeft;
                    }

                    return 0;
                }

                @Override
                public int extractEnergy(int maxExtract, boolean simulate) {
                    for (Direction direction : Direction.values())
                        if (direction != capFacing) {
                            BlockEntity sideBlock = blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos().relative(direction));

                            if (sideBlock != null) {
                                IEnergyStorage cap = sideBlock.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).orElse(null);

                                if (cap != null && cap.canExtract() && cap.getEnergyStored() > 0)
                                    return cap.extractEnergy(Math.min(this.maxExtract, maxExtract), simulate);
                            }
                        }

                    return 0;
                }
            }).cast());
        }
    }
}
