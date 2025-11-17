package crazywoddman.warium_additions.compat.curios;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;

import crazywoddman.warium_additions.WariumAdditions;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;

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
    public static Result onCurioEquip(CurioEquipEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getSlotContext().identifier().equals("hardpoint")) {
                if (player instanceof LocalPlayer localPlayer)
                    localPlayer.displayClientMessage(
                        Component
                            .translatable(WariumAdditions.MODID + ".tooltip.presskey")
                            .append(" " + WariumAdditions.ClientEvents.LAUNCH_KEY.getKey().getDisplayName().getString() + " ")
                            .append(Component.translatable(WariumAdditions.MODID + ".tooltip.launch_missile")),
                        true
                    );
                player.level().playLocalSound(
                    player.getX(), player.getY(), player.getZ(),
                    ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.tryParse("block.iron_trapdoor.open")),
                    SoundSource.NEUTRAL,
                    1.0F,
                    0.3F,
                    false
                );
            }
            else CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {
                switch (ForgeRegistries.ITEMS.getKey(event.getStack().getItem()).getPath()) {
                    case "light_machine_gun" -> inventory.addTransientSlotModifiers(AMMOBOX_SLOT_MAP);
                    case "empty_missile_hardpoint" -> inventory.addTransientSlotModifiers(HARDPOINT_SLOT_MAP);
                    case "machine_gun_box" -> {
                        playAmmoboxEquipSound(player);
                        if (player instanceof LocalPlayer localPlayer)
                            localPlayer.displayClientMessage(
                                Component
                                    .translatable(WariumAdditions.MODID + ".tooltip.presskey")
                                    .append(" " + WariumAdditions.ClientEvents.SHOOT_KEY.getKey().getDisplayName().getString() + " ")
                                    .append(Component.translatable(WariumAdditions.MODID + ".tooltip.shoot")),
                                true
                            );
                    }
                } 
            });
        }
        return event.getResult();
    }

    @SubscribeEvent
    public static Result onCurioUnequip(CurioUnequipEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getSlotContext().identifier().equals("hardpoint"))
                player.level().playLocalSound(
                    player.getX(), player.getY(), player.getZ(),
                    ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.tryParse("block.iron_trapdoor.open")),
                    SoundSource.NEUTRAL,
                    1.0F,
                    0.3F,
                    false
                );
            else CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {
                switch (ForgeRegistries.ITEMS.getKey(event.getStack().getItem()).getPath()) {
                    case "light_machine_gun" -> inventory.removeSlotModifiers(AMMOBOX_SLOT_MAP);
                    case "empty_missile_hardpoint" -> inventory.removeSlotModifiers(HARDPOINT_SLOT_MAP);
                    case "machine_gun_box" -> playAmmoboxEquipSound(player);
                }
            });
        }

        return event.getResult();
    }

    private static void playAmmoboxEquipSound(Player player) {
        player.level().playLocalSound(
            player.getX(), player.getY(), player.getZ(),
            ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "pistolaction")),
            SoundSource.NEUTRAL,
            1.0F,
            0.8F,
            false
        );
    }
}