package crazywoddman.warium_additions.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;

public record BlastFurnaceRecipes(ItemStack inputtop, ItemStack inputbottom, ItemStack output) {

    public ItemStack getInputTop() {
        return inputtop;
    }

    public ItemStack getInputBottom() {
        return inputbottom;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static final List<BlastFurnaceRecipes> RECIPES = List.of(
        new BlastFurnaceRecipes(
            WariumAdditionsUtil.getWariumStack("iron_dust"),
            WariumAdditionsUtil.getWariumStack("iron_dust"),
            WariumAdditionsUtil.getWariumStack("steel_ingot")
        ),
        new BlastFurnaceRecipes(
            WariumAdditionsUtil.getWariumStack("zinc_dust"),
            WariumAdditionsUtil.getWariumStack("copper_dust"),
            WariumAdditionsUtil.getWariumStack("brass_ingot")
        )
    );
}
