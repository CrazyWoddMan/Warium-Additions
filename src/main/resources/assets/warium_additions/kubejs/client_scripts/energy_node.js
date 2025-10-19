Ponder.registry((event) => {
    event.create("crusty_chunks:energy_node").scene("energy_node", "Energy Node", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:energy_node", false,);
		scene.world.modifyBlock([2, 1, 2], (curState) => curState.with("facing", "up"), false);
		scene.world.showSection([2, 1, 2], Facing.DOWN);

		scene.idle(10);

		scene.text(80, "Energy Nodes are used to transfer electricity", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.world.hideSection([2, 1, 2], Facing.UP);

		scene.idle(20);

		scene.world.setBlock([4, 1, 2], "crusty_chunks:generator", false);
		scene.world.modifyBlock([4, 1, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([4, 1, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([0, 1, 2], "crusty_chunks:large_electric_motor", false,);
		scene.world.modifyBlock([0, 1, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([0, 1, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([3, 1, 2], "crusty_chunks:energy_node", false);
		scene.world.modifyBlock([3, 1, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([3, 1, 2], Facing.EAST);

		scene.idle(5);

		scene.world.setBlock([1, 1, 2], "crusty_chunks:energy_node", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("facing", "east"), false);
		scene.world.showSection([1, 1, 2], Facing.WEST);

		scene.idle(20);

		scene.showControls(20, [3.8, 2, 2.5], "down").rightClick().withItem("crusty_chunks:cable");

		scene.idle(30);

		scene.showControls(20, [1.2, 2, 2.5], "down").rightClick().withItem("crusty_chunks:cable");

		scene.idle(5);

		scene.overlay.showLine(PonderPalette.BLACK, [1.2, 1.5, 2.5], [3.8, 1.5, 2.5], 370);

		scene.idle(30);

		scene.text(100, "Rotation Generator generates power from rotation", [4.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.text(100, "Electric Motor converts power back to rotation", [0.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.world.hideSection([4, 1, 2], Facing.SOUTH);
		scene.world.hideSection([0, 1, 2], Facing.SOUTH);
		
		scene.idle(15);

		scene.world.setBlock([4, 1, 2], "crusty_chunks:energy_battery", false);
		scene.world.showSection([4, 1, 2], Facing.SOUTH);
		scene.world.setBlock([0, 1, 2], "crusty_chunks:energy_battery", false);
		scene.world.showSection([0, 1, 2], Facing.SOUTH);

		scene.text(100, "Energy Batteries can store electricity", [0.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.world.setBlock([2, 1, 4], "crusty_chunks:energy_distribution_node", false);
		scene.world.showSection([2, 1, 4], Facing.DOWN);

		scene.idle(10);

		scene.world.setBlock([3, 1, 4], "crusty_chunks:energy_node", false);
		scene.world.modifyBlock([3, 1, 4], (curState) => curState.with("facing", "east"), false);
		scene.world.showSection([3, 1, 4], Facing.WEST);
		scene.world.setBlock([1, 1, 4], "crusty_chunks:energy_node", false);
		scene.world.modifyBlock([1, 1, 4], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([1, 1, 4], Facing.EAST);

		scene.idle(10);

		scene.world.setBlock([2, 2, 4], "crusty_chunks:energy_node", false);
		scene.world.modifyBlock([2, 2, 4], (curState) => curState.with("facing", "up"), false);
		scene.world.showSection([2, 2, 4], Facing.DOWN);

		scene.idle(20);

		scene.showControls(10, [3.8, 2, 2.5], "down").rightClick().withItem("crusty_chunks:cable");

		scene.idle(20);

		scene.showControls(10, [3.2, 2, 4.5], "down").rightClick().withItem("crusty_chunks:cable");

		scene.idle(5);

		scene.overlay.showLine(PonderPalette.BLACK, [3.5, 1.5, 2.5], [3.5, 1.5, 4.5], 1045);

		scene.idle(20);

		scene.showControls(10, [1.8, 2, 4.5], "down").rightClick().withItem("crusty_chunks:cable");

		scene.idle(20);

		scene.showControls(10, [1.2, 2, 2.5], "down").rightClick().withItem("crusty_chunks:cable");

		scene.idle(5);

		scene.overlay.showLine(PonderPalette.BLACK, [1.5, 1.5, 2.5], [1.5, 1.5, 4.5], 1000);

		scene.idle(20);

		scene.text(200, "Energy Distribution Node can be used to connect multiple Energy Nodes", [2.5, 1.5, 4]).placeNearTarget();
	});
});