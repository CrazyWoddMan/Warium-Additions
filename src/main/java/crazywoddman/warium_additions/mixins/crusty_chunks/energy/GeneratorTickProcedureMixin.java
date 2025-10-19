package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.GeneratorTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.config.Config;

@Mixin(GeneratorTickProcedure.class)
public class GeneratorTickProcedureMixin {

    private static Direction getFacing(BlockState state) {
        for (Property<?> property : state.getProperties())
            if (property instanceof DirectionProperty facing)
                return state.getValue(facing);

        return null;
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        )
    )
    private static void redirectPutDouble(CompoundTag compound, String key, double value) {}

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
        )
    )
    private static int redirectSendParticles(ServerLevel level, ParticleOptions p, double x, double y, double z, int i, double d1, double d2, double d3, double d4) {
        BlockEntity blockEntity = level.getBlockEntity(BlockPos.containing(x - 0.5, y - 0.5, z - 0.5));
        Direction facing = getFacing(blockEntity.getBlockState());

        if (facing != null) {
            BlockEntity backBlockEntity = level.getBlockEntity(blockEntity.getBlockPos().relative(facing.getOpposite()));

            if (backBlockEntity != null) {
                IEnergyStorage storage = blockEntity.getCapability(ForgeCapabilities.ENERGY).orElse(null);
                int energy = Math.min(Config.SERVER.energyTransferLimit.get(), (int)Math.round(backBlockEntity.getPersistentData().getDouble("KineticPower") * Config.SERVER.kineticToFeRate.get()));
                BlockEntity frontBlockEntity = level.getBlockEntity(blockEntity.getBlockPos().relative(facing));
                if (storage.getEnergyStored() != energy)
                    storage.receiveEnergy(energy, false);

                if (frontBlockEntity != null)
                    frontBlockEntity.getCapability(ForgeCapabilities.ENERGY, facing.getOpposite()).ifPresent(handler -> {
                        if (handler.canReceive())
                            handler.receiveEnergy(energy, false);
                    });
            }
        }
            
        return level.sendParticles(p, x, y, z, i, d1, d2, d3, d4);
    }

    @ModifyVariable(
        method = "execute",
        name = "Kinetic",
        at = @At(value = "STORE", ordinal = 2),
        remap = false
    )
    private static double modifyKinetic(double value, LevelAccessor world, double x, double y, double z, BlockState blockstate) {
        world
        .getBlockEntity(BlockPos.containing(x, y, z))
        .getCapability(ForgeCapabilities.ENERGY)
        .ifPresent(storage -> {
            if (storage.getEnergyStored() > 0)
                storage.receiveEnergy(0, false);
        });

        return value;
    }
}
