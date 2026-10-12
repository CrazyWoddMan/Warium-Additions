package crazywoddman.warium_additions.mixins.crusty_chunks.power;

import net.mcreator.crustychunks.procedures.EngineUpdateProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static crazywoddman.warium_additions.config.WadditionsConfig.*;

@Mixin(EngineUpdateProcedure.class)
public class EngineUpdateProcedureMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 50),
        remap = false,
        require = 0
    )
    private static double wadditions$mediumDieselPower(double value) {
        return MEDIUM_DIESEL_ENGINE_POWER.get();
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 35),
        remap = false,
        require = 0
    )
    private static double modiwadditions$smallDieselPower(double value) {
        return SMALL_DIESEL_ENGINE_POWER.get();
    }
}
