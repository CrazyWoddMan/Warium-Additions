package crazywoddman.warium_additions.mixins.createdieselgenerators;

import com.jesz.createdieselgenerators.content.diesel_engine.IEngine;
import com.jesz.createdieselgenerators.content.diesel_engine.huge.HugeDieselEngineBlockEntity;
import com.jesz.createdieselgenerators.content.diesel_engine.modular.ModularDieselEngineBlockEntity;
import com.jesz.createdieselgenerators.content.diesel_engine.normal.DieselEngineBlockEntity;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = {
    @Condition("createdieselgenerators"),
    @Condition("valkyrien_warium")
})
@Mixin(
    value = {
        DieselEngineBlockEntity.class,
        ModularDieselEngineBlockEntity.class,
        HugeDieselEngineBlockEntity.class
    }, 
    remap = false
)
public class GeneratorsThrottleControl {

    @Redirect(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lcom/jesz/createdieselgenerators/content/diesel_engine/normal/DieselEngineBlockEntity;enabled()Z"
        ),
        require = 0
    )
    private boolean redirectEnabled(DieselEngineBlockEntity engine) {
        return addThrottleControl(engine);
    }

    @Redirect(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lcom/jesz/createdieselgenerators/content/diesel_engine/modular/ModularDieselEngineBlockEntity;enabled()Z"
        ),
        require = 0
    )
    private boolean redirectEnabled(ModularDieselEngineBlockEntity engine) {
        return addThrottleControl(engine);
    }

    @Redirect(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lcom/jesz/createdieselgenerators/content/diesel_engine/huge/HugeDieselEngineBlockEntity;enabled()Z"
        ),
        require = 0
    )
    private boolean redirectEnabled(HugeDieselEngineBlockEntity engine) {
        return addThrottleControl(engine);
    }

    private static boolean addThrottleControl(IEngine engine) {
        return engine.enabled() && WariumAdditionsUtil
            .getThrottle((BlockEntity)engine)
            .map(t -> t != 0)
            .orElse(true);
    }
}
