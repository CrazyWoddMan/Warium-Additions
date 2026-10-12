package crazywoddman.warium_additions.compat.create;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.create.blocks.WariumCreateBlockEntities;
import crazywoddman.warium_additions.compat.create.blocks.WariumCreateBlocks;
import crazywoddman.warium_additions.compat.create.fluids.WariumCreateFluids;
import crazywoddman.warium_additions.compat.create.items.WariumCreateItems;
import crazywoddman.warium_additions.compat.create.recipes.PaintToolDeployingRecipe;
import crazywoddman.warium_additions.events.WadditionsForgeEvents;
import net.createmod.catnip.lang.FontHelper.Palette;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class WariumCreate {
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(WariumAdditions.MODID);
    
    static {
        REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription
                .Modifier(item, Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
        });
    }

    public static void register(IEventBus bus) {
        MinecraftForge.EVENT_BUS.addListener(WadditionsForgeEvents::onLivingTick);
        REGISTRATE.registerEventListeners(bus);
        WariumCreateItems.register(bus);
        WariumCreateBlocks.register(bus);
        WariumCreateBlockEntities.register();
        WariumCreateFluids.register();

        if (WariumAdditions.WARIUM_PONDER_JEI)
            MinecraftForge.EVENT_BUS.addListener(PaintToolDeployingRecipe::register);
    }
}
