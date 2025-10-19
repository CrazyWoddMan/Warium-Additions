Ponder.registry((event) => {
    event.create("crusty_chunks:light_combustion_engine").scene("light_combustion_engine", "Light Combustion Engine", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:light_combustion_engine", false);
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(100, "Light Combustion Engine produces rotation force using diesel", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(120);

		scene.world.setBlock([0, 1, 3], "crusty_chunks:fuel_tank", false);
		scene.world.showSection([0, 1, 3], Facing.DOWN);

		scene.idle(20);
		
		scene.text(100, "Fuel Tank is needed to provide the fuel", [0.5, 1.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.text(100, "Right click with Diesel Bucket to fill Fuel Tank with diesel", [0.5, 1.5, 3.5]).placeNearTarget().attachKeyFrame();
		scene.showControls(100, [0.8, 1.5, 4], "down").leftClick().withItem("crusty_chunks:diesel_bucket");

		scene.idle(110);

		scene.text(100, "Use Fuel Hose to connect Fuel Tank to the engine", [0.5, 1.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.showControls(30, [2.8, 1.5, 3], "down").rightClick().withItem("crusty_chunks:fuel_hose");

		scene.idle(40);

		scene.showControls(30, [0.8, 1.5, 4], "down").rightClick().withItem("crusty_chunks:fuel_hose");

		scene.overlay.showLine(PonderPalette.BLUE, [2.5, 1.5, 2.5], [0.5, 1.5, 3.5], 60);

		scene.idle(65);

		scene.world.setBlock([1, 1, 2], "minecraft:lever", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 1, 2], Facing.DOWN);

		scene.idle(20);

		scene.text(80, "Redstone signal should be used to turn the engine on", [1.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.text(180, "Rotation can be used to power multiple machines", [2.5, 1.5, 2]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:lathe", false);
		scene.world.modifyBlock([2, 1, 1], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([2, 1, 1], Facing.EAST);

		scene.idle(20);

		scene.world.hideSection([2, 1, 1], Facing.EAST);

		scene.idle(15);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:press", false);
		scene.world.modifyBlock([2, 1, 1], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([2, 1, 1], Facing.EAST);

		scene.idle(20);

		scene.world.hideSection([2, 1, 1], Facing.EAST);

		scene.idle(15);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:extruder", false);
		scene.world.modifyBlock([2, 1, 1], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([2, 1, 1], Facing.EAST);

		scene.idle(20);

		scene.world.hideSection([2, 1, 1], Facing.EAST);

		scene.idle(15);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:cutter", false);
		scene.world.modifyBlock([2, 1, 1], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([2, 1, 1], Facing.EAST);

		scene.idle(20);

		scene.world.hideSection([2, 1, 1], Facing.EAST);

		scene.idle(15);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:crusher", false);
		scene.world.modifyBlock([2, 1, 1], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([2, 1, 1], Facing.EAST);

		scene.idle(20);

		scene.world.hideSection([2, 1, 1], Facing.EAST);

		scene.idle(20);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:extension_shaft", false);
		scene.world.showSection([2, 1, 1], Facing.SOUTH);

		scene.idle(5);

		scene.world.setBlock([2, 1, 0], "crusty_chunks:extension_shaft", false);
		scene.world.showSection([2, 1, 0], Facing.SOUTH);

		scene.idle(20);
		
		scene.text(80, "Extension Shafts can be used to extend the rotation further", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.world.hideSection([2, 1, 1], Facing.UP);
		scene.world.hideSection([2, 1, 0], Facing.UP);

		scene.idle(20);

		scene.world.setBlock([2, 1, 1], "crusty_chunks:generator", false);
		scene.world.showSection([2, 1, 1], Facing.SOUTH);

		scene.idle(20);
		
		scene.text(100, "Or it can be converted to electricity using Rotation Generator", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();

		scene.idle(120);

		scene.world.setBlock([2, 1, 0], "crusty_chunks:energy_node", false);
		scene.world.showSection([2, 1, 0], Facing.SOUTH);

		scene.idle(20);
		
		scene.text(100, "And outputted by Energy Node", [2.5, 1.5, 0.5]).placeNearTarget();

		scene.idle(20);

		scene.world.toggleRedstonePower([3, 1, 2]);
		scene.effects.createRedstoneParticles([3, 1, 2], 0xFF0000, 3);
		scene.markAsFinished();

		for (let e = 0; e < 1000; e++) {
			for (let i = 0; i < 4; i++) {
				scene.particles.simple(1, "crusty_chunks:sparks", [2.5, 1.5, 1.5]).motion([
					1.5 * Math.random() - 0.75,
					1.5 * Math.random(),
					1.5 * Math.random() - 0.75
				]).scale(0.8).gravity(0.4);
			}
			scene.idle(5);
		}
	});
});