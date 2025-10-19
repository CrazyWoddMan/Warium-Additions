Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_incendiary_warhead").scene("ordinance_incendiary_warhead", "Ordinance Fire Nose Warhead", (scene, util) => {

        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_incendiary_warhead", false);
		let head = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(80, "Ordinance Fire Nose Warhead is used for building incendiary rockets and bombs", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(100);
		
		scene.world.moveSection(head, [0, 0, -1], 5);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 1, 3], "crusty_chunks:ordinance_core", false);
		let core = scene.world.showIndependentSectionImmediately([2, 1, 3]);
		scene.world.moveSection(core, [0, 0, -1], 5);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 1, 4], "crusty_chunks:ordinance_fins", false);
		let fins = scene.world.showIndependentSectionImmediately([2, 1, 4]);
		scene.world.moveSection(fins, [0, 0, -1], 5);
		
		scene.idle(10);
		
		scene.world.moveSection(fins, [2, 0, 0], 13);
		scene.world.moveSection(core, [2, 0, 0], 13);
		scene.world.moveSection(head, [2, 0, 0], 13);
		
		scene.idle(10);
		
		scene.world.setBlock([2, 2, 3], "crusty_chunks:ordinance_thruster", false);
		let thruster = scene.world.showIndependentSectionImmediately([2, 2, 3]);
		scene.world.moveSection(thruster, [0, -1, 0], 5);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 2, 2], "crusty_chunks:ordinance_core", false);
		let core2 = scene.world.showIndependentSectionImmediately([2, 2, 2]);
		scene.world.moveSection(core2, [0, -1, 0], 5);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 2, 1], "crusty_chunks:ordinance_incendiary_warhead", false);
		let head2 = scene.world.showIndependentSectionImmediately([2, 2, 1]);
		scene.world.moveSection(head2, [0, -1, 0], 5);
		
		scene.idle(10);		
		
		scene.world.setBlock([0, 1, 4], "crusty_chunks:ordinance_thruster", false);
		scene.world.modifyBlock([0, 1, 4], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([0, 1, 4], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.setBlock([0, 2, 4], "crusty_chunks:ordinance_core", false);
		scene.world.modifyBlock([0, 2, 4], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([0, 2, 4], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.setBlock([0, 3, 4], "crusty_chunks:ordinance_incendiary_warhead", false);
		scene.world.modifyBlock([0, 3, 4], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([0, 3, 4], Facing.DOWN);
		
		scene.idle(10);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [4, 1, 3], 55);
		scene.text(50, "Fins is used for Fire Bomb", [4.5, 1.5, 3.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 3], 65);
		scene.text(60, "Truster is used for horizontal Incendiary Rocket", [2.5, 1.5, 3.8]).placeNearTarget();
		
		scene.idle(70);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [0, 1, 4], 55);
		scene.text(50, "And vertical Incendiary Rocket", [0.5, 1.3, 4.5]).placeNearTarget();
		
		scene.idle(60);
		
		scene.showControls(60, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change both rockets direction", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 2], 60);
		let out2 = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out2, [0, 2, 4], 60);
		scene.showControls(60, [2.5, 2, 2.8], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click Ordinance Core with Weapon Aimer in second hand to attach", [2.5, 1.5, 3]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.setBlocks([1, 1, 4, 1, 3, 4], "crusty_chunks:steel_block", false);
		scene.world.showSection([1, 1, 4], Facing.DOWN);
		scene.world.setBlocks([4, 4, 1, 4, 4, 3], "crusty_chunks:steel_block", false);
		scene.world.showSection([4, 4, 0, 4, 4, 4], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showSection([1, 2, 4], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.moveSection(fins, [0, 2, 0], 10);
		scene.world.moveSection(core, [0, 2, 0], 10);
		scene.world.moveSection(head, [0, 2, 0], 10);
		scene.world.showSection([1, 3, 4], Facing.DOWN);
		
		scene.idle(15);
		
		scene.world.setBlock([3, 4, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([3, 4, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([3, 4, 2], Facing.EAST);
		scene.world.setBlock([1, 2, 3], "minecraft:stone_button", false);
		scene.world.showSection([1, 2, 3], Facing.SOUTH);
		scene.world.setBlock([1, 1, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(80, "Redstone signal must power Ordinance Core part of the rocket/bomb to launch it", [1.5, 1.0, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
		scene.effects.createRedstoneParticles([1, 1, 2], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([2, 2, 1, 2, 2, 3], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:incindiary_rocket_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.3d, -2.0d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 1.5d, 1.0d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
		
		scene.idle(45);
		
		scene.world.toggleRedstonePower([1, 2, 3]);
		scene.effects.createRedstoneParticles([1, 1, 3], 0xFF0000, 3);
		scene.idle(5);
		
		scene.world.setBlocks([0, 1, 4, 0, 3, 4], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:incindiary_rocket_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 2.0d, -0.3d], Rotation: [0.0f, 90.0f], Pos: [0.5d, 4.0d, 4.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 2, 3]);
		
		scene.idle(20);
		
		scene.world.hideSection([0, 0, 0, 4, 0, 4], Facing.DOWN);
		scene.world.hideSection([1, 1, 2, 1, 3, 4], Facing.DOWN);
		
		scene.idle(25);
		
		scene.world.setBlocks([0, 0, 0, 4, 0, 4], "minecraft:air", false);
		scene.world.toggleRedstonePower([3, 4, 2]);
		scene.effects.createRedstoneParticles([3, 4, 2], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([2, 1, 2, 2, 1, 4], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:fire_bomb_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, -0.1d], Rotation: [180.0f, 0.0f], Pos: [4.5d, 3.0d, 1.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([3, 4, 2]);
    }).scene("ordinance_incendiary_warhead_sim", "Fire Bomb showcase", "kubejs:fire_bomb", (scene, util) => {
		scene.world.showIndependentSectionImmediately([0, 0, 0, 32, 0, 32]);
        scene.scaleSceneView(0.3);
		scene.setSceneOffsetY(-6.0);
		scene.world.setBlocks([0, 3, 0, 32, 3, 32], "minecraft:grass_block", false);
		let grass = scene.world.showIndependentSectionImmediately([0, 3, 0, 32, 3, 32]);
		scene.world.moveSection(grass, [0, -2, 0], 0);
		scene.world.createEntity("crusty_chunks:fire_bomb_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, -1.0d, 0.0d], Rotation: [0.0f, -90.0f], Pos: [16.0d, 35.0d, 16.0d]}')
        });
		
		scene.idle(20);
		
		scene.world.createEntity("crusty_chunks:fire_client_effect", [16, 5, 16]);
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.6, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.7, 0.7, 0.7]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.4, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.4, 0.7, 0.4]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.6, 1, 0]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.6, 1, 0.3]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.6, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.7, 0.7, 0.7]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.4, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.4, 0.7, 0.4]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.8, 1, 0]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.6, 1, 0.3]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0, 0.7, 0.6]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.7, 0.7, -0.7]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.4, 0.7, -0.4]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0, 1, 0.8]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0.3, 1, 0.6]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0, 0.7, -0.6]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.7, 0.7, -0.7]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0, 0.7, 0.4]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.4, 0.7, -0.4]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([0, 1, -0.8]);
        });
		scene.world.createEntity("crusty_chunks:flame_thrower_ember", [16, 4, 16], e => {
			e.setDeltaMovement([-0.3, 1, -0.6]);
        });
		
		scene.idle(3);
		
		scene.world.showIndependentSectionImmediately([0, 1, 0, 32, 2, 32]);
		scene.world.setBlocks([0, 3, 0, 32, 3, 32], "minecraft:air", false);
	});
});