package crazywoddman.warium_additions.mixins.create;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.ElectricFireboxBlockEntity;
import net.mcreator.crustychunks.block.entity.EnergyBatteryBlockEntity;
import net.mcreator.crustychunks.block.entity.EnergyConveyorBlockEntity;
import net.mcreator.crustychunks.block.entity.EnergyNodeBlockEntity;
import net.mcreator.crustychunks.block.entity.GeneratorBlockEntity;
import net.mcreator.crustychunks.block.entity.LargeElectricMotorBlockEntity;
import net.mcreator.crustychunks.block.entity.PowerReactorPortBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

import org.spongepowered.asm.mixin.Mixin;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;

import crazywoddman.warium_additions.utils.WadditionsUtil;

import java.util.List;

@Restriction(require = @Condition("create"))
@Mixin(
    value = {
        ElectricFireboxBlockEntity.class,
        LargeElectricMotorBlockEntity.class,
        GeneratorBlockEntity.class,
        EnergyBatteryBlockEntity.class,
        PowerReactorPortBlockEntity.class,
        EnergyNodeBlockEntity.class,
        EnergyConveyorBlockEntity.class
    },
    remap = false
)
public class EnergyTooltip implements IHaveGoggleInformation {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(storage -> {
            tooltip.add(Component
                .literal("    ")
                .append(Component.translatable("block.crusty_chunks." + ForgeRegistries.BLOCKS.getKey(blockEntity.getBlockState().getBlock()).getPath()))
                .append(Component.literal(":"))
                .withStyle(ChatFormatting.AQUA)
            );
            
            tooltip.add(Component
                .literal("    ")
                .append(Component.translatable("createaddition.tooltip.energy.stored"))
                .withStyle(ChatFormatting.GRAY)
            );
            tooltip.add(Component
                .literal("     ")
                .append(Component.literal(WadditionsUtil.formatFE(storage.getEnergyStored())))
                .withStyle(ChatFormatting.AQUA)
            );
            
            tooltip.add(Component
                .literal("    ")
                .append(Component.translatable("createaddition.tooltip.energy.capacity"))
                .withStyle(ChatFormatting.GRAY)
            );
            tooltip.add(Component
                .literal("     ")
                .append(Component.literal(WadditionsUtil.formatFE(storage.getMaxEnergyStored())))
                .withStyle(ChatFormatting.AQUA)
            );
            return true;
        }).orElse(false);
    }
}
