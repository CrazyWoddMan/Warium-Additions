package crazywoddman.warium_additions.mixins.crusty_chunks.firearms;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.ArmorPeelerRightclickedProcedure;
import net.mcreator.crustychunks.procedures.AutoPistolFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BattleRifleFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BoltFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BreakActionFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BreechRifleFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BurstRifleFireScriptProcedure;
import net.mcreator.crustychunks.procedures.EradicationFireScriptProcedure;
import net.mcreator.crustychunks.procedures.FlameThrowerFireScriptProcedure;
import net.mcreator.crustychunks.procedures.FlarePistolFireScriptProcedure;
import net.mcreator.crustychunks.procedures.GrenadeLauncherFireProcedure;
import net.mcreator.crustychunks.procedures.LMGFireScriptProcedure;
import net.mcreator.crustychunks.procedures.LeverGunFireScriptProcedure;
import net.mcreator.crustychunks.procedures.MCFireScriptProcedure;
import net.mcreator.crustychunks.procedures.RevolverFireScriptProcedure;
import net.mcreator.crustychunks.procedures.RifleFireScriptProcedure;
import net.mcreator.crustychunks.procedures.SMGFireScriptProcedure;
import net.mcreator.crustychunks.procedures.SemiAutomaticPistolEntitySwingsItemProcedure;
import net.mcreator.crustychunks.procedures.ShotgunFireScriptProcedure;
import net.mcreator.crustychunks.procedures.SingleShotFireScriptProcedure;
import net.mcreator.crustychunks.procedures.StealthPistolFireProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.config.WadditionsConfig;

/**
 * Prevents ammo consumption and casings drop for players in Creative Mode
 */
@Mixin({
    AutoPistolFireScriptProcedure.class,
    SemiAutomaticPistolEntitySwingsItemProcedure.class,
    StealthPistolFireProcedure.class,
    RevolverFireScriptProcedure.class,
    SMGFireScriptProcedure.class,
    MCFireScriptProcedure.class,
    LeverGunFireScriptProcedure.class,
    RifleFireScriptProcedure.class,
    BurstRifleFireScriptProcedure.class,
    LMGFireScriptProcedure.class,
    BattleRifleFireScriptProcedure.class,
    BoltFireScriptProcedure.class,
    BreakActionFireScriptProcedure.class,
    ShotgunFireScriptProcedure.class,
    BreechRifleFireScriptProcedure.class,
    SingleShotFireScriptProcedure.class,
    FlameThrowerFireScriptProcedure.class,
    GrenadeLauncherFireProcedure.class,
    ArmorPeelerRightclickedProcedure.class,
    EradicationFireScriptProcedure.class,
    FlarePistolFireScriptProcedure.class
})
public class CreativeFireMixin {
    private static final ItemStack FAKE_TANK = new ItemStack(CrustyChunksModItems.FLAME_THROWER_TANK_CHESTPLATE.get());
    
    static {
        FAKE_TANK.getOrCreateTag().putInt("Fluid", 1000);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        ),
        require = 0
    )
    private static void wadditions$putDouble(CompoundTag tag, String key, double value, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!wadditions$enabled(entity))
            tag.putDouble(key, value);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V"
        ),
        require = 0
    )
    private static void wadditions$putBoolean(CompoundTag tag, String key, boolean value, LevelAccessor world, double x, double y, double z, Entity entity, ItemStack stack) {
        if (!wadditions$enabled(entity) || !stack.is(CrustyChunksModItems.FLAME_THROWER_ANIMATED.get()))
            tag.putBoolean(key, value);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
        ),
        require = 0
    )
    private static boolean wadditions$addFreshEntity(ServerLevel level, Entity casing, LevelAccessor world, double x, double y, double z, Entity entity) {
        return wadditions$enabled(entity) && casing instanceof ItemEntity ? false : level.addFreshEntity(casing);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
        ),
        require = 0
    )
    private static ItemStack wadditions$getItemBySlot(LivingEntity entity, EquipmentSlot slot) {
        return wadditions$enabled(entity) ? FAKE_TANK : entity.getItemBySlot(slot);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"
        ),
        require = 0
    )
    private static void wadditions$setitemInHand(LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (!wadditions$enabled(entity))
            entity.setItemInHand(hand, stack);
    }

    private static boolean wadditions$enabled(Entity entity) {
        return !WadditionsConfig.CREATIVE_AMMO_CONSUME.get() && ((Player)entity).isCreative();
    }
}
