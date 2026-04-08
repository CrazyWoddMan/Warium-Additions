package crazywoddman.warium_additions.mixins.tfmg;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.drmangotea.tfmg.content.engines.base.AbstractEngineBlockEntity;
import com.drmangotea.tfmg.content.engines.types.AbstractSmallEngineBlockEntity;

@Restriction(require = {
    @Condition("tfmg"),
    @Condition("valkyrien_warium")
})
@Mixin(value = AbstractSmallEngineBlockEntity.class, remap = false)
public class AbstractSmallEngineThrottleControl {

    private double throttle;
    private boolean throttleControl;

     @Redirect(
        method = "analogSignalChanged",
        at = @At(
            value = "INVOKE",
            target = "Lcom/drmangotea/tfmg/content/engines/types/AbstractSmallEngineBlockEntity;updateRotation()V"
        )
    )
    private void redirectSignalAssignment(AbstractSmallEngineBlockEntity engine) {
        if (this.throttleControl) {
            engine.highestSignal = (int)(15.0 / Config.SERVER.maxThrottle.get() * this.throttle);
            engine.fuelInjectionRate = engine.highestSignal / 15f;
        }

        engine.updateRotation();
    }

    @Inject(
        method = "tick",
        at = @At("TAIL")
    )
    private void injectTick(CallbackInfo ci) {
        AbstractEngineBlockEntity engine = ((AbstractEngineBlockEntity)(Object)this);
        boolean throttleControl;
        double throttle;
        Optional<Double> potentialThrottle = WariumAdditionsUtil.getThrottle(engine);

        if (potentialThrottle.isPresent()) {
            throttleControl = true;
            throttle = potentialThrottle.get();
        } else {
            throttleControl = false;
            throttle = 0;
        }

        if (this.throttle != throttle || this.throttleControl != throttleControl) {
            this.throttle = throttle;
            this.throttleControl = throttleControl;
            engine.signalChanged = true;
        }
    }
}
