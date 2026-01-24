package crazywoddman.warium_additions.compat.create;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Template
 */
public interface EngineersGoggles {
    default boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		return false;
	}

    default boolean containedFluidTooltip(List<Component> tooltip, boolean isPlayerSneaking, LazyOptional<IFluidHandler> handler) {
        return false;
    }
}
