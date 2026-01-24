package crazywoddman.warium_additions.compat.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import crazywoddman.warium_additions.compat.jei.StructuredRecipeCategory;

public class ThermalFurnaceCategory extends StructuredRecipeCategory<SmeltingRecipe> {
    public static final RecipeType<SmeltingRecipe> TYPE = RecipeType.create("crusty_chunks", CrustyChunksModItems.THERMAL_FURNACE.getId().getPath(), SmeltingRecipe.class);

    public ThermalFurnaceCategory(IGuiHelper guiHelper) {
        super(
            guiHelper,
            TYPE,
            CrustyChunksModItems.THERMAL_FURNACE,
            guiHelper.drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/ab_gui.png"),
                6, 3, 164, 80
            ).setTextureSize(176, 166).build(),
            1.7f,
            70, 55
        );
        firebox();
        addBlock(CrustyChunksModItems.THERMAL_FURNACE, 0, 1, 0);
        addBlock(CrustyChunksModItems.BLAST_FUNNEL, 0, 2, 0);
        addBlock(CrustyChunksModItems.BLAST_FUNNEL, 0, 3, 0);
        addBlock(CrustyChunksModItems.BLAST_FUNNEL, 0, 4, 0);
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SmeltingRecipe recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(19, 32)
        .addIngredients(recipe.getIngredients().get(0));

        builder
        .addOutputSlot(127, 32)
        .addItemStack(recipe.getResultItem(getRegistryAccess()));
    }
}
