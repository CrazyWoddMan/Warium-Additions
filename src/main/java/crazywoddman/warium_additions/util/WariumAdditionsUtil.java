package crazywoddman.warium_additions.util;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.curios.CuriosUtil;
import crazywoddman.warium_additions.registry.RegistryItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class WariumAdditionsUtil {

    public static ItemStack getWariumStack(String id) {
        return new ItemStack(
            ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", id)
            )
        );
    }

    public static FluidStack getWariumFluid(String id, int amount) {
        return new FluidStack(
            ForgeRegistries.FLUIDS.getValue(ResourceLocation.fromNamespaceAndPath("crusty_chunks", id)),
            amount
        );
    }

    public static Ingredient getIngredientTag(String namespace, String id) {
        return Ingredient.of(
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, id))
        );
    }

    public static void checkEnergy(LevelAccessor level, Player player, boolean isSelected) {
        if (!isSelected && WariumAdditions.curios)
            isSelected = CuriosUtil.getItem(player, "belt", RegistryItems.ENERGY_METER.get()).isPresent();

        if (isSelected) {
            Vec3 eyePosition = player.getEyePosition(1.0F);
            Vec3 endPosition = eyePosition.add(player.getViewVector(1.0F).scale(player.getBlockReach()));
            BlockHitResult hitResult = level.clip(new ClipContext(
                eyePosition, 
                endPosition, 
                ClipContext.Block.OUTLINE, 
                ClipContext.Fluid.NONE, 
                player
            ));
            
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockEntity blockEntity = level.getBlockEntity(hitResult.getBlockPos());

                if (blockEntity != null)
                    blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> {
                        player.displayClientMessage(
                        Component
                            .translatable("warium_additions.tooltip.energy.power")
                            .append(Component.literal(": " + WariumAdditionsUtil.formatFE(storage.getEnergyStored()))),
                        true
                        );
                    });
            }
        }
    }

    /**
     * @param first fluid
     * @param second fluid to compare with
     * @return {@code true} if fluids are similar
     */
    public static boolean compareFluids(Fluid first, Fluid second) {
        if (first.equals(second))
            return true;

        String firstPath = ForgeRegistries.FLUIDS.getKey(first).getPath();
        String secondPath = ForgeRegistries.FLUIDS.getKey(second).getPath();

        if (
            firstPath.equals(secondPath) ||
            ForgeRegistries.FLUIDS.tags().getTag(FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", firstPath))).contains(second) ||
            ForgeRegistries.FLUIDS.tags().getTag(FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", secondPath))).contains(first)
        )
            return true;
        
        return false;
    }

    /**
     * Formats time to {@code mm:ss} or {@code hh:mm:ss}
     * @param totalSeconds time in seconds
     * @return formatted <b>totalSeconds<b>
     */
    public static String formatSeconds(int totalSeconds) {
        int seconds = totalSeconds % 60;

        if (totalSeconds >= 3600) {
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            int minutes = totalSeconds / 60;
            return String.format("%d:%02d", minutes, seconds);
        }
    }

    public static String formatFE(int fe) {
        if (fe >= 1_000_000_000)
            return Math.round(fe / 100_000_000) / 10.0 + "Gfe";

        else if (fe >= 1_000_000)
            return Math.round(fe / 100_000) / 10.0 + "Mfe";
        
        else if (fe >= 1_000)
            return Math.round(fe / 100) / 10.0 + "Kfe";
            
        return fe + "fe";
    }
}
