package crazywoddman.warium_additions.mixins.crusty_chunks.power;

import net.mcreator.crustychunks.procedures.EngineCyllinderOnTickUpdateProcedure;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import static crazywoddman.warium_additions.config.WadditionsConfig.*;

@Mixin(EngineCyllinderOnTickUpdateProcedure.class)
public class EngineCyllinderOnTickUpdateProcedureMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 50),
        remap = false,
        require = 0
    )
    private static double wadditions$largeEnginePower(double value, LevelAccessor world, double x, double y, double z) {
        return LARGE_ENGINE_POWER.get();
    }
}
