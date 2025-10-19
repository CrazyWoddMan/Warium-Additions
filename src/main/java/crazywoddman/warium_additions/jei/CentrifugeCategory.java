package crazywoddman.warium_additions.jei;

import java.util.Random;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.mojang.blaze3d.platform.InputConstants;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

public class CentrifugeCategory implements IRecipeCategory<CentrifugeRecipes> {

    public static final RecipeType<CentrifugeRecipes> TYPE = RecipeType.create("crusty_chunks", "centrifuge", CentrifugeRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private int ticks() {
        return (int)(System.currentTimeMillis() / 50L);
    }

    public CentrifugeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(124, 76);
        this.icon = guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            WariumAdditionsUtil.getWariumStack("centrifuge_core")
        );
        this.slot = guiHelper.getSlotDrawable();
    }

    @Override
    public RecipeType<CentrifugeRecipes> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Centrifuge");
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
    public void setRecipe(IRecipeLayoutBuilder builder, CentrifugeRecipes recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(21, 32)
        .addItemStack(recipe.getInput())
        .setBackground(slot, -1, -1)
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Centrifuge Core input").withStyle(ChatFormatting.BLUE));
        });

        builder
        .addOutputSlot(21, 12)
        .addItemStack(recipe.getOutputTop())
        .setBackground(slot, -1, -1)
        .addTooltipCallback((view, tooltip) -> {
            tooltip
            .add(1, Component.literal("Centrifuge Top output").withStyle(ChatFormatting.RED));
        });

        builder
        .addOutputSlot(21, 52)
        .addItemStack(recipe.getOutputBottom())
        .setBackground(slot, -1, -1)
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Centrifuge Bottom output").withStyle(ChatFormatting.RED));
        });
    }

    @Override
    public void draw(CentrifugeRecipes recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.pose().pushPose();
        graphics.pose().translate(41, 0, 0);
        graphics.pose().scale(0.25f, 0.25f, 1.0f);
        graphics.blit(
            ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "textures/jei/centrifuge.png"),
            0, 0,
            0, 0,
            253, 304,
            253, 304
        );
        graphics.pose().popPose();
        graphics.drawString(
            Minecraft.getInstance().font,
            "200s",
            100,
            68,
            0x8B8B8B,
            false
        );

        for (int i = 0; i < 10; i++)
            for (int e = 0; e <= 120 / 40; e++) {
                Random rand = new Random(i * 12345L  + e * 54321L);
                int epoch = ((ticks() + rand.nextInt(40)) / 40) - e;
                int birthTick = epoch * 40 - rand.nextInt(40);
                int age = ticks() - birthTick;

                if (age < 0)
                    continue;

                int life = 80 + rand.nextInt(41);

                if (age > life)
                    continue;

                graphics.pose().pushPose();
                graphics.pose().translate(
                    35 + rand.nextInt(57) + (float)Math.cos(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 48 * age / (float)life,
                    2 + rand.nextInt(72) + (float)Math.sin(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 48 * age / (float)life,
                    0
                );
                graphics.pose().scale(0.7f, 0.7f, 1.0f);
                graphics.blit(
                    ResourceLocation.fromNamespaceAndPath("minecraft", "textures/particle/spark_" + (ticks() / (10 + rand.nextInt(5))) % 8 + ".png"),
                    0, 0,
                    0, 0,
                    8, 8,
                    8, 8
                );
                graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                graphics.pose().popPose();
            }

        if (mouseX >= 60 && mouseX <= 84 && mouseY >= 6 && mouseY <= 12)
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("centrifuge_top"),
                (int)mouseX, (int)mouseY
            );
        
        else if (
            (mouseX >= 60 && mouseX <= 85 && mouseY >= 38 && mouseY <= 50) ||
            (mouseX >= 60 && mouseX <= 72 && mouseY >= 33 && mouseY <= 37)
            )
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("centrifuge_core"),
                (int)mouseX, (int)mouseY
            );
        
        else if (
            (mouseX >= 52 && mouseX <= 77 && mouseY >= 0 && mouseY <= 34) ||
            (mouseX >= 52 && mouseX <= 104 && mouseY >= 4 && mouseY <= 25) ||
            (mouseX >= 60 && mouseX <= 93 && mouseY >= 4 && mouseY <= 37) ||
            (mouseX >= 41 && mouseX <= 66 && mouseY >= 9 && mouseY <= 34) ||
            (mouseX >= 52 && mouseX <= 104 && mouseY >= 40 && mouseY <= 67) ||
            (mouseX >= 41 && mouseX <= 67 && mouseY >= 47 && mouseY <= 72) ||
            (mouseX >= 69 && mouseX <= 93 && mouseY >= 67 && mouseY <= 76)
            )
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("giant_coil"),
                (int)mouseX, (int)mouseY
            );
    }

    @Override
    public boolean handleInput(CentrifugeRecipes recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (input.getType() == InputConstants.Type.MOUSE && input.getValue() == 0 && WariumJEI.jeiRuntime != null) {
            if (mouseX >= 60 && mouseX <= 84 && mouseY >= 6 && mouseY <= 12) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("centrifuge_top")
                        )
                ));

                return true;
            }

            else if (
                (mouseX >= 60 && mouseX <= 85 && mouseY >= 38 && mouseY <= 50) ||
                (mouseX >= 60 && mouseX <= 72 && mouseY >= 33 && mouseY <= 37)
                ) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("centrifuge_core")
                        )
                ));

                return true;
            }

            else if (
                (mouseX >= 52 && mouseX <= 77 && mouseY >= 0 && mouseY <= 34) ||
                (mouseX >= 52 && mouseX <= 104 && mouseY >= 4 && mouseY <= 25) ||
                (mouseX >= 60 && mouseX <= 93 && mouseY >= 4 && mouseY <= 37) ||
                (mouseX >= 41 && mouseX <= 66 && mouseY >= 9 && mouseY <= 34) ||
                (mouseX >= 52 && mouseX <= 104 && mouseY >= 40 && mouseY <= 67) ||
                (mouseX >= 41 && mouseX <= 67 && mouseY >= 47 && mouseY <= 72) ||
                (mouseX >= 69 && mouseX <= 93 && mouseY >= 67 && mouseY <= 76)
                ) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("giant_coil")
                        )
                ));

                return true;
            }
        }

        return false;
    }
}
