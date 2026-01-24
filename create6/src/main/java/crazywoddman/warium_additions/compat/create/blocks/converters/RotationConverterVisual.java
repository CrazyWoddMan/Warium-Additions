package crazywoddman.warium_additions.compat.create.blocks.converters;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
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
import net.minecraft.world.level.block.state.properties.AttachFace;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.function.Consumer;

public class RotationConverterVisual extends KineticBlockEntityVisual<RotationConverterBlockEntity> implements SimpleDynamicVisual, SimpleTickableVisual {

    protected RotatingInstance shaft;
    protected TransformedInstance dial, head, dial2, head2;
    final Direction facing, opposite;
    final AttachFace face;

    public RotationConverterVisual(VisualizationContext context, RotationConverterBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        facing = blockState.getValue(RotationConverterBlock.FACING);
        face = blockState.getValue(RotationConverterBlock.FACE);
        opposite = facing.getOpposite();
        
        shaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT_HALF))
            .createInstance()
            .rotateToFace(Direction.SOUTH, opposite)
            .setup(blockEntity)
            .setPosition(getVisualPosition());
        shaft.setChanged();

        Instancer<TransformedInstance> dialModel = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.GAUGE_DIAL));
        Instancer<TransformedInstance> headModel = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.GAUGE_HEAD_SPEED));

        PoseStack ms = new PoseStack();
        var msr = TransformStack.of(ms);
        msr.translate(getVisualPosition());

        float progress = Mth.lerp(AnimationTickHolder.getPartialTicks(), blockEntity.prevDialState, blockEntity.dialState);

        dial = dialModel.createInstance();
        head = headModel.createInstance();

        if (face == AttachFace.WALL) {
            dial2 = dialModel.createInstance();
            head2 = headModel.createInstance();
        }

        setupDialTransform(ms, msr, progress);
    }

    @Override
    public void tick(SimpleTickableVisual.Context context) {
        if (shaft != null)
            shaft.setup(blockEntity).setChanged();
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

        msr.pushPose();

        if (face == AttachFace.WALL) {
            msr.center()
               .rotate((float) ((-facing.toYRot()) / 180 * Math.PI), Direction.UP)
               .uncenter();
            msr.translate(0, 0, -1f / 16f);

            head.setTransform(ms).setChanged();
            
            msr.translate(0, dialPivot, dialPivot)
               .rotate((float) (Math.PI / 2 * -progress), Direction.EAST)
               .translate(0, -dialPivot, -dialPivot);

            dial.setTransform(ms).setChanged();

            msr.popPose();
            msr.pushPose();
            
            msr.center()
               .rotate(-opposite.toYRot() / 180 * (float)Math.PI, Direction.UP)
               .uncenter();
            msr.translate(0, 0, 1f / 16f);

            head2.setTransform(ms).setChanged();
            
            msr.translate(0, dialPivot, dialPivot)
               .rotate((float) (Math.PI / 2 * -progress), Direction.EAST)
               .translate(0, -dialPivot, -dialPivot);

            dial2.setTransform(ms).setChanged();
        } else {
            msr.center()
               .rotate((face == AttachFace.FLOOR ? -1 : 1) * (float)Math.PI / 2, Direction.NORTH)
               .rotate((90 - facing.toYRot()) / 180 * (float)Math.PI, Direction.EAST)
               .uncenter();

            if (face == AttachFace.CEILING)
                msr.translate(0, ((facing == Direction.SOUTH || facing == Direction.NORTH) ? -1f : 1f)/16f, 0);
            else
                msr.translate(0, -1f/16f, 0);

            head.setTransform(ms).setChanged();

            msr.translate(0, dialPivot, dialPivot)
               .rotate((float) (Math.PI / 2 * -progress), Direction.EAST)
               .translate(0, -dialPivot, -dialPivot);

            dial.setTransform(ms).setChanged();
        }

        msr.popPose();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(this.shaft);
        relight(this.dial, this.head);
        
        if (this.dial2 != null && this.head2 != null)
            relight(this.dial2, this.head2);
    }

    @Override
    protected void _delete() {
        this.shaft.delete();
        this.dial.delete();
        this.head.delete();
        if (this.dial2 != null) dial2.delete();
        if (this.head2 != null) head2.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(this.shaft);
        consumer.accept(this.dial);
        consumer.accept(this.head);
        if (this.dial2 != null) consumer.accept(this.dial2);
        if (this.head2 != null) consumer.accept(this.head2);
    }
}
