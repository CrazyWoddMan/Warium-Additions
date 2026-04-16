package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.EngineThrottleSystemProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EngineThrottleSystemProcedure.class)
public class EngineThrottleSystemProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z",
            ordinal = 6
        ),
        remap = false
    )
    private static boolean fixNegativeThrottle1(String value, Object object) {
        return value.startsWith("Throttle");
    }


    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z",
            ordinal = 8
        ),
        remap = false
    )
    private static boolean fixNegativeThrottle2(String value, Object object) {
        return ((String)object).startsWith("Throttle");
    }
}
