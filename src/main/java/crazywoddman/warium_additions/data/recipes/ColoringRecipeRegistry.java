package crazywoddman.warium_additions.data.recipes;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.create.CreateColoringRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

// TODO: piece of shit needs redesign
@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class ColoringRecipeRegistry {
    public static final List<ColoringRecipe> CACHED = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerStarted(ServerStartedEvent event) {
        updateCacheAndGenerateRecipes(event.getServer());
    }

    public static void updateCacheAndGenerateRecipes(MinecraftServer server) {
        RecipeManager recipeManager = server.getRecipeManager();
        
        CACHED.clear();
        for (Recipe<?> recipe : recipeManager.getRecipes())
            if (recipe instanceof ColoringRecipe coloringRecipe)
                CACHED.add(coloringRecipe);

        if (CACHED.isEmpty())
            return;

        Collection<Recipe<?>> existingRecipes = recipeManager.getRecipes();
        List<Recipe<?>> allRecipes = new ArrayList<>(existingRecipes);
        int recipesAdded = 0;

        for (ColoringRecipe recipe : CACHED) {
            ResourceLocation soapRecipeId = ResourceLocation.fromNamespaceAndPath(
                recipe.getId().getNamespace(),
                recipe.getId().getPath() + "_cleaning"
            );

            Recipe<?> soapRecipe = createSoapCleanRecipe(soapRecipeId, recipe);
            if (soapRecipe != null) {
                allRecipes.add(soapRecipe);
                recipesAdded++;
            }
            
            if (!WariumAdditions.CREATE)
                continue;

            for (DyeColor color : DyeColor.values()) {
                if (!recipe.fluidIngredients.isEmpty()) {
                    ResourceLocation mixingRecipeId = ResourceLocation.fromNamespaceAndPath(
                        recipe.getId().getNamespace(),
                        recipe.getId().getPath() + "_" + color.getName() + "_mixing"
                    );

                    Recipe<?> mixingRecipe = CreateColoringRecipe.createMixingRecipe(mixingRecipeId, recipe, color);

                    if (mixingRecipe != null) {
                        allRecipes.add(mixingRecipe);
                        recipesAdded++;
                    }
                }

                if (!recipe.itemIngredients.isEmpty()) {
                    ResourceLocation deployingRecipeId = ResourceLocation.fromNamespaceAndPath(
                        recipe.getId().getNamespace(),
                        recipe.getId().getPath() + "_" + color.getName() + "_deploying"
                    );

                    Recipe<?> deployingRecipe = CreateColoringRecipe.createDeployingRecipe(deployingRecipeId, recipe, color);

                    if (deployingRecipe != null) {
                        allRecipes.add(deployingRecipe);
                        recipesAdded++;
                    }
                }
            }
        }

        if (recipesAdded > 0) {
            recipeManager.replaceRecipes(allRecipes);
            System.out.println("Generated " + recipesAdded + " recipes from " + CACHED.size() + " ColoringRecipes");

            if (WariumAdditions.SUPPLEMENTARIES)
                System.out.println("Supplementaries detected - crafting recipes with soap included");
        }
    }

    public static Ingredient createIngredientFromPattern(String pattern, String color) {
        if (pattern == null || pattern.isEmpty())
            return Ingredient.EMPTY;

        String processedPattern = pattern.replace("{color}", color);
        
        try {
            if (processedPattern.startsWith("#")) {
                String tagName = processedPattern.substring(1);
                ResourceLocation tagLocation = ResourceLocation.parse(tagName);
                return Ingredient.fromValues(Stream.of(
                    new Ingredient.TagValue(ItemTags.create(tagLocation))
                ));
            } else {
                ResourceLocation itemLocation = ResourceLocation.parse(processedPattern);
                if (ForgeRegistries.ITEMS.containsKey(itemLocation))
                    return Ingredient.of(new ItemStack(ForgeRegistries.ITEMS.getValue(itemLocation)));
            }
        } catch (Exception e) {
            System.err.println("Failed to create ingredient from pattern: " + pattern + " with color: " + color);
        }
        
        return Ingredient.EMPTY;
    }

    public static Ingredient createIngredientExcludingColor(String pattern, DyeColor excludeColor) {

        if (pattern == null || pattern.isEmpty())
            return Ingredient.EMPTY;
            
        try {
            if (pattern.startsWith("#")) {
                Ingredient tagIngredient = Ingredient.fromValues(Stream.of(
                    new Ingredient.TagValue(ItemTags.create(ResourceLocation.parse(pattern.substring(1))))
                ));
                
                List<ItemStack> validItems = new ArrayList<>();
                
                for (ItemStack stack : tagIngredient.getItems()) {
                    String itemPath = ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath();

                    if (!itemPath.contains(excludeColor.getName()))
                        validItems.add(stack);
                }
                
                if (validItems.isEmpty())
                    return tagIngredient;
                
                return Ingredient.of(validItems.toArray(new ItemStack[0]));
            } else {
                ResourceLocation itemLocation = ResourceLocation.parse(pattern);
                if (ForgeRegistries.ITEMS.containsKey(itemLocation))
                    return Ingredient.of(new ItemStack(ForgeRegistries.ITEMS.getValue(itemLocation)));
            }
        } catch (Exception e) {
            System.err.println("Failed to create ingredient excluding color from pattern: " + pattern);
        }
        
        return Ingredient.EMPTY;
    }

    private static Recipe<?> createSoapCleanRecipe(ResourceLocation id, ColoringRecipe recipe) {
        if (!WariumAdditions.SUPPLEMENTARIES)
            return null;

        try {
            if (!recipe.basePattern.startsWith("#"))
                return null;
            
            ResourceLocation tagLocation = ResourceLocation.parse(recipe.basePattern.substring(1));
            ITag<Item> items = ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(tagLocation));
            
            if (items.isEmpty())
                return null;
            
            Item result = items.iterator().next();
            List<ItemStack> validIngredients = new ArrayList<>();

            for (Item item : items)
                if (item != result)
                    validIngredients.add(new ItemStack(item));
            
            if (validIngredients.isEmpty())
                return null;

            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(Ingredient.of(validIngredients.toArray(new ItemStack[0])));
            ResourceLocation soapLocation = ResourceLocation.fromNamespaceAndPath("supplementaries", "soap");

            if (!ForgeRegistries.ITEMS.containsKey(soapLocation))
                return null;
            
            ingredients.add(Ingredient.of(new ItemStack(ForgeRegistries.ITEMS.getValue(soapLocation))));

            return new ShapelessRecipe(
                id,
                "misc",
                CraftingBookCategory.MISC,
                new ItemStack(result),
                NonNullList.of(Ingredient.EMPTY, ingredients.toArray(new Ingredient[0]))
            );

        } catch (Exception e) {
            System.err.println("Failed to create soap cleaning recipe " + id + ": " + e.getMessage());
            return null;
        }
    }

    public static List<String> getPatternsForSoapBlacklist() {
        List<String> patterns = new ArrayList<>();

        for (ColoringRecipe recipe : CACHED)
            if (!recipe.resultPattern.isEmpty()) {
                String pattern = recipe.resultPattern
                    .replace("_{color}_", "")
                    .replace("_{color}", "")
                    .replace("{color}_", "")
                    .replace("{color}", "");
                patterns.add(pattern);
            }
        
        return patterns;
    }
}
