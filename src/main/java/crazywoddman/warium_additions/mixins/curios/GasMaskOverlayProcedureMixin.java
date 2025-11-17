package crazywoddman.warium_additions.mixins.curios;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.compat.curios.CuriosUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.GasMaskOverlayProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Restriction(require = @Condition("curios"))
@Mixin(value = GasMaskOverlayProcedure.class, remap = false)
public class GasMaskOverlayProcedureMixin {
    @Inject(
        method = "execute",
        at = @At("RETURN"),
        cancellable = true
    )
    private static void injectReturn(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(
            cir.getReturnValueZ() ||
            (entity instanceof LivingEntity livingEntity && CuriosUtil.getItem(livingEntity, "head", CrustyChunksModItems.GAS_MASK_HELMET.get()).isPresent())
        );
    }
}
