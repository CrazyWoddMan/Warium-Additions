Ponder.registry((event) => {
    event.create("crusty_chunks:ordinance_fins").scene("ordinance_fins", "Ordinance Fins", (scene, util) => {

        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:ordinance_fins", false);
		let fins = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(40, "Ordinance Fins is used only for bombs", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(60);
		
		scene.world.moveSection(fins, [0, 0, 1], 5);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 1], "crusty_chunks:ordinance_core", false);
		let core = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(core, [0, 0, 1], 10);
		
		scene.idle(3);
		
		scene.world.setBlock([2, 1, 0], "crusty_chunks:ordinance_heavy_warhead", false);
		let head = scene.world.showIndependentSectionImmediately([2, 1, 0]);
		scene.world.moveSection(head, [0, 0, 1], 10);
		
		scene.idle(10);
		
		scene.world.setBlocks([2, 4, 1, 2, 4, 3], "crusty_chunks:steel_block", false);
		scene.world.showSection([2, 4, 1, 2, 4, 3], Facing.DOWN);
		
		scene.idle(10);
		
		scene.world.moveSection(fins, [0, 2, 0], 10);
		scene.world.moveSection(core, [0, 2, 0], 10);
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
		
		scene.world.setBlocks([2, 1, 0, 2, 1, 2], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:large_bomb_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, -0.1d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 3.0d, 1.5d]}')
        });
		
		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 4, 2]);
    });
});