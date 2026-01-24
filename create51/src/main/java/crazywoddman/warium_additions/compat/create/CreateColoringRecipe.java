package crazywoddman.warium_additions.compat.create;

import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.recipes.ColoringRecipe;
import crazywoddman.warium_additions.recipes.ColoringRecipeRegistry;


@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class CreateColoringRecipe {
    public static Recipe<?> createMixingRecipe(ResourceLocation recipeId, ColoringRecipe template, DyeColor color) {
        try {
            String colorName = color.getName();
            ResourceLocation outputLocation = ResourceLocation.parse(template.getResultItem().replace("{color}", colorName));

            if (!ForgeRegistries.ITEMS.containsKey(outputLocation))
                return null;

            Ingredient baseIngredient = ColoringRecipeRegistry.createIngredientExcludingColor(template.getBaseIngredientPattern(), color);

            if (baseIngredient.isEmpty())
                return null;
            
            FluidStack fluidStack = FluidStack.EMPTY;

            for (ColoringRecipe.FluidIngredient fluidIngredient : template.getFluidIngredients()) {
                fluidStack = fluidIngredient.createFluidStack(colorName);
                if (fluidStack.isEmpty())
                    return null;
            }

            return new ProcessingRecipeBuilder<>(MixingRecipe::new, recipeId)
                .withItemIngredients(baseIngredient)
                .withFluidIngredients(FluidIngredient.fromFluidStack(fluidStack))
                .output(new ItemStack(ForgeRegistries.ITEMS.getValue(outputLocation)))
                .duration(100)
                .build();
                
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mixing recipe [" + recipeId + "]", e);
        }
    }

    public static Recipe<?> createDeployingRecipe(ResourceLocation recipeId, ColoringRecipe template, DyeColor color) {
        try {
            String colorName = color.getName();
            ResourceLocation outputLocation = ResourceLocation.parse(template.getResultItem().replace("{color}", colorName));

            if (!ForgeRegistries.ITEMS.containsKey(outputLocation))
                return null;
            
            Ingredient baseIngredient = ColoringRecipeRegistry.createIngredientExcludingColor(template.getBaseIngredientPattern(), color);
            
            if (baseIngredient.isEmpty())
                return null;
            
            Ingredient toolIngredient = ColoringRecipeRegistry.createIngredientFromPattern(template.getItemIngredientPatterns().get(0), colorName);
            
            if (toolIngredient.isEmpty())
                return null;
    
            return new ProcessingRecipeBuilder<>(DeployerApplicationRecipe::new, recipeId)
                .withItemIngredients(baseIngredient, toolIngredient)
                .output(new ItemStack(ForgeRegistries.ITEMS.getValue(outputLocation)))
                .build();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to create deploying recipe [" + recipeId + "]", e);
        }
    }
}
