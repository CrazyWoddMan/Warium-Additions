package crazywoddman.warium_additions.data.recipes;

import com.google.gson.JsonObject;

import crazywoddman.warium_additions.data.WariumAdditionsRecipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ColoringRecipe extends AbstractRecipe {
    public final String basePattern;
    public final List<String> itemIngredients;
    public final List<FluidIngredient> fluidIngredients;
    public final String resultPattern;

    public ColoringRecipe(
        ResourceLocation id,
        String baseIngredient, 
        List<String> itemIngredients,
        List<FluidIngredient> fluidIngredients,
        String resultItem
    ) {
        super(id, WariumAdditionsRecipes.COLORING_SERIALIZER, WariumAdditionsRecipes.COLORING_TYPE);
        this.basePattern = baseIngredient;
        this.itemIngredients = itemIngredients != null ? itemIngredients : new ArrayList<>();
        this.fluidIngredients = fluidIngredients != null ? fluidIngredients : new ArrayList<>();
        this.resultPattern = resultItem;
    }

    public static class FluidIngredient {
        private final String fluidPattern;
        private final int amount;

        public FluidIngredient(String fluidPattern, int amount) {
            this.fluidPattern = fluidPattern;
            this.amount = amount;
        }

        public String getFluidPattern() {
            return fluidPattern;
        }

        public int getAmount() {
            return amount;
        }

        public FluidStack createFluidStack(String color) {
            try {
                String processedPattern = fluidPattern.replace("{color}", color);
                ResourceLocation fluidLocation = ResourceLocation.parse(processedPattern);
                if (ForgeRegistries.FLUIDS.containsKey(fluidLocation))
                    return new FluidStack(ForgeRegistries.FLUIDS.getValue(fluidLocation), amount);
            } catch (Exception e) {
                System.err.println("Failed to create fluid stack from pattern: " + fluidPattern + " with color: " + color);
            }

            return FluidStack.EMPTY;
        }
    }

    public static class Serializer implements RecipeSerializer<ColoringRecipe> {
        
        @Override
        public ColoringRecipe fromJson(ResourceLocation id, JsonObject json) {
            String baseIngredient = "";
            List<String> itemIngredients = new ArrayList<>();
            List<FluidIngredient> fluidIngredients = new ArrayList<>();

            if (json.has("ingredients")) {
                JsonArray ingredientsArray = GsonHelper.getAsJsonArray(json, "ingredients");
                
                for (int i = 0; i < ingredientsArray.size(); i++) {
                    JsonElement element = ingredientsArray.get(i);
                    JsonObject ingredientObj = element.getAsJsonObject();
                    
                    if (ingredientObj.has("fluid")) {
                        String fluidPattern = GsonHelper.getAsString(ingredientObj, "fluid");
                        int amount = GsonHelper.getAsInt(ingredientObj, "amount", 1000);
                        fluidIngredients.add(new FluidIngredient(fluidPattern, amount));
                    } else {
                        String ingredientPattern = "";

                        if (ingredientObj.has("item"))
                            ingredientPattern = GsonHelper.getAsString(ingredientObj, "item");
                        else if (ingredientObj.has("tag"))
                            ingredientPattern = "#" + GsonHelper.getAsString(ingredientObj, "tag");
                        
                        if (!ingredientPattern.isEmpty()) {
                            if (i == 0)
                                baseIngredient = ingredientPattern;
                            else
                                itemIngredients.add(ingredientPattern);
                        }
                    }
                }
            }

            String resultItem = "";

            if (json.has("result"))
                resultItem = GsonHelper.getAsString(
                    GsonHelper.getAsJsonObject(json, "result"),
                    "item"
                );

            return new ColoringRecipe(id, baseIngredient, itemIngredients, fluidIngredients, resultItem);
        }

        @Override
        public ColoringRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            String baseIngredient = buffer.readUtf();
            int itemCount = buffer.readVarInt();
            List<String> itemIngredients = new ArrayList<>();

            for (int i = 0; i < itemCount; i++)
                itemIngredients.add(buffer.readUtf());
            
            int fluidCount = buffer.readVarInt();
            List<FluidIngredient> fluidIngredients = new ArrayList<>();

            for (int i = 0; i < fluidCount; i++) {
                String fluidPattern = buffer.readUtf();
                int amount = buffer.readVarInt();
                fluidIngredients.add(new FluidIngredient(fluidPattern, amount));
            }
            
            String resultItem = buffer.readUtf();

            return new ColoringRecipe(id, baseIngredient, itemIngredients, fluidIngredients, resultItem);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ColoringRecipe recipe) {
            buffer.writeUtf(recipe.basePattern);
            buffer.writeVarInt(recipe.itemIngredients.size());

            for (String pattern : recipe.itemIngredients)
                buffer.writeUtf(pattern);
            
            buffer.writeVarInt(recipe.fluidIngredients.size());

            for (FluidIngredient fluidIngredient : recipe.fluidIngredients) {
                buffer.writeUtf(fluidIngredient.fluidPattern);
                buffer.writeVarInt(fluidIngredient.amount);
            }
            
            buffer.writeUtf(recipe.resultPattern);
        }
    }
}
