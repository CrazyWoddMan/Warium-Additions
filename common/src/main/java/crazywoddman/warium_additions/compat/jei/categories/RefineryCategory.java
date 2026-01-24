package crazywoddman.warium_additions.compat.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.handlers.IGlobalGuiHandler;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.network.chat.Component;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.ChatFormatting;
import crazywoddman.warium_additions.compat.jei.StructuredRecipeCategory;
import crazywoddman.warium_additions.compat.jei.recipes.RefineryRecipe;

public class RefineryCategory extends StructuredRecipeCategory<RefineryRecipe> implements IGlobalGuiHandler {
    public static final RecipeType<RefineryRecipe> TYPE = RecipeType.create("crusty_chunks", "refinery", RefineryRecipe.class);

    public RefineryCategory(IGuiHelper guiHelper) {
        super(
            guiHelper,
            TYPE,
            CrustyChunksModItems.REFINERY,
            guiHelper.createBlankDrawable(63, 121),
            2.1f,
            25, 91
        );
        firebox();
        addBlock(CrustyChunksModItems.REFINERY, 0, 1, 0);
        addBlock(CrustyChunksModItems.REFINERY_TOWER, 0, 2, 0);
        addBlock(CrustyChunksModItems.REFINERY_TOWER, 0, 3, 0);
        addBlock(CrustyChunksModItems.REFINERY_TOWER, 0, 4, 0);
        addBlock(CrustyChunksModItems.REFINERY_TOWER, 0, 5, 0);
    }
    
    @SuppressWarnings("removal")
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RefineryRecipe recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(1, 82)
        .setStandardSlotBackground()
        .addItemLike(recipe.input().get());

        builder
        .addOutputSlot(1, 64)
        .addFluidStack(recipe.output1().get(), 250)
        .setStandardSlotBackground()
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Can be collected with bucket").withStyle(ChatFormatting.GOLD));
        });

        builder
        .addOutputSlot(1, 46)
        .addFluidStack(recipe.output2().get(), 250)
        .setStandardSlotBackground()
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Can be collected with bucket").withStyle(ChatFormatting.GOLD));
        });
        
        builder
        .addOutputSlot(1, 28)
        .addFluidStack(recipe.output3().get(), 250)
        .setStandardSlotBackground()
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Can be collected with bucket").withStyle(ChatFormatting.GOLD));
        });

        builder
        .addOutputSlot(1, 10)
        .addFluidStack(recipe.output4().get(), 250)
        .setStandardSlotBackground()
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Can be collected with bucket").withStyle(ChatFormatting.GOLD));
        });
    }
}
