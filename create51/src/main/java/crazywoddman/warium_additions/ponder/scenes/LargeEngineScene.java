package crazywoddman.warium_additions.ponder.scenes;

import java.util.function.Supplier;

import crazywoddman.warium_additions.ponder.WariumPonderUtils;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.init.CrustyChunksModParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class LargeEngineScene extends WariumPonderUtils {
    private enum Part {
        DRIVE_SHAFT, CYLLINER, SMOKESTACK;
    }
    public static void scene(CustomSceneBuilder builder) {
        var scene = builder.scene;
        var util = builder.util;
        var extras = builder.extras;
        Supplier<Block> block = () -> ((BlockItem)builder.item.get()).getBlock();
        Part part;
        if (builder.item == CrustyChunksModItems.DRIVE_SHAFT)
            part = Part.DRIVE_SHAFT;
        else if (builder.item == CrustyChunksModItems.ENGINE_CYLLINDER)
            part = Part.CYLLINER;
        else if (builder.item == CrustyChunksModItems.LARGE_ENGINE_SMOKESTACK)
            part = Part.SMOKESTACK;
        else
            throw new IllegalStateException("No scene variant is avaible for " + ForgeRegistries.ITEMS.getKey(builder.item.get()));

        scene.title(ForgeRegistries.ITEMS.getKey(builder.item.get()).getPath(), "Large Engine " + switch (part) {
            case DRIVE_SHAFT -> "Drive Shaft";
            case CYLLINER -> "Cyllinder";
            case SMOKESTACK -> "Smokestack";
        });
        scene.configureBasePlate(0, 0, 9);
        scene.scaleSceneView(0.9f);
        scene.setSceneOffsetY(-1);
        scene.showBasePlate();
        extras.setBlock(4, 1 + part.ordinal(), 4, block);
        var main = extras.showIndependentSection(4, 1 + part.ordinal(), 4, Direction.DOWN);

        if (part != Part.DRIVE_SHAFT)
            extras.moveSection(main, 0, -part.ordinal(), 0, 0);

        scene.idle(20);

        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("Large Engine " + switch (part) {
                case DRIVE_SHAFT -> "Drive Shaft";
                case CYLLINER -> "Cyllinder";
                case SMOKESTACK -> "Smokestack";
            } + " is used to build Large Engine")
            .pointAt(new Vec3(4.5, 1.5, 4.5))
            .placeNearTarget();

        scene.idle(95);

        switch (part) {
            case DRIVE_SHAFT -> {
                extras.setBlock(4, 2, 4, CrustyChunksModBlocks.ENGINE_CYLLINDER);
                scene.world().showSection(extras.sel(4, 2, 4), Direction.DOWN);

                scene.idle(5);

                extras.setBlock(4, 3, 4, CrustyChunksModBlocks.LARGE_ENGINE_SMOKESTACK);
                scene.world().showSection(extras.sel(4, 3, 4), Direction.DOWN);
            }
            case CYLLINER -> {
                extras.moveSection(main, 0, 1, 0, 5);

                scene.idle(5);

                extras.setBlock(4, 1, 4, CrustyChunksModBlocks.DRIVE_SHAFT);
                scene.world().showSection(extras.sel(4, 1, 4), Direction.UP);
                extras.setBlock(4, 3, 4, CrustyChunksModBlocks.LARGE_ENGINE_SMOKESTACK);
                scene.world().showSection(extras.sel(4, 3, 4), Direction.DOWN);
            }
            case SMOKESTACK -> {
                extras.moveSection(main, 0, 2, 0, 7);

                scene.idle(7);

                extras.setBlock(4, 2, 4, CrustyChunksModBlocks.ENGINE_CYLLINDER);
                scene.world().showSection(extras.sel(4, 2, 4), Direction.UP);

                scene.idle(5);

                extras.setBlock(4, 1, 4, CrustyChunksModBlocks.DRIVE_SHAFT);
                scene.world().showSection(extras.sel(4, 1, 4), Direction.UP);
            }
        }

        scene.idle(20);

        extras.setBlock(1, 1, 4, CrustyChunksModBlocks.FUEL_TANK);
        scene.world().showSection(extras.sel(1, 1, 4), Direction.DOWN);

        scene.idle(20);

        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("Fuel Tank Connection Port is needed to provide the fuel")
            .pointAt(new Vec3(1.5, 1.5, 4.5))
            .placeNearTarget();

        scene.idle(90);

        scene.overlay()
            .showText(100)
            .attachKeyFrame()
            .text("Right click with Diesel Bucket to fill Fuel Tank with diesel")
            .pointAt(new Vec3(1.5, 1.5, 4.5))
            .placeNearTarget();

        extras.showControls(100, 2, 1.5, 4, "RIGHT", f -> f
            .rightClick()
            .withItem(new ItemStack(CrustyChunksModItems.DIESEL_BUCKET.get()))
        );

        scene.idle(110);

        scene.overlay()
            .showText(100)
            .attachKeyFrame()
            .text("Use Fuel Hose to connect Fuel Tank to the engine")
            .pointAt(new Vec3(1.5, 1.5, 4.5))
            .placeNearTarget();

        scene.idle(20);

        extras.showControls(30, 5, 2.5, 4, "RIGHT", f -> f
            .rightClick()
            .withItem(new ItemStack(CrustyChunksModItems.FUEL_HOSE.get()))
        );

        scene.idle(40);

        extras.showControls(30, 1.8, 1.5, 5, "DOWN", f -> f
            .rightClick()
            .withItem(new ItemStack(CrustyChunksModItems.FUEL_HOSE.get()))
        );

        extras.showLine("BLUE", 1.5, 1.5, 4.5, 4.5, 2.5, 4.5, 60);

        scene.idle(65);

        extras.setBlock(3, 1, 4, () -> Blocks.LEVER, state -> state.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST));
        scene.world().showSection(extras.sel(3, 1, 4), Direction.EAST);

        scene.idle(20);

        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("Redstone signal should be used to turn the engine on")
            .pointAt(new Vec3(3.8, 1.5, 4.5))
            .placeNearTarget();

        scene.idle(20);

        scene.world().toggleRedstonePower(extras.sel(3, 1, 4));
        scene.effects().indicateRedstone(util.grid().at(3, 1, 4));

        scene.idle(20);

        extras.emitParticles(4.5, 4.5, 4.5, CrustyChunksModParticleTypes.SMOKE.get(), 0, 0, 0);

        scene.idle(50);

        scene.overlay()
            .showText(120)
            .attachKeyFrame()
            .text("One Large Engine section produces the same amount of rotation force as Medium Diesel Engine")
            .pointAt(new Vec3(4.5, 1.5, 4))
            .placeNearTarget();

        scene.idle(130);

        scene.overlay()
            .showText(30)
            .attachKeyFrame()
            .text("However...")
            .pointAt(new Vec3(4.5, 2.5, 4.5))
            .placeNearTarget();

        scene.idle(40);

        for (int i = 1; i <= 3; i++) {
            var enginein = extras.showIndependentSection(4, 1, 4, 4, 3, 4, Direction.SOUTH);
            extras.moveSection(enginein, 0, 0, -i, 0);
            var engineip = extras.showIndependentSection(4, 1, 4, 4, 3, 4, Direction.NORTH);
            extras.moveSection(engineip, 0, 0, i, 0);
            scene.idle(5);
        }

        var engine8 = extras.showIndependentSection(4, 1, 4, 4, 3, 4, Direction.NORTH);
        extras.moveSection(engine8, 0, 0, 4, 0);

        scene.idle(20);

        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("Up to 8 sections can be stacked to to increase the power")
            .pointAt(new Vec3(4.5, 2.5, 4.5))
            .placeNearTarget();

        scene.idle(90);

        for (int i = 4; i >= -3; i--) {
            if (i == 0)
                continue;

            var leveri = extras.showIndependentSection(3, 1, 4, Direction.EAST);
            extras.moveSection(leveri, 0, 0, i, 0);
            scene.idle(3);
        }

        for (int i = 4; i >= -3; i--) {
            if (i == 0)
                continue;

            var tanki = extras.showIndependentSection(1, 1, 4, Direction.DOWN);
            extras.moveSection(tanki, 0, 0, i, 0);
            scene.idle(3);
        }

        scene.idle(17);

        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("Each section requires redstone signal individually")
            .pointAt(new Vec3(3.8, 1.5, 1.5))
            .placeNearTarget();

        scene.idle(90);

        scene.overlay()
            .showText(200)
            .text("And each section must be connected to individual Fuel Tank Connection Port")
            .pointAt(new Vec3(1.5, 1.5, 1.5))
            .placeNearTarget();
    }
}