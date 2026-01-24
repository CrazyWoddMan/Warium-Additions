package crazywoddman.warium_additions.util;

import java.util.Optional;

import crazywoddman.warium_additions.WariumAdditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class WariumAdditionsUtil {
    public static Ingredient getIngredientTag(String namespace, String id) {
        return Ingredient.of(
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, id))
        );
    }

    /**
     * @return current time in ticks
     */
    public static int ticks() {
        return (int)(System.currentTimeMillis() / 50);
    }

    /**
     * @return number from <b>size</b> ranged cycle that changes every tick
     */
    public static int ticker(int size) {
        return ((ticks() / 20) % size);
    }

    /**
     * @return element from <b>elements</b> ranged cycle that changes every tick
     */
    @SafeVarargs
    public static final <T> T ticker(T... elements) {
        return elements[ticker(elements.length)];
    }

    /**
     * Gets {@code throttle} from Control Node
     * @param blockEntity this BlockEntity
     * @param altThrottle
     * @return {@code altThrottle} if Valkyrien Warium is not installed, Control Node position is not set or Control Node not found;
     * {@code 0} if configured key restricts current {@code throttle} value;
     * {@code throttle} set by Control Node in other cases
     */
    public static Integer getThrottle(BlockEntity blockEntity, Integer altThrottle) {
        return WariumAdditions.valkyrien_warium ? ValkyrienWariumUtils.getThrottle(blockEntity, altThrottle) : altThrottle;
    }

    public static Optional<BlockEntity> getTargetBlock(Player player, Level level) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 endPosition = eyePosition.add(player.getViewVector(1.0F).scale(player.getBlockReach()));
        BlockHitResult hitResult = level.clip(new ClipContext(
            eyePosition,
            endPosition,
            ClipContext.Block.OUTLINE, 
            ClipContext.Fluid.NONE, 
            player
        ));
        
        return hitResult.getType() == HitResult.Type.BLOCK
        ? Optional.ofNullable(level.getBlockEntity(hitResult.getBlockPos()))
        : Optional.empty();
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
