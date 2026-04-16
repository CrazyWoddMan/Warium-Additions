package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.mcreator.valkyrienwarium.procedures.SetThrottleMinusProcedure;
import net.mcreator.valkyrienwarium.procedures.SetThrottlePlusProcedure;
import net.minecraft.nbt.CompoundTag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin({SetThrottlePlusProcedure.class, SetThrottleMinusProcedure.class})
public class SetThrottleMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putString(Ljava/lang/String;Ljava/lang/String;)V"
        )
    )
    private static void redirectThrottleModeSet(CompoundTag tag, String key, String value) {
        String current = tag.getString(key);

        if (current.equals(value))
            return;

        tag.putString(
            key,
            current.equals("Throttle")
            ? (value.endsWith("+") ? "Throttle-" : "Throttle+")
            : (current.startsWith("Throttle") ? "Throttle" : value)
        );
    }
}
