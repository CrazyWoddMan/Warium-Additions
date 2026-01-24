package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.mcreator.valkyrienwarium.block.entity.LiquidFuelRocketBlockEntity;
import net.mcreator.valkyrienwarium.block.entity.TestThrusterBlockEntity;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin({LiquidFuelRocketBlockEntity.class, TestThrusterBlockEntity.class})
public class FuelContainingBlockEntitiesMixin {

    @Shadow(remap = false)
    private FluidTank fluidTank;

     @Inject(
        method = "<init>",
        at = @At("RETURN"),
        remap = false
    )
    private void acceptAnyKerosene(BlockPos position, BlockState state, CallbackInfo callback) {
        fluidTank.setValidator(stack ->
            WariumAdditionsUtil.compareFluids(stack.getFluid(), CrustyChunksModFluids.KEROSENE.get())
            || ((BlockEntity)(Object)this instanceof TestThrusterBlockEntity
                ? WariumAdditionsUtil.compareFluids(stack.getFluid(), CrustyChunksModFluids.HYDRAZINE.get())
                : false
            )
        );
    }
}
