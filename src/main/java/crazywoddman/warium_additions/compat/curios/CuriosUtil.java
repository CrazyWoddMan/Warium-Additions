package crazywoddman.warium_additions.compat.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosUtil {

    public static boolean checkForItem(Player player, String slot, Item item) {
        IDynamicStackHandler belt = player.getCapability(CuriosCapability.INVENTORY).orElse(null).getStacksHandler(slot).orElse(null).getStacks();

        for (int i = 0; i < belt.getSlots(); i++)
            if (belt.getStackInSlot(i).getItem() == item)
                return true;

        return false;
    }

    public static void registerBeltRenderers(Item[] items) {
        for (Item item : items)
            CuriosRendererRegistry.register(item, () -> new BeltItemsCurio());
    }

    public static void registerHeadRenderers(Item[] items) {
        for (Item item : items)
            CuriosRendererRegistry.register(item, () -> new HeadItemsCurio());
    }

    public static void registerBackRenderers(Item[] items) {
        for (Item item : items)
            CuriosRendererRegistry.register(item, () -> new HardpointCurio());
    }
}
