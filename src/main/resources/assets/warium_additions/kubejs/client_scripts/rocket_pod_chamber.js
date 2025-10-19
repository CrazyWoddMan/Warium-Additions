Ponder.registry((event) => {
    event.create("crusty_chunks:rocket_pod_chamber").scene("rocket_pod_chamber", "Rocket Pod Chamber", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:rocket_pod_chamber", false);
		scene.world.setBlock([1, 1, 1], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 1, 1], (curState) => curState.with("face", "floor"), false);
		
		scene.idle(10);
		
		scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(80, "Rocket Pod Chamber is used to build complete Rocket Pod", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.setBlock([2, 1, 1], "crusty_chunks:rocket_pod", false);
		scene.world.showIndependentSection([2, 1, 1], Facing.SOUTH);
		scene.world.setBlock([2, 1, 3], "crusty_chunks:rocket_pod_chamber", false);
		scene.world.showIndependentSection([2, 1, 3], Facing.NORTH);
		
		scene.idle(10);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 2, 2, 1, 3], 45);
		scene.text(40, "2 Rocket Pod Chambers", [2.5, 1.5, 3.0]).placeNearTarget().attachKeyFrame();
		
		scene.idle(50);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 1], 55);
		scene.text(50, "Armor Peeler Rocket Pod", [2.5, 1.5, 2.0]).placeNearTarget();
		
		scene.idle(65);
		
		scene.showControls(40, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(40, "Weapon Aimer can be used to change shooting direction", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 1], 60);
		scene.showControls(60, [2.5, 2.0, 1.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click with Weapon Aimer in second hand to attach", [2.5, 2.0, 1.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 1, 2.5], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 2.7, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.addKeyframe();
		scene.showControls(20, [2.5, 2.0, 1.5], "down").rightClick().withItem("crusty_chunks:armor_peeler_rocket");
		
		scene.idle(30);
		
		scene.world.showSection([1, 1, 1], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(100, "Redstone signal must power Armor Peeler Rocket Pod part to launch rocket", [1.5, 1.0, 1.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.toggleRedstonePower([1, 1, 1]);
		scene.effects.createRedstoneParticles([1, 1, 1], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.createEntity("crusty_chunks:rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, -3.0d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 1.5d, 0.9d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 1, 1]);
    });
});