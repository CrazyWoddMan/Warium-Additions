package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.RefineryOnTickUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import crazywoddman.warium_additions.WariumAdditions;

@Mixin(RefineryOnTickUpdateProcedure.class)
public class RefineryOnTickUpdateProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        ),
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V",
                ordinal = 0
            ),
            to = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V",
                ordinal = 5
            )
        )
    )
    private static void redirectPutDouble(CompoundTag compoundTag, String key, double value, LevelAccessor world, double x, double y, double z) {
        switch (compoundTag.getString("Fluid")) {
            case "Oil" -> getFluidTank(world, BlockPos.containing(x, y + 1, z)).fill(new FluidStack(CrustyChunksModFluids.OIL.get(), 250), FluidAction.EXECUTE);
            case "Diesel" -> getFluidTank(world, BlockPos.containing(x, y + 2, z)).fill(new FluidStack(CrustyChunksModFluids.DIESEL.get(), 250), FluidAction.EXECUTE);
            case "Kerosene" -> getFluidTank(world, BlockPos.containing(x, y + 3, z)).fill(new FluidStack(CrustyChunksModFluids.KEROSENE.get(), 250), FluidAction.EXECUTE);
            case "Petrolium" -> getFluidTank(world, BlockPos.containing(x, y + 4, z)).fill(new FluidStack(CrustyChunksModFluids.PETROLIUM.get(), 250), FluidAction.EXECUTE);
        }
    }

    private static IFluidHandler getFluidTank(LevelAccessor world, BlockPos pos) {
        return world.getBlockEntity(pos).getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"
        )
    )
    private static Item redirectGetItem(ItemStack stack, LevelAccessor world, double x, double y, double z) {
        if (stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "refinery_input")))) {
            for(Fluid fluid : List.of(CrustyChunksModFluids.OIL.get(), CrustyChunksModFluids.DIESEL.get(), CrustyChunksModFluids.KEROSENE.get(), CrustyChunksModFluids.PETROLIUM.get())) {
                FluidStack fluidStack = getFluidTank(world, BlockPos.containing(x, ++y, z)).getFluidInTank(0);

                if (!fluidStack.isEmpty() && !fluidStack.getFluid().equals(fluid))
                    return Items.AIR;
            }

            return CrustyChunksModItems.SHALE_OIL.get();
        }

        return Items.AIR;
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
    private static void redirectShrink(ItemStack stack, int amount) {
        if (!(stack.getItem() instanceof BucketItem))
            stack.shrink(amount);
    }

    @ModifyArg(
        method = "lambda$execute$0",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/items/IItemHandlerModifiable;setStackInSlot(ILnet/minecraft/world/item/ItemStack;)V"
        ),
        remap = false
    )
    private static ItemStack modifyStackInSlot(ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem() instanceof BucketItem)
            stack = new ItemStack(Items.BUCKET, 1);
        
        return stack;
    }
}
