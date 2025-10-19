package crazywoddman.warium_additions.mixins.create;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.BlockMinerBlockEntity;
import net.mcreator.crustychunks.block.entity.EngineCyllinderBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankInputBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankModuleBlockEntity;
import net.mcreator.crustychunks.block.entity.JetTurbineBlockEntity;
import net.mcreator.crustychunks.block.entity.LightCombustionEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumPetrolEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.OilFireboxBlockEntity;
import net.mcreator.crustychunks.block.entity.RefineryTowerBlockEntity;
import net.mcreator.crustychunks.block.entity.SmalPetrolEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallDieselEngineBlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Restriction(require = @Condition(value = "create", versionPredicates = "[0.5.1.j]"))
@Mixin(
    value = {
        FuelTankBlockEntity.class,
        FuelTankModuleBlockEntity.class,
        FuelTankInputBlockEntity.class,
        LightCombustionEngineBlockEntity.class,
        SmallDieselEngineBlockEntity.class,
        MediumPetrolEngineBlockEntity.class,
        SmalPetrolEngineBlockEntity.class,
        EngineCyllinderBlockEntity.class,
        JetTurbineBlockEntity.class,
        RefineryTowerBlockEntity.class,
        OilFireboxBlockEntity.class,
        BlockMinerBlockEntity.class
    },
    remap = false
)
public abstract class FluidTooltipProvider implements IHaveGoggleInformation {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(
            tooltip,
            isPlayerSneaking,
            ((BlockEntity)(Object)this).getCapability(ForgeCapabilities.FLUID_HANDLER)
        );
    }
}
