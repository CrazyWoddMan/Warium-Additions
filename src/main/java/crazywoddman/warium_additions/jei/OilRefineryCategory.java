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
import com.mojang.blaze3d.systems.RenderSystem;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

import com.mojang.blaze3d.platform.InputConstants;

public class OilRefineryCategory implements IRecipeCategory<OilRefineryRecipes> {

    public static final RecipeType<OilRefineryRecipes> TYPE = RecipeType.create("crusty_chunks", "refinery", OilRefineryRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private int ticks() {
        return (int)(System.currentTimeMillis() / 50L);
    }
    private int firebox() {
        return ((ticks() / 20) % 3);
    }

    public OilRefineryCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(63, 121);
        this.icon = guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            WariumAdditionsUtil.getWariumStack("refinery")
        );
        this.slot = guiHelper.getSlotDrawable();
    }

    @Override
    public RecipeType<OilRefineryRecipes> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Oil Refinery");
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
    public void setRecipe(IRecipeLayoutBuilder builder, OilRefineryRecipes recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(1, 82)
        .addItemStack(recipe.getInput())
        .setBackground(slot, -1, -1);

        builder
        .addOutputSlot(1, 64)
        .addFluidStack(recipe.getOutput1().getFluid(), recipe.getOutput1().getAmount())
        .setBackground(slot, -1, -1)
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Can be collected with bucket").withStyle(ChatFormatting.GOLD));
        });

        builder
        .addOutputSlot(1, 46)
        .addFluidStack(recipe.getOutput2().getFluid(), recipe.getOutput2().getAmount())
        .setBackground(slot, -1, -1)
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Can be collected with bucket").withStyle(ChatFormatting.GOLD));
        });
        
        builder
        .addOutputSlot(1, 28)
        .addFluidStack(recipe.getOutput3().getFluid(), recipe.getOutput3().getAmount())
        .setBackground(slot, -1, -1)
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Can be collected with bucket").withStyle(ChatFormatting.GOLD));
        });

        builder
        .addOutputSlot(1, 10)
        .addFluidStack(recipe.getOutput4().getFluid(), recipe.getOutput4().getAmount())
        .setBackground(slot, -1, -1)
        .addTooltipCallback((view, tooltip) -> {
            tooltip.add(1, Component.literal("Can be collected with bucket").withStyle(ChatFormatting.GOLD));
        });
    }

    @Override
    public void draw(OilRefineryRecipes recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.pose().pushPose();
        graphics.pose().translate(21, 0, 0);
        graphics.pose().scale(0.25f, 0.25f, 1.0f);
        graphics.blit(
            ResourceLocation.fromNamespaceAndPath(
                WariumAdditions.MODID,
                switch (firebox()) {
                    case 1 -> "textures/jei/refinery_kerosene.png";
                    case 2 -> "textures/jei/refinery_electric.png";
                    default -> "textures/jei/refinery_firebox.png";
                }
            ),
            0, 0,
            0, 0,
            169, 489,
            169, 489
        );

        graphics.pose().popPose();
        for (int i = 0; i < 7; i++) {
            Random rand = new Random(ticks() / 40 * 1000L + i * 7919L);
            int life = 20 + rand.nextInt(21);

            if (ticks() % 40 > life)
                continue;

            graphics.pose().pushPose();
            graphics.pose().translate(
                10 + rand.nextInt(67),
                75 + rand.nextInt(41),
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

        for (int i = 0; i < 6; i++)
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
                    10 + rand.nextInt(57) + (float)Math.cos(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 8 * age / (float)life,
                    75 + rand.nextInt(41) + (float)Math.sin(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 8 * age / (float)life,
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
        
        for (int e = 0; e < 7; e++) {
            int epoch = (ticks() / 20) - e;
            int age = ticks() - epoch * 20;
            
            if (age < 0 || age >= 100)
                continue;

            Random rand = new Random(epoch * 12345L);
            float scale = 1.5f + rand.nextFloat() * 1.3f;

            graphics.pose().pushPose();
            graphics.pose().translate(
                23f + (rand.nextFloat() - 0.5f) * 3f,
                -15f - age * 1.5f,
                0
            );
            graphics.pose().scale(
                scale,
                scale,
                1.0f
            );
            graphics.blit(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/particle/fireball_" + (age / 10 + 1) + ".png"),
                0, 0,
                0, 0,
                16, 16,
                16, 16
            );
            graphics.pose().popPose();
        }

        if (mouseX >= 21 && mouseX <= 63 && mouseY >= 0 && mouseY <= 83)
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("refinery_tower"),
                (int)mouseX, (int)mouseY
            );

        if (mouseX >= 28 && mouseX <= 56 && mouseY >= 84 && mouseY <= 100)
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("refinery"),
                (int)mouseX, (int)mouseY
            );
        
        if (mouseX >= 28 && mouseX <= 56 && mouseY >= 101 && mouseY <= 121) {
            List<Component> tooltip = new ArrayList<>();
            tooltip
            .addAll((
                switch (firebox()) {
                    case 0 -> WariumAdditionsUtil.getWariumStack("firebox");
                    case 1 -> WariumAdditionsUtil.getWariumStack("oil_firebox");
                    case 2 -> WariumAdditionsUtil.getWariumStack("electric_firebox");
                    default -> WariumAdditionsUtil.getWariumStack("firebox");
                }
            )
            .getTooltipLines(
                Minecraft.getInstance().player,
                Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL)
            );
            tooltip
            .add(1, Component
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
    public boolean handleInput(OilRefineryRecipes recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (input.getType() == InputConstants.Type.MOUSE && input.getValue() == 0 && WariumJEI.jeiRuntime != null) {
            if (mouseX >= 21 && mouseX <= 63 && mouseY >= 0 && mouseY <= 83) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("refinery_tower")
                        )
                ));

                return true;
            }

            if (mouseX >= 28 && mouseX <= 56 && mouseY >= 84 && mouseY <= 100) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("refinery")
                        )
                ));

                return true;
            }

            if (mouseX >= 28 && mouseX <= 56 && mouseY >= 101 && mouseY <= 121) {
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
