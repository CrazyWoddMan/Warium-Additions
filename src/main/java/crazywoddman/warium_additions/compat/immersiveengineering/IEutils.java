package crazywoddman.warium_additions.compat.immersiveengineering;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import blusunrize.immersiveengineering.api.IEEnums.IOSideConfig;
import blusunrize.immersiveengineering.api.utils.DirectionUtils;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IConfigurableSides;

public class IEutils {
    public static void tryExtract(BlockEntity blockEntity, ServerLevel world, BlockPos pos) {
        blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> {
            if (storage.getEnergyStored() > 0)
                for(Direction direction : DirectionUtils.VALUES)
                    if (((IConfigurableSides)blockEntity).getSideConfig(direction) == IOSideConfig.OUTPUT) {
                        BlockEntity opposite = world.getBlockEntity(pos.relative(direction));
                        
                        if (opposite != null)
                            opposite.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).ifPresent(cap -> {
                                if (cap.canReceive()) {
                                    int available = storage.extractEnergy(1000, true);
                                    int transfered = cap.receiveEnergy(available, false);
                                    storage.extractEnergy(transfered, false);
                                }
                            });
                    }
        });
    }
}
