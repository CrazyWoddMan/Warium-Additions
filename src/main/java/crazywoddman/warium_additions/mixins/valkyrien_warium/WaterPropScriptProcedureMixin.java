package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin(targets = {
    "net.mcreator.valkyrienwarium.procedures.WaterPropScriptProcedure$6",
    "net.mcreator.valkyrienwarium.procedures.WaterPropScriptProcedure$16"
})
public class WaterPropScriptProcedureMixin {

    @Redirect(
        method = "getValue",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;getDouble(Ljava/lang/String;)D"
        )
    )
    private double redirectRotationDirection(CompoundTag tag, String key, LevelAccessor world, BlockPos pos) {
        double value = tag.getDouble(key);
        return (world.getBestNeighborSignal(pos) > 0)
            ? -value
            : value;
    }
}
