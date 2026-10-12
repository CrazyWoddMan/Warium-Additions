package crazywoddman.warium_additions.compat.create.fluids;

import org.joml.Vector3f;

import com.simibubi.create.AllFluids.TintedFluidType;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;

public class YellowcakeMixture extends TintedFluidType {

	public YellowcakeMixture(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
		super(properties, stillTexture, flowingTexture);
	}

	@Override
	protected int getTintColor(FluidStack stack) {
		return -1;
	}

	@Override
	public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
		return 0x00FFFFFF;
	}

	@Override
	protected Vector3f getCustomFogColor() {
		return new Vector3f(255, 255, 0);
	}

	@Override
	protected float getFogDistanceModifier() {
		return 0.5f;
	}

	@Override
	public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
		entity.setDeltaMovement(entity.getDeltaMovement().scale(0.8));
		return false;
	}
}
