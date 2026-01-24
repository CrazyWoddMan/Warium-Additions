package crazywoddman.warium_additions;

import crazywoddman.warium_additions.compat.create.CreateBlockEntities;
import crazywoddman.warium_additions.compat.create.CreateBlocks;
import crazywoddman.warium_additions.compat.create.CreateFluids;
import crazywoddman.warium_additions.compat.create.CreateItems;
import crazywoddman.warium_additions.compat.create.Registrate;
import crazywoddman.warium_additions.config.ClothConfig;
import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.ponder.WariumPonder;
import crazywoddman.warium_additions.recipes.WariumAdditionsRecipes;
import crazywoddman.warium_additions.registry.RegistryBlockEntities;
import crazywoddman.warium_additions.registry.RegistryBlocks;
import crazywoddman.warium_additions.registry.RegistryItems;
import net.mcreator.crustychunks.procedures.Rad1TickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WariumAdditions.MODID)
public class WariumAdditions {
    public static final String MODID = "warium_additions";
    
    protected static final ModList modlist = ModList.get();
    public static final boolean cloth_config = modlist.isLoaded("cloth_config");
    public static final boolean supplementaries = modlist.isLoaded("supplementaries");
    public static final boolean valkyrien_warium = modlist.isLoaded("valkyrien_warium");
    public static final boolean immersiveengineering = modlist.isLoaded("immersiveengineering");
    public static final boolean create = modlist.isLoaded("create");

    public WariumAdditions(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        WariumAdditionsRecipes.RECIPE_SERIALIZERS.register(bus);
        WariumAdditionsRecipes.RECIPE_TYPES.register(bus);
        WariumAdditionsRecipes.WARIUM_RECIPE_SERIALIZERS.register(bus);
        WariumAdditionsRecipes.WARIUM_RECIPE_TYPES.register(bus);
        RegistryBlocks.WARIUM_REGISTRY.register(bus);
        RegistryItems.WARIUM_REGISTRY.register(bus);

        if (create) {
            Registrate.register(bus);
            CreateBlocks.register();
            CreateBlocks.WARIUM_REGISTRY.register(bus);
            CreateBlockEntities.register();
            CreateItems.register();
            CreateFluids.register();
        }

        if (cloth_config)
            context.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        if (modlist.isLoaded("valkyrien_warium")) {
            RegistryItems.REGISTRY.register(bus);
            RegistryBlocks.REGISTRY.register(bus);
            RegistryBlocks.OLD_REGISTRY.register(bus);
            RegistryBlockEntities.REGISTRY.register(bus);
            RegistryBlockEntities.OLD_REGISTRY.register(bus);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            WariumPonder.register();

            if (modlist.isLoaded("cloth_config"))
                ClothConfig.registerConfigScreen();
        }

        @SubscribeEvent
        public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
            ResourceLocation tab = event.getTabKey().location();

            if (valkyrien_warium && tab.equals(ResourceLocation.fromNamespaceAndPath("valkyrien_warium", "warium_vs")))
                event.accept(RegistryItems.CONTROLLABLE_TRIGGER);

            if (create) {
                if (tab.equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "warium_logistics"))) {
                    event.accept(CreateItems.ITEMS.get("kinetic_converter"));
                    event.accept(CreateItems.ITEMS.get("rotation_converter"));
                }

                if (tab.equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "crusty_production")))
                    event.accept(CreateItems.ITEMS.get("yellowcake"));

                if (tab.equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "crusty_components"))) {
                    event.accept(CreateItems.ITEMS.get("petrolium_bottle"));
                    event.accept(CreateFluids.FLUIDS.get("yellowcake").get().getBucket());
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent event) {
            if (create && event.side.isServer() && event.player.tickCount % 10 == 0) {
                Level level = event.player.level();
                BlockPos pos = event.player.blockPosition();

                if (level.getFluidState(pos).getType() == CreateFluids.FLUIDS.get("yellowcake").get())
                    Rad1TickProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }
}
