package crazywoddman.warium_additions.compat.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.ChatFormatting;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.jei.StructuredRecipeCategory;
import crazywoddman.warium_additions.compat.jei.recipes.FoundryRecipe;

public class FoundryCategory extends StructuredRecipeCategory<FoundryRecipe> {
    public static final RecipeType<FoundryRecipe> TYPE = RecipeType.create("crusty_chunks", CrustyChunksModItems.FOUNDRY.getId().getPath(), FoundryRecipe.class);
    private final IDrawable input;
    private final IDrawable output;

    public FoundryCategory(IGuiHelper guiHelper) {
        super(
            guiHelper,
            TYPE,
            CrustyChunksModItems.FOUNDRY,
            guiHelper.drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/foundry_gui.png"),
                6, 7, 164, 72
            ).setTextureSize(176, 166).build(),
            2f,
            120, 35
        );
        this.input = guiHelper.drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/grayingot.png"),
                0, 0, 16, 16
            ).setTextureSize(16, 16).build();
        this.output = guiHelper.drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/graycyllinder.png"),
                0, 0, 16, 16
            ).setTextureSize(16, 16).build();
        firebox();
        addBlock(CrustyChunksModItems.FOUNDRY, 0, 1, 0);
    }
    
    @SuppressWarnings("removal")
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FoundryRecipe recipe, IFocusGroup focuses) {
        builder
            .addInputSlot(1, 1)
            .addItemStack(recipe.template())
            .addTooltipCallback((view, tooltip) -> {
                tooltip.add(1, Component.translatable(WariumAdditions.MODID + ".tooltip.not_consumed").withStyle(ChatFormatting.GOLD));
            });

        builder
            .addInputSlot(1, 28)
            .addItemStack(recipe.input())
            .setBackground(input, 0, 0);

        builder
            .addOutputSlot(1, 55)
            .addItemStack(recipe.output())
            .setBackground(output, 0, 0);
    }
}
