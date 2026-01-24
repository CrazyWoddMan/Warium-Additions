package crazywoddman.warium_additions.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.recipes.AssemblyRecipe.AssemblyRecipeSerializer;
import crazywoddman.warium_additions.recipes.AssemblyRecipe.AssemblyRecipeType;
import crazywoddman.warium_additions.recipes.ColoringRecipe.ColoringRecipeSerializer;
import crazywoddman.warium_additions.recipes.ColoringRecipe.ColoringRecipeType;

public class WariumAdditionsRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = 
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WariumAdditions.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = 
        DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, WariumAdditions.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> WARIUM_RECIPE_SERIALIZERS = 
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "crusty_chunks");
    public static final DeferredRegister<RecipeType<?>> WARIUM_RECIPE_TYPES = 
        DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "crusty_chunks");

    public static final RegistryObject<RecipeSerializer<ColoringRecipe>> COLORING_RECIPE_SERIALIZER = 
        RECIPE_SERIALIZERS.register("coloring", ColoringRecipeSerializer::new);
    public static final RegistryObject<RecipeType<ColoringRecipe>> COLORING_RECIPE_TYPE = 
        RECIPE_TYPES.register("coloring", ColoringRecipeType::new);

    public static final RegistryObject<RecipeSerializer<AssemblyRecipe>> ASSEMBLY_RECIPE_SERIALIZER = 
        WARIUM_RECIPE_SERIALIZERS.register("assembly", AssemblyRecipeSerializer::new);
    public static final RegistryObject<RecipeType<AssemblyRecipe>> ASSEMBLY_RECIPE_TYPE = 
        WARIUM_RECIPE_TYPES.register("assembly", AssemblyRecipeType::new);
}
