package crazywoddman.warium_additions.mixins.create;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.EngineCyllinderBlockEntity;
import net.mcreator.crustychunks.block.entity.FlameThrowerBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankInputBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankModuleBlockEntity;
import net.mcreator.crustychunks.block.entity.JetTurbineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumDieselEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumPetrolEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.OilFireboxBlockEntity;
import net.mcreator.crustychunks.block.entity.RefineryTowerBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallDieselEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallPetrolEngineBlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;

import crazywoddman.warium_additions.compat.create.EngineersGoggles;

import java.util.List;

@Restriction(require = @Condition("create"))
@Mixin(
    value = {
        FuelTankBlockEntity.class,
        FuelTankModuleBlockEntity.class,
        FuelTankInputBlockEntity.class,
        MediumDieselEngineBlockEntity.class,
        SmallDieselEngineBlockEntity.class,
        MediumPetrolEngineBlockEntity.class,
        SmallPetrolEngineBlockEntity.class,
        EngineCyllinderBlockEntity.class,
        JetTurbineBlockEntity.class,
        RefineryTowerBlockEntity.class,
        OilFireboxBlockEntity.class,
        FlameThrowerBlockEntity.class
    },
    remap = false
)
public class FluidTooltip implements EngineersGoggles {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(
            tooltip,
            isPlayerSneaking,
            ((BlockEntity)(Object)this).getCapability(ForgeCapabilities.FLUID_HANDLER)
        );
    }
}
