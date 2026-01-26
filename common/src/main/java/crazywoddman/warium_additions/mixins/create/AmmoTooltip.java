package crazywoddman.warium_additions.mixins.create;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.create.EngineersGoggles;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.AutocannonDrumBlockEntity;
import net.mcreator.crustychunks.block.entity.HeavyMachineGunBlockEntity;
import net.mcreator.crustychunks.block.entity.LightMachineGunBlockEntity;
import net.mcreator.crustychunks.block.entity.MachineGunBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;

import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Restriction(require = @Condition("create"))
@Mixin({AutocannonDrumBlockEntity.class, MachineGunBlockEntity.class, LightMachineGunBlockEntity.class, HeavyMachineGunBlockEntity.class})
public abstract class AmmoTooltip implements EngineersGoggles {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        CompoundTag data = blockEntity.getPersistentData();
        String type = ForgeRegistries.BLOCKS.getKey(blockEntity.getBlockState().getBlock()).getPath();
        MutableComponent empty = Component
            .literal("     ")
            .append(Component.translatable(WariumAdditions.MODID + ".tooltip.empty"))
            .withStyle(ChatFormatting.DARK_GRAY);

        tooltip.add(Component
            .literal("    ")
            .append(Component.translatable("block.crusty_chunks." + type))
            .append(":")
            .withStyle(ChatFormatting.AQUA)
        );

        int ammo = data.getInt("Ammo");
        tooltip.add(
            ammo > 0
            ? Component.literal("     ")
                .append(Component.literal(ammo + " ").withStyle(ChatFormatting.AQUA))
                .append(Component.translatable("item.crusty_chunks." + switch (type) {
                        case "autocannon_drum" -> 
                            data.getString("AmmoType").equals("Small")
                            ? "huge_bullet"
                            : "small_shell";
                        case "light_machine_gun" -> "bullet";
                        case "machine_gun" -> "large_bullet";
                        default -> "extra_large_bullet";
                    })
                    .withStyle(ChatFormatting.GRAY)
                )
            : empty
        );

        return true;
    }
}
