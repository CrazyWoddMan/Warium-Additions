package crazywoddman.warium_additions.ponder.scenes;

import crazywoddman.warium_additions.ponder.WariumPonderUtils;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class MortarScene extends WariumPonderUtils {
    public static void scene(CustomSceneBuilder builder) {
        var scene = builder.scene;
        var extras = builder.extras;
        scene.title("mortar", "How to use mortar");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        
        extras.setBlock(2, 1, 2, CrustyChunksModBlocks.MORTAR);
        
        scene.idle(10);
        
        scene.world().showSection(extras.sel(2, 1, 2), Direction.DOWN);
        
        extras.showControls(
            60,
            2.5, 2, 2.5,
            "DOWN",
            f -> f.rightClick().withItem(new ItemStack(CrustyChunksModItems.AIMER.get()))
        );
        
        scene.overlay()
            .showText(60)
            .attachKeyFrame()
            .text("Right-click with Weapon Aimer in second hand to attach it")
            .pointAt(new Vec3(2.5, 1.5, 2.5))
            .placeNearTarget();
        
        scene.idle(80);
        
        extras.showControls(
            1040,
            0.5, 2, 2.5,
            "DOWN",
            f -> f.withItem(new ItemStack(CrustyChunksModItems.AIMER.get()))
        );
        
        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch")
            .pointAt(new Vec3(-0.1, 3.7, 2.5))
            .placeNearTarget();
        
        scene.idle(100);
        
        scene.overlay()
            .showText(100)
            .attachKeyFrame()
            .text("Pitch is responsible for the vertical angle of the projectile shot and therefore the coverage distance")
            .pointAt(new Vec3(-0.1, 3.7, 2.5))
            .placeNearTarget();
        
        scene.idle(120);
        
        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("While yaw is responsible for the left/right rotation")
            .pointAt(new Vec3(-0.1, 3.7, 2.5))
            .placeNearTarget();
        
        scene.idle(100);
        
        scene.overlay()
            .showText(60)
            .attachKeyFrame()
            .text("Pitch range from -21.5 (30-100 blocks)")
            .pointAt(new Vec3(-0.1, 2.7, 2.5))
            .placeNearTarget();
        
        extras.showLine("BLUE", 2.5, 2.001, 2.2, 2.5, 2.59, 0.7, 735);
        
        scene.idle(80);
        
        scene.overlay()
            .showText(60)
            .attachKeyFrame()
            .text("To 44.5 (60-120 blocks)")
            .pointAt(new Vec3(-0.1, 3.6, 2.5))
            .placeNearTarget();
        
        extras.showLine("RED", 2.5, 2.0, 2.2, 2.5, 3.5, 2.175, 645);
        
        scene.idle(80);
        
        scene.overlay()
            .showText(80)
            .attachKeyFrame()
            .text("And default pitch is 0 (150-210 blocks)")
            .pointAt(new Vec3(-0.1, 3.2, 2.5))
            .placeNearTarget();
        
        extras.showLine("GREEN", 2.5, 2.002, 2.2, 2.5, 3.5, 0.7, 570);
        
        scene.idle(100);
        
        scene.overlay()
            .showText(140)
            .attachKeyFrame()
            .text("Conclusion: 0 pitch has the longest range. Both above and below 0 pitch decreases the distance")
            .pointAt(new Vec3(-0.1, 3.1, 2.5))
            .placeNearTarget();
        
        scene.idle(160);
        
        scene.overlay()
            .showText(140)
            .attachKeyFrame()
            .text("Lower pitch results in a less accurate shot, but projectile reaches the final destination faster")
            .pointAt(new Vec3(-0.1, 3.1, 2.5))
            .placeNearTarget();
        
        scene.idle(160);
        
        scene.overlay()
            .showText(140)
            .attachKeyFrame()
            .text("Higher pitch shot is more accurate, but projectile takes longer to reach the end point")
            .pointAt(new Vec3(-0.1, 3.1, 2.5))
            .placeNearTarget();
        
        scene.idle(160);
        
        scene.addKeyframe();
        
        extras.showControls(
            20,
            2.5, 2, 2.5,
            "DOWN",
            f -> f.rightClick().withItem(new ItemStack(CrustyChunksModItems.MORTAR_SHELL.get()))
        );
        
        scene.idle(20);
        
        extras.createEntity(
            CrustyChunksModEntities.MORTAR_PROJECTILE.get(),
            "{crit: 1b, Motion: [0.0d, 3.0d, -1.5d], Pos: [2.5d, 2.1d, 2.1d]}"
        );
        
        for (int i = 0; i < 8; i++)
            extras.emitParticles(
                2, 3, 1.2,
                ParticleTypes.CAMPFIRE_COSY_SMOKE,
                Math.random() / 50 - 0.01,
                Math.random() / 50 - 0.01,
                Math.random() / 50 - 0.01
            );
    }
}
