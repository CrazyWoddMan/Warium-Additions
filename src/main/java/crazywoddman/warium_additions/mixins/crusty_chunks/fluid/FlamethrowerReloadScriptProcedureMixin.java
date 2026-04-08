package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.FlamethrowerReloadScriptProcedure;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.data.WariumAdditionsTags;

@Mixin(FlamethrowerReloadScriptProcedure.class)
public class FlamethrowerReloadScriptProcedureMixin {
    @SuppressWarnings("deprecation")
    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"
        )
    )
    private static Item redirectIsFluidEqual(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof BucketItem bucket && bucket.getFluid().is(WariumAdditionsTags.Fluids.GASOLINE) ? CrustyChunksModItems.PETROLIUM_BUCKET.get() : item;
    }
}
