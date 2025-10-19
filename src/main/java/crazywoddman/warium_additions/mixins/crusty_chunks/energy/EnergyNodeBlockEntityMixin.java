package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.EnergyNodeBlock;
import net.mcreator.crustychunks.block.entity.EnergyNodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnergyNodeBlockEntity.class)
public class EnergyNodeBlockEntityMixin {

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectEnergyCapability(Capability<?> capability, Direction capFacing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.ENERGY) {
            BlockEntity blockEntity = (BlockEntity)(Object)this;
            Level level = blockEntity.getLevel();
            Direction nodeFacing = blockEntity.getBlockState().getValue(EnergyNodeBlock.FACING);

            if (capFacing == null) {
                BlockEntity connectedBlock = level
                .getBlockEntity(
                    blockEntity
                    .getBlockPos()
                    .relative(nodeFacing.getOpposite())
                );

                if (connectedBlock != null)
                    cir.setReturnValue(connectedBlock.getCapability(ForgeCapabilities.ENERGY, nodeFacing).cast());
            }

            else if (capFacing == nodeFacing.getOpposite()) {
                CompoundTag data = blockEntity.getPersistentData();

                if (data.contains("PowerX")) {
                    int x = data.getInt("PowerX");
                    int y = data.getInt("PowerY");
                    int z = data.getInt("PowerZ");
                    BlockEntity connectedNode = level.getBlockEntity(new BlockPos(x, y, z));

                    if (connectedNode != null && connectedNode instanceof EnergyNodeBlockEntity)
                        cir.setReturnValue(connectedNode.getCapability(ForgeCapabilities.ENERGY, null));
                }
            }
        }
    }
}
