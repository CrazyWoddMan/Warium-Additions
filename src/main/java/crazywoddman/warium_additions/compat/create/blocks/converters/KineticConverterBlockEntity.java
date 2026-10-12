package crazywoddman.warium_additions.compat.create.blocks.converters;

import java.util.List;

import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity.RotationDirection;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform.Sided;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;

import crazywoddman.warium_additions.config.WadditionsConfig;
import crazywoddman.warium_additions.data.WadditionsTags;
import crazywoddman.warium_additions.utils.WadditionsUtil;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class KineticConverterBlockEntity extends GeneratingKineticBlockEntity {
    private static final float ADDED_STRESS_CAPACITY = (float)(WadditionsConfig.KINETIC_TO_STRESS.get() / WadditionsConfig.KINETIC_TO_SPEED.get());

    public KineticConverterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private float lastThrottle;
    private float lastKineticPower;
    private ScrollOptionBehaviour<RotationDirection> movementDirection;
    private int lastSignal;

    private float scaleFromSignal(float value) {
        return this.lastSignal > 0 ? value / 15 * (15 - this.lastSignal) : value;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        movementDirection = new ScrollOptionBehaviour<RotationDirection>(
            RotationDirection.class,
            CreateLang.translateDirect("contraptions.windmill.rotation_direction"),
            this,
            new ValueBox()
        );
        movementDirection.withCallback(i -> this.updateGeneratedRotation());
        behaviours.add(movementDirection);
    }

    @Override
    public void initialize() {
        super.initialize();
        updateGeneratedRotation();
    }

    @Override
    public float getGeneratedSpeed() {
        return (this.lastKineticPower <= 0 || this.lastThrottle == 0 || this.lastSignal >= 15)
        ? 0
        : convertToDirection(
            scaleFromSignal(
                WadditionsConfig.KINETIC_TO_SPEED.get() * (movementDirection.get() == RotationDirection.CLOCKWISE ? -1 : 1) * this.lastKineticPower
                / WadditionsUtil.MAX_THROTTLE
                * this.lastThrottle
            ), 
            getBlockState().getValue(KineticConverterBlock.FACING)
        );
    }

    @Override
    public float calculateAddedStressCapacity() {
        return this.lastCapacityProvided = ADDED_STRESS_CAPACITY;
    }

    @Override
    public void tick() {
        super.tick();

        BlockPos pos = getBlockPos();
        Direction facing = getBlockState().getValue(KineticConverterBlock.FACING);
        BlockPos backPos = pos.relative(facing.getOpposite());
        BlockState backState = this.level.getBlockState(backPos);
        float newKineticPower = 0;
        float throttle = 0;

        if (backState.is(WadditionsTags.Blocks.KINETIC_OUTPUT_FRONT))
            for (Property<?> property : backState.getProperties())
                if (property instanceof DirectionProperty direction) {
                    if (backState.getValue(direction) == facing) {
                        newKineticPower = this.level.getBlockEntity(backPos).getPersistentData().getFloat("KineticPower");
                        throttle = WadditionsUtil.getThrottle(this).orElse((double)WadditionsUtil.MAX_THROTTLE).floatValue();
                    }

                    break;
                }
        
        // --------------------------------------------------------
        // to prevent Create shafts breaking when changing throttle
        if (this.level.getGameTime() % 8 != 0)
            return;
        // --------------------------------------------------------

        int signal = this.level.getBestNeighborSignal(pos);
        boolean signalChanged;

        if (this.lastSignal != signal) {
            this.lastSignal = signal;
            signalChanged = true;
        }
        else if (signal == 15) return;
        else signalChanged = false;

        boolean powerChanged = this.lastKineticPower != newKineticPower;

        if (powerChanged)
            this.lastKineticPower = newKineticPower;

        boolean throttleChanged = this.lastThrottle != throttle;

        if (throttleChanged)
            this.lastThrottle = throttle;
        
        if (signalChanged || powerChanged || throttleChanged)
            updateGeneratedRotation();
    }

    class ValueBox extends Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 8, 14.5);
        }

        public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {     
            return super.getLocalOffset(level, pos, state).add(Vec3
                .atLowerCornerOf(state.getValue(KineticConverterBlock.FACING).getNormal())
                .scale(2 / 16f)
            );
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return direction.getAxis() != state.getValue(KineticConverterBlock.FACING).getAxis();
        }
    }
}
