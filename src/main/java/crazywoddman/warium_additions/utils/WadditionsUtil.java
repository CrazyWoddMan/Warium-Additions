package crazywoddman.warium_additions.utils;

import java.util.Optional;
import java.util.function.Supplier;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.data.WadditionsTags;
import net.mcreator.crustychunks.CrustyChunksMod;
import net.mcreator.crustychunks.init.CrustyChunksModBlockEntities;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.energy.IEnergyStorage;

public class WadditionsUtil {
    public static final int
    MAX_THROTTLE = 10,
    ELECTRIC_FIREBOX_CONSUME = 1000;
    /**
     * @param blockEntity {@link BlockEntity} which is linked to Vehicle Control Node (VCN)
     * @return {@link Optional#empty()} if Valkyrien Warium is not installed, VCN position is not set or VCN not found;
     * @return {@code 0} if configured key restricts current throttle value;
     * <p>{@code throttle} set by VCN in other cases
     */
    public static Optional<Double> getThrottle(BlockEntity blockEntity) {
        return WariumAdditions.VALKYRIEN_WARIUM ? Throttle.get(blockEntity) : Optional.empty();
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, path);
    }

    public static ResourceLocation warium(String path) {
        return ResourceLocation.fromNamespaceAndPath(CrustyChunksMod.MODID, path);
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
     * Formats time to {@code mm:ss} or {@code hh:mm:ss} if amount of seconds exceeds 3600
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

    /**
     * Formats Forge Energy to <b>fe/Kfe/Mfe/Gfe</b> based on it's value
     * @param totalSeconds time in seconds
     * @param fe value
     * @return formatted <b>totalSeconds</b>
     * @see IEnergyStorage
     */
    public static String formatFE(int fe) {
        if (fe >= 1_000_000_000)
            return Math.round(fe / 100_000_000) / 10.0 + "Gfe";

        else if (fe >= 1_000_000)
            return Math.round(fe / 100_000) / 10.0 + "Mfe";
        
        else if (fe >= 1_000)
            return Math.round(fe / 100) / 10.0 + "Kfe";
            
        return fe + "fe";
    }

    public static enum Requirements {
        MET(""),
        INVALID_OUTPUT("invalid_output"),
        INCOMPLETE_STRUCTURE("incomplete_structure"),
        NOT_ENOUGH_KINETIC("kinetic.not_enough"),
        NOT_ENOUGH_RODS("fuel_rods.not_enough");

        private final String value;

        Requirements(String value) {
            this.value = value;
        }

        public MutableComponent component() {
            return Component.translatable(WariumAdditions.MODID + ".tooltip." + value);
        }
    }

    public static final Direction[] HORIZONTAL_DIRECTIONS = {Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH};

    public static boolean verifyReactorStructure(Level level, BlockPos main) {
        BlockChecker validator = new BlockChecker(level);
        BlockPos below = main.below();

        if (!validator.check(below, CrustyChunksModBlocks.BREEDER_REACTOR_CORE) || !validator.check(main.below(2), CrustyChunksModBlocks.BREEDER_REACTOR_PORT))
            return false;

        for (Direction side : HORIZONTAL_DIRECTIONS) {
            if (!level.getBlockEntity(below.relative(side, 2), CrustyChunksModBlockEntities.REACTION_CHAMBER.get()).map(be -> be.getPersistentData().getBoolean("Ready")).orElse(false))
                return false;

            for (Direction subSide : HORIZONTAL_DIRECTIONS)
                if (!validator.check(main.relative(side, 2).relative(subSide), CrustyChunksModBlocks.CONTROL_ROD))
                    return false;

            BlockPos corner = main.relative(side).relative(side.getClockWise());
            
            if (!validator.check(corner, CrustyChunksModBlocks.REACTOR_CASING)
                || !validator.check(corner.relative(side, 2), CrustyChunksModBlocks.REACTOR_CASING)
                || !validator.check(corner.relative(side.getClockWise(), 2), CrustyChunksModBlocks.REACTOR_CASING)
            ) return false;
        }
            
        return true;
    }

    public static boolean verifyReactorRods(Level level, BlockPos main) {
        for (Direction side : HORIZONTAL_DIRECTIONS)
            if (!level.getBlockState(main.relative(side, 2).above()).is(WadditionsTags.Blocks.FUEL_ROD))
                return false;
            
        return true;
    }

    private static record BlockChecker(Level level) {
        private boolean check(BlockPos pos, Supplier<Block> block) {
            return this.level.getBlockState(pos).is(block.get());
        }
    }
}
