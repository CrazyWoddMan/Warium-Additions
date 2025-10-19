package crazywoddman.warium_additions.mixins.create;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.AutocannonDrumBlockEntity;
import net.mcreator.crustychunks.block.entity.MachineGunBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Restriction(require = @Condition(value = "create", versionPredicates = "[0.5.1.j]"))
@Mixin(value = {AutocannonDrumBlockEntity.class, MachineGunBlockEntity.class})
public abstract class AmmoTooltipProvider implements IHaveGoggleInformation {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        boolean isDrum = blockEntity instanceof AutocannonDrumBlockEntity;

        int ammo = blockEntity.getPersistentData().getInt("Ammo");

        if (ammo <= 0) {
            return false;
        }

        tooltip.add(Component
            .literal("    ")
            .append(Component.translatable(isDrum ? "block.crusty_chunks.autocannon_drum" : "item.crusty_chunks.machine_gun_box"))
            .append(Component.literal(":"))
            .withStyle(ChatFormatting.GRAY)
        );
        tooltip.add(Component
            .literal("    ")
            .append(Component.literal(" " + String.valueOf(ammo)) + " ")
            .append(Component.translatable(
                isDrum ?
                (
                    blockEntity.getPersistentData().getString("AmmoType").equals("Small") ?
                    "item.crusty_chunks.huge_bullet" :
                    "item.crusty_chunks.small_shell"
                ) :
                "item.crusty_chunks.large_bullet"
            ))
            .withStyle(ChatFormatting.AQUA)
        );

        return true;
    }
}
