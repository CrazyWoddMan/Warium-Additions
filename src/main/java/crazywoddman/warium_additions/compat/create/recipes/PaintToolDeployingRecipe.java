package crazywoddman.warium_additions.compat.create.recipes;

import java.util.List;
import java.util.Optional;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerRecipeSearchEvent;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

import crazywoddman.warium_ponder_jei.data.WPJrecipes;
import crazywoddman.warium_ponder_jei.data.recipes.PaintToolRecipe;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class PaintToolDeployingRecipe extends DeployerApplicationRecipe {
    private final ItemStack tool;

    private PaintToolDeployingRecipe(ProcessingRecipeParams params, ItemStack tool) {
        super(params);
        this.tool = tool;
    }

    @Override
    public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults) {
        List<ItemStack> result = super.rollResults(rollableResults);
        CompoundTag tag = tool.getTag();

        if (tag != null) {
            int fluid = tag.getInt("Fluid");

            if (fluid <= 1) {
                tag.remove("Fluid");
                tag.remove("Color");
                tag.remove("Loaded");
            } else {
                tag.putInt("Fluid", fluid - 1);
            }
        }

        return result;
    }

    public static void register(DeployerRecipeSearchEvent event) {
        RecipeWrapper inventory = event.getInventory();
        ItemStack tool = inventory.getItem(1);

        if (!tool.is(CrustyChunksModItems.PAINT_TOOL.get()))
            return;

        CompoundTag toolTag = tool.getTag();

        if (toolTag == null || toolTag.getInt("Fluid") < 1)
            return;

        String color = toolTag.getString("Color");

        if (color.isEmpty() || color.equals("none"))
            return;

        DeployerBlockEntity deployer = event.getBlockEntity();
        ItemStack stack = inventory.getItem(0);
        Item item = stack.getItem();

        for (PaintToolRecipe recipe : WPJrecipes.PAINT_TOOL.get(deployer.getLevel())) {
            if (recipe.ingredient.test(stack)) {
                recipe.getResult(item, color).ifPresent(result -> {
                    ItemStack resultStack = result.getDefaultInstance();
                    CompoundTag tag = stack.getTag();

                    if (tag != null)
                        resultStack.setTag(tag.copy());

                    LogUtils.getLogger().info(resultStack.toString() + tag);

                    event.addRecipe(() -> Optional.of(
                        new ProcessingRecipeBuilder<>(
                            params -> new PaintToolDeployingRecipe(params, deployer.getPlayer().getMainHandItem()),
                            recipe.id
                        ).require(recipe.ingredient)
                         .require(Ingredient.of(tool))
                         .output(resultStack)
                         .toolNotConsumed()
                         .build()
                    ), 60);
                });
                return;
            }
        }
    }
}
