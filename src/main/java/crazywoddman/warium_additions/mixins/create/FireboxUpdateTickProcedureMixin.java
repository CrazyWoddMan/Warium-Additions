package crazywoddman.warium_additions.mixins.create;

import net.mcreator.crustychunks.procedures.FireboxUpdateTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;

import crazywoddman.warium_additions.config.Config;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition("create"))
@Mixin(value = FireboxUpdateTickProcedure.class, remap = false)
public class FireboxUpdateTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD")
    )
    private static void setHeatLevel(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockPos pos = BlockPos.containing(x, y, z);
        BlockState state = world.getBlockState(pos);
        boolean hasCoal = false;

        for (ItemStack stack : ((FireboxBlockEntityAccessor)world.getBlockEntity(pos)).getStacks()) {
            if (!stack.isEmpty() && (stack.getItem() == Items.COAL || stack.getItem() == Items.COAL)) {
                hasCoal = true;
                break;
            }
        }

        BlazeBurnerBlock.HeatLevel requiredHeat = HeatLevel.valueOf(Config.SERVER.fireboxHeatLevel.get());
        BlazeBurnerBlock.HeatLevel currentHeat = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);

        if (hasCoal) {
            if (currentHeat != requiredHeat)
                world.setBlock(pos, state.setValue(BlazeBurnerBlock.HEAT_LEVEL, requiredHeat), 3);
        } else if (currentHeat == requiredHeat)
            world.setBlock(pos, state.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.NONE), 3);
    }
}
