package crazywoddman.warium_additions.ponder.scenes;

import java.util.function.Supplier;

import crazywoddman.warium_additions.ponder.WariumPonderUtils;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class ReactorScenes extends WariumPonderUtils {
    public static void reactorInterface(CustomSceneBuilder builder) {
        var scene = builder.scene;
        var extras = builder.extras;
        boolean isBreeder = builder.item == CrustyChunksModItems.BREEDER_REACTOR_INTERFACE;
        Supplier<Block> reactorInterface = () -> ((BlockItem)builder.item.get()).getBlock();
        var reactorPort = isBreeder ? CrustyChunksModBlocks.BREEDER_REACTOR_PORT : CrustyChunksModBlocks.POWER_REACTOR_PORT;
        scene.title(ForgeRegistries.ITEMS.getKey(builder.item.get()).getPath(), (isBreeder ? "Breeder" : "Power") + " Reactor");
        scene.configureBasePlate(0, 0, 7);
        scene.showBasePlate();

        extras.setBlock(3, 1, 3, reactorInterface);
        var main = extras.showIndependentSection(3, 1, 3, Direction.DOWN);

        scene.idle(20);

        scene.overlay().showText(100)
            .attachKeyFrame()
            .text(
                isBreeder
                ? "Breeder Reactor Interface is used to input items in the breeder reactor"
                : "Power Reactor Interface is used to build power reactor"
            )
            .pointAt(new Vec3(3.5, 1.5, 3.5))
            .placeNearTarget();

        scene.idle(120);

        scene.world().hideIndependentSection(main, Direction.UP);

        scene.idle(20);

        extras.setBlock(6, 1, 3, reactorPort);
        var port = extras.showIndependentSection(6, 1, 3, Direction.DOWN);

        scene.idle(3);

        extras.setBlock(6, 2, 3, CrustyChunksModBlocks.BREEDER_REACTOR_CORE);
        var core = extras.showIndependentSection(6, 2, 3, Direction.DOWN);

        scene.idle(3);

        extras.setBlock(6, 3, 3, reactorInterface);
        var top = extras.showIndependentSection(6, 3, 3, Direction.DOWN);

        scene.idle(3);

        extras.setBlock(4, 1, 3, CrustyChunksModBlocks.REACTION_CHAMBER);
        var chamber1 = extras.showIndependentSection(4, 1, 3, Direction.DOWN);

        scene.idle(3);
        extras.setBlock(4, 2, 3, CrustyChunksModBlocks.REACTION_CHAMBER);
        var chamber2 = extras.showIndependentSection(4, 2, 3, Direction.DOWN);

        scene.idle(3);

        extras.setBlock(4, 3, 3, CrustyChunksModBlocks.EMPTY_FUEL_RODS);
        var rods = extras.showIndependentSection(4, 3, 3, Direction.DOWN);

        scene.idle(3);

        extras.setBlock(2, 1, 3, CrustyChunksModBlocks.CONTROL_ROD);
        var rod1 = extras.showIndependentSection(2, 1, 3, Direction.DOWN);

        scene.idle(3);

        extras.setBlock(2, 2, 3, CrustyChunksModBlocks.CONTROL_ROD);
        var rod2 = extras.showIndependentSection(2, 2, 3, Direction.DOWN);

        scene.idle(3);

        extras.setBlock(0, 1, 3, CrustyChunksModBlocks.REACTOR_CASING);
        var casing1 = extras.showIndependentSection(0, 1, 3, Direction.DOWN);

        scene.idle(3);

        extras.setBlock(0, 2, 3, CrustyChunksModBlocks.REACTOR_CASING);
        var casing2 = extras.showIndependentSection(0, 2, 3, Direction.DOWN);

        scene.idle(10);

        extras.showOutline("BLUE", 6, 1, 3, 6, 3, 3, 85);
        extras.showOutline("BLUE", 4, 1, 3, 4, 2, 3, 85);
        extras.showOutline("BLUE", 2, 1, 3, 2, 2, 3, 85);
        extras.showOutline("BLUE", 0, 1, 3, 0, 2, 3, 85);
        scene.overlay().showText(80)
            .attachKeyFrame()
            .text((isBreeder ? "Breeder" : "Power") + " reactor consists of 4 main parts")
            .pointAt(new Vec3(0, 2.5, 3.5))
            .placeNearTarget();

        scene.idle(100);

        scene.world().hideIndependentSection(chamber1, Direction.UP);
        scene.world().hideIndependentSection(chamber2, Direction.UP);
        scene.world().hideIndependentSection(rods, Direction.UP);
        scene.world().hideIndependentSection(rod1, Direction.UP);
        scene.world().hideIndependentSection(rod2, Direction.UP);
        scene.world().hideIndependentSection(casing1, Direction.UP);
        scene.world().hideIndependentSection(casing2, Direction.UP);

        scene.idle(20);

        extras.moveSection(port, -3, 0, 0, 10);
        extras.moveSection(core, -3, 0, 0, 10);
        extras.moveSection(top, -3, 0, 0, 10);

        scene.idle(10);

        scene.overlay()
            .showText(60)
            .attachKeyFrame()
            .text("Core part consists of 3 blocks")
            .pointAt(new Vec3(3.5, 2.5, 3.5))
            .placeNearTarget();

        scene.idle(80);

        extras.moveSection(core, 0, 1, 0, 10);
        extras.moveSection(top, 0, 2, 0, 10);

        scene.idle(10);

        extras.showOutline("BLUE", 3, 5, 3, 85);
        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text((isBreeder ? "Breeder" : "Power") + " Reactor Interface for input")
            .pointAt(new Vec3(3.5, 5.5, 3.5))
            .placeNearTarget();

        scene.idle(90);

        extras.showOutline("BLUE", 3, 3, 3, 65);
        scene.overlay()
            .showText(60)
            .attachKeyFrame()
            .text("Reactor Core")
            .pointAt(new Vec3(3.5, 3.5, 3.5))
            .placeNearTarget();

        scene.idle(70);

        extras.showOutline("BLUE", 3, 1, 3, 85);
        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text((isBreeder ? "Breeder" : "Power") + " Reactor Port for output")
            .pointAt(new Vec3(3.5, 1.5, 3.5))
            .placeNearTarget();

        scene.idle(90);

        extras.moveSection(core, 0, -1, 0, 10);
        extras.moveSection(top, 0, -2, 0, 10);

        scene.idle(15);

        scene.world().hideIndependentSection(port, Direction.WEST);
        scene.world().hideIndependentSection(core, Direction.WEST);
        scene.world().hideIndependentSection(top, Direction.WEST);

        scene.idle(15);

		var chamber3 = extras.showIndependentSection(4, 1, 3, Direction.WEST);
		var chamber4 = extras.showIndependentSection(4, 2, 3, Direction.WEST);
		var empty_rods = extras.showIndependentSection(4, 3, 3, Direction.WEST);
		extras.moveSection(chamber3, -1, 0, 0, 0);
		extras.moveSection(chamber4, -1, 0, 0, 0);
		extras.moveSection(empty_rods, -1, 0, 0, 0);

		scene.idle(20);

        scene.overlay()
            .showText(80)
            .text("Chamber part consists of 3 blocks too")
            .pointAt(new Vec3(3.5, 2, 3.5))
            .placeNearTarget()
            .attachKeyFrame();

		scene.idle(90);

		extras.showOutline("BLUE", 3, 1, 3, 3, 2, 3, 65);
        scene.overlay()
            .showText(60)
            .text("2 Reaction Chambers")
            .pointAt(new Vec3(3.5, 2, 3.5))
            .placeNearTarget()
            .attachKeyFrame();

		scene.idle(70);

		extras.moveSection(empty_rods, 0, 0.9, 0, 10);

		scene.idle(20);

		extras.showOutline("BLUE", 3, 3, 3, 65);
        scene.overlay()
            .showText(60)
            .text("And Empty Fuel Rods")
            .pointAt(new Vec3(3.5, 3.5, 3.5))
            .placeNearTarget()
            .attachKeyFrame();

		scene.idle(70);

		extras.moveSection(empty_rods, 0, -0.9, 0, 10);

		scene.idle(20);

		scene.world().hideIndependentSection(chamber3, Direction.WEST);
		scene.world().hideIndependentSection(chamber4, Direction.WEST);
		scene.world().hideIndependentSection(empty_rods, Direction.WEST);

		scene.idle(15);

		var rod3 = extras.showIndependentSection(2, 1, 3, Direction.WEST);
		var rod4 = extras.showIndependentSection(2, 2, 3, Direction.WEST);
		extras.moveSection(rod3, 1, 0, 0, 0);
		extras.moveSection(rod4, 1, 0, 0, 0);

		scene.idle(20);

		extras.showOutline("BLUE", 3, 1, 3, 3, 2, 3, 65);
        scene.overlay()
            .showText(60)
            .text("2 Control Rods")
            .pointAt(new Vec3(3.5, 2, 3.5))
            .placeNearTarget()
            .attachKeyFrame();

		scene.idle(70);

		scene.world().hideIndependentSection(rod3, Direction.WEST);
		scene.world().hideIndependentSection(rod4, Direction.WEST);

		scene.idle(15);

		var casing3 = extras.showIndependentSection(0, 1, 3, Direction.WEST);
		var casing4 = extras.showIndependentSection(0, 2, 3, Direction.WEST);
		extras.moveSection(casing3, 3, 0, 0, 0);
		extras.moveSection(casing4, 3, 0, 0, 0);

		scene.idle(20);

		extras.showOutline("BLUE", 3, 1, 3, 3, 2, 3, 65);
        scene.overlay()
            .showText(60)
            .text("And 2 Reactor Casings")
            .pointAt(new Vec3(3.5, 2, 3.5))
            .placeNearTarget()
            .attachKeyFrame();

		scene.idle(70);

		scene.world().hideIndependentSection(casing3, Direction.UP);
		scene.world().hideIndependentSection(casing4, Direction.UP);

		scene.idle(20);
		
		scene.addKeyframe();
		var port1 = extras.showIndependentSection(6, 1, 3, Direction.DOWN);
		extras.moveSection(port1, -3, 0, 0, 0);

		scene.idle(3);

		var core1 = extras.showIndependentSection(6, 2, 3, Direction.DOWN);
		extras.moveSection(core1, -3, 0, 0, 0);

		scene.idle(3);

		var top1 = extras.showIndependentSection(6, 3, 3, Direction.DOWN);
		extras.moveSection(top1, -3, 0, 0, 0);

		scene.idle(15);

		var rods1 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.SOUTH);
		extras.moveSection(rods1, 1, 1, -1, 0);

		scene.idle(15);

		var casings1 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.EAST);
		extras.moveSection(casings1, 2, 1, -1, 0);
		var casings2 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.WEST);
		extras.moveSection(casings2, 4, 1, -1, 0);

		scene.idle(20);

		var chambers1 = extras.showIndependentSection(4, 1, 3, 4, 2, 3, Direction.SOUTH);
		extras.moveSection(chambers1, -1, 1, -2, 0);

		scene.idle(15);

		var rods2 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.EAST);
		extras.moveSection(rods2, 0, 1, -2, 0);
		var rods3 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.WEST);
		extras.moveSection(rods3, 2, 1, -2, 0);

		scene.idle(20);

		var rods4 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.SOUTH);
		extras.moveSection(rods4, 1, 1, -3, 0);
		var casings3 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.SOUTH);
		extras.moveSection(casings3, 2, 1, -3, 0);
		var casings4 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.SOUTH);
		extras.moveSection(casings4, 4, 1, -3, 0);

		scene.idle(20);

		scene.rotateCameraY(-35);

		scene.idle(20);

		var rods5 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.NORTH);
		extras.moveSection(rods5, 1, 1, 1, 0);
		var casings5 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.NORTH);
		extras.moveSection(casings5, 2, 1, 1, 0);
		var casings6 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.NORTH);
		extras.moveSection(casings6, 4, 1, 1, 0);
		var chambers2 = extras.showIndependentSection(4, 1, 3, 4, 2, 3, Direction.NORTH);
		extras.moveSection(chambers2, -1, 1, 2, 0);
		var rods6 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.NORTH);
		extras.moveSection(rods6, 0, 1, 2, 0);
		var rods7 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.NORTH);
		extras.moveSection(rods7, 2, 1, 2, 0);
		var rods8 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.NORTH);
		extras.moveSection(rods8, 1, 1, 3, 0);
		var casings7 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.NORTH);
		extras.moveSection(casings7, 2, 1, 3, 0);
		var casings8 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.NORTH);
		extras.moveSection(casings8, 4, 1, 3, 0);

		scene.idle(20);

		var rods9 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.WEST);
		extras.moveSection(rods9, 2, 1, 0, 0);
		var chambers3 = extras.showIndependentSection(4, 1, 3, 4, 2, 3, Direction.WEST);
		extras.moveSection(chambers3, 1, 1, 0, 0);
		var rods10 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.WEST);
		extras.moveSection(rods10, 3, 1, 1, 0);
		var rods11 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.WEST);
		extras.moveSection(rods11, 3, 1, -1, 0);
		var rods12 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.WEST);
		extras.moveSection(rods12, 4, 1, 0, 0);
		var casings9 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.WEST);
		extras.moveSection(casings9, 6, 1, -1, 0);
		var casings10 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.WEST);
		extras.moveSection(casings10, 6, 1, 1, 0);

		scene.idle(20);

		var rods13 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.EAST);
		extras.moveSection(rods13, 0, 1, 0, 0);
		var chambers4 = extras.showIndependentSection(4, 1, 3, 4, 2, 3, Direction.EAST);
		extras.moveSection(chambers4, -3, 1, 0, 0);
		var rods14 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.EAST);
		extras.moveSection(rods14, -1, 1, 1, 0);
		var rods15 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.EAST);
		extras.moveSection(rods15, -1, 1, -1, 0);
		var rods16 = extras.showIndependentSection(2, 1, 3, 2, 2, 3, Direction.EAST);
		extras.moveSection(rods16, -2, 1, 0, 0);
		var casings11 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.EAST);
		extras.moveSection(casings11, 0, 1, -1, 0);
		var casings12 = extras.showIndependentSection(0, 1, 3, 0, 2, 3, Direction.EAST);
		extras.moveSection(casings12, 0, 1, 1, 0);

		scene.idle(20);

		var empty_rods1 = extras.showIndependentSection(4, 3, 3, Direction.DOWN);
		extras.moveSection(empty_rods1, -1, 1, -2, 0);
		var empty_rods2 = extras.showIndependentSection(4, 3, 3, Direction.DOWN);
		extras.moveSection(empty_rods2, -1, 1, 2, 0);
		var empty_rods3 = extras.showIndependentSection(4, 3, 3, Direction.DOWN);
		extras.moveSection(empty_rods3, 1, 1, 0, 0);
		var empty_rods4 = extras.showIndependentSection(4, 3, 3, Direction.DOWN);
		extras.moveSection(empty_rods4, -3, 1, 0, 0);
    }
}
