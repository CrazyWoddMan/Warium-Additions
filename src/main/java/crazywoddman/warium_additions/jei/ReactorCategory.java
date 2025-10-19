package crazywoddman.warium_additions.jei;

import java.util.Random;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.mojang.blaze3d.platform.InputConstants;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

public class ReactorCategory implements IRecipeCategory<ReactorRecipes> {

    public static final RecipeType<ReactorRecipes> TYPE = RecipeType.create("crusty_chunks", "reactor", ReactorRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private int ticks() {
        return (int)(System.currentTimeMillis() / 50L);
    }

    public ReactorCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(150, 125);
        this.icon = guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            WariumAdditionsUtil.getWariumStack("breeder_reactor_interface")
        );
        this.slot = guiHelper.getSlotDrawable();
    }

    @Override
    public RecipeType<ReactorRecipes> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Reactor");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ReactorRecipes recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(67, 1)
        .addItemStack(recipe.getInput())
        .setBackground(slot, -1, -1);

        builder
        .addOutputSlot(67, 108)
        .addItemStack(recipe.getOutput())
        .setBackground(slot, -1, -1);
    }

    @Override
    public void draw(ReactorRecipes recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.pose().pushPose();
        graphics.pose().translate(0, 16, 0);
        graphics.pose().scale(0.25f, 0.25f, 1.0f);
        graphics.blit(
            ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "textures/jei/reactor_" + (ticks() / 20) % 4 + ".png"),
            0, 0,
            0, 0,
            600, 352,
            600, 352
        );
        graphics.pose().popPose();
        graphics.drawString(
            Minecraft.getInstance().font,
            "400s",
            126,
            117,
            0x8B8B8B,
            false
        );
        
        for (int i = 0; i < 7; i++) {
            int life = 20 + i * 3;
            int age = (ticks() + i * 7 + 74) % life;

            if (age > life)
                continue;

            Random rand = new Random(i * 14881488L + ticks() / life);
            graphics.pose().pushPose();
            graphics.pose().translate(
                72 + (float)Math.cos(rand.nextFloat() * (float)Math.PI * 2) * 5 + rand.nextFloat() * 5,
                35 + (float)Math.sin(rand.nextFloat() * (float)Math.PI * 2) * 5 + rand.nextFloat() * 5,
                0
            );
            graphics.pose().scale(
                0.6f - 0.6f * age / (float)life,
                0.6f - 0.6f * age / (float)life,
                1.0f
            );
            graphics.blit(
                ResourceLocation.fromNamespaceAndPath("minecraft", "textures/particle/soul_fire_flame.png"),
                0, 0,
                0, 0,
                8, 8,
                8, 8
            );
            graphics.pose().popPose();
        }
        int[] x = {40, 40, 104, 104};
        int[] y = {40, 20, 40, 20};
        for (int e = 0; e < 4; e++)
            for (int i = 0; i < 7; i++) {
                int life = 20 + i * 3;
                int age = (ticks() + i * 7 + 57) % life;
                if (age > life) continue;
                Random rand = new Random(i * 14881488L + (long)e * 123 + ticks() / life);
                graphics.pose().pushPose();
                graphics.pose().translate(
                    x[e] + (float)Math.cos(rand.nextFloat() * (float)Math.PI * 2) * 5 + rand.nextFloat() * 5,
                    y[e] + (float)Math.sin(rand.nextFloat() * (float)Math.PI * 2) * 5 + rand.nextFloat() * 5,
                    0
                );
                graphics.pose().scale(
                    0.6f - 0.6f * age / (float)life,
                    0.6f - 0.6f * age / (float)life,
                    1.0f
                );
                graphics.blit(
                    ResourceLocation.fromNamespaceAndPath("minecraft", "textures/particle/soul_fire_flame.png"),
                    0, 0,
                    0, 0,
                    8, 8,
                    8, 8
                );
                graphics.pose().popPose();
            }
        
        if (mouseX >= 64 && mouseX <= 86 && mouseY >= 35 && mouseY <= 41)
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("breeder_reactor_interface"),
                (int)mouseX, (int)mouseY
            );

        else if (
            (mouseX >= 34 && mouseX <= 55 && mouseY >= 24 && mouseY <= 32) ||
            (mouseX >= 94 && mouseX <= 115 && mouseY >= 24 && mouseY <= 32) ||
            (mouseX >= 34 && mouseX <= 55 && mouseY >= 43 && mouseY <= 50) ||
            (mouseX >= 94 && mouseX <= 115 && mouseY >= 43 && mouseY <= 50)
        ) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.addAll((WariumAdditionsUtil.getWariumStack("empty_fuel_rods")).getTooltipLines(
                Minecraft.getInstance().player,
                Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL)
            );
            tooltip.add(1, Component
                .literal("Must contain at least 1 Uranium Fuel Rod")
                .withStyle(ChatFormatting.GOLD)
            );
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                tooltip.stream().map(Component::getVisualOrderText).toList(),
                (int)mouseX, (int)mouseY
            );
        }

        else if (mouseX >= 60 && mouseX <= 90 && mouseY >= 78 && mouseY <= 103)
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("breeder_reactor_port"),
                (int)mouseX, (int)mouseY
            );
        
        else if (
            (mouseX >= 15 && mouseX <= 32 && mouseY >= 33 && mouseY <= 43) ||
            (mouseX >= 117 && mouseX <= 134 && mouseY >= 33 && mouseY <= 43) ||
            (mouseX >= 25 && mouseX <= 64 && mouseY >= 23 && mouseY <= 36) ||
            (mouseX >= 85 && mouseX <= 125 && mouseY >= 23 && mouseY <= 36) ||
            (mouseX >= 25 && mouseX <= 64 && mouseY >= 41 && mouseY <= 53) ||
            (mouseX >= 85 && mouseX <= 125 && mouseY >= 41 && mouseY <= 53) ||
            (mouseX >= 15 && mouseX <= 29 && mouseY >= 51 && mouseY <= 96) ||
            (mouseX >= 120 && mouseX <= 134 && mouseY >= 51 && mouseY <= 96) ||
            (mouseX >= 60 && mouseX <= 89 && mouseY >= 54 && mouseY <= 97)
        )
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("control_rod"),
                (int)mouseX, (int)mouseY
            );
        
        else if (
            (mouseX >= 0 && mouseX <= 152 && mouseY >= 16 && mouseY <= 101) &&
            !(mouseX >= 0 && mouseX <= 22 && mouseY >= 16 && mouseY <= 22) &&
            !(mouseX >= 128 && mouseX <= 150 && mouseY >= 16 && mouseY <= 22) &&
            !(mouseX >= 0 && mouseX <= 22 && mouseY >= 95 && mouseY <= 101) &&
            !(mouseX >= 128 && mouseX <= 150 && mouseY >= 95 && mouseY <= 101)
        )
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("reactor_casing"),
                (int)mouseX, (int)mouseY
            );
        
    }

    @Override
    public boolean handleInput(ReactorRecipes recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (input.getType() == InputConstants.Type.MOUSE && input.getValue() == 0 && WariumJEI.jeiRuntime != null) {
            if (mouseX >= 64 && mouseX <= 86 && mouseY >= 35 && mouseY <= 41) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("breeder_reactor_interface")
                        )
                ));

                return true;
            }

            else if (
                (mouseX >= 34 && mouseX <= 55 && mouseY >= 24 && mouseY <= 32) ||
                (mouseX >= 94 && mouseX <= 115 && mouseY >= 24 && mouseY <= 32) ||
                (mouseX >= 34 && mouseX <= 55 && mouseY >= 43 && mouseY <= 50) ||
                (mouseX >= 94 && mouseX <= 115 && mouseY >= 43 && mouseY <= 50)
            ) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("empty_fuel_rods")
                        )
                ));

                return true;
            }
            else if (mouseX >= 60 && mouseX <= 90 && mouseY >= 78 && mouseY <= 103) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("breeder_reactor_port")
                        )
                ));

                return true;
            }
            else if (
                (mouseX >= 15 && mouseX <= 32 && mouseY >= 33 && mouseY <= 43) ||
                (mouseX >= 117 && mouseX <= 134 && mouseY >= 33 && mouseY <= 43) ||
                (mouseX >= 25 && mouseX <= 64 && mouseY >= 23 && mouseY <= 36) ||
                (mouseX >= 85 && mouseX <= 125 && mouseY >= 23 && mouseY <= 36) ||
                (mouseX >= 25 && mouseX <= 64 && mouseY >= 41 && mouseY <= 53) ||
                (mouseX >= 85 && mouseX <= 125 && mouseY >= 41 && mouseY <= 53) ||
                (mouseX >= 15 && mouseX <= 29 && mouseY >= 51 && mouseY <= 96) ||
                (mouseX >= 120 && mouseX <= 134 && mouseY >= 51 && mouseY <= 96) ||
                (mouseX >= 60 && mouseX <= 89 && mouseY >= 54 && mouseY <= 97)
            ) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("control_rod")
                        )
                ));

                return true;
            }
            else if (
                (mouseX >= 0 && mouseX <= 152 && mouseY >= 16 && mouseY <= 101) &&
                !(mouseX >= 0 && mouseX <= 22 && mouseY >= 16 && mouseY <= 22) &&
                !(mouseX >= 128 && mouseX <= 150 && mouseY >= 16 && mouseY <= 22) &&
                !(mouseX >= 0 && mouseX <= 22 && mouseY >= 95 && mouseY <= 101) &&
                !(mouseX >= 128 && mouseX <= 150 && mouseY >= 95 && mouseY <= 101)
            ) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("reactor_casing")
                        )
                ));

                return true;
            }
        }

        return false;
    }
}
