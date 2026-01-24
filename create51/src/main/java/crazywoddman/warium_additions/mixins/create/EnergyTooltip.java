package crazywoddman.warium_additions.mixins.create;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.ElectricFireboxBlockEntity;
import net.mcreator.crustychunks.block.entity.EnergyBatteryBlockEntity;
import net.mcreator.crustychunks.block.entity.EnergyNodeBlockEntity;
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

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;

import java.util.List;
import java.util.Optional;

@Restriction(require = @Condition("create"))
@Mixin(
    value = {
        ElectricFireboxBlockEntity.class,
        LargeElectricMotorBlockEntity.class,
        GeneratorBlockEntity.class,
        EnergyBatteryBlockEntity.class,
        PowerReactorPortBlockEntity.class,
        SolarGeneratorBlockEntity.class,
        EnergyNodeBlockEntity.class
    },
    remap = false
)
public abstract class EnergyTooltip implements IHaveGoggleInformation {

    private final int fireboxConsumption = Config.SERVER.electricFireboxConsumption.get();
    private final int reactorGeneration = Config.SERVER.powerReactorGeneration.get();

    private static final LangBuilder builder() {
        return Lang.builder("createaddition");
    }


    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        Optional<IEnergyStorage> resolve = blockEntity.getCapability(ForgeCapabilities.ENERGY).resolve();

		if (!resolve.isPresent())
			return false;
        
        IEnergyStorage energyStorage = resolve.get();
        String type = ForgeRegistries.BLOCKS.getKey(blockEntity.getBlockState().getBlock()).getPath();
        String energy = WariumAdditionsUtil.formatFE(energyStorage.getEnergyStored());

        builder()
        .add(Lang.builder("block").translate("crusty_chunks." + type))
        .text(":")
        .style(ChatFormatting.AQUA)
        .forGoggles(tooltip);
        
        if (List.of("energy_battery", "electric_firebox", "power_reactor_port").contains(type)) {
            builder()
            .translate("tooltip.energy.stored")
            .style(ChatFormatting.GRAY)
            .forGoggles(tooltip);

            builder()
            .text(energy)
            .style(ChatFormatting.AQUA)
            .forGoggles(tooltip, 1);

            builder()
            .translate("tooltip.energy.capacity")
            .style(ChatFormatting.GRAY)
            .forGoggles(tooltip);

            builder()
            .text(WariumAdditionsUtil.formatFE(energyStorage.getMaxEnergyStored()))
            .style(ChatFormatting.AQUA)
            .forGoggles(tooltip, 1);
        }

        if (!type.equals("energy_battery")) {
            Level level = blockEntity.getLevel();
            BlockPos pos = blockEntity.getBlockPos();

            builder()
            .translate(switch (type) {
                case "energy_node" -> "tooltip.energy.usage";
                case "electric_firebox" -> "tooltip.energy.consumption";
                case "large_electric_motor" -> "tooltip.energy.consumption";
                default -> "tooltip.energy.production";
            })
            .style(ChatFormatting.GRAY)
            .forGoggles(tooltip);

            LangBuilder part = switch (type) {
                case "electric_firebox" -> builder()
                    .text(ChatFormatting.AQUA, WariumAdditionsUtil.formatFE(level.hasNeighborSignal(pos) ? this.fireboxConsumption : 0) + "/40t")
                    .text(ChatFormatting.DARK_GRAY, " ~" + WariumAdditionsUtil.formatFE(this.fireboxConsumption / 40) + "/t");
                case "power_reactor_port" -> {
                    blockEntity = level.getBlockEntity(pos.atY(pos.getY() + 2));
                    yield builder().text(ChatFormatting.AQUA, WariumAdditionsUtil.formatFE(
                        (blockEntity != null && blockEntity.getPersistentData().getBoolean("status"))
                        ? this.reactorGeneration
                        : 0
                    ) + "/t");
                }
                default -> builder().text(ChatFormatting.AQUA, energy + "/t");
            };
                
            part.forGoggles(tooltip, 1);
        }

        return true;
        
    }
}
