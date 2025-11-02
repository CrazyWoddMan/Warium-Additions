package crazywoddman.warium_additions.compat.jei;

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
import com.mojang.blaze3d.systems.RenderSystem;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

import com.mojang.blaze3d.platform.InputConstants;

public class FoundryCategory implements IRecipeCategory<FoundryRecipes> {

    public static final RecipeType<FoundryRecipes> TYPE = RecipeType.create("crusty_chunks", "foundry", FoundryRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable input;
    private final IDrawable output;
    private int ticks() {
        return (int)(System.currentTimeMillis() / 50L);
    }
    private int firebox() {
        return ((ticks() / 20) % 3);
    }

    public FoundryCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/foundry_gui.png"),
                6, 7, 164, 72
            ).setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            WariumAdditionsUtil.getWariumStack("foundry")
        );
        this.input = guiHelper
            .drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/grayingot.png"),
                0, 0, 16, 16
            ).setTextureSize(16, 16).build();
        this.output = guiHelper
            .drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/graycyllinder.png"),
                0, 0, 16, 16
            )
            .setTextureSize(16, 16).build();
    }

    @Override
    public RecipeType<FoundryRecipes> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Foundry");
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
    public void setRecipe(IRecipeLayoutBuilder builder, FoundryRecipes recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(1, 1)
        .addItemStack(recipe.getInputTop())
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Not Consumed").withStyle(ChatFormatting.GOLD));
        });

        builder
        .addInputSlot(1, 28)
        .addItemStack(recipe.getInputBottom())
        .setBackground(input, 0, 0);

        builder
        .addOutputSlot(1, 55)
        .addItemStack(recipe.getOutput())
        .setBackground(output, 0, 0);
    }

    @Override
    public void draw(FoundryRecipes recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.pose().pushPose();
        graphics.pose().translate(120, 32, 0);
        graphics.pose().scale(0.25f, 0.25f, 1.0f);
        graphics.blit(
            ResourceLocation.fromNamespaceAndPath(
                WariumAdditions.MODID,
                switch (firebox()) {
                    case 1 -> "textures/jei/foundry_kerosene.png";
                    case 2 -> "textures/jei/foundry_electric.png";
                    default -> "textures/jei/foundry_firebox.png";
                }
            ),
            0, 0,
            0, 0,
            93, 160,
            93, 160
        );

        graphics.pose().popPose();
        for (int i = 0; i < 6; i++) {
            Random rand = new Random(ticks() / 40 * 1000L + i * 7919L);
            int life = 20 + rand.nextInt(21);
            if (ticks() % 40 > life)
                continue;
            graphics.pose().pushPose();
            graphics.pose().translate(
                100 + rand.nextInt(67),
                32 + rand.nextInt(41),
                0
            );
            graphics.pose().scale(
                1.0f - (ticks() % 40 / (float)life),
                1.0f - (ticks() % 40 / (float)life),
                1.0f
            );
            graphics.blit(
                ResourceLocation.fromNamespaceAndPath("minecraft", "textures/particle/flame.png"),
                0, 0,
                0, 0,
                8, 8,
                8, 8
            );
            graphics.pose().popPose();
        }

        RenderSystem.enableBlend();
        for (int i = 0; i < 7; i++)
            for (int e = 0; e <= 4; e++) {
                int epoch = (ticks() / 40) - e;
                int birthTick = epoch * 40;
                int age = ticks() - birthTick;
                if (age < 0)
                    continue;
                Random rand = new Random(epoch * 2000L + i * 12345L);
                int life = 80 + rand.nextInt(41);
                if (age > life)
                    continue;
                graphics.pose().pushPose();
                graphics.pose().translate(
                    100 + rand.nextInt(57) + (float)Math.cos(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 8 * age / (float)life,
                    32 + rand.nextInt(41) + (float)Math.sin(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 8 * age / (float)life,
                    0
                );
                graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f - age / (float)life);
                graphics.blit(
                    ResourceLocation.fromNamespaceAndPath("minecraft", "textures/particle/big_smoke_" + rand.nextInt(12) + ".png"),
                    0, 0,
                    0, 0,
                    16, 16,
                    16, 16
                );
                graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                graphics.pose().popPose();
            }
        

        if (mouseX >= 120 && mouseX <= 143 && mouseY >= 32 && mouseY <= 57)
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("foundry"),
                (int)mouseX, (int)mouseY
            );
        
        if (mouseX >= 120 && mouseX <= 143 && mouseY >= 58 && mouseY <= 72) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.addAll((
                switch (firebox()) {
                    case 0 -> WariumAdditionsUtil.getWariumStack("firebox");
                    case 1 -> WariumAdditionsUtil.getWariumStack("oil_firebox");
                    case 2 -> WariumAdditionsUtil.getWariumStack("electric_firebox");
                    default -> WariumAdditionsUtil.getWariumStack("firebox");
                }
            ).getTooltipLines(
                Minecraft.getInstance().player,
                Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL
            ));
            tooltip.add(1, Component
                .literal(
                    switch (firebox()) {
                        case 0 -> "Must be fueled";
                        case 1 -> "Must be fueled";
                        case 2 -> "Must be powered";
                        default -> "Must be fueled";
                    }
                )
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
    public boolean handleInput(FoundryRecipes recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (input.getType() == InputConstants.Type.MOUSE && input.getValue() == 0 && WariumJEI.jeiRuntime != null) {
            if (mouseX >= 120 && mouseX <= 143 && mouseY >= 32 && mouseY <= 57) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("foundry")
                        )
                ));

                return true;
            }
            if (mouseX >= 120 && mouseX <= 143 && mouseY >= 58 && mouseY <= 72) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            switch (firebox()) {
                                case 0 -> WariumAdditionsUtil.getWariumStack("firebox");
                                case 1 -> WariumAdditionsUtil.getWariumStack("oil_firebox");
                                case 2 -> WariumAdditionsUtil.getWariumStack("electric_firebox");
                                default -> WariumAdditionsUtil.getWariumStack("firebox");
                            }
                        )
                ));

                return true;
            }
        }

        return false;
    }
}
