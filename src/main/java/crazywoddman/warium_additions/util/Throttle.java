package crazywoddman.warium_additions.util;

import net.mcreator.valkyrienwarium.block.entity.VehicleControlNodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class Throttle {
    public static Integer get(BlockEntity blockEntity, Integer altThrottle) {
        Integer result = altThrottle;
        CompoundTag data = blockEntity.getPersistentData();

        if (data.contains("ControlX")) {
            BlockEntity controlNode = blockEntity.getLevel().getBlockEntity(
                new BlockPos(
                    data.getInt("ControlX"),
                    data.getInt("ControlY"),
                    data.getInt("ControlZ")
                )
            );

            if (controlNode != null && controlNode instanceof VehicleControlNodeBlockEntity) {
                int throttle = controlNode.getPersistentData().getInt("Throttle");
                String key = data.getString("Key");

                if (throttle != 0 && switch (key) {
                    case "Throttle+" -> throttle > 0;
                    case "Throttle-" -> throttle < 0;
                    default -> true;
                })
                    result = throttle;
                
                else
                    result = 0;
            }
        }
        
        return result;
    }
}
