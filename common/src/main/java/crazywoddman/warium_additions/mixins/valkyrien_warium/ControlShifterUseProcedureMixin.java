package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.mcreator.valkyrienwarium.procedures.ControlShifterUseProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;
import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.IScrollValue;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin(ControlShifterUseProcedure.class)
public class ControlShifterUseProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void redirectDouble(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (entity instanceof Player player && world instanceof ServerLevel level)
            WariumAdditionsUtil.getTargetBlock(player, level).ifPresent(blockEntity -> {
                if (blockEntity instanceof IScrollValue accessor) {
                    int scrollValue = accessor.getScrollValue();
                    double ratio = Config.SERVER.kineticToFeRate.get();
                    int[] values = new int[]{
                        (int)Math.round(Config.SERVER.smallDieselEnginePower.get() * ratio),
                        (int)Math.round(Config.SERVER.smallPetrolEnginePower.get() * ratio),
                        (int)Math.round(Config.SERVER.mediumDieselEnginePower.get() * ratio),
                        (int)Math.round(Config.SERVER.mediumPetrolEnginePower.get() * ratio),
                        (int)Math.round(Config.SERVER.largeEnginePower.get() * ratio),
                        (int)Math.round(Config.SERVER.jetTurbinePower.get() * ratio),
                        Config.SERVER.electricFireboxConsumption.get(),
                        Config.SERVER.energyTransferLimit.get()
                    };

                    values = Arrays
                        .stream(values)
                        .distinct()
                        .sorted()
                        .toArray();
                        
                    if (scrollValue > values[values.length - 2])
                        scrollValue = values[0];
                    else {
                        values = Arrays.copyOf(values, values.length + 1);
                        values[values.length - 1] = values[0];

                        System.out.println("values: " + Arrays.toString(values));
                        
                        for (int i = 0; i < values.length; i++) {
                            System.out.println("values[" + i + "]: " + values[i]);
                            if (scrollValue <= values[i]) {
                                System.out.println("values[" + i + " + " + 1 + "]: " + values[i + 1]);
                                scrollValue = values[i + 1];
                                break;
                            }
                        }
                    }

                    accessor.setScrollValue(scrollValue);
                    player.displayClientMessage(
                        Component
                            .translatable("warium_additions.tooltip.energy.limit")
                            .append(": " + WariumAdditionsUtil.formatFE(scrollValue)),
                        true
                    );
                    ci.cancel();
                }
            });
    }
}
