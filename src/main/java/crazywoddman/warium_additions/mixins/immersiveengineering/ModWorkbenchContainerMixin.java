package crazywoddman.warium_additions.mixins.immersiveengineering;

import blusunrize.immersiveengineering.common.gui.ModWorkbenchContainer;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import net.minecraftforge.items.ItemStackHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition("immersiveengineering"))
@Mixin(value = ModWorkbenchContainer.class, remap = false)
public class ModWorkbenchContainerMixin {

    @Redirect(
        method = "rebindSlots",
        at = @At(
            value = "FIELD",
            target = "Lblusunrize/immersiveengineering/common/gui/ModWorkbenchContainer;clientInventory:Lnet/minecraftforge/items/ItemStackHandler;"
        )
    )
    private ItemStackHandler redirectClientInventory(ModWorkbenchContainer instance) {
        return new ItemStackHandler(34);
    }

    @ModifyConstant(
        method = "rebindSlots",
        constant = @Constant(intValue = 20)
    )
    private int modifyMaxNumDynamicSlotsRebind(int value) {
        return 33;
    }
}