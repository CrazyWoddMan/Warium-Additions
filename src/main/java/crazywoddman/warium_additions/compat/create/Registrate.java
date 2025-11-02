package crazywoddman.warium_additions.compat.create;

import crazywoddman.warium_additions.WariumAdditions;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;
import com.simibubi.create.foundation.item.TooltipModifier;

import net.minecraftforge.eventbus.api.IEventBus;

public class Registrate {
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(WariumAdditions.MODID);
    public static final CreateRegistrate REGISTRATE_OLDID = CreateRegistrate.create("warium_create");

    static {
        REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription
                .Modifier(item, Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
        });
    }

    public static void register(IEventBus bus) {
        REGISTRATE.registerEventListeners(bus);
        REGISTRATE_OLDID.registerEventListeners(bus);
    }
}
