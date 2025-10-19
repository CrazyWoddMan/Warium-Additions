package crazywoddman.warium_additions.mixins.ponderjs;

import com.almostreliable.ponderjs.PonderJS;

import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(value = "ponderjs", versionPredicates = "[1.4.0]"))
@Mixin(value = PonderJS.class, remap = false)
public class PonderJSMixin {

    @Shadow
    private static boolean initialized;
    
    @Inject(
        method = "init",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void skipResourceReload(CallbackInfo ci) {
        if (!initialized)
            initialized = true;
        ci.cancel();
    }
}
