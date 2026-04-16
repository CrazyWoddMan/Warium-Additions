package crazywoddman.warium_additions;

import crazywoddman.warium_additions.compat.create.WariumCreateBlockEntities;
import crazywoddman.warium_additions.compat.create.WariumCreateBlocks;
import crazywoddman.warium_additions.compat.create.WariumCreateFluids;
import crazywoddman.warium_additions.compat.create.WariumCreateItems;
import crazywoddman.warium_additions.blocks.WariumAdditionsBlocks;
import crazywoddman.warium_additions.compat.create.Registrate;
import crazywoddman.warium_additions.config.ClothConfig;
import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.data.WariumAdditionsRecipes;
import crazywoddman.warium_additions.items.WariumAdditionsItems;
import crazywoddman.warium_additions.network.NetworkHandler;
import net.mcreator.crustychunks.procedures.Rad1TickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// TODO: [07:09:30] [Server thread/ERROR] [ne.mi.fm.lo.RuntimeDistCleaner/DISTXFORM]: Attempted to load class net/minecraft/client/multiplayer/ClientLevel for invalid dist DEDICATED_SERVER
// TODO: add shoot keybind
@Mod(WariumAdditions.MODID)
public class WariumAdditions {
    public static final String MODID = "warium_additions";
    
    public static final ModList MODLIST = ModList.get();
    public static final boolean WARIUM_PONDER_JEI = MODLIST.isLoaded("warium_ponder_jei");
    public static final boolean SUPPLEMENTARIES = MODLIST.isLoaded("supplementaries");
    public static final boolean VALKYRIEN_WARIUM = MODLIST.isLoaded("valkyrien_warium");
    public static final boolean IMMERSIVEENGINEERING = MODLIST.isLoaded("immersiveengineering");
    public static final boolean CREATE = MODLIST.isLoaded("create");

    public WariumAdditions(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        WariumAdditionsBlocks.register(bus);
        WariumAdditionsItems.register(bus);
        WariumAdditionsRecipes.register(bus);
        NetworkHandler.register();
        Config.register(context);

        if (MODLIST.isLoaded("cloth_config"))
            bus.addListener((FMLClientSetupEvent event) -> ClothConfig.register(context));

        if (CREATE) {
            MinecraftForge.EVENT_BUS.addListener(WariumAdditions::onPlayerTick);
            Registrate.register(bus);
            WariumCreateBlocks.register(bus);
            WariumCreateBlockEntities.register();
            WariumCreateItems.register();
            WariumCreateFluids.register();
        }
    }

    /**
     * Increases player's radiation exposure if they immerse themselves in the Yellowcake Mixture
     * @see WariumCreateFluids
     */
    private static void onPlayerTick(PlayerTickEvent event) {
        if (event.side.isServer() && event.player.tickCount % 10 == 0) {
            Level level = event.player.level();
            BlockPos pos = event.player.blockPosition();

            if (level.isLoaded(pos) && level.getFluidState(pos).getFluidType() == WariumCreateFluids.YELLOWCAKE_FLUID.get().getFluidType())
                Rad1TickProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
