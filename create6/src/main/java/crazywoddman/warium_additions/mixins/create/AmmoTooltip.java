package crazywoddman.warium_additions.mixins.create;

import crazywoddman.warium_additions.WariumAdditions;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.mcreator.crustychunks.block.entity.AutocannonDrumBlockEntity;
import net.mcreator.crustychunks.block.entity.HeavyMachineGunBlockEntity;
import net.mcreator.crustychunks.block.entity.LightMachineGunBlockEntity;
import net.mcreator.crustychunks.block.entity.MachineGunBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;

import org.spongepowered.asm.mixin.Mixin;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;

import java.util.List;

// TODO rewrite without LangBuilder and add new blocks support
@Restriction(require = @Condition("create"))
@Mixin({AutocannonDrumBlockEntity.class, MachineGunBlockEntity.class, LightMachineGunBlockEntity.class, HeavyMachineGunBlockEntity.class})
public abstract class AmmoTooltip implements IHaveGoggleInformation {

    private static LangBuilder builder() {
        return Lang.builder("item");
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        CompoundTag data = blockEntity.getPersistentData();
        String type = ForgeRegistries.BLOCKS.getKey(blockEntity.getBlockState().getBlock()).getPath();
        LangBuilder empty = Lang.builder(WariumAdditions.MODID)
            .translate("tooltip.empty")
            .style(ChatFormatting.DARK_GRAY);
        int ammo = data.getInt("Ammo");

        builder()
        .add(Lang.builder("block").translate("crusty_chunks." + type))
        .text(":")
        .style(ChatFormatting.AQUA)
        .forGoggles(tooltip);
        
        if (data.getBoolean("Loaded"))
            builder()
            .translate("crusty_chunks.machine_gun_box")
            .text(":")
            .style(ChatFormatting.GRAY)
            .forGoggles(tooltip);
        else if (ammo > 0)
            builder()
            .add(
                type.equals("autocannon_drum")
                ? Lang.builder(WariumAdditions.MODID).translate("tooltip.ammo")
                : builder().translate("crusty_chunks.machine_gun_box")
            )
            .text(":")
            .style(ChatFormatting.GRAY)
            .forGoggles(tooltip);
        else {
            builder()
            .add(empty)
            .forGoggles(tooltip, 1);
            
            return true;
        }
        
        if (ammo > 0)
            builder()
            .add(
                builder()
                .text(ammo + " ")
                .style(ChatFormatting.AQUA)
            )
            .add(
                builder()
                .translate(switch (type) {
                    case "autocannon_drum" -> 
                        data.getString("AmmoType").equals("Small")
                        ? "crusty_chunks.huge_bullet"
                        : "crusty_chunks.small_shell";
                    case "light_machine_gun" -> "crusty_chunks.bullet";
                    case "machine_gun" -> "crusty_chunks.large_bullet";
                    default -> "crusty_chunks.extra_large_bullet";
                })
                .style(ChatFormatting.GRAY)
            )
            .forGoggles(tooltip, 1);
        else builder()
            .add(empty)
            .forGoggles(tooltip, 1);

        return true;
    }
}
