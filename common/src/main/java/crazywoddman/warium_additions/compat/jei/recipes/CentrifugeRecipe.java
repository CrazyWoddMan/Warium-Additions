package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public record CentrifugeRecipe(ItemStack input, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4) {
    
    protected static CentrifugeRecipe recipe(Supplier<Item> input, Supplier<Item> output) {
        return recipe(input, output, () -> Items.AIR);
    }

    protected static CentrifugeRecipe recipe(Supplier<Item> input, Supplier<Item> output1, Supplier<Item> output2) {
        return recipe(input, output1, output2, () -> Items.AIR);
    }

    protected static CentrifugeRecipe recipe(Supplier<Item> input, Supplier<Item> output1, Supplier<Item> output2, Supplier<Item> output3) {
        return recipe(input, output1, output2, output3, () -> Items.AIR);
    }

    protected static CentrifugeRecipe recipe(Supplier<Item> input, Supplier<Item> output1, Supplier<Item> output2, Supplier<Item> output3, Supplier<Item> output4) {
        return new CentrifugeRecipe(
            new ItemStack(input.get()),
            new ItemStack(output1.get()),
            new ItemStack(output2.get()),
            new ItemStack(output3.get()),
            new ItemStack(output4.get())
        );
    }
    public static final List<CentrifugeRecipe> RECIPES = List.of(
        recipe(
            CrustyChunksModItems.URANIUM_NEUTRAL_DUST,
            CrustyChunksModItems.URANIUM_ENRICHED_TINY_DUST,
            CrustyChunksModItems.URANIUM_DEPLETED_DUST
        ),
        recipe(
            CrustyChunksModItems.LITHIUM_DUST,
            CrustyChunksModItems.ENRICHED_LITHIUM_NUGGET
        ),
        recipe(
            CrustyChunksModItems.ALUMINATE_DUST,
            CrustyChunksModItems.FILTERED_ALUMINATE_DUST,
            () -> Items.RED_SAND
        ),
        recipe(
            CrustyChunksModItems.FILTERED_PYROCHLORE_DUST,
            CrustyChunksModItems.NIOBIUM_TINY_DUST,
            CrustyChunksModItems.NIOBIUM_TINY_DUST,
            CrustyChunksModItems.SULFUR
        )
    );
}
