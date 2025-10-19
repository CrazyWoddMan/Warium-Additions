package crazywoddman.warium_additions.item;

import blusunrize.immersiveengineering.api.IETags;
import blusunrize.immersiveengineering.common.items.WirecutterItem;
import net.mcreator.crustychunks.procedures.CuttersRightclickedOnBlockProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ModifiedCuttersItem extends WirecutterItem {
	public ModifiedCuttersItem() {
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

	@Override
	public boolean mineBlock(ItemStack itemstack, Level pLevel, BlockState state, BlockPos pPos, LivingEntity pEntityLiving)
	{
		boolean effective = state.is(IETags.wirecutterHarvestable);
		itemstack.hurtAndBreak(1, pEntityLiving, (player) -> {
			player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});

		return effective;
	}

	public InteractionResult useOn(UseOnContext context) {
      super.useOn(context);
      CuttersRightclickedOnBlockProcedure.execute(context.getLevel(), (double)context.getClickedPos().getX(), (double)context.getClickedPos().getY(), (double)context.getClickedPos().getZ(), context.getItemInHand());
      return InteractionResult.SUCCESS;
   }
}
