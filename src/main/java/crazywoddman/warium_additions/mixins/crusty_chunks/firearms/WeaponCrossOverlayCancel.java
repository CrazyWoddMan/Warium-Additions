package crazywoddman.warium_additions.mixins.crusty_chunks.firearms;

import net.mcreator.crustychunks.procedures.WeaponCrossOverlayProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.client.PatchedCrosshairOverlay;

/**
 * Cancels Warium's firearms crosshair since it breaks Resource Packs that affect HUD
 * @see PatchedCrosshairOverlay
 */
@Mixin(WeaponCrossOverlayProcedure.class)
public class WeaponCrossOverlayCancel {

    @Inject(
        method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;)V",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void wadditions$execute(Event event, LevelAccessor world, Entity entity, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
        method = "execute(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;)V",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void wadditions$execute(LevelAccessor world, Entity entity, CallbackInfo ci) {
        ci.cancel();
    }
}