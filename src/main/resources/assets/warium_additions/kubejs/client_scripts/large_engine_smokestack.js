Ponder.registry((event) => {
    event.create("crusty_chunks:large_engine_smokestack").scene("large_engine_smokestack", "Large Engine Smokestack", "kubejs:9x9", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlock([4, 3, 4], "crusty_chunks:large_engine_smokestack", false);
		let cyllinder = scene.world.showIndependentSection([4, 3, 4], Facing.DOWN);
		scene.world.moveSection(cyllinder, [0, -2, 0], 0);
		
		scene.idle(20);
		
		scene.text(100, "Large Engine Smokestack is used to build large combustion engine", [4.5, 1.5, 4.5]).placeNearTarget().attachKeyFrame();

		scene.idle(115);

		scene.world.moveSection(cyllinder, [0, 2, 0], 10);

		scene.idle(8);

		scene.world.setBlock([4, 2, 4], "crusty_chunks:engine_cyllinder", false);
		scene.world.showSection([4, 2, 4], Facing.UP);

		scene.idle(5);

		scene.world.setBlock([4, 1, 4], "crusty_chunks:drive_shaft", false);
		scene.world.showSection([4, 1, 4], Facing.UP);

		scene.idle(20);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [4, 2, 4], 55);
		scene.text(50, "Large Engine Cyllinder", [4.5, 2.2, 4.5]).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [4, 1, 4], 55);
		scene.text(50, "Large Engine Drive Shaft", [4.5, 1.2, 4.5]).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		scene.world.setBlock([1, 1, 4], "crusty_chunks:fuel_tank", false);
		scene.world.showSection([1, 1, 4], Facing.DOWN);

		scene.idle(20);

		scene.text(80, "Fuel Tank is needed to provide the fuel", [1.5, 1.5, 4.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.text(100, "Right click with Diesel Bucket to fill Fuel Tank with diesel", [1.5, 1.5, 4.5]).placeNearTarget().attachKeyFrame();
		scene.showControls(100, [2, 1.5, 4], "right").rightClick().withItem("crusty_chunks:diesel_bucket");

		scene.idle(110);

		scene.text(100, "Use Fuel Hose to connect Fuel Tank to the engine", [1.5, 1.5, 4.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.showControls(30, [5, 2.5, 4], "right").rightClick().withItem("crusty_chunks:fuel_hose");

		scene.idle(40);

		scene.showControls(30, [1.8, 1.5, 5], "down").rightClick().withItem("crusty_chunks:fuel_hose");

		scene.overlay.showLine(PonderPalette.BLUE, [1.5, 1.5, 4.5], [4.5, 2.5, 4.5], 60);

		scene.idle(65);

		scene.world.setBlock([3, 1, 4], "minecraft:lever", false);
		scene.world.modifyBlock([3, 1, 4], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([3, 1, 4], Facing.EAST);

		scene.idle(20);

		scene.text(80, "Redstone signal should be used to turn the engine on", [3.8, 1.5, 4.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.world.toggleRedstonePower([3, 1, 4]);
		scene.effects.createRedstoneParticles([3, 1, 4], 0xFF0000, 3);

		scene.idle(20);

		scene.particles.simple(1, "crusty_chunks:smoke", [4.5, 4.5, 4.5]);

		scene.idle(50);

		scene.text(120, "One large engine section produces the same amount of rotation force as light combustion engine", [4.5, 1.5, 4]).placeNearTarget().attachKeyFrame();

		scene.idle(130);

		scene.text(30, "However...", [4.5, 2.5, 4.5]).placeNearTarget().attachKeyFrame();

		scene.idle(40);

		let engine2 = scene.world.showIndependentSection([4, 1, 4, 4, 3, 4], Facing.SOUTH);
		scene.world.moveSection(engine2, [0, 0, -1], 0);
		let engine3 = scene.world.showIndependentSection([4, 1, 4, 4, 3, 4], Facing.NORTH);
		scene.world.moveSection(engine3, [0, 0, 1], 0);

		scene.idle(5);

		let engine4 = scene.world.showIndependentSection([4, 1, 4, 4, 3, 4], Facing.SOUTH);
		scene.world.moveSection(engine4, [0, 0, -2], 0);
		let engine5 = scene.world.showIndependentSection([4, 1, 4, 4, 3, 4], Facing.NORTH);
		scene.world.moveSection(engine5, [0, 0, 2], 0);

		scene.idle(5);

		let engine6 = scene.world.showIndependentSection([4, 1, 4, 4, 3, 4], Facing.SOUTH);
		scene.world.moveSection(engine6, [0, 0, -3], 0);
		let engine7 = scene.world.showIndependentSection([4, 1, 4, 4, 3, 4], Facing.NORTH);
		scene.world.moveSection(engine7, [0, 0, 3], 0);

		scene.idle(5);

		let engine8 = scene.world.showIndependentSection([4, 1, 4, 4, 3, 4], Facing.NORTH);
		scene.world.moveSection(engine8, [0, 0, 4], 0);

		scene.idle(20);

		scene.text(80, "Up to 8 sections can be stacked to to increase the power", [4.5, 2.5, 4.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		let lever2 = scene.world.showIndependentSection([3, 1, 4], Facing.EAST);
		scene.world.moveSection(lever2, [0, 0, 4], 0);

		scene.idle(3);

		let lever3 = scene.world.showIndependentSection([3, 1, 4], Facing.EAST);
		scene.world.moveSection(lever3, [0, 0, 3], 0);

		scene.idle(3);

		let lever4 = scene.world.showIndependentSection([3, 1, 4], Facing.EAST);
		scene.world.moveSection(lever4, [0, 0, 2], 0);

		scene.idle(3);

		let lever5 = scene.world.showIndependentSection([3, 1, 4], Facing.EAST);
		scene.world.moveSection(lever5, [0, 0, 1], 0);

		scene.idle(3);

		let lever6 = scene.world.showIndependentSection([3, 1, 4], Facing.EAST);
		scene.world.moveSection(lever6, [0, 0, -1], 0);

		scene.idle(3);

		let lever7 = scene.world.showIndependentSection([3, 1, 4], Facing.EAST);
		scene.world.moveSection(lever7, [0, 0, -2], 0);

		scene.idle(3);

		let lever8 = scene.world.showIndependentSection([3, 1, 4], Facing.EAST);
		scene.world.moveSection(lever8, [0, 0, -3], 0);

		scene.idle(20);

		scene.text(80, "Each section requires redstone signal individually", [3.8, 1.5, 1.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.text(200, "And each section must be connected to Fuel Tank individually", [1.5, 1.5, 4.5]).placeNearTarget();
	});
});