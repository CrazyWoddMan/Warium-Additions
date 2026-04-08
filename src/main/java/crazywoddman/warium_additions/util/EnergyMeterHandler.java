package crazywoddman.warium_additions.util;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class EnergyMeterHandler {
    private boolean isActive;
    
    public void tick(Level level, Entity entity) {
        if (level.isClientSide && entity instanceof Player player) {
            boolean isActive = false;
            Item ENERGY_METER = CrustyChunksModItems.ENERGY_METER.get();
            
            if (player.getMainHandItem().getItem() == ENERGY_METER || player.getOffhandItem().getItem() == ENERGY_METER) {
                if (WariumAdditionsUtil.getTargetBlock(player, level).map(blockEntity -> 
                        blockEntity.getCapability(ForgeCapabilities.ENERGY).map(storage -> {
                            player.displayClientMessage(
                                Component
                                    .translatable("warium_additions.tooltip.energy.energy")
                                    .append(Component.literal(": " + WariumAdditionsUtil.formatFE(storage.getEnergyStored()))),
                                true
                            );

                            return true;
                        }).orElse(false)
                    ).orElse(false)
                )
                    isActive = true;
            }

            if (this.isActive && !isActive)
                player.displayClientMessage(Component.empty(), true);
            
            this.isActive = isActive;
        }
    }
}