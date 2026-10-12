package crazywoddman.warium_additions.client;

import com.mojang.blaze3d.systems.RenderSystem;

import crazywoddman.warium_additions.data.WadditionsTags;
import crazywoddman.warium_additions.mixins.crusty_chunks.firearms.WeaponCrossOverlayCancel;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.network.CrustyChunksModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;

/**
 * A replacement for Warium's Weapon Cross, compatible with HUD-affecting Resource Packs
 * @see WeaponCrossOverlayCancel
 */
public class PatchedCrosshairOverlay {
    private static final ResourceLocation
    ICONS = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/icons.png"),
    ICONS_SNEAK = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/iconssneak.png"),
    ICONS_SHOTGUN = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/iconshotgun.png"),
    ICONS_SHOTGUN_SNEAK = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/screens/iconshotgunsneak.png");

    public static void onRenderCrosshairPre(RenderGuiOverlayEvent.Pre event) {
        if (!event.getOverlay().id().toString().equals("minecraft:crosshair"))
            return;

        Minecraft mc = Minecraft.getInstance();
        
        if (mc.player == null || !mc.options.getCameraType().isFirstPerson())
            return;

        ItemStack stack = mc.player.getMainHandItem();
            
        if (!stack.is(WadditionsTags.Items.FIREARMS))
            return;

        event.setCanceled(true);
        boolean isAiming = mc.player.getCapability(CrustyChunksModVariables.PLAYER_VARIABLES_CAPABILITY)
                                    .map(cap -> cap.AimDownSights)
                                    .orElse(false);
        ResourceLocation crosshair;

        if (stack.is(CrustyChunksModItems.PUMP_ACTION_SHOTGUN_ANIMATED.get()) || stack.is(CrustyChunksModItems.BREAK_ACTION_SHOTGUN_ANIMATED.get())) {
            crosshair = isAiming ? ICONS_SHOTGUN_SNEAK : ICONS_SHOTGUN;
        } else {
            crosshair = isAiming ? ICONS_SNEAK : ICONS;
        }
        
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, crosshair);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        event.getGuiGraphics().blit(
            crosshair,
            (mc.getWindow().getGuiScaledWidth() - 16) / 2,
            (mc.getWindow().getGuiScaledHeight() - 16) / 2,
            0, 0,
            16, 16,
            256, 256
        );
        RenderSystem.disableBlend();
    }
}