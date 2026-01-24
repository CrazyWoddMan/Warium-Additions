package crazywoddman.warium_additions.blocks;

import net.mcreator.crustychunks.block.EnergyBatteryBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import blusunrize.immersiveengineering.api.IEEnums.IOSideConfig;
import blusunrize.immersiveengineering.api.utils.DirectionUtils;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IConfigurableSides;

@SuppressWarnings("deprecation")
public class ModifiedEnergyBattery extends EnergyBatteryBlock {

    public ModifiedEnergyBattery(Properties properties) {
        super();
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, (EnergyBatteryBlock)(Object)this, 40);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        
        BlockEntity blockEntity = world.getBlockEntity(pos);
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
        
        world.scheduleTick(pos, (EnergyBatteryBlock)(Object)this, 1);
    }
}
