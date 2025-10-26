package crazywoddman.warium_additions;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.mcreator.crustychunks.network.CrustyChunksModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CustomCrosshairOverlay {

    private static final ResourceLocation ICONS = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/icons.png");
    private static final ResourceLocation ICONS_SNEAK = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/iconssneak.png");
    private static final ResourceLocation ICONS_SHOTGUN = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/iconshotgun.png");
    private static final ResourceLocation ICONS_SHOTGUN_SNEAK = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/iconshotgunsneak.png");
    
    private static final ResourceLocation PUMP_SHOTGUN = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "pump_action_shotgun_animated");
    private static final ResourceLocation BREAK_SHOTGUN = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "break_action_shotgun_animated");

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onRenderCrosshairPre(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().id().toString().equals("minecraft:crosshair")) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            
            if (player != null && mc.options.getCameraType().isFirstPerson()) {
                ItemStack mainHandItem = player.getMainHandItem();
                
                if (mainHandItem.is(ItemTags.create(ResourceLocation.tryParse("crusty_chunks:firearm")))) {
                    event.setCanceled(true);
                    ResourceLocation crosshairTexture = getCrosshairTexture(player, mainHandItem);
                    
                    if (crosshairTexture != null) {
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.setShaderTexture(0, crosshairTexture);
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();

                        int crosshairSize = 16;

                        event.getGuiGraphics().blit(
                            crosshairTexture,
                            (mc.getWindow().getGuiScaledWidth() - crosshairSize) / 2,
                            (mc.getWindow().getGuiScaledHeight() - crosshairSize) / 2,
                            0, 0,
                            crosshairSize, crosshairSize,
                            256, 256
                        );
                        RenderSystem.disableBlend();
                    }
                }
            }
        }
    }

    private static ResourceLocation getCrosshairTexture(Player player, ItemStack mainHandItem) {
        boolean isAiming = player.getCapability(CrustyChunksModVariables.PLAYER_VARIABLES_CAPABILITY)
            .orElse(new CrustyChunksModVariables.PlayerVariables())
            .AimDownSights;

        return List.of(PUMP_SHOTGUN, BREAK_SHOTGUN).contains(ForgeRegistries.ITEMS.getKey(mainHandItem.getItem()))
        ? (isAiming ? ICONS_SHOTGUN_SNEAK : ICONS_SHOTGUN)
        : (isAiming ? ICONS_SNEAK : ICONS);
    }
}