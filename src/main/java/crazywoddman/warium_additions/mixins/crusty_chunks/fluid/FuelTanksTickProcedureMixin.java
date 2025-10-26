package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.procedures.FuelTankInputTickProcedure;
import net.mcreator.crustychunks.procedures.FuelTankModuleOnTickUpdateProcedure;
import net.mcreator.crustychunks.procedures.FuelTankTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    value = {
        FuelTankTickProcedure.class,
        FuelTankModuleOnTickUpdateProcedure.class,
        FuelTankInputTickProcedure.class
    }
)
public class FuelTanksTickProcedureMixin {
    private static Fluid realFluid;
    private static BlockPos neighborPos;

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putString(Ljava/lang/String;Ljava/lang/String;)V"
        )
    )
    private static void redirectPut(CompoundTag compound, String key, String value) {}

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "Kerosene"),
        remap = false
    )
    private static String modifyKerosene(String value) {
        return "";
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "Diesel"),
        remap = false
    )
    private static String modifyDiesel(String value) {
        return "";
    }

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        remap = false
    )
    private static void captureFluid(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(x, y, z));

        if (blockEntity != null)
            blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
                realFluid = handler.getFluidInTank(0).getFluid();
            });
    }
    
    @ModifyVariable(
        method = "execute",
        name = "amount",
        at = @At(
            value = "STORE",
            ordinal = 1
        ),
        remap = false
    )
    private static double modifyFirstAmount(double original) {
        return 0.0;
    }

    @ModifyArg(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelAccessor;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;",
            ordinal = 1
        )
    )
    private static BlockPos getNeighborPos(BlockPos pos) {
        neighborPos = pos;
        return pos;
    }

    @ModifyVariable(
        method = "execute",
        name = "amount",
        at = @At(
            value = "STORE",
            ordinal = 2
        ),
        remap = false
    )
    private static double modifySecondAmount(double original, LevelAccessor world, double x, double y, double z) {
        return (neighborPos != null && world.getBlockEntity(neighborPos) != null)
        ? world
            .getBlockEntity(neighborPos)
            .getCapability(ForgeCapabilities.FLUID_HANDLER)
            .orElse(null)
            .fill(new FluidStack(realFluid, 250), FluidAction.SIMULATE)
        : 0;
    }

    @ModifyArg(
        method = "lambda$execute$3",
        at = @At(
            value = "INVOKE", 
            target = "Lnet/minecraftforge/fluids/FluidStack;<init>(Lnet/minecraft/world/level/material/Fluid;I)V"
        ),
        remap = false
    )
    private static Fluid modifyFluid(Fluid fluid) {
        return realFluid == null ? Fluids.EMPTY : realFluid;
    }
}
