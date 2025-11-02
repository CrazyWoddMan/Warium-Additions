package crazywoddman.warium_additions.compat.jei;

import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import com.mojang.blaze3d.platform.InputConstants;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

public class ExtruderCategory implements IRecipeCategory<ExtruderRecipes> {

    public static final RecipeType<ExtruderRecipes> TYPE = RecipeType.create("crusty_chunks", "extruder", ExtruderRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private int ticks() {
        return (int)(System.currentTimeMillis() / 50L);
    }
    private boolean isDropoff() {
        return ((ticks() / 20) % 2 == 0);
    }

    public ExtruderCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(92, 27);
        this.icon = guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            WariumAdditionsUtil.getWariumStack("extruder")
        );
        this.slot = guiHelper.getSlotDrawable();
    }

    @Override
    public RecipeType<ExtruderRecipes> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Extruder");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ExtruderRecipes recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(1, 5)
        .addItemStacks(List.of(recipe.getInput().getItems()))
        .setBackground(slot, -1, -1);

        builder
        .addOutputSlot(75, 5)
        .addItemStack(recipe.getOutput())
        .setBackground(slot, -1, -1);
    }

    @Override
    public void draw(ExtruderRecipes recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.pose().pushPose();
        graphics.pose().translate(27, 0, 0);
        graphics.pose().scale(0.25f, 0.25f, 1.0f);
        graphics.blit(
            ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, isDropoff() ? "textures/jei/press_conveyor.png" : "textures/jei/press_dropoff.png"),
            0, 0,
            0, 0,
            152, 107,
            152, 107
        );
        graphics.pose().popPose();

        if (mouseX >= 27 && mouseX <= 49 && mouseY >= 2 && mouseY <= 26) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.addAll(WariumAdditionsUtil.getWariumStack("extruder").getTooltipLines(
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

        if (mouseX >= 50 && mouseX <= 64 && mouseY >= 2 && mouseY <= 23)
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                isDropoff() ? WariumAdditionsUtil.getWariumStack("conveyor") : WariumAdditionsUtil.getWariumStack("conveyor_dropoff"),
                (int)mouseX, (int)mouseY
            );
        
    }

    @Override
    public boolean handleInput(ExtruderRecipes recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (input.getType() == InputConstants.Type.MOUSE && input.getValue() == 0 && WariumJEI.jeiRuntime != null) {
            if (mouseX >= 27 && mouseX <= 49 && mouseY >= 2 && mouseY <= 26) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("extruder")
                        )
                ));
                
                return true;
            }

            if (mouseX >= 50 && mouseX <= 64 && mouseY >= 2 && mouseY <= 23) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            isDropoff() ? WariumAdditionsUtil.getWariumStack("conveyor") : WariumAdditionsUtil.getWariumStack("conveyor_dropoff")
                        )
                ));

                return true;
            }
        }

        return false;
    }
}
