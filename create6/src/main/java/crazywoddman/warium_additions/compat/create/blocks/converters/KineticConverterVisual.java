package crazywoddman.warium_additions.compat.create.blocks.converters;

import java.util.function.Consumer;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Quaternionf;

public class KineticConverterVisual extends KineticBlockEntityVisual<KineticConverterBlockEntity> implements SimpleDynamicVisual {

    protected TransformedInstance shaft;
    final Direction facing;

    public KineticConverterVisual(VisualizationContext context, KineticConverterBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        facing = blockState.getValue(KineticConverterBlock.FACING);
        
        shaft = instancerProvider().instancer(InstanceTypes.TRANSFORMED, 
            Models.partial(AllPartialModels.SHAFT_HALF))
            .createInstance();
        
        transformModels(partialTick);
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        transformModels(ctx.partialTick());
    }

    private void transformModels(float partialTick) {
        float angle = getRotationAngle(facing.getAxis());
        float speed = blockEntity.getSpeed();
        
        // Вычисляем угол вращения
        float rotationAngle = (angle + speed * partialTick) % 360;
        
        // Создаем PoseStack для трансформаций
        PoseStack poseStack = new PoseStack();
        
        // Применяем вращение
        Quaternionf rotation = new Quaternionf();
        switch (facing.getAxis()) {
            case X -> rotation.rotateX((float) Math.toRadians(rotationAngle));
            case Y -> rotation.rotateY((float) Math.toRadians(rotationAngle));
            case Z -> rotation.rotateZ((float) Math.toRadians(rotationAngle));
        }
        
        poseStack.mulPose(rotation);
        
        shaft.setTransform(poseStack);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(shaft);
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(shaft);
    }

    @Override
    protected void _delete() {
        shaft.delete();
    }

    private float getRotationAngle(Direction.Axis axis) {
        return KineticBlockEntityVisual.rotationOffset(blockState, axis, blockEntity.getBlockPos());
    }
}
