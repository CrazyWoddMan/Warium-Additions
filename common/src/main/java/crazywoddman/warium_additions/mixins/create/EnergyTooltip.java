package crazywoddman.warium_additions.mixins.create;

import crazywoddman.warium_additions.compat.create.EngineersGoggles;
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
public class EnergyTooltip implements EngineersGoggles {
    private final int fireboxConsumption = Config.SERVER.electricFireboxConsumption.get();
    private final int reactorGeneration = Config.SERVER.powerReactorGeneration.get();

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        Optional<IEnergyStorage> resolve = blockEntity.getCapability(ForgeCapabilities.ENERGY).resolve();

		if (!resolve.isPresent())
			return false;
        
        IEnergyStorage energyStorage = resolve.get();
        String type = ForgeRegistries.BLOCKS.getKey(blockEntity.getBlockState().getBlock()).getPath();
        String energy = WariumAdditionsUtil.formatFE(energyStorage.getEnergyStored());

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

        if (!type.equals("energy_battery")) {
            Level level = blockEntity.getLevel();
            BlockPos pos = blockEntity.getBlockPos();

            tooltip.add(Component
                .literal("    ")
                .append(Component.translatable("createaddition.tooltip.energy." + switch (type) {
                    case "energy_node" -> "usage";
                    case "electric_firebox" -> "consumption";
                    case "large_electric_motor" -> "consumption";
                    default -> "production";
                })).withStyle(ChatFormatting.GRAY)
            );

            tooltip.add(Component
                .literal("     ")
                .append(switch (type) {
                    case "electric_firebox" -> {
                        int consumption = level.hasNeighborSignal(pos) ? this.fireboxConsumption : 0;
                        yield Component
                            .literal(WariumAdditionsUtil.formatFE(consumption) + "/40t")
                            .withStyle(ChatFormatting.AQUA)
                            .append(Component.literal(" ~" + WariumAdditionsUtil.formatFE(consumption / 40) + "/t").withStyle(ChatFormatting.DARK_GRAY));
                    }
                    case "power_reactor_port" -> {
                        blockEntity = level.getBlockEntity(pos.atY(pos.getY() + 2));
                        yield Component.literal(WariumAdditionsUtil.formatFE(
                            (blockEntity != null && blockEntity.getPersistentData().getBoolean("status"))
                            ? this.reactorGeneration
                            : 0
                        ) + "/t").withStyle(ChatFormatting.AQUA);
                    }
                    default -> Component.literal(energy + "/t").withStyle(ChatFormatting.AQUA);
                })
            );
        }

        return true;
        
    }
}
