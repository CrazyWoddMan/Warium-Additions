package crazywoddman.warium_additions.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HeadItemsCurio implements ICurioRenderer {

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
            model.head.translateAndRotate(poseStack);

        poseStack.translate(0.0F, -0.25F, 0.0F);
        poseStack.scale(2.1F, 2.1F, 2.1F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        LivingEntity entity = slotContext.entity();
        Level level = entity.level();
        Minecraft
        .getInstance()
        .getItemRenderer()
        .renderStatic(
            stack,
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