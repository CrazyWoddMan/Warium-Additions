Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_inline_fission_warhead").scene("ordinance_inline_fission_warhead", "Ordinance Core Fission Bomb", (scene, util) => {

        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_inline_fission_warhead", false);
		let inline = scene.world.showIndependentSectionImmediately([2, 1, 2]);
		
		scene.idle(10);
		
		scene.text(100, "Ordinance Core Fission Bomb is the main part of the droppable Fission Bomb", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(120);
		
		scene.world.moveSection(inline, [0, 0, -1], 5);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 1, 4], "crusty_chunks:ordinance_core", false);
		let core = scene.world.showIndependentSectionImmediately([2, 1, 4]);
		scene.world.moveSection(core, [0, 0, -2], 7);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 0], "crusty_chunks:ordinance_fission_initiator_head", false);
		let head = scene.world.showIndependentSection([2, 1, 0], Facing.SOUTH);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 2, 4], "crusty_chunks:ordinance_fins", false);
		let fins = scene.world.showIndependentSectionImmediately([2, 2, 4]);
		scene.world.moveSection(fins, [0, -1, 0], 0);
		scene.world.moveSection(fins, [0, 0, -1], 7);
		
		scene.idle(10);
		
		scene.text(155, "It needs to be filled with 6x Implosion Lens and 1x Fission Core in order to be ready for an activation", [2.0, 1.9, 1.8]).placeNearTarget();
		
		scene.idle(10);
		
		scene.showControls(145, [2.8, 1.5, 1.8], "down").withItem("crusty_chunks:fission_core");
		
		scene.idle(10);
		
		scene.showControls(135, [2.8, 3, 1.8], "down").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(130, [2.15, 4.15, 1.8], "left").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(125, [2.1, 2.7, 1.8], "left").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(120, [2.15, 0.97, 1.8], "left").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(115, [2.8, 1.6, 1.8], "up").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(110, [3.5, 3.6, 1.8], "right").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(105, [3.5, 2.1, 1.8], "right").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(100, [3.5, 0.4, 1.8], "right").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(10);
		
		scene.addKeyframe();
		
		scene.idle(105);
		
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
		
		scene.world.setBlocks([2, 1, 0, 2, 2, 4], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:nuclear_bomb_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, -0.1d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 2.7d, 1.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 4, 2]);
    }).scene("nuclear_bomb", "Nuclear Bomb showcase", "kubejs:nuclear_bomb", (scene, util) => {
		scene.scaleSceneView(0.1);
		scene.setSceneOffsetY(-10.0);
		scene.world.setBlocks([0, 109, 0, 98, 114, 98], "minecraft:stone", false);
		scene.world.setBlocks([0, 115, 0, 98, 116, 98], "minecraft:dirt", false);
		scene.world.setBlocks([0, 117, 0, 98, 117, 98], "minecraft:grass_block", false);
		let clean = scene.world.showIndependentSectionImmediately([1, 109, 1, 98, 117, 98]);
		let grass1 = scene.world.showIndependentSectionImmediately([0, 117, 0, 98, 117, 0]);
		let grass2 = scene.world.showIndependentSectionImmediately([0, 117, 1, 0, 117, 98]);
		let side1 = scene.world.showIndependentSectionImmediately([0, 109, 0, 98, 116, 0]);
		let side2 = scene.world.showIndependentSectionImmediately([0, 109, 1, 0, 116, 98]);
		scene.world.moveSection(clean, [0, -109, 0], 0);
		scene.world.moveSection(grass1, [0, -109, 0], 0);
		scene.world.moveSection(grass2, [0, -109, 0], 0);
		scene.world.moveSection(side1, [0, -109, 0], 0);
		scene.world.moveSection(side2, [0, -109, 0], 0);
		let bomb = scene.world.createEntity("crusty_chunks:nuclear_bomb_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, -1.0d, 0.0d], Rotation: [0.0f, -90.0f], Pos: [47.0d, 100.0d, 47.0d]}')
        });
		
		scene.idle(51);
		
		scene.world.createEntity("crusty_chunks:small_client_effect", [47, 9, 47]);
		scene.world.createEntity("crusty_chunks:nuclear_secondary_effect", [49.5, 40.0, 49.5]);
		
		scene.idle(2);
		
		scene.world.modifyEntity(bomb, e => {
			e.setNbt('{Motion: [0.0d, 0.0d, 0.0d], Rotation: [0.0f, -90.0f], Pos: [47.0d, 10.0d, 47.0d]}')
		});
		
		scene.idle(3);
		
		scene.world.removeEntity(bomb);
		let cut = scene.world.showIndependentSectionImmediately([22, 103, 23, 75, 108, 75]);
		scene.world.setBlocks([22, 112, 23, 75, 117, 75], "minecraft:air", false);
		scene.world.moveSection(cut, [0, -100, 0], 0);
		scene.world.createEntity("crusty_chunks:giant_shock_explosion_bypass", [49.5, 20.0, 49.5]);
		scene.world.createEntity("crusty_chunks:nuclear_blast_entity", [49.5, 20.0, 49.5]);
		
		scene.idle(1);
		
		scene.world.createEntity("crusty_chunks:giant_shock_explosion_bypass", [49.5, 20.0, 49.5]);
		scene.world.createEntity("crusty_chunks:nuclear_thermal_rad", [49.5, 20.0, 49.5]);
		scene.particles.simple(20, "crusty_chunks:large_smoke", [0, 3, 0]).density(5).gravity(0.001).scale(3).lifetime(2400).motion([0, 0.7, 0]).area([98, 8, 98]);
		scene.world.setBlocks([49, 109, 49, 49, 117, 49], "minecraft:air", false);
		scene.world.setBlock([49, 9, 49], "minecraft:diamond_block", false);
		scene.world.showIndependentSectionImmediately([49, 9, 49]);
		
		scene.world.showIndependentSectionImmediately([0, 0, 0, 98, 8, 98]);
		scene.world.setBlocks([22, 103, 23, 75, 108, 75], "minecraft:air", false);
		scene.world.setBlocks([1, 109, 1, 98, 117, 98], "minecraft:air", false);
		scene.world.setBlocks([0, 117, 0, 98, 117, 0], "minecraft:air", false);
		scene.world.setBlocks([0, 117, 1, 0, 117, 98], "minecraft:air", false);
		
		scene.particles.simple(130, "explosion", [20, 5, 20]).scale(1.5).density(30).area([78, 50, 78]);
		scene.particles.simple(140, "explosion", [0, 5, 0]).scale(1.25).density(20).area([98, 50, 98]);
		scene.particles.simple(80, "explosion", [-60, 5, -60]).density(100).area([158, 30, 158]);
		scene.particles.simple(100, "explosion", [-40, 5, -40]).density(100).area([138, 30, 138]);
		scene.particles.simple(120, "explosion", [-20, 5, -20]).density(100).area([118, 30, 118]);
		scene.particles.simple(150, "explosion", [0, 5, 0]).density(5).area([98, 50, 98]);
		scene.particles.simple(160, "explosion", [0, 3, 0]).density(3).lifetime(8).area([98, 50, 98]);
		scene.particles.simple(200, "explosion", [0, 1, 0]).density(3).lifetime(8).area([98, 50, 98]);
		scene.particles.simple(220, "explosion", [0, 1, 0]).density(1).lifetime(8).area([98, 50, 98]);
	});
});