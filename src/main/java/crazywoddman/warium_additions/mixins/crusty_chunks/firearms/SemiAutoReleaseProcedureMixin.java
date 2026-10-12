package crazywoddman.warium_additions.mixins.crusty_chunks.firearms;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.client.WadditionsKeyMappings;
import net.mcreator.crustychunks.procedures.SemiAutoReleaseProcedure;
import net.minecraft.client.KeyMapping;

@Mixin(SemiAutoReleaseProcedure.class)
public class SemiAutoReleaseProcedureMixin {

    @Redirect(
        method = "onClientTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/KeyMapping;isDown()Z"
        )
    )
    private static boolean wadditions$isDown(KeyMapping key) {
        return WadditionsKeyMappings.SHOOT.isDown();
    }
}