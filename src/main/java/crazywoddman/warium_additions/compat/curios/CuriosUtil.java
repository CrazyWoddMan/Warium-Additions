package crazywoddman.warium_additions.compat.curios;

import java.util.Optional;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosUtil {
    public static Optional<IDynamicStackHandler> getSlot(LivingEntity entity, String slotID) {
        return CuriosApi
            .getCuriosInventory(entity)
            .resolve()
            .flatMap(inventory -> getSlot(inventory, slotID));
    }

    public static Optional<IDynamicStackHandler> getSlot(ICuriosItemHandler inventory, String slot) {
        return inventory
            .getStacksHandler(slot)
            .filter(handler -> {
                int slots = handler.getSlots();

                if (slots > 0) {
                    IDynamicStackHandler stacks = handler.getStacks();

                    for (int i = 0; i < slots; i++)
                        if (!stacks.getStackInSlot(i).isEmpty())
                            return true;
                }

                return false;
            })
            .map(ICurioStacksHandler::getStacks);
    }

    public static Optional<ItemStack> getItem(LivingEntity entity, String slotID, Item item) {
        return getSlot(entity, slotID).flatMap(stacks -> {
            for (int i = 0; i < stacks.getSlots(); i++) {
                ItemStack stack = stacks.getStackInSlot(i);

                if (stack.is(item))
                    return Optional.of(stack);
            }

            return Optional.empty();
        });
    }

    public static void registerBeltRenderers(Item[] items) {
        for (Item item : items)
            CuriosRendererRegistry.register(item, BeltItemsCurio::new);
    }

    public static void registerHeadRenderers(Item[] items) {
        for (Item item : items)
            CuriosRendererRegistry.register(item, HeadItemsCurio::new);
    }

    public static void registerSpecialRenderers() {
        CuriosRendererRegistry.register(CrustyChunksModItems.GAS_MASK_HELMET.get(), GasMaskCurio::new);
        CuriosRendererRegistry.register(CrustyChunksModItems.FLAME_THROWER_TANK_CHESTPLATE.get(), FlameThrowerTankCurio::new);
    }

    public static void registerHardpointRenderers(Item[] items) {
        for (Item item : items)
            CuriosRendererRegistry.register(item, HardpointCurio::new);
    }
}
