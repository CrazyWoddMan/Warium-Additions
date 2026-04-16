package crazywoddman.warium_additions.compat.create.blocks.converters;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.ColoredLitOverlayInstance;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.engine_room.flywheel.lib.visual.SimpleTickableVisual;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.function.Consumer;

import org.joml.Matrix4f;

public class RotationConverterVisual extends KineticBlockEntityVisual<RotationConverterBlockEntity> implements SimpleDynamicVisual, SimpleTickableVisual {

    protected RotatingInstance shaft;
    protected TransformedInstance[] dials = new TransformedInstance[2];
    protected TransformedInstance[] heads = new TransformedInstance[2];
    protected final Direction facing;
    protected final boolean dial;
    protected final Matrix4f[] panelMatrix = new Matrix4f[2];

    public RotationConverterVisual(VisualizationContext context, RotationConverterBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        this.facing = blockState.getValue(RotationConverterBlock.FACING);
        this.dial = blockState.getValue(RotationConverterBlock.DIAL);
        
        this.shaft = instancerProvider()
            .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT_HALF))
            .createInstance()
            .rotateToFace(Direction.SOUTH, facing.getOpposite())
            .setup(blockEntity)
            .setPosition(getVisualPosition());
        this.shaft.setChanged();

        Instancer<TransformedInstance> dialModel = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.GAUGE_DIAL));
        Instancer<TransformedInstance> headModel = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.GAUGE_HEAD_SPEED));
        // ------------------ THE PROCEDURE OF ETERNAL AGONY AND SUFFERING ------------------
        PoseStack pose = new PoseStack();
        PoseTransformStack msr = TransformStack.of(pose);
        msr.translate(getVisualPosition());

        for (int i = 0; i < 2; i++) {
            this.dials[i] = dialModel.createInstance();
            this.heads[i] = headModel.createInstance();
        }

        Direction facing = this.facing;
        int t = -1;

        for (int i = 0; i < 2; i++) {
            PoseStack tempPose = new PoseStack();
            TransformStack<?> transform = TransformStack.of(tempPose);

            transform.center();

            if (this.dial) {
                if (this.facing.getAxis() != Direction.Axis.Y)
                    transform.rotate(-facing.toYRot() / 180f * Mth.PI, Direction.UP);
                else if (t == 1)
                    transform.rotate(Mth.PI, Direction.UP);
            } else if (this.facing.getAxis() == Direction.Axis.Y) {
                transform.rotate(t * Mth.HALF_PI, Direction.UP);
            } else {
                transform.rotate(t * (facing.toYRot() - 90) / 180f * Mth.PI, Direction.UP);
                transform.rotate(t * Mth.HALF_PI, Direction.NORTH);
            }

            transform.uncenter();

            if (this.dial) {
                if (this.facing.getAxis() == Direction.Axis.Y)
                    transform.translate(0, (this.facing == Direction.UP ? -1 : 1) / 16f, 0);
                else transform.translate(0, 0, t / 16f);
            } else {
                float y = this.facing.getAxis() == Direction.Axis.Y
                    ? (this.facing == Direction.UP ? -1 : 1) / 16f
                    : (this.facing.getAxis() == Direction.Axis.X ? -1 : t) / 16f;
                transform.translate(0, y, 0);
            }

            panelMatrix[i] = new Matrix4f(tempPose.last().pose());
            facing = facing.getOpposite();
            t = -t;
        }
        // ---------------------------------------------------------------------------------
        setupDialTransform(pose, msr, Mth.lerp(AnimationTickHolder.getPartialTicks(), blockEntity.prevDialState, blockEntity.dialState));
    }

    protected void forEach(Consumer<ColoredLitOverlayInstance> operation) {
        for (TransformedInstance dial : this.dials)
            operation.accept(dial);

        for (TransformedInstance head : this.heads)
            operation.accept(head);

        operation.accept(this.shaft);
    }

    @Override
    public void tick(SimpleTickableVisual.Context context) {
        if (this.shaft != null)
            this.shaft.setup(blockEntity).setChanged();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        if (Mth.equal(blockEntity.prevDialState, blockEntity.dialState))
            return;

        float progress = Mth.lerp(ctx.partialTick(), blockEntity.prevDialState, blockEntity.dialState);
        PoseStack ms = new PoseStack();
        PoseTransformStack msr = TransformStack.of(ms);
        msr.translate(getVisualPosition());
        setupDialTransform(ms, msr, progress);
    }

    private void setupDialTransform(PoseStack ms, TransformStack<?> msr, float progress) {
        float dialPivot = 5.75f / 16f;

    for (int i = 0; i < 2; i++) {
        msr.pushPose();
        ms.mulPoseMatrix(panelMatrix[i]);
        heads[i].setTransform(ms).setChanged();
        msr.translate(0, dialPivot, dialPivot)
           .rotate(Mth.HALF_PI * -progress, Direction.EAST)
           .translate(0, -dialPivot, -dialPivot);
        dials[i].setTransform(ms).setChanged();
        msr.popPose();
    }
    }

    @Override
    public void updateLight(float partialTick) {
        forEach(this::relight);
    }

    @Override
    protected void _delete() {
        forEach(ColoredLitOverlayInstance::delete);
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        forEach(consumer::accept);
    }
}
