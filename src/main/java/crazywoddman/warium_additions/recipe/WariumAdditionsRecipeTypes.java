package crazywoddman.warium_additions.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import crazywoddman.warium_additions.WariumAdditions;

import java.util.function.Supplier;

public enum WariumAdditionsRecipeTypes {
    
    COLORING(() -> new ColoringRecipe.Serializer());

    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    private final RegistryObject<RecipeType<?>> typeObject;

    WariumAdditionsRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = name().toLowerCase();
        this.id = ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, name);
        
        this.serializerObject = Recipes.SERIALIZER_REGISTER.register(name, serializerSupplier);
        this.typeObject = Recipes.TYPE_REGISTER.register(name, 
            () -> new RecipeType<ColoringRecipe>() {
                @Override
                public String toString() {
                    return id.toString();
                }
            });
    }

    public static void register() {}

    public RecipeSerializer<?> getSerializer() {
        return serializerObject.get();
    }

    public RecipeType<?> getType() {
        return typeObject.get();
    }

    public static class Recipes {
        public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = 
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WariumAdditions.MODID);
            
        public static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = 
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, WariumAdditions.MODID);
    }
}
