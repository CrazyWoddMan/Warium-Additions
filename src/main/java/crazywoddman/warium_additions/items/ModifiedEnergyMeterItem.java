package crazywoddman.warium_additions.items;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModifiedEnergyMeterItem extends Item {
   public ModifiedEnergyMeterItem() {
      super(new Properties().stacksTo(1));
   }

   @Override
   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      super.inventoryTick(stack, level, entity, slotId, isSelected);

      if (level.isClientSide && entity instanceof Player player)
         WariumAdditionsUtil.checkEnergy(level, player, isSelected);
   }
}
