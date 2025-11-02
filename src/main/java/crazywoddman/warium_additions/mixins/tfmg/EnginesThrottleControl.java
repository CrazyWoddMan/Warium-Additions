package crazywoddman.warium_additions.mixins.tfmg;

import com.drmangotea.tfmg.blocks.engines.compact.CompactEngineBlockEntity;
import com.drmangotea.tfmg.blocks.engines.low_grade_fuel.LowGradeFuelEngineBlockEntity;
import com.drmangotea.tfmg.blocks.engines.radial.RadialEngineBlockEntity;
import com.drmangotea.tfmg.blocks.engines.small.AbstractEngineBlockEntity;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.ThrottleProvider;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(require = {
    @Condition(value = "tfmg", versionPredicates = "[0.9.3]"),
    @Condition("valkyrien_warium")
})
// TODO: test with create 6
// @Restriction(require = @Condition("tfmg"))
@Mixin(
    value = {
        AbstractEngineBlockEntity.class,
        CompactEngineBlockEntity.class,
        LowGradeFuelEngineBlockEntity.class,
        RadialEngineBlockEntity.class
    },
    remap = false
)
public class EnginesThrottleControl {

    @Shadow(remap = false)
    int signal;

    private final int maxThrottle = Config.SERVER.maxThrottle.get();
    private final boolean throttleToRotationDirection = Config.SERVER.throttleToRotationDirection.get();
    private int lastSignal;
    private int lastRedstoneSignal;

    @Inject(
        method = "analogSignalChanged",
        at = @At("HEAD"),
        cancellable = true
    )
    private void analogSignalChangedTweak(int newSignal, CallbackInfo ci) {
        this.lastRedstoneSignal = newSignal;
        ci.cancel();
    }

    @Inject(
        method = "getGeneratedSpeed",
        at = @At("RETURN"),
        cancellable = true
    )
    private void injectReturn(CallbackInfoReturnable<Float> cir) {
        float originalValue = cir.getReturnValue();

        if (originalValue != 0.0F && this.throttleToRotationDirection && this.lastSignal < 0)
            cir.setReturnValue(-1 * originalValue);

    }

    @Inject(
        method = "tick",
        at = @At("HEAD")
    )
    private void injectSignalTick(CallbackInfo ci) {
        BlockEntity blockEntity = (BlockEntity)(Object) this;
        
        if (blockEntity.getLevel().getGameTime() % 8 != 0)
            return;
        
        int newSignal = this.lastRedstoneSignal;
        
        if (this.lastRedstoneSignal == 0) {
            int throttle = ThrottleProvider.get(blockEntity, 0);

            if (throttle != 0)
                newSignal = Math.round(15.0F / this.maxThrottle * throttle);
        }

        if (this.lastSignal != newSignal) {
            this.lastSignal = newSignal;
            this.signal = Math.abs(newSignal);
        }
    }
}
