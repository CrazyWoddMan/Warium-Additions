package crazywoddman.warium_additions.jei;

import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import com.mojang.blaze3d.platform.InputConstants;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

public class CrusherCategory implements IRecipeCategory<MineralGrinderRecipes> {

    public static final RecipeType<MineralGrinderRecipes> TYPE = RecipeType.create("crusty_chunks", "crusher", MineralGrinderRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;

    public CrusherCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(97, 24);
        this.icon = guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            WariumAdditionsUtil.getWariumStack("crusher")
        );
        this.slot = guiHelper.getSlotDrawable();
    }

    @Override
    public RecipeType<MineralGrinderRecipes> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Crusher");
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
        builder
        .addInputSlot(1, 4)
        .addItemStack(recipe.getInput())
        .setBackground(slot, -1, -1);

        builder
        .addOutputSlot(60, 4)
        .addItemStack(recipe.getOutput());

        builder
        .addOutputSlot(81, 4)
        .addItemStack(recipe.getChanceOutput())
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("25% Chance").withStyle(ChatFormatting.GOLD));
        });
    }

    @Override
    public void draw(MineralGrinderRecipes recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.pose().pushPose();
        graphics.pose().translate(27, 0, 0);
        graphics.pose().scale(0.25f, 0.25f, 1.0f);
        graphics.blit(
            ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "textures/jei/crusher.png"),
            0, 0,
            0, 0,
            88, 96,
            88, 96
        );
        graphics.pose().popPose();

        if (mouseX >= 27 && mouseX <= 48 && mouseY >= 0 && mouseY <= 24) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.addAll(WariumAdditionsUtil.getWariumStack("crusher").getTooltipLines(
                Minecraft.getInstance().player,
                Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL)
            );
            tooltip.add(1, Component
                .literal("Requires rotation force")
                .withStyle(ChatFormatting.GOLD)
            );
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                tooltip.stream().map(Component::getVisualOrderText).toList(),
                (int)mouseX, (int)mouseY
            );
        }
    }

    @Override
    public boolean handleInput(MineralGrinderRecipes recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (input.getType() == InputConstants.Type.MOUSE && input.getValue() == 0 && WariumJEI.jeiRuntime != null) {
            if (mouseX >= 27 && mouseX <= 48 && mouseY >= 0 && mouseY <= 24) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("crusher")
                        )
                ));

                return true;
            }
        }

        return false;
    }
}
