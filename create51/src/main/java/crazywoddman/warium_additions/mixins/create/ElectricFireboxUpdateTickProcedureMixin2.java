package crazywoddman.warium_additions.mixins.create;

import net.mcreator.crustychunks.procedures.ElectricFireboxUpdateTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import crazywoddman.warium_additions.config.Config;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;

@Restriction(require = @Condition("create"))
@Mixin(ElectricFireboxUpdateTickProcedure.class)
public class ElectricFireboxUpdateTickProcedureMixin2 {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z",
            ordinal = 0
        )
    )
    private static boolean setHeatLevel(Level level, BlockPos pos) {
        boolean hasSignal = level.hasNeighborSignal(pos);
        level.getBlockEntity(pos).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> {
            BlockState currentState = level.getBlockState(pos);
            BlockState newState = currentState.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.valueOf(
                hasSignal && storage.getEnergyStored() >= Config.SERVER.electricFireboxConsumption.get()
                ? Config.SERVER.electricFireboxHeatLevel.get()
                : "NONE"
            ));

            if (currentState != newState)
                level.setBlock(pos, newState, Block.UPDATE_INVISIBLE);
        });

        return hasSignal;
    }
}
