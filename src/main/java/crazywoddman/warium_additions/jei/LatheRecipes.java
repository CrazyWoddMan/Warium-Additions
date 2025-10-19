package crazywoddman.warium_additions.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;

public record LatheRecipes(ItemStack input, ItemStack output) {

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static final List<LatheRecipes> RECIPES = List.of(
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("cast_component"),
            WariumAdditionsUtil.getWariumStack("bored_component")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("steel_cylinder"),
            WariumAdditionsUtil.getWariumStack("steel_tube")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("brass_ingot"),
            WariumAdditionsUtil.getWariumStack("brass_fitting")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("large_projectile"),
            WariumAdditionsUtil.getWariumStack("hollowed_large_projectile")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("extra_large_projectile"),
            WariumAdditionsUtil.getWariumStack("hollowed_extra_large_projectile")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("huge_projectile"),
            WariumAdditionsUtil.getWariumStack("hollowed_huge_projectile")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("small_unbored_barrel"),
            WariumAdditionsUtil.getWariumStack("small_bored_barrel")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("medium_unbored_barrel"),
            WariumAdditionsUtil.getWariumStack("medium_bored_barrel")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("large_unbored_barrel"),
            WariumAdditionsUtil.getWariumStack("large_bored_barrel")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("huge_unbored_barrel"),
            WariumAdditionsUtil.getWariumStack("huge_bored_barrel")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("small_unbored_cannon_barrel"),
            WariumAdditionsUtil.getWariumStack("autocannon_barrel")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("medium_unbored_cannon_barrel"),
            WariumAdditionsUtil.getWariumStack("autocannon_barrel")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("large_unbored_cannon_barrel"),
            WariumAdditionsUtil.getWariumStack("battle_cannon_barrel")
        ),
        new LatheRecipes(
            WariumAdditionsUtil.getWariumStack("huge_unbored_cannon_barrel"),
            WariumAdditionsUtil.getWariumStack("artillery_barrel")
        )
    );
}
