package crazywoddman.warium_additions.mixins.crusty_chunks.firearms;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.mcreator.crustychunks.item.ArmorPeelerAnimatedItem;
import net.mcreator.crustychunks.item.AutoPistolItem;
import net.mcreator.crustychunks.item.AutomaticRifleItem;
import net.mcreator.crustychunks.item.BattleRifleItem;
import net.mcreator.crustychunks.item.BoltActionRifleAnimatedItem;
import net.mcreator.crustychunks.item.BreakActionShotgunAnimatedItem;
import net.mcreator.crustychunks.item.BreechRifleItem;
import net.mcreator.crustychunks.item.BurstRifleItem;
import net.mcreator.crustychunks.item.EradicationItem;
import net.mcreator.crustychunks.item.FlameThrowerAnimatedItem;
import net.mcreator.crustychunks.item.FlarePistolItem;
import net.mcreator.crustychunks.item.GrenadeLauncherItem;
import net.mcreator.crustychunks.item.LMGAnimatedItem;
import net.mcreator.crustychunks.item.LeverRifleItem;
import net.mcreator.crustychunks.item.MachineCarbineItem;
import net.mcreator.crustychunks.item.PumpActionShotgunAnimatedItem;
import net.mcreator.crustychunks.item.RevolverAnimatedItem;
import net.mcreator.crustychunks.item.SMGAnimatedItem;
import net.mcreator.crustychunks.item.ScopedBoltActionRifleAnimatedItem;
import net.mcreator.crustychunks.item.ScopedBreechRifleItem;
import net.mcreator.crustychunks.item.SemiAutomaticPistolAnimatedItem;
import net.mcreator.crustychunks.item.SemiAutomaticRifleAnimatedItem;
import net.mcreator.crustychunks.item.SingleShotRifleItem;
import net.mcreator.crustychunks.item.StealthPistolItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@Mixin({
    AutoPistolItem.class,
    SemiAutomaticPistolAnimatedItem.class,
    StealthPistolItem.class,
    RevolverAnimatedItem.class,
    SMGAnimatedItem.class,
    MachineCarbineItem.class,
    LeverRifleItem.class,
    SemiAutomaticRifleAnimatedItem.class,
    AutomaticRifleItem.class,
    BurstRifleItem.class,
    LMGAnimatedItem.class,
    BattleRifleItem.class,
    BoltActionRifleAnimatedItem.class,
    ScopedBoltActionRifleAnimatedItem.class,
    BreakActionShotgunAnimatedItem.class,
    PumpActionShotgunAnimatedItem.class,
    BreechRifleItem.class,
    ScopedBreechRifleItem.class,
    SingleShotRifleItem.class,
    FlameThrowerAnimatedItem.class,
    GrenadeLauncherItem.class,
    ArmorPeelerAnimatedItem.class,
    FlarePistolItem.class,
    EradicationItem.class
})
public class FireArmsKeyMappingsMixin extends Item {

    private FireArmsKeyMappingsMixin() {
        super(null);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return false;
    }
    
    @Inject(
        method = "use",
        at = @At("HEAD"),
        cancellable = true
    )
    private void wadditions$use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        cir.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(hand)));
    }

    @Inject(
        method = "onEntitySwing",
        at = @At("HEAD"),
        cancellable = true,
        remap = false,
        require = 0
    )
    private void wadditions$onEntitySwing(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}