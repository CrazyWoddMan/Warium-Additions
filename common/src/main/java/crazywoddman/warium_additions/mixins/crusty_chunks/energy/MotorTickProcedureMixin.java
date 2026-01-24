package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.LargeElectricMotorBlock;
import net.mcreator.crustychunks.procedures.MotorTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.config.Config;

@Mixin(MotorTickProcedure.class)
public class MotorTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void rewriteExecute(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        if (world instanceof Level level) {
            BlockPos pos = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            int kineticPower = (int) Math.round(blockEntity.getCapability(ForgeCapabilities.ENERGY).map(storage -> {
                int energy = storage.getEnergyStored();

                if (!level.hasNeighborSignal(pos) && energy == 0) {
                    Direction facing = blockEntity.getBlockState().getValue(LargeElectricMotorBlock.FACING);
                    energy = Optional.ofNullable(level.getBlockEntity(pos.relative(facing.getOpposite()))).map(connected -> 
                        storage.receiveEnergy(
                            connected.getCapability(ForgeCapabilities.ENERGY, facing).map(cap -> {
                                int extracted =
                                cap.canExtract()
                                ? cap.extractEnergy(storage.receiveEnergy(Math.min(Config.SERVER.energyTransferLimit.get(), Config.SERVER.motorConsumptionLimit.get()), true), false)
                                : 0;
                                // if (extracted != 0)
                                    System.out.println("extracted: " + extracted);
                                return extracted;
                            }).orElse(0),
                            false
                        )
                    ).orElse(0);
                }
                
                return energy;
            }).orElse(0) / Config.SERVER.kineticToFeRate.get());

            CompoundTag data = blockEntity.getPersistentData();
            BlockState state;

            if (data.getDouble("KineticPower") != kineticPower) {
                data.putInt("KineticPower", kineticPower);
                state = blockEntity.getBlockState();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
            }
            // else if (level.getGameTime() % 20 == 0) {
                state = blockEntity.getBlockState();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_NONE);
            // }
        }
    
        ci.cancel();
    }
}
