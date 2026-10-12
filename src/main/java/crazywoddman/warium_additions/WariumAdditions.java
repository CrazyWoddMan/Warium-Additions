package crazywoddman.warium_additions;

import crazywoddman.warium_additions.config.WadditionsConfig;
import crazywoddman.warium_additions.blocks.WadditionsBlocks;
import crazywoddman.warium_additions.compat.create.WariumCreate;
import crazywoddman.warium_additions.items.WadditionsItems;
import crazywoddman.warium_additions.network.NetworkHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// TODO: save WariumVS connection data in schematics
@Mod(WariumAdditions.MODID)
public class WariumAdditions {
    public static final String MODID = "warium_additions";
    public static final boolean
    VALKYRIEN_WARIUM = ModList.get().isLoaded("valkyrien_warium"),
    CREATE = ModList.get().isLoaded("create"),
    WARIUM_PONDER_JEI = ModList.get().isLoaded("warium_ponder_jei");

    public WariumAdditions(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        WadditionsBlocks.register(bus);
        WadditionsItems.register(bus);
        NetworkHandler.register();
        WadditionsConfig.register(context);

        if (CREATE)
            WariumCreate.register(bus);
    }
}
