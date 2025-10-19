package crazywoddman.warium_additions.mixins.create;

import net.mcreator.crustychunks.procedures.BreederReactorTickProcedure;
import net.mcreator.crustychunks.procedures.CentrifugeCoreTickProcedure;
import net.mcreator.crustychunks.init.CrustyChunksModGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition(value = "create", versionPredicates = "[0.5.1.j]"))
@Mixin(value = {BreederReactorTickProcedure.class, CentrifugeCoreTickProcedure.class}, remap = false)
public class EnrichmentTimeChacher {

    @Inject(
        method = "execute",
        at = @At("HEAD")
    )
    private static void cacheEnrichmentTime(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        if (!world.isClientSide())
            world.getBlockEntity(BlockPos.containing(x, y, z)).getPersistentData().putInt(
                "enrichmentTimeGamerule",
                world.getLevelData().getGameRules().getInt(CrustyChunksModGameRules.ENRICHMENT_TIME)
            );
    }
}
