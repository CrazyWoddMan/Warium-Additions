package crazywoddman.warium_additions.mixins.create;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.ElectricFireboxBlockEntity;
import net.mcreator.crustychunks.block.entity.EnergyBatteryBlockEntity;
import net.mcreator.crustychunks.block.entity.GeneratorBlockEntity;
import net.mcreator.crustychunks.block.entity.LargeElectricMotorBlockEntity;
import net.mcreator.crustychunks.block.entity.PowerReactorPortBlockEntity;
import net.mcreator.crustychunks.block.entity.SolarGeneratorBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;

import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.Optional;

@Restriction(require = @Condition(value = "create", versionPredicates = "[0.5.1.j]"))
@Mixin(
    value = {
        ElectricFireboxBlockEntity.class,
        LargeElectricMotorBlockEntity.class,
        GeneratorBlockEntity.class,
        EnergyBatteryBlockEntity.class,
        PowerReactorPortBlockEntity.class,
        SolarGeneratorBlockEntity.class
    },
    remap = false
)
public abstract class EnergyTooltipProvider implements IHaveGoggleInformation {

    private final int fireboxConsumption = Config.SERVER.electricFireboxConsumption.get();
    // private final int reactorGeneration = Config.SERVER.reactorGeneration.get();
    private final int solarGeneration = Config.SERVER.solarGeneration.get();


    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        Optional<IEnergyStorage> resolve = blockEntity.getCapability(ForgeCapabilities.ENERGY).resolve();

		if (!resolve.isPresent())
			return false;
        
        IEnergyStorage energyStorage = resolve.get();
        String type = ForgeRegistries.BLOCKS.getKey(blockEntity.getBlockState().getBlock()).getPath();
        tooltip.add(Component
                .literal("    ")
                .append(Component.translatable("block.crusty_chunks." + type))
                .append(Component.literal(":"))
                .withStyle(ChatFormatting.AQUA)
            );
        
        if (List.of("energy_battery", "electric_firebox", "power_reactor_port").contains(type)) {
            tooltip.add(Component
                .literal("    ")
                .append(Component.translatable("createaddition.tooltip.energy.stored"))
                .withStyle(ChatFormatting.GRAY)
            );
            tooltip.add(Component
                .literal("     ")
                .append(Component.literal(WariumAdditionsUtil.formatFE(energyStorage.getEnergyStored())))
                .withStyle(ChatFormatting.AQUA)
            );
            
            tooltip.add(Component
                .literal("    ")
                .append(Component.translatable("createaddition.tooltip.energy.capacity"))
                .withStyle(ChatFormatting.GRAY)
            );
            tooltip.add(Component
                .literal("     ")
                .append(Component.literal(WariumAdditionsUtil.formatFE(energyStorage.getMaxEnergyStored())))
                .withStyle(ChatFormatting.AQUA)
            );
        }

        if (!List.of("energy_battery", "power_reactor_port").contains(type)) {
            Level level = blockEntity.getLevel();
            BlockPos pos = blockEntity.getBlockPos();
            tooltip.add(Component
                .literal("    ")
                .append(Component.translatable(List.of("electric_firebox", "large_electric_motor").contains(type) ? "createaddition.tooltip.energy.consumption" : "createaddition.tooltip.energy.production"))
                .withStyle(ChatFormatting.GRAY)
            );
            tooltip.add(Component
                .literal("     ")
                .append(Component.literal(WariumAdditionsUtil.formatFE(switch (type) {
                    case "electric_firebox" -> level.hasNeighborSignal(pos) ? 0 : fireboxConsumption;
                    case "generator" -> energyStorage.getEnergyStored();
                    case "large_electric_motor" -> energyStorage.getEnergyStored();
                    // TODO: find a way to check reactor status
                    // case "power_reactor_input" -> reactorGeneration;
                    case "solar_generator" -> level.isDay() && level.canSeeSkyFromBelowWater(pos) ? solarGeneration : 0;
                    default -> 0;
                }) + "/t"))
                .withStyle(ChatFormatting.AQUA)
            );
        }

        return true;
        
    }
}
