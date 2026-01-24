package crazywoddman.warium_additions.compat.create.blocks.converters;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;

// TODO: fix dial target render

public class RotationConverterRenderer extends KineticBlockEntityRenderer<RotationConverterBlockEntity> {

    public RotationConverterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(RotationConverterBlockEntity blockEntity, BlockState state) {
        Direction facing = state.getValue(RotationConverterBlock.FACING);
        return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state, facing.getOpposite());
    }

    @Override
    protected void renderSafe(RotationConverterBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (VisualizationManager.supportsVisualization(blockEntity.getLevel())) return;

        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);

        BlockState state = blockEntity.getBlockState();
        Direction facing = state.getValue(RotationConverterBlock.FACING);
        AttachFace face = state.getValue(RotationConverterBlock.FACE);
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());

        SuperByteBuffer shaftHalf = CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state, facing.getOpposite());
        standardKineticRotationTransform(shaftHalf, blockEntity, light).renderInto(ms, vb);

        SuperByteBuffer dialBuffer = CachedBuffers.partial(AllPartialModels.GAUGE_DIAL, state);
        SuperByteBuffer headBuffer = CachedBuffers.partial(AllPartialModels.GAUGE_HEAD_SPEED, state);

        float dialPivot = 5.75f / 16;
        float progress = Mth.lerp(partialTicks, blockEntity.prevDialState, blockEntity.dialState);

        if (face == AttachFace.WALL) {
            renderGaugePanelWall(ms, vb, dialBuffer, headBuffer, facing, -1f / 16f, dialPivot, progress, light, overlay);
            renderGaugePanelWall(ms, vb, dialBuffer, headBuffer, facing.getOpposite(), 1f / 16f, dialPivot, progress, light, overlay);
        } else {
            renderGaugePanel(ms, vb, dialBuffer, headBuffer, facing, face, dialPivot, progress, light, overlay);
        }
    }

    private void renderGaugePanelWall(PoseStack ms, VertexConsumer vb, SuperByteBuffer dialBuffer, SuperByteBuffer headBuffer,
                                 Direction panelFacing, float offset, float dialPivot, float progress, int light, int overlay) {
        dialBuffer.rotateCentered((float) ((-panelFacing.toYRot()) / 180 * Math.PI), Axis.YN)
                .translate(0, 0, offset)
                .translate(0, dialPivot, dialPivot)
                .rotate((float) (Math.PI / 2 * -progress), Axis.YN)
                .translate(0, -dialPivot, -dialPivot)
                .light(light)
                .renderInto(ms, vb);

        headBuffer.rotateCentered((float) ((-panelFacing.toYRot()) / 180 * Math.PI), Axis.YN)
                .translate(0, 0, offset)
                .light(light)
                .renderInto(ms, vb);
    }

    private void renderGaugePanel(
        PoseStack ms,
        VertexConsumer vb,
        SuperByteBuffer dialBuffer,
        SuperByteBuffer headBuffer,
        Direction facing,
        AttachFace face,
        float dialPivot,
        float progress,
        int light,
        int overlay
    ) {
        double offset;
        if (face == AttachFace.CEILING)
                offset = ((facing == Direction.SOUTH || facing == Direction.NORTH) ? -1f : 1f)/16f;
            else
                offset = -1f/16f;
        dialBuffer.rotateCentered((face == AttachFace.FLOOR ? -1 : 1) * (float)Math.PI / 2, Axis.YN)
                .rotateCentered((90 - facing.toYRot()) / 180 * (float)Math.PI, Axis.YN)
                .translate(0, offset, 0)
                .translate(0, dialPivot, dialPivot)
                .rotate((float) (Math.PI / 2 * -progress), Axis.YN)
                .translate(0, -dialPivot, -dialPivot)
                .light(light)
                .renderInto(ms, vb);

        headBuffer.rotateCentered((face == AttachFace.FLOOR ? -1 : 1) * (float)Math.PI / 2, Axis.YN)
                .rotateCentered((90 - facing.toYRot()) / 180 * (float)Math.PI, Axis.YN)
                .translate(0, offset, 0)
                .light(light)
                .renderInto(ms, vb);
    }
}
