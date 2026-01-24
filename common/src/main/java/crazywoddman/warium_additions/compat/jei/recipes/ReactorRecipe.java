package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record ReactorRecipe(ItemStack input, ItemStack output) {

    protected static ReactorRecipe recipe(Supplier<Item> input, Supplier<Item> output) {
        return new ReactorRecipe(new ItemStack(input.get()), new ItemStack(output.get()));
    }

    public static final List<ReactorRecipe> RECIPES = List.of(
        recipe(CrustyChunksModItems.URANIUM_ENRICHED_DUST, CrustyChunksModItems.PLUTONIUM_NUGGET),
        recipe(CrustyChunksModItems.ENRICHED_LITHIUM_INGOT, CrustyChunksModItems.TINY_LITHIUM_DEUTERIDE)
    );
}
