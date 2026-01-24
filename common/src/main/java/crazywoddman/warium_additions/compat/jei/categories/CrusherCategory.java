package crazywoddman.warium_additions.compat.jei.categories;

import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.ChatFormatting;
import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.jei.StructuredRecipeCategory;
import crazywoddman.warium_additions.compat.jei.recipes.CrusherRecipe;

public class CrusherCategory extends StructuredRecipeCategory<CrusherRecipe> {
    public static final RecipeType<CrusherRecipe> TYPE = RecipeType.create("crusty_chunks", CrustyChunksModItems.ASSEMBLY_CRUSHER.getId().getPath(), CrusherRecipe.class);

    public CrusherCategory(IGuiHelper guiHelper) {
        super(
            guiHelper,
            TYPE,
            CrustyChunksModItems.ASSEMBLY_CRUSHER,
            guiHelper.createBlankDrawable(106, 57),
            1.7f,
            31, 5
        );
        addBlock(CrustyChunksModItems.ASSEMBLY_CRUSHER, 0, 0, 0, tooltip -> tooltip
            .add(1, Component.translatable(WariumAdditions.MODID + ".tooltip.rotation_required")
            .withStyle(ChatFormatting.GOLD))
        );
        addBlock(CrustyChunksModItems.PRODUCTION_INPUT, -1, 0, 0);
        addBlock(CrustyChunksModItems.PRODUCTION_OUTPUT, 1, 0, 0);
    }

    @Override
    public RecipeType<CrusherRecipe> getRecipeType() {
        return TYPE;
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrusherRecipe recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(1, 4)
        .addItemStack(recipe.input())
        .setStandardSlotBackground();

        builder
        .addInputSlot(33, 40)
        .addItemStacks(List.of(
            new ItemStack(CrustyChunksModItems.IRONGEAR.get()),
            new ItemStack(CrustyChunksModItems.STEEL_CRUSHING_WHEEL.get())
        ))
        .setStandardSlotBackground();

        builder
        .addOutputSlot(71, 4)
        .addItemStack(recipe.output1())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(89, 4)
        .addItemStack(recipe.output2())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(71, 22)
        .addItemStack(recipe.output3())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(89, 22)
        .addItemStack(recipe.output4())
        .setStandardSlotBackground();
    }
}
