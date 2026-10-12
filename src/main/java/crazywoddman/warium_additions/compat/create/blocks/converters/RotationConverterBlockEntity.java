package crazywoddman.warium_additions.compat.create.blocks.converters;

import crazywoddman.warium_additions.config.WadditionsConfig;
import crazywoddman.warium_additions.utils.WadditionsUtil;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.IRotate.SpeedLevel;
import com.simibubi.create.content.kinetics.gauge.SpeedGaugeBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;

import net.createmod.catnip.theme.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class RotationConverterBlockEntity extends SpeedGaugeBlockEntity {

    public RotationConverterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private final int kineticToStressRatio = WadditionsConfig.KINETIC_TO_STRESS.get();
    private final int kineticToSpeedRatio = WadditionsConfig.KINETIC_TO_SPEED.get();
    private float lastThrottle;
    private int lastSignal;

    private float scaleFromSignal(float value) {
        return this.lastSignal > 0 ? value / 15 * (15 - this.lastSignal) : value;
    }

    @Override
    public void tick() {
        super.tick();
        
        BlockPos pos = getBlockPos();
        int signal = this.level.getBestNeighborSignal(pos);
        float throttle = WadditionsUtil.getThrottle(this).orElse((double)WadditionsUtil.MAX_THROTTLE).floatValue();

        if (this.lastThrottle != throttle || this.lastSignal != signal) {
            this.lastThrottle = throttle;
            this.lastSignal = signal;

            if (this.level.isClientSide)
                return;

            KineticNetwork network = getOrCreateNetwork();
            
            if (network != null)
                network.updateStressFor(this, calculateStressApplied());

            onSpeedChanged(this.speed);
        }

    }

    @Override
	public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
		float speed = Math.round(Math.abs(scaleFromSignal(getSpeed())) / WadditionsUtil.MAX_THROTTLE * this.lastThrottle * 10) / 10;
        getPersistentData().putDouble("KineticPower", speed / this.kineticToSpeedRatio);
        this.dialTarget = getDialTarget(speed);
        this.color = Color.mixColors(SpeedLevel.of(speed).getColor(), 0xffffff, .25f);
        notifyUpdate();
	}

    @Override
    public float calculateStressApplied() {
        return this.lastStressApplied = Math.round(scaleFromSignal(this.kineticToStressRatio) / this.kineticToSpeedRatio / WadditionsUtil.MAX_THROTTLE * this.lastThrottle);
    }

    @Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("gui.gauge.info_header")
			.style(ChatFormatting.AQUA)
			.forGoggles(tooltip);
		CreateLang.translate("gui.speedometer.title")
			.style(ChatFormatting.GRAY)
			.forGoggles(tooltip);
		SpeedLevel.getFormattedSpeedText(scaleFromSignal(getSpeed()) / WadditionsUtil.MAX_THROTTLE * this.lastThrottle, this.overStressed)
			.forGoggles(tooltip);
        addStressImpactStats(tooltip, calculateStressApplied());

		return true;
	}
}
