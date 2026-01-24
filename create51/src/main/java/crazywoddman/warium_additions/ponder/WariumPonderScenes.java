package crazywoddman.warium_additions.ponder;

import crazywoddman.warium_additions.ponder.scenes.*;
import net.mcreator.crustychunks.init.CrustyChunksModItems;

public class WariumPonderScenes extends WariumPonderUtils {
    protected static void register(WariumPonderUtils wariumPonder) {
        wariumPonder.addStoryBoard("5x5", MortarScene::scene, CrustyChunksModItems.MORTAR);
        wariumPonder.addStoryBoard("9x9", LargeEngineScene::scene, CrustyChunksModItems.DRIVE_SHAFT, CrustyChunksModItems.ENGINE_CYLLINDER, CrustyChunksModItems.LARGE_ENGINE_SMOKESTACK);
        wariumPonder.addStoryBoard("5x5", HardpointScene::scene, CrustyChunksModItems.EMPTY_MISSILE_HARDPOINT);
        wariumPonder.addStoryBoard("7x7", ReactorScenes::reactorInterface, CrustyChunksModItems.POWER_REACTOR_INTERFACE, CrustyChunksModItems.BREEDER_REACTOR_INTERFACE);
    }
}
