package crazywoddman.warium_additions.compat.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.ChatFormatting;

import java.util.function.Supplier;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.jei.StructuredRecipeCategory;
import crazywoddman.warium_additions.compat.jei.recipes.FabricatorRecipe;

public class FabricatorCategory extends StructuredRecipeCategory<FabricatorRecipe> {
    public enum Type {
        MECHANICAL(CrustyChunksModItems.ASSEMBLY_MECHANICAL_FABRICATOR),
        CIRCUIT_TYPE(CrustyChunksModItems.ASSEMBLY_CIRCUIT_FABRICATOR);

        public final RecipeType<FabricatorRecipe> type;
        public final Supplier<Item> item;

        Type(RegistryObject<Item> item) {
            this.type = RecipeType.create("crusty_chunks",  item.getId().getPath(), FabricatorRecipe.class);
            this.item = item;
        }
    }

    public FabricatorCategory(IGuiHelper guiHelper, Type type) {
        super(
            guiHelper,
            type.type,
            type.item,
            guiHelper.createBlankDrawable(106, 49),
            1.7f,
            31, 8
        );
        addBlock(type.item, 0, 0, 0, tooltip -> tooltip
            .add(1, Component.translatable(WariumAdditions.MODID + ".tooltip.rotation_required")
            .withStyle(ChatFormatting.GOLD))
        );
        addBlock(CrustyChunksModItems.PRODUCTION_INPUT, -1, 0, 0);
        addBlock(CrustyChunksModItems.PRODUCTION_OUTPUT, 1, 0, 0);
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FabricatorRecipe recipe, IFocusGroup focuses) {
        builder
        .addInputSlot(1, 7)
        .addItemStack(recipe.input())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(71, 7)
        .addItemStack(recipe.output())
        .setStandardSlotBackground();

        builder
        .addOutputSlot(89, 7)
        .setStandardSlotBackground();

        builder
        .addOutputSlot(71, 25)
        .setStandardSlotBackground();

        builder
        .addOutputSlot(89, 25)
        .setStandardSlotBackground();
    }
}
