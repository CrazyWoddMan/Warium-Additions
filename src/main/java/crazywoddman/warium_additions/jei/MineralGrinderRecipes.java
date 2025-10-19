package crazywoddman.warium_additions.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public record MineralGrinderRecipes(ItemStack input, ItemStack output, ItemStack chanceoutput) {

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ItemStack getChanceOutput() {
        return chanceoutput;
    }

    public static final List<MineralGrinderRecipes> RECIPES = List.of(
        new MineralGrinderRecipes(
            WariumAdditionsUtil.getWariumStack("raw_uranium"),
            WariumAdditionsUtil.getWariumStack("uranium_neutraltiny_dust"),
            WariumAdditionsUtil.getWariumStack("uranium_depleted_tiny_dust")
        ),
        new MineralGrinderRecipes(
            WariumAdditionsUtil.getWariumStack("raw_zinc"),
            WariumAdditionsUtil.getWariumStack("zinc_dust"),
            WariumAdditionsUtil.getWariumStack("zinc_dust")
        ),
        new MineralGrinderRecipes(
            WariumAdditionsUtil.getWariumStack("raw_lead"),
            WariumAdditionsUtil.getWariumStack("lead_dust"),
            WariumAdditionsUtil.getWariumStack("lead_dust")
        ),
        new MineralGrinderRecipes(
            WariumAdditionsUtil.getWariumStack("raw_nickel"),
            WariumAdditionsUtil.getWariumStack("nickel_dust"),
            WariumAdditionsUtil.getWariumStack("nickel_dust")
        ),
        new MineralGrinderRecipes(
            WariumAdditionsUtil.getWariumStack("raw_beryllium"),
            WariumAdditionsUtil.getWariumStack("beryllium_dust"),
            WariumAdditionsUtil.getWariumStack("beryllium_dust")
        ),
        new MineralGrinderRecipes(
            WariumAdditionsUtil.getWariumStack("raw_lithium"),
            WariumAdditionsUtil.getWariumStack("lithium_dust"),
            WariumAdditionsUtil.getWariumStack("lithium_dust")
        ),
        new MineralGrinderRecipes(
            new ItemStack(Items.RAW_GOLD),
            WariumAdditionsUtil.getWariumStack("gold_dust"),
            WariumAdditionsUtil.getWariumStack("gold_dust")
        ),
        new MineralGrinderRecipes(
            new ItemStack(Items.RAW_IRON),
            WariumAdditionsUtil.getWariumStack("iron_dust"),
            WariumAdditionsUtil.getWariumStack("iron_dust")
        ),
        new MineralGrinderRecipes(
            new ItemStack(Items.RAW_COPPER),
            WariumAdditionsUtil.getWariumStack("copper_dust"),
            WariumAdditionsUtil.getWariumStack("copper_dust")
        ),
        new MineralGrinderRecipes(
            new ItemStack(Items.COBBLESTONE),
            new ItemStack(Items.GRAVEL),
            new ItemStack(Items.FLINT)
        ),
        new MineralGrinderRecipes(
            new ItemStack(Items.GRAVEL),
            new ItemStack(Items.SAND),
            new ItemStack(Items.FLINT)
        ),
        new MineralGrinderRecipes(
            WariumAdditionsUtil.getWariumStack("bauxite"),
            WariumAdditionsUtil.getWariumStack("bauxite_dust"),
            new ItemStack(Items.SAND)
        )
    );
}
