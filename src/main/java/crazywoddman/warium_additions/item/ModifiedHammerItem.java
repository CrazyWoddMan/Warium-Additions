package crazywoddman.warium_additions.item;

import blusunrize.immersiveengineering.common.items.HammerItem;
import net.minecraft.world.item.ItemStack;

public class ModifiedHammerItem extends HammerItem {
	public ModifiedHammerItem() {
		super();
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return 64;
	}

	@Override
	public boolean isRepairable(ItemStack itemstack) {
      return false;
   }
}
