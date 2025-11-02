package crazywoddman.warium_additions.compat.jei;

import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.helpers.IGuiHelper;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class WariumJEI implements IModPlugin {

    public static IJeiRuntime jeiRuntime;

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        jeiRuntime = runtime;
    }

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("warium_ponder", "jei");

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
            new FoundryCategory(guiHelper),
            new LatheCategory(guiHelper),
            new OilRefineryCategory(guiHelper),
            new CentrifugeCategory(guiHelper),
            new ReactorCategory(guiHelper),
            new PressCategory(guiHelper),
            new CutterCategory(guiHelper),
            new ExtruderCategory(guiHelper),
            new CrusherCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(
            MineralGrinderCategory.TYPE,
            MineralGrinderRecipes.RECIPES
        );
        registration.addRecipes(
            BlastFurnaceCategory.TYPE,
            BlastFurnaceRecipes.RECIPES
        );
        registration.addRecipes(
            ThermalFurnaceCategory.TYPE,
            ThermalFurnaceRecipes.RECIPES
        );
        registration.addRecipes(
            FoundryCategory.TYPE,
            FoundryRecipes.RECIPES
        );
        registration.addRecipes(
            LatheCategory.TYPE,
            LatheRecipes.RECIPES
        );
        registration.addRecipes(
            OilRefineryCategory.TYPE,
            OilRefineryRecipes.RECIPES
        );
        registration.addRecipes(
            CentrifugeCategory.TYPE,
            CentrifugeRecipes.RECIPES
        );
        registration.addRecipes(
            ReactorCategory.TYPE,
            ReactorRecipes.RECIPES
        );
        registration.addRecipes(
            PressCategory.TYPE,
            PressRecipes.RECIPES
        );
        registration.addRecipes(
            CutterCategory.TYPE,
            CutterRecipes.RECIPES
        );
        registration.addRecipes(
            ExtruderCategory.TYPE,
            ExtruderRecipes.RECIPES
        );
        registration.addRecipes(
            CrusherCategory.TYPE,
            MineralGrinderRecipes.RECIPES
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(
            BlastFurnaceCategory.TYPE,
            WariumAdditionsUtil.getWariumStack("blast_furnace"),
            WariumAdditionsUtil.getWariumStack("blast_funnel"),
            WariumAdditionsUtil.getWariumStack("firebox"),
            WariumAdditionsUtil.getWariumStack("oil_firebox"),
            WariumAdditionsUtil.getWariumStack("electric_firebox")
        );
        registration.addRecipeCatalysts(
            ThermalFurnaceCategory.TYPE,
            WariumAdditionsUtil.getWariumStack("thermal_furnace"),
            WariumAdditionsUtil.getWariumStack("blast_funnel"),
            WariumAdditionsUtil.getWariumStack("firebox"),
            WariumAdditionsUtil.getWariumStack("oil_firebox"),
            WariumAdditionsUtil.getWariumStack("electric_firebox")
        );
        registration.addRecipeCatalysts(
            FoundryCategory.TYPE,
            WariumAdditionsUtil.getWariumStack("foundry"),
            WariumAdditionsUtil.getWariumStack("firebox"),
            WariumAdditionsUtil.getWariumStack("oil_firebox"),
            WariumAdditionsUtil.getWariumStack("electric_firebox")
        );
        registration.addRecipeCatalysts(
            OilRefineryCategory.TYPE,
            WariumAdditionsUtil.getWariumStack("refinery_tower"),
            WariumAdditionsUtil.getWariumStack("refinery"),
            WariumAdditionsUtil.getWariumStack("firebox"),
            WariumAdditionsUtil.getWariumStack("oil_firebox"),
            WariumAdditionsUtil.getWariumStack("electric_firebox")
        );
        registration.addRecipeCatalysts(
            CentrifugeCategory.TYPE,
            WariumAdditionsUtil.getWariumStack("centrifuge_top"),
            WariumAdditionsUtil.getWariumStack("centrifuge_core"),
            WariumAdditionsUtil.getWariumStack("centrifuge_bottom"),
            WariumAdditionsUtil.getWariumStack("giant_coil")
        );
        registration.addRecipeCatalysts(
            ReactorCategory.TYPE,
            WariumAdditionsUtil.getWariumStack("breeder_reactor_interface"),
            WariumAdditionsUtil.getWariumStack("breeder_reactor_core"),
            WariumAdditionsUtil.getWariumStack("breeder_reactor_port"),
            WariumAdditionsUtil.getWariumStack("reaction_chamber"),
            WariumAdditionsUtil.getWariumStack("control_rod"),
            WariumAdditionsUtil.getWariumStack("reactor_casing"),
            WariumAdditionsUtil.getWariumStack("empty_fuel_rods"),
            WariumAdditionsUtil.getWariumStack("fuel_rod")
        );
        registration.addRecipeCatalyst(
            WariumAdditionsUtil.getWariumStack("mineral_grinder"),
            MineralGrinderCategory.TYPE
        );
        registration.addRecipeCatalyst(
            WariumAdditionsUtil.getWariumStack("crusher"),
            CrusherCategory.TYPE
        );
        registration.addRecipeCatalyst(
            WariumAdditionsUtil.getWariumStack("lathe"),
            LatheCategory.TYPE
        );
        registration.addRecipeCatalyst(
            WariumAdditionsUtil.getWariumStack("press"),
            PressCategory.TYPE
        );
        registration.addRecipeCatalyst(
            WariumAdditionsUtil.getWariumStack("cutter"),
            CutterCategory.TYPE
        );
        registration.addRecipeCatalyst(
            WariumAdditionsUtil.getWariumStack("extruder"),
            ExtruderCategory.TYPE
        );
    }
}
