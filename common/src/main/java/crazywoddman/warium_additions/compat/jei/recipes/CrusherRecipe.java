package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public record CrusherRecipe(ItemStack input, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4) {
    
    protected static CrusherRecipe recipe(Supplier<Item> input, Supplier<Item> output) {
        return recipe(input, output, () -> Items.AIR);
    }

    protected static CrusherRecipe recipe(Supplier<Item> input, Supplier<Item> output1, Supplier<Item> output2) {
        return recipe(input, output1, output2, () -> Items.AIR);
    }

    protected static CrusherRecipe recipe(Supplier<Item> input, Supplier<Item> output1, Supplier<Item> output2, Supplier<Item> output3) {
        return recipe(input, output1, output2, output3, () -> Items.AIR);
    }

    protected static CrusherRecipe recipe(Supplier<Item> input, Supplier<Item> output1, Supplier<Item> output2, Supplier<Item> output3, Supplier<Item> output4) {
        return new CrusherRecipe(
            new ItemStack(input.get()),
            new ItemStack(output1.get()),
            new ItemStack(output2.get()),
            new ItemStack(output3.get()),
            new ItemStack(output4.get())
        );
    }

    public static final List<CrusherRecipe> RECIPES = List.of(
        recipe(
            CrustyChunksModItems.RAW_ZINC,
            CrustyChunksModItems.ZINC_DUST,
            CrustyChunksModItems.ZINC_DUST,
            CrustyChunksModItems.NICKEL_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_LEAD,
            CrustyChunksModItems.LEAD_DUST,
            CrustyChunksModItems.LEAD_DUST,
            CrustyChunksModItems.NICKEL_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_NICKEL,
            CrustyChunksModItems.NICKEL_DUST,
            CrustyChunksModItems.NICKEL_DUST,
            CrustyChunksModItems.LEAD_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_URANIUM,
            CrustyChunksModItems.URANIUM_NEUTRALTINY_DUST,
            CrustyChunksModItems.URANIUM_NEUTRALTINY_DUST,
            CrustyChunksModItems.URANIUM_DEPLETED_TINY_DUST,
            CrustyChunksModItems.LEAD_DUST
        ),
        recipe(
            CrustyChunksModItems.RAW_BERYLLIUM,
            CrustyChunksModItems.BERYLLIUM_DUST,
            CrustyChunksModItems.BERYLLIUM_DUST,
            CrustyChunksModItems.LITHIUM_NUGGET
        ),
        recipe(
            CrustyChunksModItems.RAW_LITHIUM,
            CrustyChunksModItems.LITHIUM_DUST,
            CrustyChunksModItems.LITHIUM_DUST,
            CrustyChunksModItems.LITHIUM_NUGGET
        ),
        recipe(
            CrustyChunksModItems.PYROCHLORE,
            CrustyChunksModItems.PYROCHLORE_DUST,
            CrustyChunksModItems.PYROCHLORE_DUST,
            CrustyChunksModItems.SULFUR,
            CrustyChunksModItems.NICKEL_DUST
        ),
        recipe(
            CrustyChunksModItems.BAUXITE,
            CrustyChunksModItems.BAUXITE_DUST,
            () -> Items.SAND,
            CrustyChunksModItems.BAUXITE_DUST
        ),
        recipe(
            () -> Items.RAW_GOLD,
            CrustyChunksModItems.GOLD_DUST,
            CrustyChunksModItems.GOLD_DUST,
            () -> Items.GOLD_NUGGET
        ),
        recipe(
            () -> Items.RAW_IRON,
            CrustyChunksModItems.IRON_DUST,
            CrustyChunksModItems.IRON_DUST,
            CrustyChunksModItems.NICKEL_DUST
        ),
        recipe(
            () -> Items.RAW_COPPER,
            CrustyChunksModItems.COPPER_DUST,
            CrustyChunksModItems.COPPER_DUST,
            CrustyChunksModItems.SULFUR
        ),
        recipe(
            () -> Items.COBBLESTONE,
            () -> Items.GRAVEL,
            () -> Items.FLINT,
            () -> Items.SAND,
            CrustyChunksModItems.NITRATE
        ),
        recipe(
            () -> Items.GRAVEL,
            () -> Items.SAND,
            () -> Items.FLINT,
            () -> Items.CLAY_BALL,
            CrustyChunksModItems.NITRATE
        ),
        recipe(
            () -> Items.DIRT,
            () -> Items.CLAY_BALL,
            CrustyChunksModItems.NITRATE,
            () -> Items.SAND,
            () -> Items.BONE_MEAL
        )
    );
}
