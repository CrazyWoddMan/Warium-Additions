package crazywoddman.warium_additions.mixins.createdieselgenerators;

import com.jesz.createdieselgenerators.content.tools.hammer.HammerItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;

import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition("createdieselgenerators"))
@Mixin(HammerItem.class)
public class HammerItemMixin implements IForgeItem {

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack newStack = new ItemStack((HammerItem)(Object)this);
        newStack.setDamageValue(stack.getDamageValue() + 1);
        return newStack.getDamageValue() >= stack.getMaxDamage() ? ItemStack.EMPTY : newStack;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }
}
