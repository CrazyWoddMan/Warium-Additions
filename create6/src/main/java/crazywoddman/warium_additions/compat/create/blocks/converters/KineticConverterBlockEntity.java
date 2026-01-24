package crazywoddman.warium_additions.compat.create.blocks.converters;

import java.util.List;

import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity.RotationDirection;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform.Sided;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class KineticConverterBlockEntity extends GeneratingKineticBlockEntity {

    public KineticConverterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private final int defaultStress = Config.SERVER.kineticToStressRatio.get();
    private final int defaultSpeed = Config.SERVER.kineticToSpeedRatio.get();
    private final int maxThrottle = Config.SERVER.maxThrottle.get();
    private int lastThrottle;
    private float lastKineticPower;
    public ScrollOptionBehaviour<RotationDirection> movementDirection;

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
            
        return (this.lastKineticPower <= 0.0f || this.lastThrottle == 0.0f)
        ? 0
        : convertToDirection(
            this.defaultSpeed * (movementDirection.get() == RotationDirection.CLOCKWISE ? -1 : 1) * this.lastKineticPower
            / this.maxThrottle
            * Math.abs(this.lastThrottle), 
            getBlockState().getValue(KineticConverterBlock.FACING)
        );
    }

    @Override
    public float calculateAddedStressCapacity() {
        float capacity = (float) this.defaultStress / this.defaultSpeed;
        this.lastCapacityProvided = capacity;

        return capacity;
    }

    @Override
    public void tick() {
        super.tick();

        // if (this.level.getGameTime() % 8 != 0)
        //     return;

        Direction facing = getBlockState().getValue(KineticConverterBlock.FACING);
        BlockPos backPos = getBlockPos().relative(facing.getOpposite());
        BlockState backState = this.level.getBlockState(backPos);
        BlockEntity backBlockEntity = this.level.getBlockEntity(backPos);
        float newKineticPower = 0.0f;
        int newThrottle = 0;

        if (backBlockEntity != null) {
            CompoundTag backData = backBlockEntity.getPersistentData();

            if (backData.contains("KineticPower")) {
                DirectionProperty facingProp = null;

                for (Property<?> property : backState.getProperties())
                    if (property instanceof DirectionProperty directionProperty) {
                        facingProp = directionProperty;
                        break;
                    }

                if (facingProp != null && backState.getValue(facingProp) == facing) {
                    newKineticPower = backData.getFloat("KineticPower");
                    newThrottle = WariumAdditionsUtil.getThrottle(this, this.maxThrottle);
                }
            }
        }

        boolean powerChanged = this.lastKineticPower != newKineticPower;

        if (powerChanged)
            this.lastKineticPower = newKineticPower;

        boolean throttleChanged = this.lastThrottle != newThrottle;

        if (throttleChanged)
            this.lastThrottle = newThrottle;
        
        if (powerChanged || throttleChanged)
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
