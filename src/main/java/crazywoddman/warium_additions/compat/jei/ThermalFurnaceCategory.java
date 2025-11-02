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

public class ThermalFurnaceCategory implements IRecipeCategory<ThermalFurnaceRecipes> {

    public static final RecipeType<ThermalFurnaceRecipes> TYPE = RecipeType.create("crusty_chunks", "thermal_furnace", ThermalFurnaceRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;
    private int ticks() {
        return (int)(System.currentTimeMillis() / 50L);
    }
    private int firebox() {
        return ((ticks() / 20) % 3);
    }

    public ThermalFurnaceCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .drawableBuilder(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/ab_gui.png"),
                6, 3, 164, 80
            )
            .setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            WariumAdditionsUtil.getWariumStack("thermal_furnace")
        );
    }

    @Override
    public RecipeType<ThermalFurnaceRecipes> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Thermal Furnace");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ThermalFurnaceRecipes recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(19, 32)
        .addItemStack(recipe.getInput());

        builder
        .addOutputSlot(127, 32)
        .addItemStack(recipe.getOutput());
    }

    @Override
    public void draw(ThermalFurnaceRecipes recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.pose().pushPose();
        graphics.pose().translate(70, 0, 0);
        graphics.pose().scale(0.25f, 0.25f, 1.0f);
        graphics.blit(
            ResourceLocation.fromNamespaceAndPath(
                WariumAdditions.MODID,
                switch (firebox()) {
                    case 1 -> "textures/jei/thermal_furnace_kerosene.png";
                    case 2 -> "textures/jei/thermal_furnace_electric.png";
                    default -> "textures/jei/thermal_furnace_firebox.png";
                }
            ),
            0, 0,
            0, 0,
            93, 325,
            93, 325
        );

        graphics.pose().popPose();

        for (int i = 0; i < 7; i++) {
            Random rand = new Random(ticks() / 40 * 1000L + i * 7919L);
            int life = 20 + rand.nextInt(21);

            if (ticks() % 40 > life)
                continue;

            graphics.pose().pushPose();
            graphics.pose().translate(
                50 + rand.nextInt(67),
                40 + rand.nextInt(41),
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
        RenderSystem.defaultBlendFunc();

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
                    50 + rand.nextInt(57) + (float)Math.cos(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 8 * age / (float)life,
                    40 + rand.nextInt(41) + (float)Math.sin(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 8 * age / (float)life,
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
        
        for (int i = 0; i < 10; i++) 
            for (int e = 0; e <= 24; e++) {
                int epoch = (ticks() / 5) - e;
                int birthTick = epoch * 5;
                int age = ticks() - birthTick;

                if (age < 0)
                    continue;

                Random rand = new Random(epoch * 12345L);
                int life = 80 + rand.nextInt(41);

                if (age > life)
                    continue;
                    
                graphics.pose().pushPose();
                graphics.pose().translate(
                    75 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 32 * age / (float)life,
                    -35 + rand.nextInt(25) + (float)Math.sin(rand.nextFloat() * Math.PI * 2) * 8 + (rand.nextBoolean() ? 1f : -1f) * rand.nextFloat() * 16 * age / (float)life,
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
        

        if (mouseX >= 76 && mouseX <= 87 && mouseY >= 0 && mouseY <= 48)
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("blast_funnel"),
                (int)mouseX, (int)mouseY
            );

        if (mouseX >= 70 && mouseX <= 93 && mouseY >= 49 && mouseY <= 65) {
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                WariumAdditionsUtil.getWariumStack("thermal_furnace"),
                (int)mouseX, (int)mouseY
            );
        }
        if (mouseX >= 70 && mouseX <= 93 && mouseY >= 66 && mouseY <= 80) {
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
                Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL)
            );
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
    public boolean handleInput(ThermalFurnaceRecipes recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (input.getType() == InputConstants.Type.MOUSE && input.getValue() == 0 && WariumJEI.jeiRuntime != null) {
            if (mouseX >= 76 && mouseX <= 87 && mouseY >= 0 && mouseY <= 48) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("blast_funnel")
                        )
                ));

                return true;
            }

            if (mouseX >= 70 && mouseX <= 93 && mouseY >= 49 && mouseY <= 65) {
                WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                    WariumJEI.jeiRuntime.getJeiHelpers()
                        .getFocusFactory()
                        .createFocus(
                            RecipeIngredientRole.OUTPUT,
                            VanillaTypes.ITEM_STACK,
                            WariumAdditionsUtil.getWariumStack("thermal_furnace")
                        )
                ));

                return true;
            }

            if (mouseX >= 70 && mouseX <= 93 && mouseY >= 66 && mouseY <= 80) {
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
