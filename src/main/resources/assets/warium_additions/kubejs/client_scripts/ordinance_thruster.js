Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_thruster").scene("ordinance_thruster", "Ordinance Thruster", (scene, util) => {

        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_thruster", false);
		let thruster = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(60, "Ordinance Thruster is used only for rockets", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(80);
		
		scene.world.moveSection(thruster, [0, 0, 1], 5);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 1], "crusty_chunks:ordinance_core", false);
		let core = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(core, [0, 0, 1], 5);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 0], "crusty_chunks:ordinance_heavy_warhead", false);
		let head = scene.world.showIndependentSectionImmediately([2, 1, 0]);
		scene.world.moveSection(head, [0, 0, 1], 5);
		
		scene.idle(5);
		
		scene.world.moveSection(thruster, [1, 0, 0], 10);
		scene.world.moveSection(core, [1, 0, 0], 10);
		scene.world.moveSection(head, [1, 0, 0], 10);
		
		scene.idle(3);
		
		scene.world.setBlock([1, 1, 3], "crusty_chunks:ordinance_thruster", false);
		scene.world.modifyBlock([1, 1, 3], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 1, 3], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.setBlock([1, 2, 3], "crusty_chunks:ordinance_core", false);
		scene.world.modifyBlock([1, 2, 3], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 2, 3], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.setBlock([1, 3, 3], "crusty_chunks:ordinance_heavy_warhead", false);
		scene.world.modifyBlock([1, 3, 3], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 3, 3], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(40, "Rockets can be horizontal", [3.5, 1.5, 2.7]).placeNearTarget().attachKeyFrame();
		
		scene.idle(50);
		
		scene.text(40, "Or vertical", [0.5, 3.3, 2.5]).placeNearTarget();
		
		scene.idle(60);
		
		scene.text(120, "Vertical rockets by default will fly to the side that player was standing when Ordinance Core was placed", [0.5, 3.3, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.overlay.showLine(PonderPalette.RED, [1.5, 2.7, 1.5], [1.5, 2.7, 2.5], 125);
		let villager = scene.world.createEntity("villager", [1.5, 1.0, 1.5], e => {
			e.lookAt("feet", [1.5, 1.0, 3.5]);
		});
		
		scene.idle(5);
		
		scene.overlay.showLine(PonderPalette.BLUE, [1.5, 3.5, 2.5], [1.5, 3.5, 3.5], 120);
		
		scene.idle(125);
		
		scene.world.removeEntity(villager);
		
		scene.idle(10);
		
		scene.showControls(60, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change both horizontal or vertical rocket direction", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 1, 2], 60);
		let out2 = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out2, [1, 2, 3], 60);
		scene.showControls(60, [3.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click Ordinance Core with Weapon Aimer in second hand to attach", [3.5, 1.5, 3]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.setBlocks([0, 1, 3, 0, 3, 3], "crusty_chunks:steel_block", false);
		scene.world.showSection([0, 1, 3], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showSection([0, 2, 3], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showSection([0, 3, 3], Facing.DOWN);
		
		scene.idle(15);
		
		scene.world.setBlock([0, 2, 2], "minecraft:stone_button", false);
		scene.world.showSection([0, 2, 2], Facing.SOUTH);
		scene.world.setBlock([2, 2, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([2, 2, 2], (curState) => curState.with("face", "floor"), false);
		let button = scene.world.showIndependentSectionImmediately([2, 2, 2]);
		scene.world.moveSection(button, [0, -1, 0], 10);
		
		scene.idle(10);
		
		scene.text(80, "Redstone signal must power Ordinance Core part of the rocket to launch it", [0.6, 2.4, 3.0]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.toggleRedstonePower([0, 2, 2]);
		scene.effects.createRedstoneParticles([0, 2, 2], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([1, 1, 3, 1, 3, 3], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 2.0d, -0.3d], Rotation: [0.0f, 90.0f], Pos: [1.5d, 4.0d, 3.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([0, 2, 2]);
		
		scene.idle(45);
		
		scene.world.toggleRedstonePower([2, 2, 2]);
		scene.effects.createRedstoneParticles([2, 1, 2], 0xFF0000, 3);
		scene.idle(5);
		
		scene.world.setBlocks([2, 1, 0, 2, 1, 2], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.3d, -2.0d], Rotation: [180.0f, 0.0f], Pos: [3.5d, 1.5d, 1.0d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([2, 2, 2]);
    });
});