package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.ElectricFireboxUpdateTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import crazywoddman.warium_additions.config.Config;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElectricFireboxUpdateTickProcedure.class)
public class ElectricFireboxUpdateTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void injectExecute(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockPos pos = BlockPos.containing(x, y, z);

        world.getBlockEntity(pos).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> {
            int canReceive = storage.receiveEnergy(Config.SERVER.energyTransferLimit.get(), true);

            if (canReceive > 0)
                for (Direction side : Direction.values()) {
                    BlockEntity sideBlock = world.getBlockEntity(pos.relative(side));

                    if (sideBlock != null && sideBlock.getCapability(ForgeCapabilities.ENERGY, side.getOpposite()).map(cap -> {
                        if (cap.canExtract()) {
                            int extracted = cap.extractEnergy(canReceive, false);

                            if (extracted > 0 && storage.receiveEnergy(extracted, false) > 0)
                                return true;
                        }

                        return false;
                    }).orElse(false)) {
                        break;
                    }
                }
        });
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(intValue = 1000),
        remap = false
    )
    private static int modifyMaxReceive(int original) {
        return Config.SERVER.electricFireboxConsumption.get();
    }
}
