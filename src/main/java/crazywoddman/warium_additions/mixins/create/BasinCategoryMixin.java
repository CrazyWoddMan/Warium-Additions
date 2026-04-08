package crazywoddman.warium_additions.mixins.create;

import com.simibubi.create.AllTags;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.content.processing.recipe.HeatCondition;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition("create"))
@Mixin(remap = false, value = BasinCategory.class)
public class BasinCategoryMixin {
    
    @Inject(
        method = "setRecipe",
        at = @At("TAIL")
    )
    private void addCustomBlazeCakeSlot(IRecipeLayoutBuilder builder, BasinRecipe recipe, IFocusGroup focuses, CallbackInfo ci) {
        HeatCondition requiredHeat = recipe.getRequiredHeat();

        if (!requiredHeat.testBlazeBurner(HeatLevel.KINDLED))
            builder
            .addSlot(RecipeIngredientRole.CATALYST, 153, 81)
            .addIngredients(Ingredient.of(AllTags.AllItemTags.BLAZE_BURNER_FUEL_SPECIAL.tag));
    }
}
