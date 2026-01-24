package crazywoddman.warium_additions.compat.jei;

import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.helpers.IGuiHelper;

import java.util.List;
import crazywoddman.warium_additions.WariumAdditions;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import crazywoddman.warium_additions.recipes.WariumAdditionsRecipes;
import crazywoddman.warium_additions.compat.jei.recipes.*;
import crazywoddman.warium_additions.compat.jei.categories.*;

@JeiPlugin
public class WariumJEI implements IModPlugin {

    public static IJeiRuntime jeiRuntime;

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        jeiRuntime = runtime;
    }

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "jei");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
            new MineralGrinderCategory(guiHelper),
            new BlastFurnaceCategory(guiHelper),
            new ThermalFurnaceCategory(guiHelper),
            new AssemblyFurnaceCategory(guiHelper),
            new FoundryCategory(guiHelper),
            new RefineryCategory(guiHelper),
            new CentrifugeCategory(guiHelper),
            new ReactorCategory(guiHelper),
            new CrusherCategory(guiHelper),
            new AssemblyCategory(guiHelper),
            new DigesterCategory(guiHelper),
            new FabricatorCategory(guiHelper, FabricatorCategory.Type.MECHANICAL),
            new FabricatorCategory(guiHelper, FabricatorCategory.Type.CIRCUIT_TYPE)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        List<SmeltingRecipe> smelting = manager.getAllRecipesFor(RecipeType.SMELTING);

        registration.addRecipes(
            AssemblyCategory.TYPE,
            manager.getAllRecipesFor(WariumAdditionsRecipes.ASSEMBLY_RECIPE_TYPE.get())
        );
        registration.addRecipes(MineralGrinderCategory.TYPE, MineralGrinderRecipe.RECIPES);
        registration.addRecipes(BlastFurnaceCategory.TYPE, BlastFurnaceRecipe.RECIPES);
        registration.addRecipes(ThermalFurnaceCategory.TYPE, smelting);
        registration.addRecipes(AssemblyFurnaceCategory.TYPE, smelting);
        registration.addRecipes(FoundryCategory.TYPE, FoundryRecipe.RECIPES);
        registration.addRecipes(RefineryCategory.TYPE, RefineryRecipe.RECIPES);
        registration.addRecipes(CentrifugeCategory.TYPE, CentrifugeRecipe.RECIPES);
        registration.addRecipes(ReactorCategory.TYPE, ReactorRecipe.RECIPES);
        registration.addRecipes(CrusherCategory.TYPE, CrusherRecipe.RECIPES);
        registration.addRecipes(DigesterCategory.TYPE, DigesterRecipe.RECIPES);
        registration.addRecipes(FabricatorCategory.Type.MECHANICAL.type, FabricatorRecipe.MECHANICAL_RECIPES);
        registration.addRecipes(FabricatorCategory.Type.CIRCUIT_TYPE.type, FabricatorRecipe.CIRCUIT_RECIPES);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(
            BlastFurnaceCategory.TYPE,
            CrustyChunksModItems.BLAST_FURNACE.get()
        );
        registration.addRecipeCatalysts(
            ThermalFurnaceCategory.TYPE,
            CrustyChunksModItems.THERMAL_FURNACE.get()
        );
        registration.addRecipeCatalysts(
            AssemblyFurnaceCategory.TYPE,
            CrustyChunksModItems.ASSEMBLY_FURNACE.get()
        );
        registration.addRecipeCatalysts(
            FoundryCategory.TYPE,
            CrustyChunksModItems.FOUNDRY.get()
        );
        registration.addRecipeCatalysts(
            RefineryCategory.TYPE,
            CrustyChunksModItems.REFINERY.get(),
            CrustyChunksModItems.REFINERY_TOWER.get()
        );
        registration.addRecipeCatalysts(
            CentrifugeCategory.TYPE,
            CrustyChunksModItems.GIANT_COIL.get(),
            CrustyChunksModItems.ASSEMBLY_CENTRIFUGE_BOTTOM.get(),
            CrustyChunksModItems.ASSEMBLY_CENTRIFUGE_MIDDLE.get(),
            CrustyChunksModItems.ASSEMBLY_CENTRIFUGE_TOP.get()
        );
        registration.addRecipeCatalysts(
            ReactorCategory.TYPE,
            CrustyChunksModItems.BREEDER_REACTOR_INTERFACE.get(),
            CrustyChunksModItems.BREEDER_REACTOR_CORE.get(),
            CrustyChunksModItems.BREEDER_REACTOR_PORT.get(),
            CrustyChunksModItems.REACTION_CHAMBER.get(),
            CrustyChunksModItems.CONTROL_ROD.get(),
            CrustyChunksModItems.REACTOR_CASING.get(),
            CrustyChunksModItems.EMPTY_FUEL_RODS.get(),
            CrustyChunksModItems.FUEL_ROD.get()
        );
        registration.addRecipeCatalysts(
            MineralGrinderCategory.TYPE,
            CrustyChunksModItems.MINERAL_GRINDER.get()
        );
        registration.addRecipeCatalysts(
            CrusherCategory.TYPE,
            CrustyChunksModItems.ASSEMBLY_CRUSHER.get()
        );
        registration.addRecipeCatalysts(
            AssemblyCategory.TYPE,
            CrustyChunksModItems.ASSEMBLY_MACHINE.get(),
            CrustyChunksModItems.ASSEMBLY_DEPOT.get(),
            CrustyChunksModItems.MECHANICAL_EXTRUDER.get(),
            CrustyChunksModItems.MECHANICAL_PRESS.get(),
            CrustyChunksModItems.MECHANICAL_BORE.get(),
            CrustyChunksModItems.MECHANICAL_SHEAR.get()
        );
        registration.addRecipeCatalysts(
            DigesterCategory.TYPE,
            CrustyChunksModItems.BAUXITE_DIGESTER.get()
        );
        registration.addRecipeCatalysts(
            FabricatorCategory.Type.MECHANICAL.type,
            CrustyChunksModItems.ASSEMBLY_MECHANICAL_FABRICATOR.get()
        );
        registration.addRecipeCatalysts(
            FabricatorCategory.Type.CIRCUIT_TYPE.type,
            CrustyChunksModItems.ASSEMBLY_CIRCUIT_FABRICATOR.get()
        );
    }
}
