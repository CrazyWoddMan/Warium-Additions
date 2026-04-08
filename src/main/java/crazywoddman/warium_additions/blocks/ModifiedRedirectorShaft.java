package crazywoddman.warium_additions.blocks;

import net.mcreator.crustychunks.block.RedirectorShaftBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import java.util.List;

public class ModifiedRedirectorShaft extends RedirectorShaftBlock {
    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(itemstack, level, list, flag);
      int size = list.size();

      if (size > 0)
        list.set(size - 1, Component.translatable("block.crusty_chunks.redirector_shaft.alt_description_0"));

      list.add(Component.translatable("block.crusty_chunks.redirector_shaft.alt_description_1"));
   }
}
