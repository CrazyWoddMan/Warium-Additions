package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.mcreator.valkyrienwarium.procedures.ReturnThrottleMinusProcedure;
import net.mcreator.valkyrienwarium.procedures.ReturnThrottlePlusProcedure;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin({ReturnThrottlePlusProcedure.class, ReturnThrottleMinusProcedure.class})
public class ReturnThrottleMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"
        ),
        remap = false
    )
    private static boolean tweakArrowsGlow(String value, Object object) {
        return value.equals(object) || value.equals("Throttle");
    }
}
