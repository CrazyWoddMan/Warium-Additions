Ponder.registry((event) => {
    event.create("crusty_chunks:fuel_tank_module").scene("fuel_tank", "Fuel Tank", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlocks([2, 1, 2, 1, 2, 3], "crusty_chunks:fuel_tank_module", false);
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(80, "Fuel Tank can contain 1 bucket of any fuel", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(95);

		scene.world.showSection([1, 1, 2], Facing.DOWN);

		scene.idle(3);

		scene.world.showSection([1, 1, 3], Facing.DOWN);

		scene.idle(3);

		scene.world.showSection([2, 1, 3], Facing.DOWN);

		scene.idle(10);

		scene.world.showSection([2, 2, 2], Facing.DOWN);

		scene.idle(3);

		scene.world.showSection([1, 2, 2], Facing.DOWN);

		scene.idle(3);

		scene.world.showSection([1, 2, 3], Facing.DOWN);

		scene.idle(3);

		scene.world.showSection([2, 2, 3], Facing.DOWN);

		scene.idle(15);
		
		scene.text(100, "Fuel Tanks placed next to each other are automatically connected", [1.5, 2.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.text(100, "Right click with Diesel Bucket to fill Fuel Tank with diesel", [1.5, 2.5, 2.5]).placeNearTarget().attachKeyFrame();
		scene.showControls(100, [1.8, 2.5, 3], "down").leftClick().withItem("crusty_chunks:diesel_bucket");

		scene.idle(110);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:fuel_tank", false);
		scene.world.showSection([2, 1, 1], Facing.DOWN);

		scene.idle(15);

		scene.text(100, "Fuel Tank Connection Port must be used to output fuel", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.text(120, "Use Fuel Hose to connect Fuel Tank Connection Port to varios machines", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.showControls(100, [2.8, 1.5, 2], "down").rightClick().withItem("crusty_chunks:fuel_hose");
	});
});