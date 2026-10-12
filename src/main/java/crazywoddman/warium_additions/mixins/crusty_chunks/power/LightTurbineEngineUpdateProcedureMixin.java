package crazywoddman.warium_additions.mixins.crusty_chunks.power;

import net.mcreator.crustychunks.procedures.LightTurbineEngineUpdateProcedure;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import static crazywoddman.warium_additions.config.WadditionsConfig.*;

@Mixin(LightTurbineEngineUpdateProcedure.class)
public class LightTurbineEngineUpdateProcedureMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 65),
        remap = false,
        require = 0
    )
    private static double wadditions$lighTurbineEnginePower(double value, LevelAccessor world, double x, double y, double z) {
        return LIGHT_TURBINE_ENGINE_POWER.get();
    }
}
