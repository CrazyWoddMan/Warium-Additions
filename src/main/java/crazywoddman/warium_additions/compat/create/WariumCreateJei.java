package crazywoddman.warium_additions.compat.create;

import java.util.ArrayList;
import java.util.List;

import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import crazywoddman.warium_ponder_jei.data.WPJrecipes;
import crazywoddman.warium_ponder_jei.data.recipes.PaintToolRecipe;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class WariumCreateJei {

    public static void registerRecipes(IRecipeRegistration registration) {
        List<PaintToolRecipe> original = WPJrecipes.PAINT_TOOL.get(Minecraft.getInstance().level);
        List<DeployerApplicationRecipe> recipes = new ArrayList<>(original.size());
        ItemStack stack = CrustyChunksModItems.PAINT_TOOL.get().getDefaultInstance();
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("Fluid", 100);
        tag.putString("Color", "remove");
        Ingredient ingredient = Ingredient.of(stack);

        for (PaintToolRecipe recipe : original) {
            recipes.add(
                new ProcessingRecipeBuilder<>(DeployerApplicationRecipe::new, recipe.id)
                .require(recipe.ingredient)
                .require(ingredient)
                .output(recipe.clear)
                .build()
            );
        }
        registration.addRecipes(
            new RecipeType<>(ResourceLocation.fromNamespaceAndPath("create", "deploying"), DeployerApplicationRecipe.class),
            recipes
        );
    }
}
