package crazywoddman.warium_additions.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HardpointCurio implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
        ItemStack stack,
        SlotContext slotContext,
        PoseStack poseStack,
        RenderLayerParent<T, M> renderLayerParent,
        MultiBufferSource multiBufferSource,
        int light,
        float limbSwing,
        float limbSwingAmount,
        float partialTicks,
        float ageInTicks,
        float netHeadYaw,
        float headPitch
    ) {
        poseStack.pushPose();

        if (renderLayerParent.getModel() instanceof HumanoidModel<?> model)
            model.body.translateAndRotate(poseStack);

        LivingEntity entity = slotContext.entity();
        poseStack.translate(0.0, 0.35, 0.475);
        poseStack.scale(1.4F, 1.4F, 1.4F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        Item item = stack.getItem();
        Level level = entity.level();
        Minecraft
        .getInstance()
        .getItemRenderer()
        .renderStatic(
            new ItemStack(switch (ForgeRegistries.ITEMS.getKey(item).getPath()) {
                case "radar_spear_missile" -> CrustyChunksModItems.RADAR_SPEAR_MISSILE_HARDPOINT.get();
                case "strike_spear_missile" -> CrustyChunksModItems.STRIKE_SPEAR_MISSILE_HARDPOINT.get();
                case "seeker_spear_rocket" -> CrustyChunksModItems.SEEKER_SPEAR_MISSILE_HARDPOINT.get();
                case "fire_spear_rocket" -> CrustyChunksModItems.FIRE_SPEAR_MISSILE_HARDPOINT.get();
                default -> item;
            }),
            ItemDisplayContext.FIXED,
            LevelRenderer.getLightColor(level, entity.blockPosition().above()),
            OverlayTexture.NO_OVERLAY,
            poseStack,
            multiBufferSource,
            level,
            0
        );
        
        poseStack.popPose();
    }
}