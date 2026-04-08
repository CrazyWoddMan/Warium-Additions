package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.mcreator.valkyrienwarium.procedures.Throttle1PressProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import crazywoddman.warium_additions.config.Config;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

import org.spongepowered.asm.mixin.injection.Constant;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin(remap = false, value = Throttle1PressProcedure.class)
public class Throttle1PressProcedureMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(intValue = 10)
    )
    private static int moreNegativeThrottle(int maxThrottle) {
        return Config.SERVER.maxThrottle.get();
    }
}
