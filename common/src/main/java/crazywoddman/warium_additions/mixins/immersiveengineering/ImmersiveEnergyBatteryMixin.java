package crazywoddman.warium_additions.mixins.immersiveengineering;

import net.mcreator.crustychunks.block.entity.EnergyBatteryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.EnumMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import blusunrize.immersiveengineering.api.IEEnums.IOSideConfig;
import blusunrize.immersiveengineering.api.utils.DirectionUtils;
import blusunrize.immersiveengineering.client.utils.TextUtils;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IBlockOverlayText;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IConfigurableSides;
import blusunrize.immersiveengineering.common.config.IEClientConfig;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("immersiveengineering"))
@Mixin(EnergyBatteryBlockEntity.class)
public class ImmersiveEnergyBatteryMixin implements IConfigurableSides, IBlockOverlayText {

    public EnumMap<Direction, IOSideConfig> sideConfig = new EnumMap<>(Direction.class);

    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void injectSideConfig(BlockPos position, BlockState state, CallbackInfo ci) {
        for (Direction direction : DirectionUtils.VALUES)
            this.sideConfig.put(direction, IOSideConfig.NONE);
    }

    @Inject(
        method = "load",
        at = @At("TAIL")
    )
    private void injectRead(CompoundTag compound, CallbackInfo ci) {
        for (Direction direction : DirectionUtils.VALUES) {
            String key = "sideConfig_" + direction.ordinal();

            if (compound.contains(key))
                this.sideConfig.put(direction, IOSideConfig.VALUES[compound.getInt(key)]);
        }
    }

    @Inject(
        method = "saveAdditional",
        at = @At("TAIL")
    )
    private void injectSave(CompoundTag compound, CallbackInfo ci) {
        for(Direction direction : DirectionUtils.VALUES)
            compound.putInt("sideConfig_" + direction.ordinal(), this.sideConfig.get(direction).ordinal());
    }

    @Override
    public IOSideConfig getSideConfig(Direction side) {
        return this.sideConfig.get(side);
    }

    @Override
    public boolean toggleSide(Direction side, Player player) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();
        BlockState state = blockEntity.getBlockState();
        Block block = state.getBlock();
        this.sideConfig.put(side, IOSideConfig.next(this.sideConfig.get(side)));
        blockEntity.setChanged();
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        level.updateNeighborsAt(pos, block);
        level.blockEvent(pos, block, 0, 0);
        return true;
    }

    @Override
    public Component[] getOverlayText(Player player, HitResult mop, boolean hammer) {
        if (hammer && IEClientConfig.showTextOverlay.get() && mop instanceof BlockHitResult bmop)
            return TextUtils.sideConfigWithOpposite(
                "desc.immersiveengineering.info.blockSide.connectEnergy.",
                this.sideConfig.get(bmop.getDirection()),
                this.sideConfig.get(bmop.getDirection().getOpposite())
            );
        else
            return null;
    }

    @Override
    public boolean useNixieFont(Player player, HitResult mop) {
        return false;
    }
}
