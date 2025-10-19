Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_kinetic_head").scene("ordinance_kinetic_head", "Ordinance Kinetic Head", (scene, util) => {

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
    }).scene("ordinance_kinetic_head_sim", "Bunker Buster bomb showcase", "kubejs:bunker_buster", (scene, util) => {
		let cut1 = scene.world.showIndependentSectionImmediately([0, 0, 0, 16, 8, 8]);
		let cut2 = scene.world.showIndependentSectionImmediately([0, 0, 9, 16, 8, 16]);
        scene.scaleSceneView(0.3);
		scene.setSceneOffsetY(-15.0);
		scene.world.setBlocks([0, 10, 0, 16, 10, 16], "minecraft:grass_block", false);
		let grass1 = scene.world.showIndependentSectionImmediately([0, 10, 0, 16, 10, 8]);
		let grass2 = scene.world.showIndependentSectionImmediately([0, 10, 9, 16, 10, 16]);
		scene.world.moveSection(grass1, [0, -1, 0], 0);
		scene.world.moveSection(grass2, [0, -1, 0], 0);
		
		scene.text(30, "If Bunker Buster bomb hits solid ground...", [9.5, 9, 9.5]).placeNearTarget();
		
		scene.idle(20);
		
		scene.world.createEntity("crusty_chunks:bunker_buster_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, -1.0d, 0.0d], Rotation: [0.0f, -90.0f], Pos: [9.0d, 40.0d, 9.0d]}')
        });
		
		scene.idle(22);
		
		let top1 = scene.world.showIndependentSectionImmediately([0, 9, 0, 16, 9, 8]);
		let top2 = scene.world.showIndependentSectionImmediately([0, 9, 9, 16, 9, 16]);
		scene.world.setBlocks([0, 10, 0, 16, 10, 8], "minecraft:air", false);
		scene.world.setBlocks([0, 10, 9, 16, 10, 16], "minecraft:air", false);
		scene.world.hideIndependentSection(grass1, Facing.DOWN);
		scene.world.hideIndependentSection(grass2, Facing.DOWN);
		scene.world.createEntity("crusty_chunks:shock_clientside_bypass", [9, 8, 9]);
		
		scene.idle(320);
		
		scene.text(40, "It just creates a pretty deep impact crater", [9.5, 9, 9.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.text(20, "However...", [9.5, 9, 9.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(40);
		
		scene.world.hideIndependentSection(cut1, Facing.DOWN);
		scene.world.hideIndependentSection(cut2, Facing.DOWN);
		scene.world.hideIndependentSection(top1, Facing.DOWN);
		scene.world.hideIndependentSection(top2, Facing.DOWN);
		
		scene.idle(15);
		
		scene.world.setBlocks([0, 0, 0, 16, 11, 16], "minecraft:stone", false);
		scene.world.setBlocks([0, 12, 0, 16, 13, 16], "minecraft:dirt", false);
		scene.world.setBlocks([0, 14, 0, 16, 14, 16], "minecraft:grass_block", false);
		let cut3 = scene.world.showIndependentSection([0, 10, 0, 16, 14, 8], Facing.DOWN);
		scene.world.showSection([0, 10, 9, 16, 14, 16], Facing.DOWN);
		let cut5 = scene.world.showIndependentSection([0, 5, 0, 16, 9, 16], Facing.DOWN);
		scene.world.showSection([0, 0, 0, 16, 4, 16], Facing.DOWN);
		
		scene.idle(20);
		
		scene.world.hideIndependentSection(cut5, Facing.SOUTH);
		
		scene.idle(20);
		
		scene.world.hideIndependentSection(cut3, Facing.NORTH);
		
		scene.idle(15);
		
		scene.text(80, "Bunker Buster bomb can drill up to 5 blocks down", [9.5, 12, 9.5]).attachKeyFrame();
		
		scene.idle(25);
		
		scene.world.setBlocks([0, 5, 0, 16, 9, 16], "minecraft:air", false);
		let bomb = scene.world.createEntity("crusty_chunks:bunker_buster_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, -1.0d, 0.0d], Rotation: [0.0f, -90.0f], Pos: [9.5d, 40.0d, 9.5d]}')
        });

		scene.idle(17);
		
		scene.world.createEntity("crusty_chunks:tiny_client_effect", [9, 15, 9]);
		
		scene.idle(1);
		
		scene.world.modifyEntity(bomb, e => {
			e.setNbt('{crit: 1b, Motion: [0.0d, -0.1d, 0.0d], Pos: [9.5d, 15.0d, 9.5d], Rotation:[0.0f, -90.0f]}')
        });
		scene.world.setBlock([9, 14, 9], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:tiny_client_effect", [9, 14, 9]);
		
		scene.idle(4);
		
		scene.text(60, "And detonate on hitting 6", [9.5, 4, 9.5]);
		scene.world.setBlocks([9, 13, 9], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:tiny_client_effect", [9, 13, 9]);
		
		scene.idle(3);
		
		scene.world.setBlocks([9, 12, 9], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:tiny_client_effect", [9, 12, 9]);
		
		scene.idle(2);
		
		scene.world.setBlocks([9, 11, 9], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:tiny_client_effect", [9, 11, 9]);
		
		scene.idle(2);
		
		scene.world.setBlocks([9, 10, 9], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:tiny_client_effect", [9, 10, 9]);
		
		scene.idle(8);
		
		scene.world.createEntity("crusty_chunks:giant_shock_explosion_bypass", [9, 5, 9]);
		
		scene.idle(3);
		
		scene.world.setBlocks([0, 10, 9, 16, 14, 16], "minecraft:air", false);
		scene.world.setBlocks([0, 0, 0, 16, 4, 16], "minecraft:air", false);
		let final = scene.world.showIndependentSectionImmediately([0, 85, 0, 16, 99, 16]);
		scene.world.moveSection(final, [0, -85, 0], 0);
	});
});