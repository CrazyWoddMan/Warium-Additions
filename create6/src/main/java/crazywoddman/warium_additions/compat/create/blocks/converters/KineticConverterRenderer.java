package crazywoddman.warium_additions.compat.create.blocks.converters;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class KineticConverterRenderer extends KineticBlockEntityRenderer<KineticConverterBlockEntity> {

    public KineticConverterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(KineticConverterBlockEntity blockEntity, BlockState state) {
        Direction facing = state.getValue(KineticConverterBlock.FACING);
        return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state, facing);
    }

    @Override
    protected void renderSafe(KineticConverterBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (VisualizationManager.supportsVisualization(blockEntity.getLevel())) return;

        BlockState state = blockEntity.getBlockState();
        Direction facing = state.getValue(KineticConverterBlock.FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        int lightBehind = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().relative(facing.getOpposite()));
        SuperByteBuffer shaftHalf = CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state, facing);
        
        standardKineticRotationTransform(shaftHalf, blockEntity, lightBehind).renderInto(ms, vb);
    }
}
