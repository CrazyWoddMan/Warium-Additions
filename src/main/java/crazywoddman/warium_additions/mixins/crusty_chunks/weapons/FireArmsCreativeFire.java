package crazywoddman.warium_additions.mixins.crusty_chunks.weapons;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import crazywoddman.warium_additions.config.Config;

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
public class FireArmsCreativeFire {
    private static final ItemStack FAKE_TANK = new ItemStack(CrustyChunksModItems.FLAME_THROWER_TANK_CHESTPLATE.get());
    
    static {
        FAKE_TANK.getOrCreateTag().putInt("Fluid", 1000);
    }

    private static boolean enabled() {
        return !Config.SERVER.creativeAmmoConsume.get();
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        ),
        require = 0
    )
    private static void redirectConsumeAmmo(CompoundTag tag, String key, double value, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!enabled() || !((Player)entity).isCreative())
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
    private static void redirectConsumeAmmo(CompoundTag tag, String key, boolean value, LevelAccessor world, double x, double y, double z, Entity entity, ItemStack stack) {
        if (!enabled() || (!((Player)entity).isCreative() && !stack.is(CrustyChunksModItems.FLAME_THROWER_ANIMATED.get())))
            tag.putBoolean(key, value);
    }

    @Redirect(
        method = "execute",
        slice = @Slice(from = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/item/ItemEntity;setPickUpDelay(I)V"
        )),
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
        ),
        require = 0
    )
    private static boolean redirectCasingDrop(ServerLevel level, Entity casing, LevelAccessor world, double x, double y, double z, Entity entity) {
        return (enabled() && ((Player)entity).isCreative()) ? false : level.addFreshEntity(casing);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
        ),
        require = 0
    )
    private static ItemStack redirectTankGet(LivingEntity entity, EquipmentSlot slot) {
        return (enabled() && ((Player)entity).isCreative()) ? FAKE_TANK : entity.getItemBySlot(slot);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"
        ),
        require = 0
    )
    private static void redirectPeelerReplace(LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (!enabled() || !((Player)entity).isCreative())
            entity.setItemInHand(hand, stack);
    }
}
