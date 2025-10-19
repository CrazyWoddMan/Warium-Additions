package crazywoddman.warium_additions.jei;

import java.util.List;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

public class MineralGrinderCategory implements IRecipeCategory<MineralGrinderRecipes> {

    public static final RecipeType<MineralGrinderRecipes> TYPE = RecipeType.create("crusty_chunks", "mineral_grinder", MineralGrinderRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable raw;
    private final IDrawable dust;

    public MineralGrinderCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/mineral_grinder_gui.png"),
                20, 5, 120, 67
            )
            .setTextureSize(172, 166).build();
        this.icon = guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            WariumAdditionsUtil.getWariumStack("mineral_grinder")
        );
        this.raw = guiHelper
            .drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/rawgray.png"),
                0, 0, 16, 16
            )
            .setTextureSize(16, 16).build();
        this.dust = guiHelper
            .drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/graydust.png"),
                0, 0, 16, 16
            )
            .setTextureSize(16, 16).build();
    }

    @Override
    public RecipeType<MineralGrinderRecipes> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Mineral Grinder");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MineralGrinderRecipes recipe, IFocusGroup focuses) {
        builder.addInputSlot(21, 21)
            .addItemStacks(List.of(
                    WariumAdditionsUtil.getWariumStack("irongear"),
                    WariumAdditionsUtil.getWariumStack("steel_gear")
            ))
            .addTooltipCallback((view, tooltip) -> {
                if (view.getDisplayedIngredient(VanillaTypes.ITEM_STACK).orElse(ItemStack.EMPTY).is(WariumAdditionsUtil.getWariumStack("irongear").getItem()))
                    tooltip.add(1, Component.literal("Loses 1 durability point").withStyle(ChatFormatting.GOLD));
                else
                    tooltip.add(1, Component.literal("Not Consumed").withStyle(ChatFormatting.GOLD));
            }
        );

        builder
        .addInputSlot(57, 3)
        .addItemStack(recipe.getInput())
        .setBackground(raw, 0, 0);

        builder
        .addOutputSlot(57, 48)
        .addItemStack(recipe.getOutput())
        .setBackground(dust, 0, 0);

        builder
        .addOutputSlot(84, 48)
        .addItemStack(recipe.getChanceOutput())
        .setBackground(dust, 0, 0)
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("25% Chance").withStyle(ChatFormatting.GOLD));
        });
    }
}
