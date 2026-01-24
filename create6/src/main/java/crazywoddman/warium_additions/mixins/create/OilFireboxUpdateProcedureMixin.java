package crazywoddman.warium_additions.mixins.create;

import net.mcreator.crustychunks.procedures.OilFireboxUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.world.level.LevelAccessor;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;

import crazywoddman.warium_additions.config.Config;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition("create"))
@Mixin(OilFireboxUpdateProcedure.class)
public class OilFireboxUpdateProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        remap = false
    )
    private static void setHeatLevel(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockPos pos = BlockPos.containing(x, y, z);
        BlockState state = world.getBlockState(pos);
        BlazeBurnerBlock.HeatLevel requiredHeat = BlazeBurnerBlock.HeatLevel.valueOf(Config.SERVER.keroseneFireboxHeatLevel.get());
        BlazeBurnerBlock.HeatLevel currentHeat = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);

        if (world.getBlockEntity(pos).getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null).getFluidInTank(0).getAmount() > 0) {
            if (currentHeat != requiredHeat)
                world.setBlock(pos, state.setValue(BlazeBurnerBlock.HEAT_LEVEL, requiredHeat), 3);
        } else if (currentHeat == requiredHeat)
            world.setBlock(pos, state.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.NONE), 3);
    }
}
