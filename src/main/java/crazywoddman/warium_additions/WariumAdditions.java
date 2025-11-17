package crazywoddman.warium_additions;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;

import crazywoddman.warium_additions.compat.create.CreateBlockEntities;
import crazywoddman.warium_additions.compat.create.CreateBlocks;
import crazywoddman.warium_additions.compat.create.CreateFluids;
import crazywoddman.warium_additions.compat.create.CreateItems;
import crazywoddman.warium_additions.compat.create.Registrate;
import crazywoddman.warium_additions.compat.curios.CuriosEvents;
import crazywoddman.warium_additions.compat.curios.CuriosUtil;
import crazywoddman.warium_additions.config.ClothConfig;
import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.network.ShootKeyPacket;
import crazywoddman.warium_additions.network.LaunchKeyPacket;
import crazywoddman.warium_additions.network.NetworkHandler;
import crazywoddman.warium_additions.recipe.WariumAdditionsRecipeTypes;
import crazywoddman.warium_additions.registry.RegistryBlockEntities;
import crazywoddman.warium_additions.registry.RegistryBlocks;
import crazywoddman.warium_additions.registry.RegistryItems;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.Rad1TickProcedure;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(WariumAdditions.MODID)
public class WariumAdditions {
    public static final String MODID = "warium_additions";
    
    private static final ModList modlist = ModList.get();
    public static final boolean cloth_config = modlist.isLoaded("cloth_config");
    public static final boolean curios = modlist.isLoaded("curios");
    public static final boolean supplementaries = modlist.isLoaded("supplementaries");
    public static final boolean valkyrien_warium = modlist.isLoaded("valkyrien_warium");
    public static final boolean immersiveengineering = modlist.isLoaded("immersiveengineering");
    public static final boolean create = modlist
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

        if (cloth_config)
            context.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        if (create || supplementaries) {
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

        if (create) {
            Registrate.register(bus);
            CreateBlocks.register();
            CreateBlockEntities.register();
            CreateItems.register();
            CreateFluids.register();
        }

        if (curios) {
            NetworkHandler.register();
            MinecraftForge.EVENT_BUS.register(CuriosEvents.class);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {

        public static KeyMapping SHOOT_KEY;
        public static KeyMapping LAUNCH_KEY;

        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            if (curios) {
                SHOOT_KEY = new KeyMapping(
                    "key." + WariumAdditions.MODID + ".shoot",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_R,
                    "key.categories." + WariumAdditions.MODID
                );
                LAUNCH_KEY = new KeyMapping(
                    "key." + WariumAdditions.MODID + ".launch",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_R,
                    "key.categories." + WariumAdditions.MODID
                );
                event.register(SHOOT_KEY);
                event.register(LAUNCH_KEY);
            }
        }

        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            if (curios) {
                CuriosUtil.registerBeltRenderers(new Item[]{CrustyChunksModItems.ENERGY_METER.get(), CrustyChunksModItems.GEIGER_COUNTER.get()});
                CuriosUtil.registerHeadRenderers(new Item[]{CrustyChunksModItems.LIGHT_MACHINE_GUN.get()});
                CuriosUtil.registerSpecialRenderers();
                CuriosUtil.registerHardpointRenderers(new Item[]{
                    CrustyChunksModItems.EMPTY_MISSILE_HARDPOINT.get(),
                    CrustyChunksModItems.FIRE_SPEAR_ROCKET.get(),
                    CrustyChunksModItems.SEEKER_SPEAR_ROCKET.get(),
                    CrustyChunksModItems.RADAR_SPEAR_MISSILE.get(),
                    CrustyChunksModItems.STRIKE_SPEAR_MISSILE.get()
                });
            }
            
            if (modlist.isLoaded("cloth_config"))
                ClothConfig.registerConfigScreen();
            
            if(modlist.isLoaded("ponderjs"))
                WariumPonder.kubeJSreloadScripts(event);
        }

        @SubscribeEvent
        public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if (create) {
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

            if (valkyrien_warium && event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("valkyrien_warium", "warium_vs")))
                event.accept(RegistryItems.CONTROLLABLE_TRIGGER);
        }
    }

    @Mod.EventBusSubscriber(modid = WariumAdditions.MODID, value = Dist.CLIENT)
    public class ClientKeyEvents {
        
        private static void playDrySound(Player player) {
            player.level().playSound(
                player,
                player.blockPosition(),
                ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "dryfire")),
                SoundSource.NEUTRAL,
                1.0F,
                Mth.nextFloat(RandomSource.create(), 0.9f, 1.1f)
            );
        }
        
        @SubscribeEvent
        public static void onKeyInput(Key event) {
            if (curios) {
                if (ClientEvents.SHOOT_KEY.consumeClick()) {
                    LocalPlayer player = Minecraft.getInstance().player;
                    CuriosUtil.getItem(player, "head", CrustyChunksModItems.LIGHT_MACHINE_GUN.get()).ifPresent(s ->
                        CuriosUtil.getItem(player, "ammobox", CrustyChunksModItems.MACHINE_GUN_BOX.get()).ifPresentOrElse(stack -> {
                            if (stack.getOrCreateTag().getInt("AmmoSize") == -1 && stack.getOrCreateTag().getInt("Ammo") > 0)
                                NetworkHandler.CHANNEL.sendToServer(new ShootKeyPacket());
                            else
                                playDrySound(player);
                        }, () -> playDrySound(player))
                    );
                }

                if (ClientEvents.LAUNCH_KEY.consumeClick())
                    NetworkHandler.CHANNEL.sendToServer(new LaunchKeyPacket());
            }
        }

        private static final ResourceLocation BARREL_OVERLAY = 
            ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/barrel.png");

        @SubscribeEvent
        public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
            if (curios) {
                Minecraft mc = Minecraft.getInstance();
                
                if (mc.player != null && mc.options.getCameraType().isFirstPerson() && CuriosUtil.getItem(mc.player, "head", CrustyChunksModItems.LIGHT_MACHINE_GUN.get()).isPresent()) {
                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);
                    RenderSystem.enableBlend();
                    
                    GuiGraphics guiGraphics = event.getGuiGraphics();
                    int screenWidth = guiGraphics.guiWidth();
                    int screenHeight = guiGraphics.guiHeight();
                    int size = Math.max(screenWidth, screenHeight);
                    int offsetX = (screenWidth - size) / 2;
                    int offsetY = (screenHeight - size) / 2;
                    
                    guiGraphics.blit(
                        BARREL_OVERLAY,
                        offsetX, offsetY,
                        -90,
                        0.0F, 0.0F,
                        size, size,
                        size, size
                    );
                    
                    RenderSystem.disableBlend();
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {

        @SubscribeEvent
        public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
            if (create && event.getItemStack().is(CreateItems.YELLOWCAKE.get()))
                event.setBurnTime(12800);
        }

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent event) {
            if (create) {
                Player player = event.player;
                Level level = player.level();
                BlockPos pos = player.blockPosition();

                if (level.getFluidState(pos).getType() == CreateFluids.YELLOWCAKE_FLUID.get().getSource() && player.tickCount % 10 == 0)
                    Rad1TickProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }
}
