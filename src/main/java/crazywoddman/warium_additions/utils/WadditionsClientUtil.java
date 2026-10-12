package crazywoddman.warium_additions.utils;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class WadditionsClientUtil {

    public static Optional<Player> getLocalPlayer() {
        return Optional.ofNullable(Minecraft.getInstance().player);
    }
}
