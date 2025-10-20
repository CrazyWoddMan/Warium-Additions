package crazywoddman.warium_additions;

import crazywoddman.warium_additions.config.ClothConfig;
import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.recipe.WariumAdditionsRecipeTypes;
import crazywoddman.warium_additions.registrate.CreateBlockEntities;
import crazywoddman.warium_additions.registrate.CreateBlocks;
import crazywoddman.warium_additions.registrate.CreateFluids;
import crazywoddman.warium_additions.registrate.CreateItems;
import crazywoddman.warium_additions.registrate.Registrate;
import crazywoddman.warium_additions.registry.RegistryBlockEntities;
import crazywoddman.warium_additions.registry.RegistryBlocks;
import crazywoddman.warium_additions.registry.RegistryItems;
import net.mcreator.crustychunks.procedures.Rad1TickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WariumAdditions.MODID)
public class WariumAdditions {
    public static final String MODID = "warium_additions";
    
    private static final ModList modlist = ModList.get();
    public static final boolean supplementariesLoaded = modlist.isLoaded("supplementaries");
    public static final boolean valkyrienWariumLoaded = modlist.isLoaded("valkyrien_warium");
    public static final boolean IEloaded = modlist.isLoaded("immersiveengineering");
    public static final boolean createLoaded = modlist
            .getModContainerById("create")
            .map(container -> 
                container
                .getModInfo()
                .getVersion()
                .toString()
                .equals("0.5.1.j")
            )
            .orElse(false);

    public WariumAdditions(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        
        RegistryBlocks.WARIUM_REGISTRY.register(bus);
        RegistryItems.WARIUM_REGISTRY.register(bus);

        if (createLoaded || supplementariesLoaded) {
            WariumAdditionsRecipeTypes.register();
            WariumAdditionsRecipeTypes.Recipes.SERIALIZER_REGISTER.register(bus);
            WariumAdditionsRecipeTypes.Recipes.TYPE_REGISTER.register(bus);
        }

        if (modlist.isLoaded("valkyrien_warium")) {
            RegistryItems.REGISTRY.register(bus);
            RegistryBlocks.REGISTRY.register(bus);
            RegistryBlocks.OLD_REGISTRY.register(bus);
            RegistryBlockEntities.REGISTRY.register(bus);
            RegistryBlockEntities.OLD_REGISTRY.register(bus);
        }

        if (createLoaded) {
            context.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
            Registrate.register(bus);
            CreateBlocks.register();
            CreateBlockEntities.register();
            CreateItems.register();
            CreateFluids.register();
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            if (modlist.isLoaded("cloth_config"))
                ClothConfig.registerConfigScreen();
            
            if(modlist.isLoaded("ponderjs"))
                WariumPonder.kubeJSreloadScripts(event);
        }

        @SubscribeEvent
        public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if (createLoaded) {
                if (event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "warium_logistics"))) {
                    event.accept(CreateBlocks.KINETIC_CONVERTER.asStack());
                    event.accept(CreateBlocks.ROTATION_CONVERTER.asStack());
                }

                if (event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "crusty_production")))
                    event.accept(CreateItems.YELLOWCAKE.get());

                if (event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "crusty_components"))) {
                    event.accept(CreateItems.PETROLIUM_BOTTLE.get());
                    event.accept(CreateFluids.YELLOWCAKE_FLUID.getBucket().get());
                }
            }

            if (valkyrienWariumLoaded && event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("valkyrien_warium", "warium_vs")))
                event.accept(RegistryItems.CONTROLLABLE_TRIGGER);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.FORGE)
    public class Events {

        @SubscribeEvent
        public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
            if (createLoaded && event.getItemStack().is(CreateItems.YELLOWCAKE.get()))
                event.setBurnTime(12800);
        }

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent event) {
            if (createLoaded) {
                Player player = event.player;
                Level level = player.level();
                BlockPos pos = player.blockPosition();

                if (level.getFluidState(pos).getType() == CreateFluids.YELLOWCAKE_FLUID.get().getSource() && player.tickCount % 10 == 0)
                    Rad1TickProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }
}
