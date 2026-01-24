package crazywoddman.warium_additions.compat.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.mcreator.crustychunks.init.CrustyChunksModItems;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.jei.StructuredRecipeCategory;
import crazywoddman.warium_additions.compat.jei.recipes.CentrifugeRecipe;

public class CentrifugeCategory extends StructuredRecipeCategory<CentrifugeRecipe> {
    public static final RecipeType<CentrifugeRecipe> TYPE = RecipeType.create("crusty_chunks", "centrifuge", CentrifugeRecipe.class);

    public CentrifugeCategory(IGuiHelper guiHelper) {
        super(
            guiHelper,
            TYPE,
            CrustyChunksModItems.ASSEMBLY_CENTRIFUGE_MIDDLE,
            guiHelper.createBlankDrawable(124, 80),
            1.7f,
            40, 55
        );
        addBlock(CrustyChunksModItems.GIANT_COIL, 0, 0, 0);
        addBlock(CrustyChunksModItems.ASSEMBLY_CENTRIFUGE_BOTTOM, 0, 1, 0, tooltip -> tooltip
            .add(1, Component.translatable(WariumAdditions.MODID + ".tooltip.rotation_required")
            .withStyle(ChatFormatting.GOLD))
        );
        addBlock(CrustyChunksModItems.ASSEMBLY_CENTRIFUGE_MIDDLE, 0, 2, 0);
        addBlock(CrustyChunksModItems.ASSEMBLY_CENTRIFUGE_TOP, 0, 3, 0);
        addBlock(CrustyChunksModItems.PRODUCTION_INPUT, -1, 2, 0);
        addBlock(CrustyChunksModItems.PRODUCTION_OUTPUT, 1, 2, 0);
    }

    @Override
    public RecipeType<CentrifugeRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(WariumAdditions.MODID + ".jei.category.centrifuge");
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CentrifugeRecipe recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(10, 26)
        .addItemStack(recipe.input())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(80, 26)
        .addItemStack(recipe.output1())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(98, 26)
        .addItemStack(recipe.output2())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(80, 44)
        .addItemStack(recipe.output3())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(98, 44)
        .addItemStack(recipe.output4())
        .setStandardSlotBackground();
    }
}
