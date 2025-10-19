package crazywoddman.warium_additions.mixins.immersiveengineering;

import net.mcreator.crustychunks.procedures.EnergyBatteryTickProcedure;
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

import blusunrize.immersiveengineering.api.IEEnums.IOSideConfig;
import blusunrize.immersiveengineering.api.utils.DirectionUtils;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IConfigurableSides;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("immersiveengineering"))
@Mixin(EnergyBatteryTickProcedure.class)
public class ImmersiveBatteryTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void cancelExecute(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockPos pos = BlockPos.containing(x, y, z);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        IEnergyStorage storage = blockEntity.getCapability(ForgeCapabilities.ENERGY).orElse(null);

        if (storage.getEnergyStored() > 0)
            for(Direction direction : DirectionUtils.VALUES)
                if (((IConfigurableSides)blockEntity).getSideConfig(direction) == IOSideConfig.OUTPUT) {
                    BlockEntity opposite = world.getBlockEntity(pos.relative(direction));
                    
                    if (opposite != null)
                        opposite.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).ifPresent(cap -> {
                            if (cap.canReceive()) {
                                int available = storage.extractEnergy(1000, true);
                                int transfered = cap.receiveEnergy(available, false);
                                storage.extractEnergy(transfered, false);
                            }
                        });
                }
        ci.cancel();
    }
}
