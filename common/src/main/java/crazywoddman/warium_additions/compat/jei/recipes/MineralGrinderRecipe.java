package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public record MineralGrinderRecipe(Item input, Item output, Item chanceOutput) {
    protected static MineralGrinderRecipe recipe(Supplier<Item> input, Supplier<Item> output, Supplier<Item> chanceOutput) {
        return new MineralGrinderRecipe(input.get(), output.get(), chanceOutput.get());
    }

    public static final List<MineralGrinderRecipe> RECIPES = List.of(
        recipe(
            CrustyChunksModItems.RAW_URANIUM,
            CrustyChunksModItems.URANIUM_NEUTRAL_DUST,
            CrustyChunksModItems.URANIUM_DEPLETED_TINY_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_ZINC,
            CrustyChunksModItems.ZINC_DUST,
            CrustyChunksModItems.ZINC_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_LEAD,
            CrustyChunksModItems.LEAD_DUST,
            CrustyChunksModItems.LEAD_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_NICKEL,
            CrustyChunksModItems.NICKEL_DUST,
            CrustyChunksModItems.NICKEL_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_BERYLLIUM,
            CrustyChunksModItems.BERYLLIUM_DUST,
            CrustyChunksModItems.BERYLLIUM_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_LITHIUM,
            CrustyChunksModItems.LITHIUM_DUST,
            CrustyChunksModItems.LITHIUM_DUST
        ),
        recipe(
            () -> Items.RAW_GOLD,
            CrustyChunksModItems.GOLD_DUST,
            CrustyChunksModItems.GOLD_DUST
        ),
        recipe(
            () -> Items.RAW_IRON,
            CrustyChunksModItems.IRON_DUST,
            CrustyChunksModItems.IRON_DUST
        ),
        recipe(
            () -> Items.RAW_COPPER,
            CrustyChunksModItems.COPPER_DUST,
            CrustyChunksModItems.COPPER_DUST
        ),
        recipe(
            () -> Items.COBBLESTONE,
            () -> Items.GRAVEL,
            () -> Items.FLINT
        ),
        recipe(
            () -> Items.GRAVEL,
            () -> Items.SAND,
            () -> Items.FLINT
        ),
        recipe(
            CrustyChunksModItems.BAUXITE,
            CrustyChunksModItems.BAUXITE_DUST,
            () -> Items.SAND
        )
    );
}
