package crazywoddman.warium_additions.block.converter;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.ThrottleProvider;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.IRotate.SpeedLevel;
import com.simibubi.create.content.kinetics.gauge.SpeedGaugeBlockEntity;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class RotationConverterBlockEntity extends SpeedGaugeBlockEntity {

    public RotationConverterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setLazyTickRate(5);
    }

    private final int kineticToStressRatio = Config.SERVER.kineticToStressRatio.get();
    private final int kineticToSpeedRatio = Config.SERVER.kineticToSpeedRatio.get();
    private final int maxThrottle = Config.SERVER.maxThrottle.get();
    private int lastThrottle;
    private int lastSignal;

    private float scaleFromSignal(float value) {
        return this.lastSignal > 0 ? value / 15 * (15 - this.lastSignal) : value;
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        
        int signal = this.level.getBestNeighborSignal(this.getBlockPos());
        int throttle = Math.abs(ThrottleProvider.get(this, this.maxThrottle));

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

		float speed = Math.round(Math.abs(scaleFromSignal(getSpeed())) / this.maxThrottle * this.lastThrottle * 10) / 10;
        getPersistentData().putDouble(
            "KineticPower",
            speed / this.kineticToSpeedRatio
        );
        this.dialTarget = getDialTarget(speed);
        this.color = Color
            .mixColors(SpeedLevel.of(speed)
			.getColor(), 0xffffff, .25f);
        notifyUpdate();
	}

    @Override
    public float calculateStressApplied() {
        float impact = Math.round(scaleFromSignal(this.kineticToStressRatio) / this.kineticToSpeedRatio / this.maxThrottle * this.lastThrottle);
        this.lastStressApplied = impact;
		return impact;
    }

    @Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.translate("gui.gauge.info_header")
			.style(ChatFormatting.AQUA)
			.forGoggles(tooltip);
		Lang.translate("gui.speedometer.title")
			.style(ChatFormatting.GRAY)
			.forGoggles(tooltip);
		SpeedLevel.getFormattedSpeedText(scaleFromSignal(getSpeed()) / this.maxThrottle * this.lastThrottle, this.overStressed)
			.forGoggles(tooltip);
        addStressImpactStats(tooltip, calculateStressApplied());

		return true;
	}
}
