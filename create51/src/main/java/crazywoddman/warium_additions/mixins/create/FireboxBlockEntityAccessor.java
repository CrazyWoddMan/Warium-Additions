package crazywoddman.warium_additions.mixins.create;

import net.mcreator.crustychunks.block.entity.FireboxBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("create"))
@Mixin(remap = false, value = FireboxBlockEntity.class)
public interface FireboxBlockEntityAccessor {
    @Accessor("stacks")
    NonNullList<ItemStack> getStacks();
}
