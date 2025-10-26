package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.ArmorPeelerReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.BattleRifleReloadProcedure;
import net.mcreator.crustychunks.procedures.BoltReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.BreakActionReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.BreechRifleReloadProcedure;
import net.mcreator.crustychunks.procedures.BurstRifleReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.EradicationReloadProcedure;
import net.mcreator.crustychunks.procedures.FlamethrowerReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.FlareReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.GrenadeLauncherReloadProcedure;
import net.mcreator.crustychunks.procedures.LMGReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.MachineCarbineReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.PistolReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.RevolverReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.RifleReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.SMGReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.ShotgunReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.SingleShotReloadScriptProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

// TODO: finish
@Mixin({
    ShotgunReloadScriptProcedure.class
    // SingleShotReloadScriptProcedure.class,
    // GrenadeLauncherReloadProcedure.class,
    // BreakActionReloadScriptProcedure.class,
    // BreechRifleReloadProcedure.class,
    // EradicationReloadProcedure.class,
    // BoltReloadScriptProcedure.class,
    // PistolReloadScriptProcedure.class,
    // RifleReloadScriptProcedure.class,
    // SMGReloadScriptProcedure.class,
    // RevolverReloadScriptProcedure.class,
    // BurstRifleReloadScriptProcedure.class,
    // LMGReloadScriptProcedure.class,
    // BattleRifleReloadProcedure.class,
    // MachineCarbineReloadScriptProcedure.class,
    // FlamethrowerReloadScriptProcedure.class,
    // FlareReloadScriptProcedure.class,
    // ArmorPeelerReloadScriptProcedure.class
})
public class ShotgunReloadScriptProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"
        )
    )
    private static ItemStack redirectPut(LivingEntity entity) {
        ItemStack item = entity.getOffhandItem();

        if (entity instanceof Player player && player.isCreative() && item.isEmpty())
            return new ItemStack(CrustyChunksModItems.SHOTGUN_SHELL.get());
        
        return item;        
    }

    // @Redirect(
    //     method = "execute",
    //     slice = @Slice(from = @At(
    //         value = "INVOKE",
    //         target = "Lnet/minecraft/world/entity/LivingEntity;getOffhandItem()Lnet/minecraft/world/item/ItemStack;",
    //         ordinal = 1,
    //         remap = true
    //     )),
    //     at = @At(
    //         value = "INVOKE",
    //         target = "Lnet/minecraftforge/registries/RegistryObject;get()Ljava/lang/Object;",
    //         ordinal = 0
    //     ),
    //     remap = false
    // )
    // private static Object redirectShotgunShell(RegistryObject<?> object, LevelAccessor world, double x, double y, double z, Entity entity) {
    //     System.out.println("Test: " + object.getId());

    //     if (entity instanceof Player player && player.isCreative() && player.getOffhandItem().isEmpty())
    //         return Items.AIR;
        
    //     return object.get();
    // }

    // @Redirect(
    //     method = "execute",
    //     at = @At(
    //         value = "INVOKE",
    //         target = "Lnet/minecraft/nbt/CompoundTag;getDouble(Ljava.lang.String;)D"
    //     ),
    //     require = 0
    // )
    // private static double redirectGetBoolean(CompoundTag compound, String key, LevelAccessor world, double x, double y, double z, Entity entity) {
    //     return entity instanceof Player player && !player.isCreative() ? true : compound.getDouble(key);
    // }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"
        )
    )
    private static void redirectShrink(ItemStack stack, int amount, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!(entity instanceof Player player) || !player.isCreative())
            stack.shrink(amount);
    }
}