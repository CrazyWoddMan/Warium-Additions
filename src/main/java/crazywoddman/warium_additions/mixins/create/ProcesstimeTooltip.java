package crazywoddman.warium_additions.mixins.create;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.mcreator.crustychunks.block.entity.AssemblyCentrifugeMiddleBlockEntity;
import net.mcreator.crustychunks.block.entity.AssemblyCircuitFabricatorBlockEntity;
import net.mcreator.crustychunks.block.entity.AssemblyMechanicalFabricatorBlockEntity;
import net.mcreator.crustychunks.block.entity.BreederReactorInterfaceBlockEntity;
import net.mcreator.crustychunks.block.entity.ProductionInputBlockEntity;
import net.mcreator.crustychunks.block.entity.ProductionOutputBlockEntity;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import crazywoddman.warium_additions.util.WariumAdditionsUtil.Requirements;
import crazywoddman.warium_ponder_jei.data.WariumpPonderJeiRecipes;
import crazywoddman.warium_ponder_jei.util.WariumPonderJeiUtil;

@Restriction(require = {@Condition("create"), @Condition("wariumpj")})
@Mixin({
    BreederReactorInterfaceBlockEntity.class,
    AssemblyCentrifugeMiddleBlockEntity.class,
    AssemblyMechanicalFabricatorBlockEntity.class,
    AssemblyCircuitFabricatorBlockEntity.class
})
public class ProcesstimeTooltip implements IHaveGoggleInformation {

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        CompoundTag data = blockEntity.getPersistentData();
        int progress = data.getInt("progress");

        if (progress <= 0)
            return false;

        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();
        Requirements[] requirements = {Requirements.NOT_ENOUGH_KINETIC};
        boolean isReactor = blockEntity instanceof BreederReactorInterfaceBlockEntity;
        Direction facing = isReactor ? null : blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);

        if (isReactor)
            requirements[0] = WariumAdditionsUtil.verifyReactorStructure(level, pos) ? (WariumAdditionsUtil.verifyReactorRods(level, pos) ? Requirements.MET : Requirements.NOT_ENOUGH_RODS) : Requirements.INCOMPLETE_STRUCTURE;
        else if (blockEntity instanceof AssemblyCentrifugeMiddleBlockEntity) {
            if (!level.getBlockState(pos.above()).is(CrustyChunksModBlocks.ASSEMBLY_CENTRIFUGE_TOP.get())
                || !level.getBlockState(pos.below(2)).is(CrustyChunksModBlocks.GIANT_COIL.get())
            ) requirements[0] = Requirements.INCOMPLETE_STRUCTURE;
            else {
                BlockState bottom = level.getBlockState(pos.below());

                if (!bottom.is(CrustyChunksModBlocks.ASSEMBLY_CENTRIFUGE_BOTTOM.get()))
                    requirements[0] = Requirements.INCOMPLETE_STRUCTURE;
                else if (WariumPonderJeiUtil.checkKinetic(level, pos.below(), bottom.getValue(HorizontalDirectionalBlock.FACING).getOpposite()) >= 30)
                    requirements[0] = Requirements.MET;
            }
        } else if (WariumPonderJeiUtil.checkKinetic(level, pos, facing.getClockWise(), facing.getCounterClockWise()) >= 30)
            requirements[0] = Requirements.MET;

        BlockEntity input = isReactor ? blockEntity : level.getBlockEntity(pos.relative(facing.getOpposite()));

        if (isReactor || input instanceof ProductionInputBlockEntity)
            return input.getCapability(ForgeCapabilities.ITEM_HANDLER).map(cap -> {
                ItemStack stack = cap.getStackInSlot(0);
                return stack.isEmpty() ? false : WariumpPonderJeiRecipes.getProgressable(blockEntity, level, stack).map(recipe -> {
                    int timeLeft = recipe.getTimeLeft(progress);
                    int timeLeftover = recipe.getTimeLeftover(stack);

                    if (requirements[0] == Requirements.MET) {
                        BlockEntity output = isReactor ? level.getBlockEntity(pos.below(2)) : level.getBlockEntity(pos.relative(facing));

                        if ((!isReactor && !(output instanceof ProductionOutputBlockEntity))
                            || !output.getCapability(ForgeCapabilities.ITEM_HANDLER).map(c -> WariumPonderJeiUtil.readItemSlots(c, recipe.getResults())).orElse(false)
                        ) requirements[0] = Requirements.INVALID_OUTPUT;
                    }

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
                        .append(Component.translatable(WariumAdditions.MODID + ".tooltip.processtime"))
                        .append(Component.literal(": "))
                        .withStyle(ChatFormatting.GRAY)
                    );
                    MutableComponent time = Component.literal(WariumAdditionsUtil.formatSeconds(timeLeft)).withStyle(ChatFormatting.AQUA);

                    if (timeLeftover > 0)
                        time = time.append(Component.literal(" + " + WariumAdditionsUtil.formatSeconds(timeLeftover)).withStyle(ChatFormatting.DARK_GRAY));

                    if (requirements[0] != Requirements.MET)
                        time = time.withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.STRIKETHROUGH);

                    tooltip.add(Component.literal("     ").append(time));

                    if (requirements[0] != Requirements.MET)
                        tooltip.add(Component
                            .literal("     ")
                            .append(requirements[0].component())
                            .withStyle(ChatFormatting.RED)
                        );


                    return true;
                }).orElse(false);
            }).orElse(false);

        return false;
    }
}
