Ponder.registry((event) => {
    event.create("crusty_chunks:smoke_launcher").scene("smoke_launcher", "How to use smoke launcher", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:smoke_launcher", false);
		scene.world.setBlock([1, 1, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("face", "floor"), false);
		
		scene.idle(10);
		
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);

		scene.showControls(60, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change shooting direction", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(60, [2.5, 1.5, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click with Weapon Aimer in second hand to attach", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 2, 2.5], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 3.7, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.addKeyframe();
		scene.showControls(20, [2.5, 1.5, 2.5], "down").rightClick().withItem("crusty_chunks:smoke_mortar_shell");
		
		scene.idle(30);
		
		scene.world.showSection([1, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(80, "Redstone signal is needed to shoot", [1.5, 1.0, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
		scene.effects.createRedstoneParticles([1, 1, 2], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.createEntity("crusty_chunks:smoke_mortar_projectile", [0, 0, 0], e => {
			e.setNbt('{crit: 1b, Motion: [-0.5d, 1.0d, -2.0d], Pos: [2.5d, 1.1d, 2.1d]}')
        });
		scene.particles.simple(3, "flame", [1.5, 1, 1.5]).density(5).area([3.5, 2, 3]);
		
		scene.idle(15);
		
		scene.particles.simple(5, "crusty_chunks:smoke_screen", [2, 5, -15]);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
    });
});