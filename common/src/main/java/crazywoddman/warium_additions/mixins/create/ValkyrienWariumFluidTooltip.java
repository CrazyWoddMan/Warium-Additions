package crazywoddman.warium_additions.mixins.create;

import net.mcreator.valkyrienwarium.block.entity.LiquidFuelRocketBlockEntity;
import net.mcreator.valkyrienwarium.block.entity.TestThrusterBlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import crazywoddman.warium_additions.compat.create.EngineersGoggles;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = {
    @Condition("valkyrien_warium"),
    @Condition("create")
})
@Mixin({LiquidFuelRocketBlockEntity.class, TestThrusterBlockEntity.class})
public class ValkyrienWariumFluidTooltip implements EngineersGoggles {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(
            tooltip,
            isPlayerSneaking,
            ((BlockEntity)(Object)this).getCapability(ForgeCapabilities.FLUID_HANDLER)
        );
    }
}
