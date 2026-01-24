package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FlowingFluid;

public record RefineryRecipe(Supplier<Item> input, Supplier<FlowingFluid> output1, Supplier<FlowingFluid> output2, Supplier<FlowingFluid> output3, Supplier<FlowingFluid> output4) {
    public static final List<RefineryRecipe> RECIPES = List.of(
        new RefineryRecipe(
            CrustyChunksModItems.SHALE_OIL,
            CrustyChunksModFluids.OIL,
            CrustyChunksModFluids.DIESEL,
            CrustyChunksModFluids.KEROSENE,
            CrustyChunksModFluids.PETROLIUM
        )
    );
}
