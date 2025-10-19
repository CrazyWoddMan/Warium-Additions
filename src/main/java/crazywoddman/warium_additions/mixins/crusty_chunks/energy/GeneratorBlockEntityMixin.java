package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.GeneratorBlock;
import net.mcreator.crustychunks.block.entity.GeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.common.util.LazyOptional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;

@Mixin(GeneratorBlockEntity.class)
public class GeneratorBlockEntityMixin {

    @Unique
    private EnergyStorage energyStorage;
    private final int transLimit = Config.SERVER.energyTransferLimit.get();

    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void injectEnergyStorage(BlockPos position, BlockState state, CallbackInfo ci) {
        this.energyStorage = new EnergyStorage(transLimit, 0, transLimit) {

            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                if (simulate)
                    return 0;

                this.energy = maxReceive;
                
                return maxReceive;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                return 0;
            }
        };
    }

    @Redirect(
        method = "load",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/energy/EnergyStorage;deserializeNBT(Lnet/minecraft/nbt/Tag;)V",
            remap = false
        )
    )
    private void redirectDeserializeNBT(EnergyStorage storage, Tag tag) {
        this.energyStorage.deserializeNBT(tag);
    }

    @Redirect(
        method = "saveAdditional",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;put(Ljava/lang/String;Lnet/minecraft/nbt/Tag;)Lnet/minecraft/nbt/Tag;"
        )
    )
    private Tag redirectCompoundPut(CompoundTag compound, String key, Tag tag) {
        return compound.put("energyStorage", this.energyStorage.serializeNBT());
    }

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectEnergyCapability(Capability<?> capability, Direction facing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.ENERGY && (facing == null || facing == ((BlockEntity)(Object)this).getBlockState().getValue(GeneratorBlock.FACING)))
            cir.setReturnValue(LazyOptional.of(() -> this.energyStorage).cast());
    }
}
