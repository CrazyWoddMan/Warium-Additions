package crazywoddman.warium_additions.compat.create;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.recipes.ColoringRecipe;


@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class CreateColoringRecipe {
    public static Recipe<?> createMixingRecipe(ResourceLocation recipeId, ColoringRecipe template, DyeColor color) {
        return null;
    }

    public static Recipe<?> createDeployingRecipe(ResourceLocation recipeId, ColoringRecipe template, DyeColor color) {
        return null;
    }
}
