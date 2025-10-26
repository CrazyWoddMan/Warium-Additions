package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.procedures.HeavyFlameThrowerFireScriptProcedure;
import net.minecraft.nbt.CompoundTag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(HeavyFlameThrowerFireScriptProcedure.class)
public class HeavyFlameThrowerFireScriptProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putString(Ljava/lang/String;Ljava/lang/String;)V"
        )
    )
    private static void redirectPut(CompoundTag compound, String key, String value) {}

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "Petrol"),
        remap = false
    )
    private static String modifyKerosene(String value) {
        return "";
    }
}
