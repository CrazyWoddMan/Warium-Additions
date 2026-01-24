package crazywoddman.warium_additions.compat.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IJeiKeyMappings;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.network.chat.Component;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public abstract class StructuredRecipeCategory<T> implements IRecipeCategory<T> {
    protected record Block(Supplier<Item> item, int x, int y, int z, @Nullable Consumer<List<Component>> tooltip) {};
    protected final RecipeType<T> recipeType;
    protected final Item categoryItem;
    protected final IDrawable icon;
    protected final IDrawableStatic background;
    protected final float blockSize;
    protected final int startX;
    protected final int startY;
    protected final Collection<Block> structure;

    protected StructuredRecipeCategory(IGuiHelper guiHelper, RecipeType<T> recipeType, Supplier<Item> categoryItem, IDrawableStatic background, float blockSize, int startX, int startY, Collection<Block> structure) {
        this.recipeType = recipeType;
        this.categoryItem = categoryItem.get();
        this.icon = guiHelper.createDrawableItemLike(this.categoryItem);
        this.background = background;
        this.blockSize = blockSize;
        this.startX = startX;
        this.startY = startY;
        this.structure = structure;
    }

    protected StructuredRecipeCategory(IGuiHelper guiHelper, RecipeType<T> recipeType, Supplier<Item> icon, IDrawableStatic background, float blockSize, int startX, int startY) {
        this(
            guiHelper,
            recipeType,
            icon,
            background,
            blockSize,
            startX, startY,
            new TreeSet<>(Comparator.comparingInt(Block::y).thenComparingInt(Block::z).thenComparingInt(Block::x))
        );
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.crusty_chunks." + ForgeRegistries.ITEMS.getKey(this.categoryItem).getPath());
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public RecipeType<T> getRecipeType() {
        return this.recipeType;
    }

    protected void addBlock(Supplier<Item> item, int x, int y, int z) {
        structure.add(new Block(item, x, y, z, null));
    }

    protected void addBlock(Supplier<Item> item, int x, int y, int z, Consumer<List<Component>> tooltip) {
        structure.add(new Block(item, x, y, z, tooltip));
    }

    protected void firebox() {
        addBlock(
            () -> WariumAdditionsUtil.ticker(
                CrustyChunksModItems.FIREBOX.get(),
                CrustyChunksModItems.OIL_FIREBOX.get(),
                CrustyChunksModItems.ELECTRIC_FIREBOX.get()
            ),
            0, 0, 0,
            tooltip -> tooltip.add(1, Component.translatable(WariumAdditions.MODID + WariumAdditionsUtil.ticker(
                ".tooltip.fuel_needed",
                ".tooltip.fuel_needed",
                ".tooltip.power_needed"
            )).withStyle(ChatFormatting.GOLD))
        );
    }

    protected static RegistryAccess getRegistryAccess() {
        return Minecraft.getInstance().level.registryAccess();
    }
    
    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, T recipe, IFocusGroup focuses) {
        builder.addInputHandler(new StructureInputHandler());
    }
    
    @Override
    public void draw(T recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        double spacing = 7 * blockSize;
        Block hovered = null;
        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, -12);
        
        for (Block block : structure) {
            double isoX = this.startX + spacing * (block.x - block.z);
            double isoY = this.startY + spacing * (block.x + block.z) / 2 - 8.5 * block.y * blockSize;
            
            if (isMouseOver(mouseX, mouseY, isoX, isoY, blockSize))
                hovered = block;

            graphics.pose().translate(0, 0, 6);

            graphics.pose().pushPose();
            graphics.pose().translate(isoX, isoY, 0);
            graphics.pose().scale(blockSize, blockSize, 1);
            graphics.renderItem(new ItemStack(block.item.get()), 0, 0);
            graphics.pose().popPose();
        }
        
        if (hovered != null) {
            Minecraft mc = Minecraft.getInstance();
            List<Component> tooltip = new ItemStack(hovered.item.get()).getTooltipLines(
                mc.player,
                mc.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL
            );
            if (hovered.tooltip != null)
                hovered.tooltip.accept(tooltip);

            graphics.renderComponentTooltip(
                mc.font,
                tooltip,
                (int)mouseX, (int)mouseY
            );
        }

        graphics.pose().popPose();
    }
    
    protected static boolean isMouseOver(double mouseX, double mouseY, double x, double y, float blockSize) {
        return mouseX >= x + blockSize && mouseX <= x + 15f * blockSize && mouseY >= y + blockSize && mouseY <= y + 15 * blockSize;
    }

    private class StructureInputHandler implements IJeiInputHandler {
        @Override
        public ScreenRectangle getArea() {
            return new ScreenRectangle(0, 0, getWidth(), getHeight());
        }

        @Override
        public boolean handleInput(double mouseX, double mouseY, IJeiUserInput input) {
            if (WariumJEI.jeiRuntime != null && !input.isSimulate()) {
                IJeiKeyMappings keyMappings = WariumJEI.jeiRuntime.getKeyMappings();
                boolean showRecipe = input.is(keyMappings.getShowRecipe());
                boolean showUses = !showRecipe && input.is(keyMappings.getShowUses());

                if ((showRecipe || showUses)) {
                    double spacing = 7 * blockSize;
                    Block hovered = null;
                    ArrayList<Block> reversed = new ArrayList<>(structure);
                    Collections.reverse(reversed);

                    for (Block block : reversed) {
                        double isoX = startX + spacing * (block.x - block.z);
                        double isoY = startY + spacing * (block.x + block.z) / 2 - 8.5 * block.y * blockSize;
                        
                        if (isMouseOver(mouseX, mouseY, isoX, isoY, blockSize)) {
                            hovered = block;
                            break;
                        }
                    }

                    if (hovered != null) {
                        WariumJEI.jeiRuntime.getRecipesGui().show(List.of(
                            WariumJEI.jeiRuntime.getJeiHelpers()
                                .getFocusFactory()
                                .createFocus(
                                    showRecipe ? RecipeIngredientRole.OUTPUT : RecipeIngredientRole.INPUT,
                                    VanillaTypes.ITEM_STACK,
                                    new ItemStack(hovered.item.get())
                                )
                        ));

                        return true;
                    }
                }
            }
            
            return IJeiInputHandler.super.handleInput(mouseX, mouseY, input);
        }
    }
}
