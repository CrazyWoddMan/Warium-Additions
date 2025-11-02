package crazywoddman.warium_additions.util;

import crazywoddman.warium_additions.WariumAdditions;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ThrottleProvider {
    /**
     * Gets {@code throttle} from Control Node
     * @param blockEntity this BlockEntity
     * @param altThrottle
     * @return {@code altThrottle} if Valkyrien Warium is not installed, Control Node position is not set or Control Node not found;
     * {@code 0} if configured key restricts current {@code throttle} value;
     * {@code throttle} set by Control Node in other cases
     */
    public static Integer get(BlockEntity blockEntity, Integer altThrottle) {
        return WariumAdditions.valkyrien_warium ? Throttle.get(blockEntity, altThrottle) : altThrottle;
    }
}
