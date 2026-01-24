package crazywoddman.warium_additions.mixins.createaddition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlock;
import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlockEntity;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

@Restriction(require = {
    @Condition(value = "createaddition", versionPredicates = "[1.2.5]"),
    @Condition("valkyrien_warium")
})
@Mixin(remap = false, value = ElectricMotorBlockEntity.class)
public class ElectricMotorBlockEntityMixin {

    @Unique
    private boolean lastSignal;

    @Unique
    private Integer throttle;

    @Inject(
        method = "tick",
        at = @At("HEAD")
    )
    private void throttleControl(CallbackInfo callbackInfo) {
        BlockEntity blockEntity = (BlockEntity) (Object) this;

        if (blockEntity.getLevel().getGameTime() % 10 == 0) {
            this.lastSignal = blockEntity.getLevel().hasNeighborSignal(blockEntity.getBlockPos());
            boolean oldPowered = blockEntity.getBlockState().getValue(ElectricMotorBlock.POWERED);
            boolean powered = this.lastSignal;

            if (!this.lastSignal) {
                this.throttle = WariumAdditionsUtil.getThrottle(blockEntity, null);
                if (this.throttle != null)
                    powered = this.throttle == 0;
            }

            if (oldPowered != powered) {
                blockEntity.getLevel().setBlock(
                    blockEntity.getBlockPos(),
                    blockEntity.getBlockState().setValue(
                        ElectricMotorBlock.POWERED,
                        powered
                    ),
                    Block.UPDATE_CLIENTS
                );

                if (blockEntity instanceof GeneratingKineticBlockEntity genBlockEntity)
                    genBlockEntity.updateGeneratedRotation();
            }
        }
    }
}
