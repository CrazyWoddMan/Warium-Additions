package crazywoddman.warium_additions.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.EnergyStorage;

import blusunrize.immersiveengineering.api.IEEnums.IOSideConfig;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IConfigurableSides;

public class BatteryProvider {
    public static EnergyStorage get(BlockEntity blockEntity, EnergyStorage energyStorage, Direction facing) {  
        return new EnergyStorage(energyStorage.getMaxEnergyStored()) {
            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                return energyStorage.extractEnergy(maxExtract, simulate);
            }

            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                return canReceive() ? energyStorage.receiveEnergy(maxReceive, simulate) : 0;
            }

            @Override
            public boolean canReceive() {
                return facing == null || ((IConfigurableSides)blockEntity).getSideConfig(facing) != IOSideConfig.OUTPUT;
            }

            @Override
            public boolean canExtract() {
                return facing == null || ((IConfigurableSides)blockEntity).getSideConfig(facing) != IOSideConfig.INPUT;
            }

            @Override
            public int getEnergyStored() {
                return energyStorage.getEnergyStored();
            }
        };
    }
}
