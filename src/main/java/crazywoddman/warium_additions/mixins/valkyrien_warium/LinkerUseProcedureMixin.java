package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.mcreator.valkyrienwarium.procedures.LinkerUseProcedure;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin(LinkerUseProcedure.class)
public class LinkerUseProcedureMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "warium_vs:controllable"),
        remap = false
    )
    private static String modifyKerosene(String value) {
        return "warium_vs:linkable";
    }
}
