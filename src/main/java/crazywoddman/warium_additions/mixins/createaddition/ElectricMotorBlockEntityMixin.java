package crazywoddman.warium_additions.mixins.createaddition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlock;
import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlockEntity;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.level.block.Block;

@Restriction(require = {
    @Condition("createaddition"),
    @Condition("valkyrien_warium")
})
@Mixin(ElectricMotorBlockEntity.class)
public class ElectricMotorBlockEntityMixin {

    @Unique
    private boolean lastSignal;

    @Inject(
        method = "tick",
        at = @At("HEAD"),
        remap = false
    )
    private void throttleControl(CallbackInfo callbackInfo) {
        ElectricMotorBlockEntity blockEntity = (ElectricMotorBlockEntity)(Object)this;

        this.lastSignal = blockEntity.getLevel().hasNeighborSignal(blockEntity.getBlockPos());
        boolean powered = this.lastSignal || WariumAdditionsUtil.getThrottle(blockEntity).map(throttle -> throttle == 0).orElse(false);

        if (blockEntity.getLevel().getGameTime() % 8 != 0)
            return;

        if (blockEntity.getBlockState().getValue(ElectricMotorBlock.POWERED) != powered) {
            blockEntity.getLevel().setBlock(
                blockEntity.getBlockPos(),
                blockEntity.getBlockState().setValue(
                    ElectricMotorBlock.POWERED,
                    powered
                ),
                Block.UPDATE_CLIENTS
            );

            blockEntity.updateGeneratedRotation();
        }
    }
}
