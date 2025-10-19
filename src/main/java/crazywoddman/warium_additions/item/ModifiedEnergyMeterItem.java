package crazywoddman.warium_additions.item;

import blusunrize.immersiveengineering.common.items.VoltmeterItem;
import net.mcreator.crustychunks.procedures.EnergyMeterTestProcedure;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public class ModifiedEnergyMeterItem extends VoltmeterItem {
	public ModifiedEnergyMeterItem() {
		super();
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
      super.useOn(context);

      EnergyMeterTestProcedure.execute(
         context.getLevel(),
         (double)context.getClickedPos().getX(),
         (double)context.getClickedPos().getY(),
         (double)context.getClickedPos().getZ(),
         context.getPlayer()
      );
	  
      return InteractionResult.SUCCESS;
   }
}
