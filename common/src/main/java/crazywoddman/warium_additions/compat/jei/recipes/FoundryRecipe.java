package crazywoddman.warium_additions.compat.jei.recipes;

import java.util.List;
import java.util.function.Supplier;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record FoundryRecipe(ItemStack template, ItemStack input, ItemStack output) {

    protected static FoundryRecipe recipe(Supplier<Item> template, Supplier<Item> input, Supplier<Item> output) {
        return new FoundryRecipe(new ItemStack(template.get()), new ItemStack(input.get()), new ItemStack(output.get()));
    }

    protected static FoundryRecipe recipe(Supplier<Item> template, Supplier<Item> input, int amount, Supplier<Item> output) {
        return new FoundryRecipe(new ItemStack(template.get()), new ItemStack(input.get(), amount), new ItemStack(output.get()));
    }

    public static final List<FoundryRecipe> RECIPES = List.of(
        recipe(
            CrustyChunksModItems.COMPONENT_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT,
            CrustyChunksModItems.CAST_COMPONENT
        ),
        recipe(
            CrustyChunksModItems.CYLINDER_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT,
            CrustyChunksModItems.STEEL_CYLINDER
        ),
        recipe(
            CrustyChunksModItems.SMALL_PROJECTILE_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.LEAD_NUGGET, 3,
            CrustyChunksModItems.SMALL_PROJECTILE
        ),
        recipe(
            CrustyChunksModItems.MEDIUM_PROJECTILE_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.LEAD_NUGGET, 6,
            CrustyChunksModItems.MEDIUM_PROJECTILE
        ),
        recipe(
            CrustyChunksModItems.LARGE_PROJECTILE_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.LEAD_INGOT,
            CrustyChunksModItems.LARGE_PROJECTILE
        ),
        recipe(
            CrustyChunksModItems.EXTRA_LARGE_PROJECTILE_TEMPLATE,
            CrustyChunksModItems.LEAD_INGOT, 3,
            CrustyChunksModItems.EXTRA_LARGE_PROJECTILE
        ),
        recipe(
            CrustyChunksModItems.HUGE_PROJECTILE_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.LEAD_INGOT, 6,
            CrustyChunksModItems.HUGE_PROJECTILE
        ),
        recipe(
            CrustyChunksModItems.SMALL_BARREL_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT,
            CrustyChunksModItems.SMALL_UNBORED_BARREL
        ),
        recipe(
            CrustyChunksModItems.MEDIUM_BARREL_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT, 3,
            CrustyChunksModItems.MEDIUM_UNBORED_BARREL
        ),
        recipe(
            CrustyChunksModItems.LARGE_BARREL_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT, 4,
            CrustyChunksModItems.LARGE_UNBORED_BARREL
        ),
        recipe(
            CrustyChunksModItems.HUGE_BARREL_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT, 6,
            CrustyChunksModItems.HUGE_UNBORED_BARREL
        ),
        recipe(
            CrustyChunksModItems.SMALL_CANNON_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT, 8,
            CrustyChunksModItems.SMALL_UNBORED_CANNON_BARREL
        ),
        recipe(
            CrustyChunksModItems.MEDIUM_CANNON_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT,
            CrustyChunksModItems.MEDIUM_UNBORED_CANNON_BARREL
        ),
        recipe(
            CrustyChunksModItems.LARGE_CANNON_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT,
            CrustyChunksModItems.LARGE_UNBORED_CANNON_BARREL
        ),
        recipe(
            CrustyChunksModItems.HUGE_CANNON_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.STEEL_INGOT, 2,
            CrustyChunksModItems.HUGE_UNBORED_CANNON_BARREL
        ),
        recipe(
            CrustyChunksModItems.COMPONENT_FOUNDRY_TEMPLATE,
            CrustyChunksModItems.PLUTONIUM_INGOT, 4,
            CrustyChunksModItems.PLUTONIUM_CORE
        )
    );
}
