Ponder.registry((event) => {
    event.create("crusty_chunks:conveyor_lift").scene("conveyor_lift", "Conveyor Lift", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:conveyor_lift", false);
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);

		scene.text(80, "Conveyor lift works similar to Conveyor", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(100);
		
		scene.text(100, "The only defference is that it moves items vertically", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.world.setBlock([2, 2, 2], "crusty_chunks:conveyor_lift", false);
		scene.world.showSection([2, 2, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 3, 2], "crusty_chunks:conveyor_lift", false);
		scene.world.showSection([2, 3, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 4, 2], "crusty_chunks:conveyor_dropoff", false);
		scene.world.showSection([2, 4, 2], Facing.DOWN);

		scene.idle(30);

		scene.showControls(20, [3, 1.5, 2], "right").withItem("diamond");

		scene.idle(25);

		let item = scene.world.createItemEntity([2.5, 4.5, 1.5], [0.1 * Math.random() - 0.05, 0.3, -0.06], "diamond");

		scene.idle(22);

		scene.world.modifyEntity(item, (e) => {
			e.setDeltaMovement([0, 0, 0]);
			e.setNoGravity(true);
		});

		scene.idle(20);

		scene.world.setBlock([1, 1, 2], "crusty_chunks:conveyor", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("facing", "east"), false);
		scene.world.showSection([1, 1, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:conveyor", false);
		scene.world.modifyBlock([2, 1, 1], (curState) => curState.with("facing", "south"), false);
		scene.world.showSection([2, 1, 1], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([3, 1, 2], "crusty_chunks:conveyor", false);
		scene.world.modifyBlock([3, 1, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([3, 1, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 1, 3], "crusty_chunks:conveyor", false);
		scene.world.showSection([2, 1, 3], Facing.DOWN);

		scene.idle(20);

		scene.text(200, "Items can be inputted using conveyors from the sides", [1.5, 1.5, 2.5]).placeNearTarget();
	}).scene("conveyor", "Conveyor", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:conveyor", false);
		let conveyor = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(100, "Conveyor can be used to output items from different machines", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.world.setBlock([1, 1, 3], "crusty_chunks:lathe", false);
		let lathe = scene.world.showIndependentSection([1, 1, 3], Facing.EAST);
		scene.world.moveSection(lathe, [0.5, 0, 0], 0);
		scene.world.moveSection(lathe, [0.5, 0, 0], 15);

		scene.idle(25);

		scene.world.hideIndependentSection(lathe, Facing.EAST);
		scene.world.moveSection(lathe, [0.5, 0, 0], 15);

		scene.idle(15);

		scene.world.setBlock([1, 1, 3], "crusty_chunks:press", false);
		let press = scene.world.showIndependentSection([1, 1, 3], Facing.EAST);
		scene.world.moveSection(press, [0.5, 0, 0], 0);
		scene.world.moveSection(press, [0.5, 0, 0], 15);

		scene.idle(25);

		scene.world.hideIndependentSection(press, Facing.EAST);
		scene.world.moveSection(press, [0.5, 0, 0], 15);

		scene.idle(15);

		scene.world.setBlock([1, 1, 3], "crusty_chunks:extruder", false);
		let extruder = scene.world.showIndependentSection([1, 1, 3], Facing.EAST);
		scene.world.moveSection(extruder, [0.5, 0, 0], 0);
		scene.world.moveSection(extruder, [0.5, 0, 0], 15);

		scene.idle(25);

		scene.world.hideIndependentSection(extruder, Facing.EAST);
		scene.world.moveSection(extruder, [0.5, 0, 0], 15);

		scene.idle(15);

		scene.world.setBlock([1, 1, 3], "crusty_chunks:cutter", false);
		let cutter = scene.world.showIndependentSection([1, 1, 3], Facing.EAST);
		scene.world.moveSection(cutter, [0.5, 0, 0], 0);
		scene.world.moveSection(cutter, [0.5, 0, 0], 15);

		scene.idle(30);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:conveyor", false);
		let conveyor2 = scene.world.showIndependentSection([2, 1, 1], Facing.SOUTH);

		scene.idle(5);

		scene.world.setBlock([2, 1, 0], "crusty_chunks:conveyor_lift", false);
		let lift = scene.world.showIndependentSection([2, 1, 0], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 2, 0], "crusty_chunks:conveyor_lift", false);
		let lift2 = scene.world.showIndependentSection([2, 2, 0], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 3, 0], "crusty_chunks:conveyor_lift", false);
		let lift3 = scene.world.showIndependentSection([2, 3, 0], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 4, 0], "crusty_chunks:conveyor", false);
		scene.world.modifyBlock([2, 4, 0], (curState) => curState.with("facing", "west"), false);
		let conveyor3 = scene.world.showIndependentSection([2, 4, 0], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([1, 4, 0], "crusty_chunks:conveyor_dropoff", false);
		scene.world.modifyBlock([1, 4, 0], (curState) => curState.with("facing", "west"), false);
		let dropoff = scene.world.showIndependentSection([1, 4, 0], Facing.EAST);

		scene.idle(15);

		scene.text(120, "Item transportation chains can be constructed using different conveyor types", [2.5, 2.5, 0.5]).placeNearTarget().attachKeyFrame();

		scene.idle(130);

		scene.world.hideIndependentSection(conveyor2, Facing.NORTH);
		scene.world.hideIndependentSection(lift, Facing.NORTH);
		scene.world.hideIndependentSection(lift2, Facing.NORTH);
		scene.world.hideIndependentSection(lift3, Facing.NORTH);
		scene.world.hideIndependentSection(conveyor3, Facing.NORTH);
		scene.world.hideIndependentSection(dropoff, Facing.NORTH);

		scene.idle(20);

		scene.world.setBlock([2, 2, 2], "minecraft:hopper", false);
		let hopper = scene.world.showIndependentSectionImmediately([2, 2, 2]);
		scene.world.moveSection(hopper, [0, -2, 0], 0);
		scene.world.moveSection(hopper, [0, 1, 0], 10);
		scene.world.moveSection(conveyor, [0, 1, 0], 10);
		scene.world.moveSection(cutter, [0, 1, 0], 10);

		scene.idle(10);

		scene.world.setBlock([1, 2, 2], "create:andesite_funnel", false);
		scene.world.modifyBlock([1, 2, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.modifyBlock([1, 2, 2], (curState) => curState.with("extracting", "true"), false);
		scene.world.showSection([1, 2, 2], Facing.EAST);

		scene.idle(20);

		scene.text(200, "Hoppers or any other modded item transporters then can be used to extract items from conveyor", [2.5, 1.5, 2.5]).placeNearTarget();
	}).scene("conveyor_dropoff2", "Conveyor Dropoff", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:conveyor_dropoff", false);
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);

		scene.text(80, "Conveyor Dropoff works similar to Conveyor as well", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(100);
		
		scene.text(120, "The only defference is that it immediately drops any collected items", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.showControls(20, [2.5, 2, 2.5], "down").withItem("diamond");

		scene.idle(25);

		let item = scene.world.createItemEntity([2.5, 1.5, 1.5], [0.2 * Math.random() - 0.1, 0.2, -0.05], "diamond");

		scene.idle(11);

		scene.world.modifyEntity(item, (e) => {
			e.setDeltaMovement([0, 0, 0]);
			e.setNoGravity(true);
		});
	});
});