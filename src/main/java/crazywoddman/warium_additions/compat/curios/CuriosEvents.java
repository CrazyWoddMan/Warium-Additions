package crazywoddman.warium_additions.compat.curios;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosEvents {
    private static final ImmutableMultimap<String, AttributeModifier> HARDPOINT_SLOT_MAP = ImmutableMultimap.of(
        "hardpoint",
        new AttributeModifier(
            UUID.fromString("14881488-1488-1488-1488-148814881488"),
            "Hardpoint",
            1.0,
            AttributeModifier.Operation.ADDITION
        )
    );
    private static final ImmutableMultimap<String, AttributeModifier> AMMOBOX_SLOT_MAP = ImmutableMultimap.of(
        "ammobox",
        new AttributeModifier(
            UUID.fromString("69696969-6969-6969-6969-696969696969"),
            "Ammobox",
            1.0,
            AttributeModifier.Operation.ADDITION
        )
    );
    
    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        if (event.getEntity() instanceof ServerPlayer player)
            CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {
                ICurioStacksHandler requiredSlot = inventory.getStacksHandler("back").orElse(null);
                ICurioStacksHandler targetSlot = inventory.getStacksHandler("hardpoint").orElse(null);
                IDynamicStackHandler stacks;
                int slots;
                boolean hasItem = false;

                if (requiredSlot != null && targetSlot != null) {
                    stacks = requiredSlot.getStacks();
                    slots = targetSlot.getSlots();

                    for (int i = 0; i < stacks.getSlots(); i++)
                        if (stacks.getStackInSlot(i).is(CrustyChunksModItems.EMPTY_MISSILE_HARDPOINT.get())) {
                            hasItem = true;
                            break;
                        }

                    if (hasItem && slots == 0)
                        inventory.addTransientSlotModifiers(HARDPOINT_SLOT_MAP);

                    else if (!hasItem && slots > 0)
                        inventory.removeSlotModifiers(HARDPOINT_SLOT_MAP);
                }

                requiredSlot = inventory.getStacksHandler("head").orElse(null);
                targetSlot = inventory.getStacksHandler("ammobox").orElse(null);

                if (requiredSlot != null && targetSlot != null) {
                    stacks = requiredSlot.getStacks();
                    hasItem = false;
                    slots = targetSlot.getSlots();

                    for (int i = 0; i < stacks.getSlots(); i++)
                        if (stacks.getStackInSlot(i).is(CrustyChunksModItems.LIGHT_MACHINE_GUN.get())) {
                            hasItem = true;
                            break;
                        }

                    if (hasItem && slots == 0)
                        inventory.addTransientSlotModifiers(AMMOBOX_SLOT_MAP);

                    else if (!hasItem && slots > 0)
                        inventory.removeSlotModifiers(AMMOBOX_SLOT_MAP);
                }
            });
    }
}