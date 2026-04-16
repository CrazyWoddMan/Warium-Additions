package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.JetTurbineUpdateTickProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import crazywoddman.warium_additions.config.Config;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(conflict = @Condition("wariumtactics"))
@Mixin(JetTurbineUpdateTickProcedure.class)
public class JetTurbinePowerMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 51.0),
        remap = false
    )
    private static double modifyPowerTurbine(double value) {
        return Config.SERVER.jetTurbinePower.get();
    }
}
