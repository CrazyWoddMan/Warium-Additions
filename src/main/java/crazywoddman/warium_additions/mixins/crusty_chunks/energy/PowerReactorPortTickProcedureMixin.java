package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.PowerReactorPortTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.config.Config;

@Mixin(PowerReactorPortTickProcedure.class)
public class PowerReactorPortTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void cancelExecute(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockEntity belowEntity = world.getBlockEntity(BlockPos.containing(x, y - 1.0, z));

        if (belowEntity != null) {
            IEnergyStorage storage = world.getBlockEntity(BlockPos.containing(x, y, z)).getCapability(ForgeCapabilities.ENERGY).orElse(null);

            if (storage.getEnergyStored() > 0) {
                int canExtract = storage.extractEnergy(Config.SERVER.energyTransferLimit.get(), true);

                if (canExtract > 0) {
                    IEnergyStorage storageBelow = belowEntity.getCapability(ForgeCapabilities.ENERGY, Direction.UP).orElse(null);

                    if (storageBelow != null && storageBelow.canReceive()) {
                        int transfered = storageBelow.receiveEnergy(canExtract, false);

                        if (transfered > 0)
                            storage.extractEnergy(transfered, false);
                    }
                }
            }
        }
        ci.cancel();
    }
}
