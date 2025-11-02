package crazywoddman.warium_additions.compat.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public record ExtruderRecipes(Ingredient input, ItemStack output) {

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
    
    public static final List<ExtruderRecipes> RECIPES = List.of(
        new ExtruderRecipes(
            Ingredient.of(WariumAdditionsUtil.getWariumStack("cast_component")),
            WariumAdditionsUtil.getWariumStack("steel_gear")
        ),
        new ExtruderRecipes(
            WariumAdditionsUtil.getIngredientTag("minecraft", "planks"),
            WariumAdditionsUtil.getWariumStack("wood_component")
        ),
        new ExtruderRecipes(
            Ingredient.of(WariumAdditionsUtil.getWariumStack("steel_ingot")),
            WariumAdditionsUtil.getWariumStack("steel_wire")
        ),
        new ExtruderRecipes(
            Ingredient.of(WariumAdditionsUtil.getWariumStack("steel_wire")),
            WariumAdditionsUtil.getWariumStack("steel_spring")
        ),
        new ExtruderRecipes(
            Ingredient.of(Items.COPPER_INGOT),
            WariumAdditionsUtil.getWariumStack("copper_wire")
        ),
        new ExtruderRecipes(
            Ingredient.of(WariumAdditionsUtil.getWariumStack("copper_wire")),
            WariumAdditionsUtil.getWariumStack("copper_coil")
        )
    );
}
