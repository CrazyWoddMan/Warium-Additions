Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_ir_seeker_head").scene("ordinance_ir_seeker_head", "Ordinance IR Seeker", (scene, util) => {

        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_ir_seeker_head", false);
		let head = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(100, "Ordinance IR Seeker is used for missiles that use the infrared (IR) light emission to track and follow the target", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(120);
		
		scene.world.moveSection(head, [0, 0, -1], 5);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 3], "crusty_chunks:ordinance_core", false);
		let core = scene.world.showIndependentSectionImmediately([2, 1, 3]);
		scene.world.moveSection(core, [0, 0, -1], 5);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 4], "crusty_chunks:ordinance_thruster", false);
		let thruster = scene.world.showIndependentSectionImmediately([2, 1, 4]);
		scene.world.moveSection(thruster, [0, 0, -1], 5);
		
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
		
		scene.world.setBlock([1, 3, 3], "crusty_chunks:ordinance_ir_seeker_head", false);
		scene.world.modifyBlock([1, 3, 3], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 3, 3], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(40, "Rockets can be horizontal", [3.5, 1.5, 2.7]).placeNearTarget().attachKeyFrame();
		
		scene.idle(50);
		
		scene.text(40, "Or vertical", [0.5, 3.3, 2.5]).placeNearTarget();
		
		scene.idle(50);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 1, 3], 40);
		let out2 = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out2, [1, 1, 3], 40);
		scene.text(40, "Thruster is used in both cases", [0.5, 2.3, 2.5]).placeNearTarget();
		
		scene.idle(50);
		
		scene.showControls(60, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change both horizontal or vertical rocket direction", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 1, 2], 60);
		scene.overlay.showOutline(PonderPalette.BLUE, out2, [1, 2, 3], 60);
		scene.showControls(60, [3.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click Ordinance Core with Weapon Aimer in second hand to attach", [3.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
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
		scene.world.createEntity("crusty_chunks:ir_missile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 2.0d, -0.3d], Rotation: [0.0f, 90.0f], Pos: [1.5d, 4.0d, 3.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([0, 2, 2]);
		
		scene.idle(45);
		
		scene.world.toggleRedstonePower([2, 2, 2]);
		scene.effects.createRedstoneParticles([2, 1, 2], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([2, 1, 2, 2, 1, 4], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:ir_missile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.3d, -2.0d], Rotation: [180.0f, 0.0f], Pos: [3.5d, 1.5d, 1.0d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([2, 2, 2]);
    }).scene("ordinance_ir_seeker_sim", "IR Seeker showcase", "kubejs:base_31x31", (scene, util) => {
		scene.showBasePlate();
        scene.scaleSceneView(0.2);
		scene.setSceneOffsetY(-10.0);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 1, 29], "crusty_chunks:ordinance_thruster", false);
		scene.world.modifyBlock([2, 1, 29], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([2, 1, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.setBlock([2, 2, 29], "crusty_chunks:ordinance_core", false);
		scene.world.modifyBlock([2, 2, 29], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([2, 2, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.setBlock([2, 3, 29], "crusty_chunks:ordinance_ir_seeker_head", false);
		scene.world.modifyBlock([2, 3, 29], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([2, 3, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.setBlocks([1, 1, 29, 0, 3, 29], "crusty_chunks:steel_block", false);
		scene.world.showSection([1, 1, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.showSection([1, 2, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.showSection([1, 3, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.setBlock([1, 2, 28], "minecraft:stone_button", false);
		scene.world.showSection([1, 2, 28], Facing.SOUTH);
		
		scene.idle(5);
		
		scene.world.setBlock([29, 1, 29], "crusty_chunks:ordinance_thruster", false);
		scene.world.modifyBlock([29, 1, 29], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([29, 1, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.setBlock([29, 2, 29], "crusty_chunks:ordinance_core", false);
		scene.world.modifyBlock([29, 2, 29], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([29, 2, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.setBlock([29, 3, 29], "crusty_chunks:ordinance_heavy_warhead", false);
		scene.world.modifyBlock([29, 3, 29], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([29, 3, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.setBlocks([28, 1, 29, 28, 3, 29], "crusty_chunks:steel_block", false);
		scene.world.showSection([28, 1, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.showSection([28, 2, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.showSection([28, 3, 29], Facing.DOWN);
		
		scene.idle(2);
		
		scene.world.setBlock([28, 2, 28], "minecraft:stone_button", false);
		scene.world.showSection([28, 2, 28], Facing.SOUTH);
		
		scene.idle(20);
		
		scene.text(100, "IR rocket will follow any high enough heat source once launched", [2.0, 3.0, 28.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(110);
		
		scene.text(60, "Including other rockets", [29.0, 3.0, 28.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.modifyBlock([28, 2, 28], (curState) => curState.with("powered", "true"), false);
		scene.effects.createRedstoneParticles([1, 2, 28], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([29, 1, 29, 29, 3, 29], "minecraft:air", false);
		let rocket = scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 2.0d, -1.0d], Rotation: [0.0f, 90.0f], Pos: [29.5d, 4.0d, 29.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.modifyBlock([28, 2, 28], (curState) => curState.with("powered", "false"), false);
		scene.world.modifyBlock([1, 2, 28], (curState) => curState.with("powered", "true"), false);
		scene.effects.createRedstoneParticles([1, 2, 28], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([2, 1, 29, 2, 3, 29], "minecraft:air", false);
		
		let ir_rocket = scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 2.0d, -1.0d], Rotation: [0.0f, 90.0f], Pos: [2.5d, 4.0d, 29.5d]}')
        });
		
		scene.idle(5);
		
		scene.world.modifyEntity(ir_rocket, e => {
			e.setNbt('{Motion: [1.0d, 3.0d, -1.3d], Pos: [2.5d, 13.68d, 24.4d], Rotation: [121.02f, 71.14f]}')
        });
		
		scene.idle(5);
		
		scene.world.modifyEntity(ir_rocket, e => {
			e.setNbt('{Motion: [1.4d, 1.0d, -1.4d], Pos: [7.6d, 28.46d, 17.77d], Rotation:[135.42f, 63.86f]}')
        });
		
		scene.idle(5);
		
		scene.world.modifyBlock([1, 2, 28], (curState) => curState.with("powered", "false"), false);
		
		scene.idle(17);
		
		scene.world.createEntity("crusty_chunks:giant_shock_explosion_bypass", [40, 40, -20]);
		scene.world.removeEntity(rocket);
		scene.world.removeEntity(ir_rocket);
	});
});