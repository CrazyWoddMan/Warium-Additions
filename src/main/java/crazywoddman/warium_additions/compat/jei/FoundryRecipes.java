package crazywoddman.warium_additions.compat.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.item.ItemStack;

public record FoundryRecipes(ItemStack inputtop, ItemStack inputbottom, ItemStack output) {

    public ItemStack getInputTop() {
        return inputtop;
    }

    public ItemStack getInputBottom() {
        return inputbottom;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static final List<FoundryRecipes> RECIPES = List.of(
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("component_foundry_template"),
            WariumAdditionsUtil.getWariumStack("steel_ingot"),
            WariumAdditionsUtil.getWariumStack("cast_component")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("cylinder_foundry_template"),
            WariumAdditionsUtil.getWariumStack("steel_ingot"),
            WariumAdditionsUtil.getWariumStack("steel_cylinder")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("small_projectile_foundry_template"),
            WariumAdditionsUtil.getWariumStack("lead_nugget").copyWithCount(3),
            WariumAdditionsUtil.getWariumStack("small_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("medium_projectile_foundry_template"),
            WariumAdditionsUtil.getWariumStack("lead_nugget").copyWithCount(6),
            WariumAdditionsUtil.getWariumStack("medium_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("large_projectile_foundry_template"),
            WariumAdditionsUtil.getWariumStack("lead_ingot"),
            WariumAdditionsUtil.getWariumStack("large_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("extra_large_projectile_template"),
            WariumAdditionsUtil.getWariumStack("lead_ingot").copyWithCount(3),
            WariumAdditionsUtil.getWariumStack("extra_large_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("huge_projectile_foundry_template"),
            WariumAdditionsUtil.getWariumStack("lead_ingot").copyWithCount(6),
            WariumAdditionsUtil.getWariumStack("huge_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("small_barrel_template"),
            WariumAdditionsUtil.getWariumStack("steel_ingot"),
            WariumAdditionsUtil.getWariumStack("small_unbored_barrel")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("medium_barrel_template"),
            WariumAdditionsUtil.getWariumStack("steel_ingot").copyWithCount(3),
            WariumAdditionsUtil.getWariumStack("medium_unbored_barrel")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("large_barrel_template"),
            WariumAdditionsUtil.getWariumStack("steel_ingot").copyWithCount(4),
            WariumAdditionsUtil.getWariumStack("large_unbored_barrel")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("huge_barrel_foundry_template"),
            WariumAdditionsUtil.getWariumStack("steel_ingot").copyWithCount(6),
            WariumAdditionsUtil.getWariumStack("huge_unbored_barrel")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("small_cannon_foundry_template"),
            WariumAdditionsUtil.getWariumStack("steel_ingot").copyWithCount(8),
            WariumAdditionsUtil.getWariumStack("small_unbored_cannon_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("medium_cannon_foundry_template"),
            WariumAdditionsUtil.getWariumStack("steel_block"),
            WariumAdditionsUtil.getWariumStack("medium_unbored_cannon_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("large_cannon_foundry_template"),
            WariumAdditionsUtil.getWariumStack("steel_block"),
            WariumAdditionsUtil.getWariumStack("large_unbored_cannon_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("huge_cannon_foundry_template"),
            WariumAdditionsUtil.getWariumStack("steel_block").copyWithCount(2),
            WariumAdditionsUtil.getWariumStack("huge_unbored_cannon_projectile")
        ),
        new FoundryRecipes(
            WariumAdditionsUtil.getWariumStack("component_foundry_template"),
            WariumAdditionsUtil.getWariumStack("plutonium_ingot").copyWithCount(4),
            WariumAdditionsUtil.getWariumStack("plutonium_core")
        )
    );
}
