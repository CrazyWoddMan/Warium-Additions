package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record FabricatorRecipe(ItemStack input, ItemStack output) {
    
    protected static FabricatorRecipe recipe(Supplier<Item> input, Supplier<Item> output) {
        return new FabricatorRecipe(
            new ItemStack(input.get()),
            new ItemStack(output.get())
        );
    }

    public static final List<FabricatorRecipe> MECHANICAL_RECIPES = List.of(
        recipe(
            CrustyChunksModItems.ADVANCED_ALLOY_INGOT,
            CrustyChunksModItems.ADVANCED_ALLOY_COMPONENT
        ),
        recipe(
            CrustyChunksModItems.ALUMINUM_BLOCK,
            CrustyChunksModItems.PRECISION_COMPONENT
        )
    );

    public static final List<FabricatorRecipe> CIRCUIT_RECIPES = List.of(
        recipe(
            CrustyChunksModItems.UNFABRICATED_TECH_COMPONENT,
            CrustyChunksModItems.TECH_COMPONENT
        )
    );
}
