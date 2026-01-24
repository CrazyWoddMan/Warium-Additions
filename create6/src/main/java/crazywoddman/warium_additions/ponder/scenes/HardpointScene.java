package crazywoddman.warium_additions.ponder.scenes;

import crazywoddman.warium_additions.ponder.WariumPonderUtils;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class HardpointScene extends WariumPonderUtils {
    public static void scene(CustomSceneBuilder builder) {
        var scene = builder.scene;
        var extras = builder.extras;
        scene.title("empty_missile_hardpoint", "Medium Rocket Hardpoint");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        extras.setBlock(2, 1, 2, CrustyChunksModBlocks.EMPTY_MISSILE_HARDPOINT);
        var hardpoint = scene.world().showIndependentSection(extras.sel(2, 1, 2), Direction.DOWN);
        

        scene.idle(15);

        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("Medium Rocket Hardpoint is used for mounting and launching medium-sized rockets and missiles")
            .pointAt(new Vec3(2.5, 1.8, 2.5))
            .placeNearTarget();

        scene.idle(90);

        extras.setBlock(2, 3, 2, CrustyChunksModBlocks.STEEL_BLOCK);

        scene.world().showSection(extras.sel(2, 3, 2), Direction.DOWN);
        
        scene.idle(10);
        
        extras.moveSection(hardpoint, 0, 1, 0, 5);

        scene.idle(20);

        scene.addKeyframe();
        extras.showControls(
            20,
            3, 2.8, 2,
            "RIGHT",
            f -> f.rightClick().withItem(new ItemStack(CrustyChunksModItems.FIRE_SPEAR_ROCKET.get()))
        );

        scene.idle(3);

        extras.setBlock(2, 1, 2, CrustyChunksModBlocks.FIRE_SPEAR_MISSILE_HARDPOINT);

        scene.idle(20);

        extras.setBlock(1, 3, 2, () -> Blocks.STONE_BUTTON);
        scene.world().modifyBlock(new BlockPos(1, 3, 2), state -> state.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), false);
        scene.world().showSection(extras.sel(1, 3, 2), Direction.EAST);

        scene.idle(20);

        scene.overlay()
            .showText(60)
            .text("Redstone signal is needed launch the missile")
            .pointAt(new Vec3(2, 3.4, 2.6))
            .placeNearTarget();

        scene.idle(20);

        scene.world().toggleRedstonePower(extras.sel(1, 3, 2));
        scene.effects().createRedstoneParticles(new BlockPos(1, 3, 2), 0xFF0000, 3);

        scene.idle(5);

        extras.setBlock(2, 1, 2, CrustyChunksModBlocks.EMPTY_MISSILE_HARDPOINT);
        extras.createEntity(
            CrustyChunksModEntities.FIRE_SPEAR_ROCKET_PROJECTILE.get(),
            "{Motion: [0.0d, 0.0d, -4.0d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 2.5d, 2.5d]}"
        );

        scene.idle(15);
        
        scene.world().toggleRedstonePower(extras.sel(1, 3, 2));
    }
}
