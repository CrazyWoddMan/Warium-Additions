package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.WeaponCrossOverlayProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeaponCrossOverlayProcedure.class)
public class WeaponCrossOverlayProcedureMixin {

    @Inject(
        method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;)V",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void cancelExecute(Event event, LevelAccessor world, Entity entity, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
        method = "execute(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;)V",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void cancelAltExecute(LevelAccessor world, Entity entity, CallbackInfo ci) {
        ci.cancel();
    }
}