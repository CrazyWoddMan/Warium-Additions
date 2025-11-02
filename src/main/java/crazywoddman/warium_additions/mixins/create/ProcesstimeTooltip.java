package crazywoddman.warium_additions.mixins.create;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.BreederReactorInterfaceBlockEntity;
import net.mcreator.crustychunks.block.entity.CentrifugeCoreBlockEntity;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;

import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Restriction(require = @Condition(value = "create", versionPredicates = "[0.5.1.j]"))
@Mixin({BreederReactorInterfaceBlockEntity.class, CentrifugeCoreBlockEntity.class})
public abstract class ProcesstimeTooltip implements IHaveGoggleInformation {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        boolean isReactor = blockEntity instanceof BreederReactorInterfaceBlockEntity;

        if (structureComplete(blockEntity, blockEntity.getBlockPos(), isReactor)) {
            boolean validItem = false;
            int itemsAmount = 0;

            if (blockEntity instanceof Container container) {

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
                int enrichmentTime = data.getInt("enrichmentTimeGamerule");
                int timeLeft = (enrichmentTime * (isReactor ? 2 : 1) - data.getInt("T")) / 4;
                int timeLeftover = enrichmentTime / (isReactor ? 2 : 4) * (itemsAmount - 1);

                tooltip.add(Component
                    .literal("    ")
                    .append(Component.translatable(
                        isReactor ?
                        "block.crusty_chunks.breeder_reactor_interface" :
                        "block.crusty_chunks.centrifuge_core"
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
            return getReady(level, pos.above());

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
