Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_heavy_warhead").scene("ordinance_heavy_warhead", "Ordinance Nose Warhead", (scene, util) => {

        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_heavy_warhead", false);
		let head = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(100, "Ordinance Nose Warhead is used for building high explosive shrapnel rockets and bombs", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(120);
		
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
		
		scene.world.setBlock([2, 2, 1], "crusty_chunks:ordinance_heavy_warhead", false);
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
		
		scene.world.setBlock([0, 3, 4], "crusty_chunks:ordinance_heavy_warhead", false);
		scene.world.modifyBlock([0, 3, 4], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([0, 3, 4], Facing.DOWN);
		
		scene.idle(10);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [4, 1, 3], 55);
		scene.text(50, "Fins is used for Heavy Bomb", [4.5, 1.5, 3.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 3], 65);
		scene.text(60, "Truster is used for horizontal Heavy Rocket", [2.5, 1.5, 3.8]).placeNearTarget();
		
		scene.idle(70);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [0, 1, 4], 55);
		scene.text(50, "And vertical Heavy Rocket", [0.5, 1.3, 4.5]).placeNearTarget();
		
		scene.idle(60);
		
		scene.text(80, "Heavy Bomb can be additionally modified by Ordinance Core Warhead", [4.5, 1.5, 2.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(10);
		
		scene.world.moveSection(head, [0, 0, -1], 10);
		
		scene.idle(20);
		
		scene.world.setBlock([4, 1, 1], "crusty_chunks:ordinance_inline_warhead", false);
		let inline = scene.world.showIndependentSection([4, 1, 1], Facing.DOWN);
		
		scene.idle(60);
		
		scene.text(60, "Or even by TWO Ordinance Core Warheads!", [4.5, 1.5, 2.8]).placeNearTarget();
		
		scene.idle(10);
		
		scene.world.moveSection(fins, [0, 0, 1], 10);
		
		scene.idle(20);
		
		scene.world.setBlock([4, 1, 3], "crusty_chunks:ordinance_inline_warhead", false);
		let inline2 = scene.world.showIndependentSection([4, 1, 3], Facing.DOWN);
		
		scene.idle(40);
		
		scene.showControls(60, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change both rockets direction", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 2], 60);
		let out2 = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out2, [0, 2, 4], 60);
		scene.showControls(60, [2.5, 2, 2.8], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click Ordinance Core with Weapon Aimer in the second hand to attach", [2.5, 1.5, 2.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.setBlocks([1, 1, 4, 1, 3, 4], "crusty_chunks:steel_block", false);
		scene.world.showSection([1, 1, 4], Facing.DOWN);
		scene.world.setBlocks([4, 4, 0, 4, 4, 4], "crusty_chunks:steel_block", false);
		scene.world.showSection([4, 4, 0, 4, 4, 4], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showSection([1, 2, 4], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.moveSection(fins, [0, 2, 0], 10);
		scene.world.moveSection(inline, [0, 2, 0], 10);
		scene.world.moveSection(core, [0, 2, 0], 10);
		scene.world.moveSection(inline2, [0, 2, 0], 10);
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
		
		scene.text(80, "Redstone signal must power Ordinance Core part of the the rocket/bomb to launch it", [1.5, 1.0, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
		scene.effects.createRedstoneParticles([1, 1, 2], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([2, 2, 1, 2, 2, 3], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.3d, -2.0d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 1.5d, 1.0d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
		
		scene.idle(45);
		
		scene.world.toggleRedstonePower([1, 2, 3]);
		scene.effects.createRedstoneParticles([1, 2, 3], 0xFF0000, 3);
		scene.idle(5);
		
		scene.world.setBlocks([0, 1, 4, 0, 3, 4], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
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
		scene.world.setBlock([4, 1, 1,], "minecraft:air", false);
		scene.world.setBlock([4, 1, 3,], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:block_buster_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, -0.1d], Rotation: [180.0f, 0.0f], Pos: [4.5d, 2.5d, 2.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([3, 4, 2]);
    }).scene("ordinance_heavy_warhead_sim", "Heavy Bomb showcase", "kubejs:large_bomb", (scene, util) => {
		scene.world.showIndependentSectionImmediately([0, 0, 0, 23, 1, 23]);
        scene.scaleSceneView(0.3);
		scene.setSceneOffsetY(-6.0);
		scene.world.setBlocks([0, 3, 0, 23, 3, 23], "minecraft:grass_block", false);
		let grass = scene.world.showIndependentSectionImmediately([0, 3, 0, 23, 3, 23]);
		scene.world.moveSection(grass, [0, -1, 0], 0);
		scene.world.createEntity("crusty_chunks:large_bomb_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, -1.0d, 0.0d], Rotation: [0.0f, -90.0f], Pos: [12.0d, 35.0d, 12.0d]}')
        });
		
		scene.idle(20);
		
		scene.particles.simple(10, "explosion_emitter", [12, 4, 12]);
		scene.world.createEntity("crusty_chunks:tiny_client_effect", [12, 5, 12]);
		scene.world.createEntity("crusty_chunks:shock_clientside_bypass", [12, 5, 12]);
		scene.world.createEntity("crusty_chunks:debris", [12, 4, 12], e => {
			e.setDeltaMovement([0.6, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [12, 4, 12], e => {
			e.setDeltaMovement([0.7, 1, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [12, 4, 12], e => {
			e.setDeltaMovement([-0.6, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [12, 4, 12], e => {
			e.setDeltaMovement([-0.9, 1.3, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [12, 4, 12], e => {
			e.setDeltaMovement([0, 0.7, 0.6]);
        });
		scene.world.createEntity("crusty_chunks:debris", [12, 4, 12], e => {
			e.setDeltaMovement([0, 1, 0.7]);
        });
		scene.world.createEntity("crusty_chunks:debris", [12, 4, 12], e => {
			e.setDeltaMovement([0, 0.7, -0.6]);
        });
		scene.world.createEntity("crusty_chunks:debris", [12, 4, 12], e => {
			e.setDeltaMovement([0, 1.3, -0.9]);
        });
		
		scene.idle(3);
		
		scene.world.showIndependentSectionImmediately([0, 2, 0, 23, 2, 23]);
		scene.world.setBlocks([0, 3, 0, 31, 3, 31], "minecraft:air", false);
	}).scene("ordinance_heavy_warhead_sim1", "Super Heavy Bomb showcase", "kubejs:super_large_bomb", (scene, util) => {
		scene.world.showIndependentSectionImmediately([0, 0, 0, 31, 1, 31]);
        scene.scaleSceneView(0.3);
		scene.setSceneOffsetY(-6.0);
		scene.world.setBlocks([0, 3, 0, 31, 3, 31], "minecraft:grass_block", false);
		let grass = scene.world.showIndependentSectionImmediately([0, 3, 0, 31, 3, 31]);
		scene.world.moveSection(grass, [0, -1, 0], 0);
		scene.world.createEntity("crusty_chunks:super_large_bomb_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, -1.0d, 0.0d], Rotation: [0.0f, -90.0f], Pos: [15.0d, 35.0d, 15.0d]}')
        });
		
		scene.idle(20);
		
		scene.particles.simple(10, "explosion_emitter", [15, 4, 15]);
		scene.world.createEntity("crusty_chunks:giant_shock_explosion_bypass", [15, 6, 15]);
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0.6, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0.7, 1, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0.2, 1.7, 0.1]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([-0.6, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([-0.9, 1.3, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([-0.2, 1.7, 0.1]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0, 0.7, 0.6]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0, 1, 0.7]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0.1, 1.7, -0.2]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0, 0.7, -0.6]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0, 1.3, -0.9]);
        });
		scene.world.createEntity("crusty_chunks:debris", [15, 5, 15], e => {
			e.setDeltaMovement([0.1, 1.7, -0.2]);
        });
		
		scene.idle(3);
		
		scene.world.showIndependentSectionImmediately([0, 2, 0, 31, 2, 31]);
		scene.world.setBlocks([0, 3, 0, 31, 3, 31], "minecraft:air", false);
	}).scene("ordinance_heavy_warhead_sim2", "\'Block Buster\" Bomb showcase", "kubejs:block_buster", (scene, util) => {
		scene.world.showIndependentSectionImmediately([0, 0, 0, 59, 3, 59]);
        scene.scaleSceneView(0.17);
		scene.setSceneOffsetY(-6.0);
		scene.world.setBlocks([0, 5, 0, 59, 5, 59], "minecraft:grass_block", false);
		let grass = scene.world.showIndependentSectionImmediately([0, 5, 0, 59, 5, 59]);
		scene.world.moveSection(grass, [0, -1, 0], 0);
		scene.world.createEntity("crusty_chunks:block_buster_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, -1.0d, 0.0d], Rotation: [0.0f, -90.0f], Pos: [29.0d, 55.0d, 29.0d]}')
        });
		
		scene.idle(30);
		
		scene.particles.simple(10, "explosion_emitter", [25, 5, 29]);
		scene.particles.simple(10, "explosion_emitter", [29, 5, 25]);
		scene.particles.simple(10, "explosion_emitter", [33, 5, 29]);
		scene.particles.simple(10, "explosion_emitter", [29, 5, 33]);
		scene.world.createEntity("crusty_chunks:giant_shock_explosion_bypass", [29, 8, 29]);
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.6, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.7, 1, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.5, 2, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.5, 2, 0.3]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.2, 1.7, 0.1]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.5, 1.7, 0.3]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.6, 0.7, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.9, 1.3, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.5, 2, 0]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.5, 2, 0.3]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.2, 1.7, 0.1]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.5, 1.7, 0.3]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0, 0.7, 0.6]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0, 1, 0.7]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0, 2, 0.5]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.3, 2, 0.5]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.1, 1.7, -0.2]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([-0.3, 1.7, 0.5]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0, 0.7, -0.6]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0, 1.3, -0.9]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0, 2, -0.5]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.3, 2, -0.5]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.1, 1.7, -0.2]);
        });
		scene.world.createEntity("crusty_chunks:debris", [29, 7, 29], e => {
			e.setDeltaMovement([0.3, 1.7, -0.5]);
        });
		
		scene.idle(3);
		
		scene.world.showIndependentSectionImmediately([0, 4, 0, 59, 4, 59]);
		scene.world.setBlocks([0, 5, 0, 59, 5, 59], "minecraft:air", false);
	});
});