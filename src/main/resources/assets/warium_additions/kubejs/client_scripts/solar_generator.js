Ponder.registry((event) => {
    event.create("crusty_chunks:solar_generator").scene("solar_generator", "Solar Generator", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 2, 2], "crusty_chunks:solar_generator", false,);
		let solar = scene.world.showIndependentSection([2, 2, 2], Facing.DOWN);
		scene.world.moveSection(solar, [0, -1, 0], 0);

		scene.idle(10);

		scene.text(80, "Solar Generators can produce electricity during the day", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.world.setBlock([2, 1, 2], "crusty_chunks:energy_battery", false);
		let battery = scene.world.showIndependentSectionImmediately([2, 1, 2]);
		scene.world.moveSection(battery, [0, -1, 0], 0);
		scene.world.moveSection(battery, [0, 1, 0], 10);
		scene.world.moveSection(solar, [0, 1, 0], 10);

		scene.idle(20);

		scene.text(60, "Energy can be stored in Energy Batteries...", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.world.hideIndependentSection(battery, Facing.EAST);

		scene.idle(15);

		scene.world.setBlock([2, 1, 2], "crusty_chunks:energy_node", false);
		scene.world.modifyBlock([2, 1, 2], (curState) => curState.with("facing", "down"), false);
		scene.world.showSection([2, 1, 2], Facing.EAST);

		scene.idle(20);

		scene.text(200, "Or be directly outputted using Energy Nodes", [2.5, 1.5, 2.5]).placeNearTarget();
	});
});