package crazywoddman.warium_additions.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.crustychunks.client.model.ModelGasmask_Converted_Converted;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer.ModelRender;

public class GasMaskCurio implements ModelRender<ModelGasmask_Converted_Converted<LivingEntity>> {

    private final ModelGasmask_Converted_Converted<LivingEntity> model;
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/entities/gasmask.png");

    public GasMaskCurio() {
        EntityModelSet models = Minecraft.getInstance().getEntityModels();
        this.model = new ModelGasmask_Converted_Converted<>(models.bakeLayer(ModelGasmask_Converted_Converted.LAYER_LOCATION));
    }

    @Override
    public ModelGasmask_Converted_Converted<LivingEntity> getModel(ItemStack stack, SlotContext slotContext) {
        return this.model;
    }

    @Override
    public ResourceLocation getModelTexture(ItemStack stack, SlotContext slotContext) {
        return TEXTURE;
    }

    @Override
    public void prepareModel(
        ItemStack stack,
        SlotContext slotContext,
        PoseStack poseStack,
        RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> renderLayerParent,
        float limbSwing,
        float limbSwingAmount,
        float partialTicks,
        float ageInTicks,
        float netHeadYaw,
        float headPitch
    ) {
        EntityModel<LivingEntity> parentModel = renderLayerParent.getModel();
        
        if (parentModel instanceof HumanoidModel<?> humanoidModel) {
            ModelPart head = humanoidModel.head;
            ModelPart mask = this.model.bone;

            mask.setRotation(head.xRot, head.yRot, head.zRot);
            mask.setPos(head.x, head.y, head.z);
        }
    }
}