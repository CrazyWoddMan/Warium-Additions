package crazywoddman.warium_additions.mixins.create;

import net.mcreator.valkyrienwarium.block.entity.LiquidFuelRocketBlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(
    require = {
        @Condition("valkyrien_warium"),
        @Condition(value = "create", versionPredicates = "[0.5.1.j]")
    }
)
@Mixin(value = LiquidFuelRocketBlockEntity.class, remap = false)
public class LiquidFuelRocketTooltip implements IHaveGoggleInformation {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(
            tooltip,
            isPlayerSneaking,
            ((BlockEntity)(Object)this).getCapability(ForgeCapabilities.FLUID_HANDLER)
        );
    }
}
