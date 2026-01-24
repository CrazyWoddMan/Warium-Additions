package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record BlastFurnaceRecipe(ItemStack inputTop, ItemStack inputBottom, ItemStack output) {
    
    protected static BlastFurnaceRecipe recipe(Supplier<Item> inputTop, Supplier<Item> inputBottom, Supplier<Item> output) {
        return new BlastFurnaceRecipe(new ItemStack(inputTop.get()), new ItemStack(inputBottom.get()), new ItemStack(output.get()));
    }

    public static final List<BlastFurnaceRecipe> RECIPES = List.of(
        recipe(
            CrustyChunksModItems.IRON_DUST,
            CrustyChunksModItems.IRON_DUST,
            CrustyChunksModItems.STEEL_INGOT
        ),
        recipe(
            CrustyChunksModItems.ZINC_DUST,
            CrustyChunksModItems.COPPER_DUST,
            CrustyChunksModItems.BRASS_INGOT
        ),
        recipe(
            CrustyChunksModItems.COMPRESSED_ADVANCED_MIXTURE,
            CrustyChunksModItems.COMPRESSED_ADVANCED_MIXTURE,
            CrustyChunksModItems.ADVANCED_ALLOY_INGOT
        )
    );
}
