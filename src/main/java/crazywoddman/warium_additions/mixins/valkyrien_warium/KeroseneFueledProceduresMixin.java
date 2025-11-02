package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.mcreator.valkyrienwarium.procedures.LiquidRocketProcedureProcedure;
import net.mcreator.valkyrienwarium.procedures.TestThrusterOnTickUpdateProcedure;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin({LiquidRocketProcedureProcedure.class, TestThrusterOnTickUpdateProcedure.class})
public class KeroseneFueledProceduresMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "Kerosene"),
        remap = false
    )
    private static String modifyKerosene(String value) {
        return "";
    }

    // TODO: fix control node linking issue
}
