package crazywoddman.warium_additions.mixins.crusty_chunks.power;

import net.mcreator.crustychunks.procedures.JetTurbineUpdateTickProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static crazywoddman.warium_additions.config.WadditionsConfig.*;

@Mixin(JetTurbineUpdateTickProcedure.class)
public class JetTurbineUpdateTickProcedureMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 51),
        remap = false
    )
    private static double wadditions$turbinePower(double value) {
        return JET_TURBINE_POWER.get();
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 50, ordinal = 1),
        remap = false
    )
    private static double wadditions$AfterBurnerPower(double value) {
        return JET_TURBINE_POWER.get() - 1;
    }
}
