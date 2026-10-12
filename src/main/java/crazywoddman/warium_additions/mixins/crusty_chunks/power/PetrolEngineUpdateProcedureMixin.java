package crazywoddman.warium_additions.mixins.crusty_chunks.power;

import net.mcreator.crustychunks.procedures.PetrolEngineUpdateProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import static crazywoddman.warium_additions.config.WadditionsConfig.*;

@Mixin(PetrolEngineUpdateProcedure.class)
public class PetrolEngineUpdateProcedureMixin {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 60),
        remap = false,
        require = 0
    )
    private static double wadditions$mediumPetrolPower(double value) {
        return MEDIUM_PETROL_ENGINE_POWER.get();
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 40),
        remap = false,
        require = 0
    )
    private static double modwadditions$smallPetrolPower(double value) {
        return SMALL_PETROL_ENGINE_POWER.get();
    }
}
