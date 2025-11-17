package crazywoddman.warium_additions.mixins.curios;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.compat.curios.CuriosUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.FlameThrowerFireScriptProcedure;
import net.mcreator.crustychunks.procedures.ToxicCloudEntityProcedure;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Restriction(require = @Condition("curios"))
@Mixin({FlameThrowerFireScriptProcedure.class, ToxicCloudEntityProcedure.class})
public class CuriosItemsProceduresMixin {
    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
        )
    )
    private static ItemStack injectReturn(LivingEntity entity, EquipmentSlot slot) {
        boolean isMask = slot == EquipmentSlot.HEAD;
        Item reference = (isMask ? CrustyChunksModItems.GAS_MASK_HELMET : CrustyChunksModItems.FLAME_THROWER_TANK_CHESTPLATE).get();
        ItemStack stack = entity.getItemBySlot(slot);
        
        return stack.getItem() == reference ? stack : CuriosUtil.getItem(entity, isMask ? "head" : "back", reference).orElse(stack);
    }
}
