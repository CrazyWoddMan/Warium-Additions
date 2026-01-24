package crazywoddman.warium_additions.mixins.create;

import net.mcreator.crustychunks.init.CrustyChunksModGameRules;
import net.mcreator.crustychunks.procedures.AssemblyCentrifugeTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("create"))
@Mixin(value = AssemblyCentrifugeTickProcedure.class, remap = false)
public class AssemblyCentrifugeTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD")
    )
    private static void cacheEnrichmentTime(LevelAccessor world, double x, double y, double z, BlockState blockstate, CallbackInfo ci) {
        if (!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(x, y, z));
            blockEntity.getPersistentData().putInt(
                "enrichmentTimeGamerule",
                world.getLevelData().getGameRules().getInt(CrustyChunksModGameRules.ENRICHMENT_TIME)
            );
            blockEntity.getPersistentData().putBoolean("Ready", false);
        }
    }

    @ModifyVariable(
        method = "execute",
        at = @At(value = "STORE", ordinal = 1),
        name = "input"
    )
    private static ItemStack modifyInputItem(ItemStack stack, LevelAccessor world, double x, double y, double z) {
        world.getBlockEntity(BlockPos.containing(x, y, z)).getPersistentData().putBoolean("Ready", true);
        return stack;
    }
}
