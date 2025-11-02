package crazywoddman.warium_additions.compat.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public record PressRecipes(Ingredient input, ItemStack output) {

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
    
    public static final List<PressRecipes> RECIPES = List.of(
        new PressRecipes(
            WariumAdditionsUtil.getIngredientTag("forge", "ingots/steel"),
            WariumAdditionsUtil.getWariumStack("steelplate")
        ),
        new PressRecipes(
            Ingredient.of(WariumAdditionsUtil.getWariumStack("steelplate")),
            WariumAdditionsUtil.getWariumStack("bent_component")
        ),
        new PressRecipes(
            Ingredient.of(WariumAdditionsUtil.getWariumStack("bent_component")),
            WariumAdditionsUtil.getWariumStack("steel_tube")
        ),
        new PressRecipes(
            Ingredient.of(Items.COPPER_INGOT),
            WariumAdditionsUtil.getWariumStack("copper_plate")
        ),
        new PressRecipes(
            WariumAdditionsUtil.getIngredientTag("forge", "ingots/brass"),
            WariumAdditionsUtil.getWariumStack("brass_plate")
        ),
        new PressRecipes(
            WariumAdditionsUtil.getIngredientTag("forge", "ingots/aluminum"),
            WariumAdditionsUtil.getWariumStack("aluminum_plate")
        )
    );
}
