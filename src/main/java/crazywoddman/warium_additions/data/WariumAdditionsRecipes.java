package crazywoddman.warium_additions.data;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.data.recipes.ColoringRecipe;

public class WariumAdditionsRecipes {
    private static class RecipeTypeRegister {
        private final DeferredRegister<RecipeType<?>> register;

        private RecipeTypeRegister(String modid) {
            this.register = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, modid);
        }

        private void register(IEventBus bus) {
            register.register(bus);
        }

        private <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(String name) {
            return this.register.register(name, () -> new RecipeType<T>(){});
        }
    }

    public static void register(IEventBus bus) {
        WariumAdditionsRecipes.SERIALIZERS.register(bus);
        WariumAdditionsRecipes.TYPES.register(bus);
    }
    
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WariumAdditions.MODID);
    private static final RecipeTypeRegister TYPES = new RecipeTypeRegister(WariumAdditions.MODID);

    public static final RegistryObject<ColoringRecipe.Serializer> COLORING_SERIALIZER =
        SERIALIZERS.register("coloring", ColoringRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<ColoringRecipe>> COLORING_TYPE = TYPES.register("coloring");
}
