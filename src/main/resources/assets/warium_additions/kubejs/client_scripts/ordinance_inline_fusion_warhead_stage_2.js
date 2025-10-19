Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_inline_fusion_warhead_stage_2").scene("fusion_bomb", "Fusion bomb", (scene, util) => {

        scene.showBasePlate();

		scene.idle(10);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:ordinance_inline_fusion_warhead_stage_2", false);
		let stage2 = scene.world.showIndependentSection([2, 1, 1], Facing.SOUTH);
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_inline_fusion_warhead_stage_1", false);
		let stage1 = scene.world.showIndependentSection([2, 1, 2], Facing.NORTH);

		scene.idle(10);
		
		scene.world.setBlock([2, 1, 0], "crusty_chunks:ordinance_fission_initiator_head", false);
		let head = scene.world.showIndependentSection([2, 1, 0], Facing.SOUTH);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 1, 3], "crusty_chunks:ordinance_core", false);
		let core = scene.world.showIndependentSection([2, 1, 3], Facing.NORTH);
		
		scene.idle(5);
		
		scene.world.setBlock([2, 1, 4], "crusty_chunks:ordinance_fins", false);
		let fins = scene.world.showIndependentSection([2, 1, 4], Facing.NORTH);
		
		scene.idle(10);
		
		scene.text(60, "Fusion Bomb is the most powerful weapon in Warium", [2.5, 1.5, 2.0]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.text(175, "It must be filled with 6x Implosion Lens and 1x Fission Core in order to be ready for an activation", [2.0, 1.9, 2.5]).placeNearTarget();
		
		scene.idle(30);
		
		scene.showControls(145, [2.8, 1.5, 2.8], "down").withItem("crusty_chunks:fission_core");
		
		scene.idle(10);
		
		scene.showControls(135, [2.8, 3, 2.8], "down").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(130, [2.15, 4.15, 2.8], "left").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(125, [2.1, 2.7, 2.8], "left").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(120, [2.15, 0.97, 2.8], "left").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(115, [2.8, 1.6, 2.8], "up").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(110, [3.5, 3.6, 2.8], "right").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(105, [3.5, 2.1, 2.8], "right").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(5);
		
		scene.showControls(100, [3.5, 0.4, 2.8], "right").withItem("crusty_chunks:implosion_lens");
		
		scene.idle(10);
		
		scene.addKeyframe();
		
		scene.idle(105);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 4], 40);
		scene.text(40, "Only Fins can be used", [2.5, 1.5, 4.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.setBlocks([2, 4, 0, 2, 4, 4], "crusty_chunks:steel_block", false);
		scene.world.showSection([2, 4, 0, 2, 4, 4], Facing.DOWN);
		
		scene.idle(10);
		
		scene.world.moveSection(fins, [0, 2, 0], 10);
		scene.world.moveSection(core, [0, 2, 0], 10);
		scene.world.moveSection(stage1, [0, 2, 0], 10);
		scene.world.moveSection(stage2, [0, 2, 0], 10);
		scene.world.moveSection(head, [0, 2, 0], 10);
		
		scene.idle(10);
		
		scene.world.hideSection([0, 0, 0, 4, 0, 4], Facing.DOWN);
		scene.world.setBlock([1, 4, 3], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 4, 3], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([1, 4, 3], Facing.EAST);
		
		scene.idle(10);
		
		scene.text(100, "Redstone signal must power Ordinance Core part of the bomb to launch it", [2.6, 3.6, 4.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(60);
		
		scene.world.setBlocks([0, 0, 0, 4, 0, 4], "minecraft:air", false);
		scene.world.toggleRedstonePower([1, 4, 3]);
		scene.effects.createRedstoneParticles([1, 4, 3], 0xFF0000, 3);
		
		scene.idle(5);
		
		scene.world.setBlocks([2, 1, 0, 2, 1, 4], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:ordinance_fusion_bomb_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, -0.1d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 2.7d, 2.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 4, 3]);
	});
});