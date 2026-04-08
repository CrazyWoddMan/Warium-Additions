package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.block.ArtillerybreechBlock;
import net.mcreator.crustychunks.block.AutocannonBlock;
import net.mcreator.crustychunks.block.BattleCannonBreechBlock;
import net.mcreator.crustychunks.block.CountermeasureDispenserBlock;
import net.mcreator.crustychunks.block.FireSpearMissileHardpointBlock;
import net.mcreator.crustychunks.block.FlameThrowerBlock;
import net.mcreator.crustychunks.block.FuelTankBlock;
import net.mcreator.crustychunks.block.HeavyMachineGunBlock;
import net.mcreator.crustychunks.block.LargeRocketPodBlock;
import net.mcreator.crustychunks.block.LightAutocannonBlock;
import net.mcreator.crustychunks.block.LightMachineGunBlock;
import net.mcreator.crustychunks.block.MachineGunBlock;
import net.mcreator.crustychunks.block.MinigunBlock;
import net.mcreator.crustychunks.block.RadarSpearMissileHardpointBlock;
import net.mcreator.crustychunks.block.RocketPodBlock;
import net.mcreator.crustychunks.block.RotaryAutoCannonBlock;
import net.mcreator.crustychunks.block.SeekerSpearMissileHardpointBlock;
import net.mcreator.crustychunks.block.SmokeLauncherBlock;
import net.mcreator.crustychunks.block.StrikeSpearMissileHardpointBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.mixins.crusty_chunks.weapons.CannonsCreativeMode;
import crazywoddman.warium_additions.mixins.crusty_chunks.weapons.HeavyFlameThrowerCreativeMode;
import crazywoddman.warium_additions.mixins.crusty_chunks.weapons.PodsCreativeMode;
import crazywoddman.warium_additions.mixins.crusty_chunks.weapons.TurretsCreativeMode;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

/**
 * Adds <b>Creative Mode</b> {@link BlockState} {@link Property}.
 * <p>In this mode weapons don't require ammo and don't output casings, fluid tank doen't consume fuel
 * @see WariumAdditionsUtil#CREATIVE_MODE
 * @see TurretsCreativeMode
 * @see CannonsCreativeMode
 * @see HeavyFlameThrowerCreativeMode
 * @see PodsCreativeMode
 */
@Mixin({
    FireSpearMissileHardpointBlock.class,
    SeekerSpearMissileHardpointBlock.class,
    RadarSpearMissileHardpointBlock.class,
    StrikeSpearMissileHardpointBlock.class,
    MinigunBlock.class,
    LightMachineGunBlock.class,
    MachineGunBlock.class,
    HeavyMachineGunBlock.class,
    SmokeLauncherBlock.class,
    FlameThrowerBlock.class,
    RotaryAutoCannonBlock.class,
    AutocannonBlock.class,
    LightAutocannonBlock.class,
    BattleCannonBreechBlock.class,
    ArtillerybreechBlock.class,
    RocketPodBlock.class,
    LargeRocketPodBlock.class,
    CountermeasureDispenserBlock.class,
    FuelTankBlock.class
})
public class BlocksCreativeMode {

    @Redirect(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;",
            ordinal = 0
        )
    )
    private <T extends Comparable<T>, V extends T> Object setDefaultCreativeProperty(BlockState state, Property<T> property, V value) {
        return state.setValue(property, value).setValue(WariumAdditionsUtil.CREATIVE_MODE, false);
    }

    @Inject(
        method = "createBlockStateDefinition",
        at = @At("TAIL")
    )
    private void addCreativeProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(WariumAdditionsUtil.CREATIVE_MODE);
    }
}
