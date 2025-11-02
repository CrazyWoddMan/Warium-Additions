package crazywoddman.warium_additions.compat.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;

public record ReactorRecipes(ItemStack input, ItemStack output) {

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static final List<ReactorRecipes> RECIPES = List.of(
        new ReactorRecipes(
            WariumAdditionsUtil.getWariumStack("uranium_enriched_dust"),
            WariumAdditionsUtil.getWariumStack("plutonium_nugget")
        ),
        new ReactorRecipes(
            WariumAdditionsUtil.getWariumStack("enriched_lithium_ingot"),
            WariumAdditionsUtil.getWariumStack("tiny_lithium_deuteride")
        )
    );
}
