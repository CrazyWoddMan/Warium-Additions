package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.procedures.OilFireboxUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraft.world.level.LevelAccessor;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;

import crazywoddman.warium_additions.config.Config;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OilFireboxUpdateProcedure.class)
public class OilFireboxUpdateProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        remap = false
    )
    private static void setHeatLevel(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockPos pos = BlockPos.containing(x, y, z);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        BlockState state = world.getBlockState(pos);

        if (blockEntity != null && state != null && state.hasProperty(BlazeBurnerBlock.HEAT_LEVEL)) {
            double fuel = blockEntity.getPersistentData().getDouble("Fuel");
            HeatLevel heat = HeatLevel.valueOf(Config.SERVER.oilFireboxHeat.get());
            HeatLevel getheat = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);

            if (fuel > 0) {
                if (getheat != heat)
                    world.setBlock(pos, state.setValue(BlazeBurnerBlock.HEAT_LEVEL, heat), 3);
            } else if (getheat == heat)
                world.setBlock(pos, state.setValue(BlazeBurnerBlock.HEAT_LEVEL, HeatLevel.NONE), 3);
        }
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 0.0),
        remap = false
    )
    private static double modifyZero(double value, LevelAccessor world, double x, double y, double z) {
        return -(world.getBlockEntity(BlockPos.containing(x, y, z))).getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null).getFluidInTank(0).getAmount();
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        )
    )
    private static void redirectPutDouble(CompoundTag compoundTag, String key, double value, LevelAccessor world, double x, double y, double z) {
        if (key.equals("Fuel"))
            world.getBlockEntity(BlockPos.containing(x, y, z)).getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null).drain(1, FluidAction.EXECUTE);
        else
            compoundTag.putDouble(key, value);
    }
}
