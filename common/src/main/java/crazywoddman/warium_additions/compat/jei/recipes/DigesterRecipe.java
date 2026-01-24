package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public record DigesterRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4) {
    
    protected static DigesterRecipe recipe(Supplier<Item> input1, Supplier<Item> input2, Supplier<Item> output) {
        return recipe(input1, input2, output, () -> Items.AIR);
    }

    protected static DigesterRecipe recipe(Supplier<Item> input1, Supplier<Item> input2, Supplier<Item> output1, Supplier<Item> output2) {
        return recipe(input1, input2, output1, output2, () -> Items.AIR);
    }

    protected static DigesterRecipe recipe(Supplier<Item> input1, Supplier<Item> input2, Supplier<Item> output1, Supplier<Item> output2, Supplier<Item> output3) {
        return recipe(input1, input2, output1, output2, output3, () -> Items.AIR);
    }

    protected static DigesterRecipe recipe(Supplier<Item> input1, Supplier<Item> input2, Supplier<Item> output1, Supplier<Item> output2, Supplier<Item> output3, Supplier<Item> output4) {
        return new DigesterRecipe(
            new ItemStack(input1.get()),
            new ItemStack(input2.get()),
            new ItemStack(output1.get()),
            new ItemStack(output2.get()),
            new ItemStack(output3.get()),
            new ItemStack(output4.get())
        );
    }

    public static final List<DigesterRecipe> RECIPES = List.of(
        recipe(
            CrustyChunksModItems.BAUXITE_DUST,
            CrustyChunksModItems.NITRATE,
            CrustyChunksModItems.ALUMINATE_DUST,
            () -> Items.RED_SAND,
            () -> Items.SAND
        ),
        recipe(
            CrustyChunksModItems.PYROCHLORE_DUST,
            CrustyChunksModItems.NITRATE,
            CrustyChunksModItems.FILTERED_PYROCHLORE_DUST,
            () -> Items.QUARTZ
        )
    );
}
