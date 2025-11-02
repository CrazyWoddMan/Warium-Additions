package crazywoddman.warium_additions.compat.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;

public record CutterRecipes(ItemStack input, ItemStack output) {

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
    
    public static final List<CutterRecipes> RECIPES = List.of(
        new CutterRecipes(
            WariumAdditionsUtil.getWariumStack("cast_component"),
            WariumAdditionsUtil.getWariumStack("steel_gear")
        ),
        new CutterRecipes(
            WariumAdditionsUtil.getWariumStack("bent_component"),
            WariumAdditionsUtil.getWariumStack("cut_component")
        ),
        new CutterRecipes(
            WariumAdditionsUtil.getWariumStack("copper_plate"),
            WariumAdditionsUtil.getWariumStack("copper_wire")
        ),
        new CutterRecipes(
            WariumAdditionsUtil.getWariumStack("steelplate"),
            WariumAdditionsUtil.getWariumStack("steel_wire")
        )
    );
}
