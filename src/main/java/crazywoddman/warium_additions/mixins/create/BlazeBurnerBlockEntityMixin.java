package crazywoddman.warium_additions.mixins.create;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;

import crazywoddman.warium_additions.compat.create.blocks.BlazeBurnerHeatProvider;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition("create"))
@Mixin(BlazeBurnerBlockEntity.class)
public class BlazeBurnerBlockEntityMixin extends BlockEntity {

    private BlazeBurnerBlockEntityMixin() {
        super(null, null, null);
    }

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void wadditions$tick(CallbackInfo callback) {
        BlazeBurnerHeatProvider.tick(this, this.level, this.worldPosition);
    }
}
