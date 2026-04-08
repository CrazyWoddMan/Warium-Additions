package crazywoddman.warium_additions.mixins.crusty_chunks.energy;
import net.mcreator.crustychunks.procedures.PowerReactorTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.config.Config;

@Mixin(PowerReactorTickProcedure.class)
public class PowerReactorTickProcedureMixin {
    @Inject(
        method = "execute",
        at = @At("HEAD"),
        remap = false
    )
    private static void resetStatus(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        world.getBlockEntity(BlockPos.containing(x, y, z)).getPersistentData().putBoolean("status", false);
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(intValue = 3000),
        require = 0,
        remap = false
    )
    private static int modifyGeneration(int original, LevelAccessor world, double x, double y, double z) {
        world.getBlockEntity(BlockPos.containing(x, y, z)).getPersistentData().putBoolean("status", true);
        return Config.SERVER.powerReactorGeneration.get() * 10;
    }

    @Inject(
        method = "execute",
        at = @At("TAIL"),
        remap = false
    )
    private static void extractEnergy(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        if (!(world instanceof ServerLevel level))
            return;
            
        BlockPos portPos = BlockPos.containing(x, y - 2.0, z);
        Optional.ofNullable(world.getBlockEntity(portPos)).ifPresent(port -> {
            Optional.ofNullable(world.getBlockEntity(portPos.below())).ifPresent(belowPort ->
                belowPort.getCapability(ForgeCapabilities.ENERGY, Direction.UP).ifPresent(storageBelow -> {
                    if (storageBelow.canReceive())
                        port.getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> {
                            int canExtract = storage.extractEnergy(Config.SERVER.energyTransferLimit.get(), true);

                            if (canExtract > 0) {
                                int transfered = storageBelow.receiveEnergy(canExtract, false);

                                if (transfered > 0)
                                    storage.extractEnergy(transfered, false);
                            }
                        });
                })
            );

            BlockState state = port.getBlockState();
            level.sendBlockUpdated(portPos, state, state, Block.UPDATE_CLIENTS);
        });
    }
}
