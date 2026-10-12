package crazywoddman.warium_additions.compat.create.fluids;

import java.util.function.Supplier;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.create.WariumCreate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid.Flowing;

public class WariumCreateFluids {
    public static final Supplier<Flowing> YELLOWCAKE_FLUID = WariumCreate.REGISTRATE
		.fluid(
			"yellowcake_mixture",
			ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "block/yellowcake_mixture_still"),
			ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "block/yellowcake_mixture_flow"),
			YellowcakeMixture::new
		)
		.fluidProperties(p -> p
			.levelDecreasePerBlock(2)
			.tickRate(25)
			.slopeFindDistance(3)
			.explosionResistance(100f)
		)
		.register();

    public static void register() {}
}
