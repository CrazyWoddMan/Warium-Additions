package crazywoddman.warium_additions.compat.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public record OilRefineryRecipes(ItemStack input, FluidStack output1, FluidStack output2, FluidStack output3, FluidStack output4) {

    public ItemStack getInput() {
        return input;
    }

    public FluidStack getOutput1() {
        return output1;
    }

    public FluidStack getOutput2() {
        return output2;
    }

    public FluidStack getOutput3() {
        return output3;
    }

    public FluidStack getOutput4() {
        return output4;
    }

    public static final List<OilRefineryRecipes> RECIPES = List.of(
        new OilRefineryRecipes(
            WariumAdditionsUtil.getWariumStack("shale_oil"),
            WariumAdditionsUtil.getWariumFluid("oil", 1000),
            WariumAdditionsUtil.getWariumFluid("diesel", 1000),
            WariumAdditionsUtil.getWariumFluid("kerosene", 1000),
            WariumAdditionsUtil.getWariumFluid("petrolium", 1000)
        )
    );
}
