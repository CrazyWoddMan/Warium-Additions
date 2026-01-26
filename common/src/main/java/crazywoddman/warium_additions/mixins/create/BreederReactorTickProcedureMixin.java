package crazywoddman.warium_additions.mixins.create;

import net.mcreator.crustychunks.procedures.BreederReactorTickProcedure;
import net.mcreator.crustychunks.init.CrustyChunksModGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("create"))
@Mixin(value = BreederReactorTickProcedure.class)
public class BreederReactorTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        remap = false
    )
    private static void cacheEnrichmentTime(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        if (!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(x, y, z));
            CompoundTag data = blockEntity.getPersistentData();
            data.putBoolean("Ready", false);
            data.putInt(
                "enrichmentTimeGamerule",
                world.getLevelData().getGameRules().getInt(CrustyChunksModGameRules.ENRICHMENT_TIME)
            );
        }
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        )
    )
    private static void redirectPutDouble(CompoundTag data, String tag, double value) {
        data.putDouble(tag, value);
        data.putBoolean("Ready", true);
    }

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        remap = false
    )
    private static void sendBlockUpdate(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        if (!world.isClientSide() && world instanceof Level level) {
            BlockPos pos = BlockPos.containing(x, y, z);
            BlockState state = world.getBlockState(pos);
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_NONE);
        }
    }
}
