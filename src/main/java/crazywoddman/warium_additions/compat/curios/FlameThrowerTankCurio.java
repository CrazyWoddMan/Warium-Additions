package crazywoddman.warium_additions.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;

import net.mcreator.crustychunks.client.model.ModelFlamePack;
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

public class FlameThrowerTankCurio implements ModelRender<ModelFlamePack<LivingEntity>> {

    private final ModelFlamePack<LivingEntity> model;
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("crusty_chunks", "textures/entities/fuelpack.png");

    public FlameThrowerTankCurio() {
        EntityModelSet models = Minecraft.getInstance().getEntityModels();
        this.model = new ModelFlamePack<>(models.bakeLayer(ModelFlamePack.LAYER_LOCATION));
    }

    @Override
    public ModelFlamePack<LivingEntity> getModel(ItemStack stack, SlotContext slotContext) {
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
        
        if (parentModel instanceof HumanoidModel<?> model) {
            ModelPart body = model.body;

            for (ModelPart part : new ModelPart[]{this.model.Pack, this.model.bone, this.model.bone2}) {
                part.setRotation(body.xRot, body.yRot, body.zRot);
                part.setPos(body.x, body.y, body.z);
            }
        }
    }
}