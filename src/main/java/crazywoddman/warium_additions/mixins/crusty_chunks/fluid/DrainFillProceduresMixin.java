package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.BlockMinerReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.FluidDrainProcedure;
import net.mcreator.crustychunks.procedures.FuelTankFillProcedure;
import net.mcreator.crustychunks.procedures.HoseConnectionProcedure;
import net.mcreator.crustychunks.procedures.KeroseneFillScriptProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.WariumAdditions;

@Mixin(
    value ={
        FluidDrainProcedure.class,
        KeroseneFillScriptProcedure.class,
        BlockMinerReloadScriptProcedure.class,
        FuelTankFillProcedure.class
    }, 
    remap = false)
public class DrainFillProceduresMixin {

    private static void displayTankInfo(Player player, IFluidHandler handler) {
        if (!WariumAdditions.createLoaded) {
            FluidStack fluidInTank = handler.getFluidInTank(0);
            player.displayClientMessage(Component.literal(fluidInTank.isEmpty() ? "Empty" : (fluidInTank.getDisplayName().getString() + ": " + fluidInTank.getAmount() + "/" + handler.getTankCapacity(0))), true);
        }
    }

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void injectExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (entity instanceof Player player) {
            BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(x, y, z));
            IFluidHandler handler = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
            ItemStack newItem;
            
            if (handler != null) {
                ItemStack handStack = player.getMainHandItem();
                Item handItem = handStack.getItem();

                if (handItem.equals(CrustyChunksModItems.FUEL_HOSE.get())) {
                    HoseConnectionProcedure.execute(world, x, y, z, entity, handStack);
                    displayTankInfo(player, handler);
                    ci.cancel();
                    return;
                }

                else if (handItem.equals(Items.BUCKET)) {
                    if (handler.drain(1000, IFluidHandler.FluidAction.SIMULATE).getAmount() == 1000)
                        newItem = new ItemStack(handler.drain(1000, IFluidHandler.FluidAction.EXECUTE).getFluid().getBucket());

                    else {
                        displayTankInfo(player, handler);
                        ci.cancel();
                        return;
                    }
                }
                    
                else if (handItem instanceof BucketItem bucketItem) {
                    Fluid fluid = bucketItem.getFluid();
                    FluidStack fluidStack = new FluidStack(fluid, 1000);
                    
                    if (!fluid.equals(Fluids.EMPTY) && handler.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) == 1000) {
                        handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                        newItem = new ItemStack(Items.BUCKET);
                    }

                    else {
                        displayTankInfo(player, handler);
                        ci.cancel();
                        return;
                    }
                }

                else {
                    displayTankInfo(player, handler);
                    ci.cancel();
                    return;
                }

                if (!player.isCreative()) {
                    handStack.shrink(1);
                    
                    if (handStack.isEmpty())
                        player.setItemInHand(InteractionHand.MAIN_HAND, newItem);
                        
                    else if (!player.addItem(newItem))
                        player.spawnAtLocation(newItem, 1);
                }

                if (world instanceof Level level)
                    level.playLocalSound(
                        x, y, z,
                        ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.tryParse("item.bucket.fill")),
                        SoundSource.NEUTRAL,
                        2.0F,
                        0.2F + 1.0F / handler.getTankCapacity(0) * handler.getFluidInTank(0).getAmount(),
                        false
                    );
            
                displayTankInfo(player, handler);
            }
        }
        
        ci.cancel();
    }
}
