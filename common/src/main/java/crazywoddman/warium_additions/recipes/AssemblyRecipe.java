package crazywoddman.warium_additions.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class AssemblyRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient item;
    private final Ingredient process;
    private final ItemStack result;
    private final int runs;

    public AssemblyRecipe(ResourceLocation id, Ingredient item, Ingredient process, ItemStack result, int runs) {
        this.id = id;
        this.item = item;
        this.process = process;
        this.result = result;
        this.runs = runs;
    }

    public Ingredient getItem() {
        return item;
    }

    public Ingredient getProcess() {
        return process;
    }

    public ItemStack getResult() {
        return result;
    }

    public int getRuns() {
        return runs;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return item.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WariumAdditionsRecipes.ASSEMBLY_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return WariumAdditionsRecipes.ASSEMBLY_RECIPE_TYPE.get();
    }

    public static class AssemblyRecipeType implements RecipeType<AssemblyRecipe> {}

    public static class AssemblyRecipeSerializer implements RecipeSerializer<AssemblyRecipe> {

        @Override
        public AssemblyRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            try {
                Ingredient item = parseIngredient(json.get("item"));
                Ingredient process = parseIngredient(json.get("process"));
                JsonElement element = json.get("result");
                ItemStack result = element.isJsonPrimitive()
                    ? new ItemStack(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(element.getAsString())))
                    : CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, true);
                int runs = GsonHelper.getAsInt(json, "runs", 1);
                return new AssemblyRecipe(recipeId, item, process, result, runs);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse assembly recipe [" + recipeId + "]", e);
            }
        }

        private Ingredient parseIngredient(JsonElement element) {
            if (element.isJsonPrimitive()) {
                ResourceLocation key = ResourceLocation.tryParse(element.getAsString());
                return ForgeRegistries.ITEMS.containsKey(key)
                ? Ingredient.of(ForgeRegistries.ITEMS.getValue(key))
                : Ingredient.of(ItemTags.create(key));
            }
            
            return Ingredient.fromJson(element);
        }

        @Override
        public AssemblyRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient item = Ingredient.fromNetwork(buffer);
            Ingredient process = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            int runs = buffer.readInt();

            return new AssemblyRecipe(recipeId, item, process, result, runs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AssemblyRecipe recipe) {
            recipe.item.toNetwork(buffer);
            recipe.process.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeInt(recipe.runs);
        }
    }
}