package crazywoddman.warium_additions.compat.jei;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.client.Minecraft;

public record ThermalFurnaceRecipes(ItemStack input, ItemStack output) {

    public static List<ThermalFurnaceRecipes> getAllVanillaFurnaceRecipes() {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        if (manager == null) return List.of();

        return manager.getAllRecipesFor(RecipeType.SMELTING).stream()
            .filter(r -> r instanceof SmeltingRecipe)
            .map(r -> (SmeltingRecipe) r)
            .map(r -> new ThermalFurnaceRecipes(
                r.getIngredients().get(0).getItems()[0],
                r.getResultItem(Minecraft.getInstance().level.registryAccess())
            ))
            .toList();
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static final List<ThermalFurnaceRecipes> RECIPES = getAllVanillaFurnaceRecipes();
}
