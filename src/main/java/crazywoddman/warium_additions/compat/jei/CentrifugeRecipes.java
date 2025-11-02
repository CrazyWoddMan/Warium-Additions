package crazywoddman.warium_additions.compat.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;

public record CentrifugeRecipes(ItemStack input, ItemStack outputtop, ItemStack outputbottom) {

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutputTop() {
        return outputtop;
    }

    public ItemStack getOutputBottom() {
        return outputbottom;
    }

    public static final List<CentrifugeRecipes> RECIPES = List.of(
        new CentrifugeRecipes(
            WariumAdditionsUtil.getWariumStack("uranium_neutral_dust"),
            WariumAdditionsUtil.getWariumStack("uranium_enriched_tiny_dust"),
            WariumAdditionsUtil.getWariumStack("uranium_depleted_dust")
        ),
        new CentrifugeRecipes(
            WariumAdditionsUtil.getWariumStack("lithium_dust"),
            WariumAdditionsUtil.getWariumStack("enriched_lithium_nugget"),
            ItemStack.EMPTY
        )
    );
}
