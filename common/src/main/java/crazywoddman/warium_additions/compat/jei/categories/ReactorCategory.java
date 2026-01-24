package crazywoddman.warium_additions.compat.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.network.chat.Component;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.jei.StructuredRecipeCategory;
import crazywoddman.warium_additions.compat.jei.recipes.ReactorRecipe;

public class ReactorCategory extends StructuredRecipeCategory<ReactorRecipe> {
    public static final RecipeType<ReactorRecipe> TYPE = RecipeType.create("crusty_chunks", "reactor", ReactorRecipe.class);

    public ReactorCategory(IGuiHelper guiHelper) {
        super(
            guiHelper,
            TYPE,
            CrustyChunksModItems.BREEDER_REACTOR_INTERFACE,
            guiHelper.createBlankDrawable(150, 135),
            2f,
            59, 60
        );
        addBlock(CrustyChunksModItems.BREEDER_REACTOR_INTERFACE, 0, 1, 0);

        int[][] parts = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}};
        for (int[] offset : parts) {
            addBlock(CrustyChunksModItems.REACTION_CHAMBER, offset[0], 1, offset[1]);
            
            int[][] controlRods = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] pos : controlRods)
                addBlock(CrustyChunksModItems.CONTROL_ROD, offset[0] + pos[0], 1, offset[1] + pos[1]);

            int[][] casings = {{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
            for (int[] pos : casings)
                addBlock(CrustyChunksModItems.REACTOR_CASING, offset[0] + pos[0], 1, offset[1] + pos[1]);
        }

        addBlock(CrustyChunksModItems.REACTOR_CASING, -3, 0, 1);
        addBlock(CrustyChunksModItems.REACTOR_CASING, -1, 0, 3);
        addBlock(CrustyChunksModItems.CONTROL_ROD, 0, 0, 3);
        addBlock(CrustyChunksModItems.REACTOR_CASING, 1, 0, 3);
        addBlock(CrustyChunksModItems.CONTROL_ROD, 1, 0, 2);
        addBlock(CrustyChunksModItems.CONTROL_ROD, 2, 0, 1);
        addBlock(CrustyChunksModItems.REACTOR_CASING, 3, 0, 1);
        addBlock(CrustyChunksModItems.CONTROL_ROD, 3, 0, 0);
        addBlock(CrustyChunksModItems.REACTOR_CASING, 3, 0, -1);
        addBlock(CrustyChunksModItems.REACTOR_CASING, 1, 0, -3);
    }

    @Override
    public Component getTitle() {
        return Component.translatable(WariumAdditions.MODID + ".jei.category.reactor");
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ReactorRecipe recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(67, 1)
        .addItemStack(recipe.input())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(67, 118)
        .addItemStack(recipe.output())
        .setStandardSlotBackground();
    }
}
