package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.mcreator.crustychunks.procedures.CableConnectionProcedure;
import net.mcreator.crustychunks.procedures.HoseConnectionProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin({
    HoseConnectionProcedure.class,
    CableConnectionProcedure.class})
public class ConnectionProceduresMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"
        )
    )
    private static Block redirectGetBlock(BlockState state) {
        Block block = state.getBlock();

        if (block.equals(CrustyChunksModBlocks.REFINERY_TOWER.get()))
            return CrustyChunksModBlocks.FUEL_TANK.get();

        return block;
    }

    @Redirect(
        method = "lambda$execute$0",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V",
            remap = true
        ),
        remap = false
    )
    private static void redirectShrink(ItemStack stack, int amount, LevelAccessor world, double x, double y, double z, ItemStack itemstack, Entity entity) {
        if (entity instanceof Player player && !player.isCreative())
            stack.shrink(1);
    }
}
