package crazywoddman.warium_additions.events;

import crazywoddman.warium_additions.compat.create.fluids.WariumCreateFluids;
import net.mcreator.crustychunks.procedures.Rad1TickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;

// @EventBusSubscriber(modid = WariumAdditions.MODID)
public class WadditionsForgeEvents {

    public static void onLivingTick(LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide && entity.tickCount % 5 == 0) {
            BlockPos pos = entity.blockPosition();

            if (entity.isInFluidType(WariumCreateFluids.YELLOWCAKE_FLUID.get().getFluidType()))
                Rad1TickProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }
}