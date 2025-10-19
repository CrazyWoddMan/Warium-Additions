Ponder.registry((event) => {
    event.create("crusty_chunks:torpedo_thruster").scene("torpedo", "How to build a torpedo", "kubejs:torpedo", (scene, util) => {
		scene.scaleSceneView(0.5);
		scene.setSceneOffsetY(-4);
		scene.world.showSection([0, 0, 0, 15, 5, 15], Facing.DOWN);

		scene.idle(20);

		scene.world.showSection([7, 8, 0, 7, 8, 2], Facing.DOWN);

		scene.idle(15);

		scene.world.showSection([7, 7, 0], Facing.UP);

		scene.idle(5);

		scene.world.showSection([7, 7, 1], Facing.UP);

		scene.idle(5);

		scene.world.showSection([7, 7, 2], Facing.UP);

		scene.idle(5);

		scene.world.showSection([6, 8, 1], Facing.EAST);

		scene.idle(15);

		scene.addKeyframe();

		scene.idle(5);

		scene.world.toggleRedstonePower([6, 8, 1]);
		scene.effects.createRedstoneParticles([6, 8, 1], 0xFF0000, 3);

		scene.idle(5);
		
		scene.world.setBlocks([7, 7, 0, 7, 7, 2], "minecraft:air", false);
		let torpedo = scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, 0.8d], Rotation: [0.0f, 0.0f], Pos: [7.5d, 7.5d, 0.0d]}')
        });

		scene.idle(10);

		scene.world.modifyEntity(torpedo, (e) => {
			e.setDeltaMovement([0, 0, 1]);
		});

		scene.idle(3);

		scene.world.modifyEntity(torpedo, (e) => {
			e.setDeltaMovement([0, 0, 1]);
		});

		scene.idle(2);

		scene.world.toggleRedstonePower([6, 8, 1]);

		scene.idle(1);

		scene.world.modifyEntity(torpedo, (e) => {
			e.setDeltaMovement([0, 0, 1]);
		});

		scene.idle(3);

		scene.world.modifyEntity(torpedo, (e) => {
			e.setDeltaMovement([0, 0, 1]);
		});

		scene.idle(3);

		scene.world.modifyEntity(torpedo, (e) => {
			e.setDeltaMovement([0, 0, 1]);
		});
	});
});