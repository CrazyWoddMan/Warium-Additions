package crazywoddman.warium_additions.mixins.tfmg;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.drmangotea.tfmg.content.engines.base.AbstractEngineBlockEntity;

// TODO: doesn't work
@SuppressWarnings("unused")
@Restriction(require = {
    @Condition("tfmg"),
    @Condition("valkyrien_warium")
})
@Mixin(
    value = AbstractEngineBlockEntity.class,
    remap = false
)
public class EnginesThrottleControl {

    // @Shadow(remap = false)
    // private int signal;

    // private final int maxThrottle = Config.SERVER.maxThrottle.get();
    // private int lastSignal;
    // private int lastRedstoneSignal;

    // @Redirect(
    //     method = "analogSignalChanged",
    //     at = @At(
    //         value = "FIELD",
    //         target = "Lcom/drmangotea/tfmg/content/engines/base/AbstractEngineBlockEntity;signal:I",
    //         opcode = org.objectweb.asm.Opcodes.PUTFIELD
    //     ),
    //     remap = false
    // )
    // private void redirectSignalAssignment(AbstractEngineBlockEntity instance, int newSignal) {
    //     this.lastRedstoneSignal = newSignal;
    // }

    // @Inject(
    //     method = "tick",
    //     at = @At("TAIL")
    // )
    // private void injectSignalTick(CallbackInfo ci) {
    //     BlockEntity blockEntity = (BlockEntity)(Object)this;
        
    //     // if (blockEntity.getLevel().getGameTime() % 8 != 0)
    //     //     return;
        
    //     int newSignal = this.lastRedstoneSignal;
        
    //     if (this.lastRedstoneSignal == 0) {
    //         int throttle = WariumAdditionsUtil.getThrottle(blockEntity, 0);

    //         if (throttle != 0)
    //             newSignal = Math.round(15.0F / this.maxThrottle * throttle);
    //     }

    //     if (this.lastSignal != newSignal) {
    //         this.lastSignal = newSignal;
    //         this.signal = Math.abs(newSignal);
    //     }
    // }
}
