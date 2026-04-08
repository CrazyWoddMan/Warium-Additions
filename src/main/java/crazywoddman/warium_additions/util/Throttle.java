package crazywoddman.warium_additions.util;

import java.util.Optional;

import net.mcreator.crustychunks.procedures.EngineThrottleSystemProcedure;
import net.mcreator.valkyrienwarium.block.entity.VehicleControlNodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class Throttle {
    public static Optional<Double> get(BlockEntity blockEntity) {
        CompoundTag data = blockEntity.getPersistentData();

        if (data.contains("ControlX")) {
            BlockEntity node = blockEntity.getLevel().getBlockEntity(new BlockPos(
                data.getInt("ControlX"),
                data.getInt("ControlY"),
                data.getInt("ControlZ")
            ));

            if (node instanceof VehicleControlNodeBlockEntity) {
                BlockPos pos = blockEntity.getBlockPos();
                EngineThrottleSystemProcedure.execute(blockEntity.getLevel(), pos.getX(), pos.getY(), pos.getZ());
                double throttle = data.getDouble("Throttle");
                return Optional.of(data.getString("Key").endsWith("+") ? Math.max(0, throttle) : throttle);
            }
        }
        
        return Optional.empty();
    }
}
