package crazywoddman.warium_additions.compat.immersiveengineering;

import blusunrize.immersiveengineering.common.items.HammerItem;
import net.minecraft.world.item.ItemStack;

public class ModifiedIEHammerItem extends HammerItem {
	public ModifiedIEHammerItem() {
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
