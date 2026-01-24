package crazywoddman.warium_additions.mixins.immersiveengineering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import blusunrize.immersiveengineering.client.ClientEventHandler;
import blusunrize.immersiveengineering.common.register.IEItems.Tools;
import crazywoddman.warium_additions.compat.immersiveengineering.ModifiedIEEnergyMeterItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;

@Restriction(require = @Condition("immersiveengineering"))
@Mixin(value = ClientEventHandler.class, remap = false)
public class ClientEventHandlerMixin {

    @ModifyVariable(
        method = "onRenderOverlayPost",
        at = @At("STORE"),
        name = "equipped"
    )
    private ItemStack modifyEquippedStack(ItemStack equipped) {
        if (equipped.getItem() instanceof ModifiedIEEnergyMeterItem)
            return new ItemStack(Tools.VOLTMETER.get());

        
        return equipped;
    }
}
