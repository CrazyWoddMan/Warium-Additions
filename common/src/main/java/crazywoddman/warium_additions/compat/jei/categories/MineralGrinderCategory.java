package crazywoddman.warium_additions.compat.jei.categories;

import java.util.List;

import crazywoddman.warium_additions.compat.jei.recipes.MineralGrinderRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

public class MineralGrinderCategory implements IRecipeCategory<MineralGrinderRecipe> {
    public static final RecipeType<MineralGrinderRecipe> TYPE = RecipeType.create("crusty_chunks", CrustyChunksModItems.MINERAL_GRINDER.getId().getPath(), MineralGrinderRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable raw;
    private final IDrawable dust;

    public MineralGrinderCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(
            ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/mineral_grinder_gui.png"),
            20, 5, 120, 67
        ).setTextureSize(172, 166).build();
        this.icon = guiHelper.createDrawableItemLike(CrustyChunksModItems.MINERAL_GRINDER.get());
        this.raw = guiHelper.drawableBuilder(
            ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/rawgray.png"),
            0, 0, 16, 16
        ).setTextureSize(16, 16).build();
        this.dust = guiHelper.drawableBuilder(
            ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/graydust.png"),
            0, 0, 16, 16
        ).setTextureSize(16, 16).build();
    }

    @Override
    public RecipeType<MineralGrinderRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.crusty_chunks." + CrustyChunksModItems.MINERAL_GRINDER.getId().getPath());
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
    
    @SuppressWarnings("removal")
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MineralGrinderRecipe recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(21, 21)
        .addItemStacks(List.of(
            new ItemStack(CrustyChunksModItems.IRONGEAR.get()),
            new ItemStack(CrustyChunksModItems.STEEL_GEAR.get())
        ))
        .addTooltipCallback((view, tooltip) -> {
            if (view.getDisplayedIngredient(VanillaTypes.ITEM_STACK).orElse(ItemStack.EMPTY).is(new ItemStack(CrustyChunksModItems.IRONGEAR.get()).getItem()))
                tooltip.add(1, Component.literal("Loses 1 durability point").withStyle(ChatFormatting.GOLD));
            else
                tooltip.add(1, Component.literal("Not Consumed").withStyle(ChatFormatting.GOLD));
        });

        builder
        .addInputSlot(57, 3)
        .setBackground(raw, 0, 0)
        .addItemLike(recipe.input());

        builder
        .addOutputSlot(57, 48)
        .setBackground(dust, 0, 0)
        .addItemLike(recipe.output());

        builder
        .addOutputSlot(84, 48)
        .setBackground(dust, 0, 0)
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("25% Chance").withStyle(ChatFormatting.GOLD));
        })
        .addItemLike(recipe.chanceOutput());
    }
}
