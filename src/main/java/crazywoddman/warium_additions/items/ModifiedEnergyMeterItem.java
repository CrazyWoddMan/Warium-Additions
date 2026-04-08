package crazywoddman.warium_additions.items;

import crazywoddman.warium_additions.util.EnergyMeterHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Makes <b>Energy Meter</b> check the energy of the block that the player is currently pointing at, eliminating the need to click
 */
public class ModifiedEnergyMeterItem extends Item {
    public ModifiedEnergyMeterItem() {
        super(new Properties().stacksTo(1));
    }

    private final EnergyMeterHandler handler = new EnergyMeterHandler();

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        this.handler.tick(level, entity);
    }
}
