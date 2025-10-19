Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_inline_warhead").scene("ordinance_inline_warhead", "Ordinance Core Warhead", (scene, util) => {

        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_inline_warhead", false);
		let inline = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(100, "Ordinance Core Warhead is only required for a Bunker Buster Bomb", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(120);
		
		scene.world.moveSection(inline, [0, 0, -1], 5);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 0], "crusty_chunks:ordinance_kinetic_head", false);
		let head = scene.world.showIndependentSection([2, 1, 0], Facing.SOUTH);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 4], "crusty_chunks:ordinance_core", false);
		let core = scene.world.showIndependentSection([2, 1, 4], Facing.NORTH);
		scene.world.moveSection(core, [0, 0, -2], 0);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 2, 4], "crusty_chunks:ordinance_fins", false);
		let fins = scene.world.showIndependentSection([2, 2, 4], Facing.NORTH);
		scene.world.moveSection(fins, [0, -1, -1], 0);
		
		scene.idle(10);
		
		scene.world.moveSection(head, [1, 0, 0], 10);
		scene.world.moveSection(inline, [1, 0, 0], 10);
		scene.world.moveSection(core, [1, 0, 0], 10);
		scene.world.moveSection(fins, [1, 0, 0], 10);
		
		scene.idle(10);
		
		scene.world.setBlock([1, 1, 1], "crusty_chunks:ordinance_inline_warhead", false);
		let inline2 = scene.world.showIndependentSection([1, 1, 1], Facing.DOWN);
		
		scene.text(80, "But can also be used for a Heavy Bomb to make it heavier", [1.5, 1.5, 1.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(10);
		
		scene.world.setBlock([1, 1, 0], "crusty_chunks:ordinance_heavy_warhead", false);
		let head2 = scene.world.showIndependentSection([1, 1, 0], Facing.SOUTH);
		
		scene.idle(3);
		
		scene.world.setBlock([1, 1, 2], "crusty_chunks:ordinance_core", false);
		let core2 = scene.world.showIndependentSection([1, 1, 2], Facing.NORTH);
		
		scene.idle(5);
		
		scene.world.setBlock([1, 1, 3], "crusty_chunks:ordinance_fins", false);
		let fins2 = scene.world.showIndependentSection([1, 1, 3], Facing.NORTH);
		
		scene.idle(90);
		
		scene.world.moveSection(fins2, [0, 0, 1], 10);
		
		scene.idle(10);
		
		scene.world.setBlock([1, 2, 3], "crusty_chunks:ordinance_inline_warhead", false);
		let inline3 = scene.world.showIndependentSection([1, 2, 3], Facing.DOWN)
		scene.world.moveSection(inline3, [0, -1, 0], 0);
		
		scene.idle(10);
		
		scene.text(100, "Second Ordinance Core Warhead can be added to make it even heavier", [1.5, 1.5, 3.8]).placeNearTarget().attachKeyFrame();
    }).scene("ordinance_kinetic_head", "Ordinance Kinetic Head", (scene, util) => {

        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_kinetic_head", false);
		let head = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(100, "Ordinance Kinetic Nose is used for \"Bunker Buster\" high penetration bombs", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(120);
		
		scene.world.moveSection(head, [0, 0, -2], 5);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 3], "crusty_chunks:ordinance_inline_warhead", false);
		let inline = scene.world.showIndependentSectionImmediately([2, 1, 3]);
		scene.world.moveSection(inline, [0, 0, -2], 10);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 4], "crusty_chunks:ordinance_core", false);
		let core = scene.world.showIndependentSectionImmediately([2, 1, 4]);
		scene.world.moveSection(core, [0, 0, -2], 10);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 2, 4], "crusty_chunks:ordinance_fins", false);
		let fins = scene.world.showIndependentSectionImmediately([2, 2, 4]);
		scene.world.moveSection(fins, [0, -1, 0], 0);
		scene.world.moveSection(fins, [0, 0, -1], 10);
		
		scene.idle(10);
		
		scene.text(100, "This is the only bomb which strictly requires Ordinance Core Warhead", [2.5, 1.5, 1.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(110);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 3], 40);
		scene.text(40, "Only Fins can be used", [2.5, 1.5, 3.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.setBlocks([2, 4, 0, 2, 4, 3], "crusty_chunks:steel_block", false);
		scene.world.showSection([2, 4, 0, 2, 4, 3], Facing.DOWN);
		
		scene.idle(10);
		
		scene.world.moveSection(fins, [0, 2, 0], 10);
		scene.world.moveSection(core, [0, 2, 0], 10);
		scene.world.moveSection(inline, [0, 2, 0], 10);
		scene.world.moveSection(head, [0, 2, 0], 10);
		
		scene.idle(10);
		
		scene.world.hideSection([0, 0, 0, 4, 0, 4], Facing.DOWN);
		scene.world.setBlock([1, 4, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 4, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([1, 4, 2], Facing.EAST);
		
		scene.idle(10);
		
		scene.text(100, "Redstone signal must power Ordinance Core part of the bomb to launch it", [2.6, 3.6, 3.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.setBlocks([0, 0, 0, 4, 0, 4], "minecraft:air", false);
		scene.world.toggleRedstonePower([1, 4, 2]);
		scene.effects.createRedstoneParticles([1, 4, 2], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([2, 1, 2, 2, 2, 4], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:bunker_buster_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, -0.1d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 3.0d, 1.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 4, 2]);
    }).scene("ordinance_heavy_warhead", "Ordinance Nose Warhead", (scene, util) => {

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
    });
});