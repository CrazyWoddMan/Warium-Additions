package crazywoddman.warium_additions.mixins.immersiveengineering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import blusunrize.immersiveengineering.common.crafting.serializers.HammerCrushingRecipeSerializer;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@Restriction(require = @Condition("immersiveengineering"))
@Mixin(HammerCrushingRecipeSerializer.class)
public class HammerCrushingRecipeSerializerMixin {

    @Redirect(
        method = "readFromJson",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/crafting/Ingredient;of([Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/crafting/Ingredient;"
        )
    )
    private Ingredient redirectHammerIngredient(ItemLike[] items) {
        return Ingredient.of(ItemTags.create(ResourceLocation.fromNamespaceAndPath("immersiveengineering", "tools/hammers")));
    }
}
