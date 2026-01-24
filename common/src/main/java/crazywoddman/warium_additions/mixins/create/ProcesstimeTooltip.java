package crazywoddman.warium_additions.mixins.create;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.AssemblyCentrifugeMiddleBlock;
import net.mcreator.crustychunks.block.entity.AssemblyCentrifugeMiddleBlockEntity;
import net.mcreator.crustychunks.block.entity.BreederReactorInterfaceBlockEntity;
import net.mcreator.crustychunks.block.entity.ProductionInputBlockEntity;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.create.EngineersGoggles;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Restriction(require = @Condition("create"))
@Mixin({BreederReactorInterfaceBlockEntity.class, AssemblyCentrifugeMiddleBlockEntity.class})
public class ProcesstimeTooltip implements EngineersGoggles {
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        boolean isReactor = blockEntity instanceof BreederReactorInterfaceBlockEntity;

        if (structureComplete(blockEntity, blockEntity.getBlockPos(), isReactor)) {
            boolean validItem = false;
            int itemsAmount = 0;

            if ((isReactor ? blockEntity : blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos().relative(blockEntity.getBlockState().getValue(AssemblyCentrifugeMiddleBlock.FACING).getOpposite()))) instanceof Container container && (isReactor || container instanceof ProductionInputBlockEntity)) {

                for (int i = 0; i < container.getContainerSize(); i++) {
                    ItemStack stack = container.getItem(i);

                    if (!stack.isEmpty()) {
                        validItem = stack.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath(
                            WariumAdditions.MODID,
                            isReactor ? "reactor_acceptable" : "enrichable"
                        )));
                        itemsAmount = stack.getCount();
                        break;
                    }
                }
            }

            if (validItem) {
                CompoundTag data = blockEntity.getPersistentData();
                // TODO: fix different enrichmentTime for different items
                int enrichmentTime = data.getInt("enrichmentTimeGamerule");
                int timeLeft = (enrichmentTime * (isReactor ? 2 : 1) - data.getInt(isReactor ? "T" : "progress")) / 4;
                int timeLeftover = enrichmentTime / (isReactor ? 2 : 4) * (itemsAmount - 1);

                tooltip.add(Component
                    .literal("    ")
                    .append(Component.translatable(
                        "block.crusty_chunks." + ForgeRegistries.BLOCKS.getKey(blockEntity.getBlockState().getBlock()).getPath()
                    ))
                    .append(Component.literal(":"))
                    .withStyle(ChatFormatting.AQUA)
                );

                tooltip.add(Component
                    .literal("    ")
                    .append(Component.translatable(
                        isReactor ?
                        WariumAdditions.MODID + ".breeder_reactor.processtime" :
                        "gamerule.enrichmentTime"
                    ))
                    .append(Component.literal(": "))
                    .withStyle(ChatFormatting.GRAY)
                );

                tooltip.add(Component
                    .literal("     ")
                    .append(Component.literal(WariumAdditionsUtil.formatSeconds(timeLeft)).withStyle(ChatFormatting.AQUA))
                    .append(Component.literal(
                        timeLeftover > 0 ? 
                        " + " + WariumAdditionsUtil.formatSeconds(timeLeftover) :
                        ""
                    ).withStyle(ChatFormatting.DARK_GRAY))
                );

                return true;
            }
        }

        return false;
    }

    private static boolean getReady(Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.getPersistentData().getBoolean("Ready");
    }

    private static boolean structureComplete(BlockEntity blockEntity, BlockPos pos, boolean isReactor) {
        Level level = blockEntity.getLevel();

        if (!isReactor)
            return getReady(level, pos);

        return level.getBlockState(pos.below(2)).getBlock() == CrustyChunksModBlocks.BREEDER_REACTOR_PORT.get()
            && level.getBlockState(pos.below(1)).getBlock() == CrustyChunksModBlocks.BREEDER_REACTOR_CORE.get()
            && getReady(level, pos.below(1).north(2))
            && getReady(level, pos.below(1).south(2))
            && getReady(level, pos.below(1).west(2))
            && getReady(level, pos.below(1).east(2))

            && getReady(level, pos.north(2))
            && getReady(level, pos.south(2))
            && getReady(level, pos.west(2))
            && getReady(level, pos.east(2));
    }
}
