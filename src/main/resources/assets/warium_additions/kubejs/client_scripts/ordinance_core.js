Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_core").scene("ordinance_core", "Ordinance Core", (scene, util) => {

        scene.showBasePlate();
		
		scene.idle(10);
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_core", false);
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		scene.text(60, "Ordinance Core is used for all rockets from Warium", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(80);
		
		scene.world.setBlock([2, 1, 3], "crusty_chunks:ordinance_thruster", false);
		scene.world.setBlock([2, 1, 1], "crusty_chunks:ordinance_heavy_warhead", false);
		scene.world.showSection([2, 1, 1], Facing.DOWN);
		scene.world.showSection([2, 1, 3], Facing.DOWN);
		
		scene.idle(20);
		
		scene.showControls(60, [0.5, 2, 2.5], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change rocket direction", [-0.1, 3.7, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 2], 60);
		scene.showControls(60, [2.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click Ordinance Core with Weapon Aimer in second hand to attach", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 2, 2.5], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 3.7, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.setBlock([1, 1, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(100, "Redstone signal must power Ordinance Core part of the rocket/bomb to launch it", [1.5, 1.0, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
		scene.effects.createRedstoneParticles([1, 1, 2], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([2, 1, 1, 2, 1, 3], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.3d, -2.0d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 1.5d, 1.0d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
    });
});