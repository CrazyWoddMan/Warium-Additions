Ponder.registry((event) => {
    event.create("crusty_chunks:press").scene("press", "Automatic Press", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:press", false);
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(100, "Automatic Press has 2 requirements to work", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(120);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:conveyor", false);
		scene.world.showSection([2, 1, 1], Facing.SOUTH);

		scene.idle(20);
		
		scene.text(80, "Conveyor must be placed at the front", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.world.hideSection([2, 1, 1], Facing.EAST);

		scene.idle(15);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:conveyor_lift", false);
		scene.world.showSection([2, 1, 1], Facing.EAST);
		scene.text(80, "Lift or dropoff variant can be used", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();

		scene.idle(40);

		scene.world.hideSection([2, 1, 1], Facing.EAST);

		scene.idle(15);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:conveyor_dropoff", false);
		scene.world.showSection([2, 1, 1], Facing.EAST);

		scene.idle(40);

		scene.text(100, "And rotation force must be provided from the side", [2, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.world.setBlock([1, 1, 2], "crusty_chunks:manual_crank", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("facing", "east"), false);
		scene.world.showSection([1, 1, 2], Facing.EAST);

		scene.idle(10);

		scene.text(80, "Either by rotating Manual Crank", [1.8, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(100);

		scene.world.hideSection([1, 1, 2], Facing.WEST);

		scene.idle(15);

		scene.world.setBlock([1, 1, 2], "crusty_chunks:large_electric_motor", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("facing", "east"), false);
		scene.world.showSection([1, 1, 2], Facing.EAST);
		scene.world.setBlock([1, 1, 1], "minecraft:lever", false);
		scene.world.modifyBlock([1, 1, 1], (curState) => curState.with("facing", "east"), false);
		scene.world.modifyBlock([1, 1, 1], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 1, 1], Facing.DOWN);

		scene.idle(10);

		scene.text(60, "Or by Electric Motor", [1.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		scene.world.setBlock([0, 1, 2], "crusty_chunks:energy_node", false);
		scene.world.modifyBlock([0, 1, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([0, 1, 2], Facing.EAST);

		scene.idle(10);

		scene.text(100, "Powered using Energy Node", [0.5, 1.5, 2.5]).placeNearTarget();

		scene.idle(20);

		scene.world.toggleRedstonePower([1, 1, 1]);
		scene.effects.createRedstoneParticles([1, 1, 1], 0xFF0000, 3);
	});
});