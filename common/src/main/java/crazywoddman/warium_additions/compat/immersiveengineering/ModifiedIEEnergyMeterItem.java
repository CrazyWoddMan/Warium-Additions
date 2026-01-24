package crazywoddman.warium_additions.compat.immersiveengineering;

import blusunrize.immersiveengineering.common.items.VoltmeterItem;
import crazywoddman.warium_additions.util.EnergyMeterHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModifiedIEEnergyMeterItem extends VoltmeterItem {
   private final EnergyMeterHandler handler = new EnergyMeterHandler();

   @Override
   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      super.inventoryTick(stack, level, entity, slotId, isSelected);
      this.handler.tick(level, entity);
   }
}
