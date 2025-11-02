package crazywoddman.warium_additions.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class BeltItemsCurio implements ICurioRenderer {

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
        poseStack.translate(
            0.15,
            entity instanceof Player player && player.isCrouching() ? 0.5 : 0.64,
            -0.14
        );
        poseStack.scale(0.3F, 0.3F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.ZP.rotationDegrees(45));
        Minecraft
        .getInstance()
        .getItemRenderer()
        .renderStatic(
            stack,
            ItemDisplayContext.FIXED,
            light,
            LightTexture.FULL_BRIGHT,
            poseStack,
            multiBufferSource,
            entity.level(),
            0
        );
        
        poseStack.popPose();
    }
}