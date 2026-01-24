package crazywoddman.warium_additions.compat.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import crazywoddman.warium_additions.compat.jei.StructuredRecipeCategory;
import crazywoddman.warium_additions.compat.jei.recipes.BlastFurnaceRecipe;

public class BlastFurnaceCategory extends StructuredRecipeCategory<BlastFurnaceRecipe> {
    public static final RecipeType<BlastFurnaceRecipe> TYPE = RecipeType.create("crusty_chunks", CrustyChunksModItems.BLAST_FURNACE.getId().getPath(), BlastFurnaceRecipe.class);

    public BlastFurnaceCategory(IGuiHelper guiHelper) {
        super(
            guiHelper,
            TYPE,
            CrustyChunksModItems.BLAST_FURNACE,
            guiHelper.drawableBuilder(
            ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/blast_furnace_gui.png"),
                6, 3, 164, 80
            ).setTextureSize(176, 166).build(),
            1.7f,
            70, 55
        );
        firebox();
        addBlock(CrustyChunksModItems.BLAST_FURNACE, 0, 1, 0);
        addBlock(CrustyChunksModItems.BLAST_FUNNEL, 0, 2, 0);
        addBlock(CrustyChunksModItems.BLAST_FUNNEL, 0, 3, 0);
        addBlock(CrustyChunksModItems.BLAST_FUNNEL, 0, 4, 0);
    }

    @Override
    public RecipeType<BlastFurnaceRecipe> getRecipeType() {
        return TYPE;
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BlastFurnaceRecipe recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(1, 32)
        .addItemStack(recipe.inputTop());

        builder
        .addInputSlot(1, 59)
        .addItemStack(recipe.inputBottom());

        builder
        .addOutputSlot(145, 32)
        .addItemStack(recipe.output());
    }
}
