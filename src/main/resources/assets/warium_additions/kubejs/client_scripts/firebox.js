Ponder.registry((event) => {
    event.create("crusty_chunks:firebox").scene("firebox", "Firebox", "kubejs:7x7", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlock([3, 1, 3], "crusty_chunks:firebox", false);
		scene.world.showSection([3, 1, 3], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(100, "Firebox produces heat from coal and is needed for different resource procesing structures", [3.5, 1.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.world.hideSection([3, 1, 3], Facing.UP);

		scene.idle(20);

		scene.world.setBlock([0, 1, 3], "crusty_chunks:firebox", false);
		scene.world.setBlock([2, 1, 3], "crusty_chunks:firebox", false);
		scene.world.setBlock([4, 1, 3], "crusty_chunks:firebox", false);
		scene.world.setBlock([6, 1, 3], "crusty_chunks:firebox", false);
		scene.world.showSection([0, 1, 3], Facing.DOWN);
		scene.world.showSection([2, 1, 3], Facing.DOWN);
		scene.world.showSection([4, 1, 3], Facing.DOWN);
		scene.world.showSection([6, 1, 3], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([6, 2, 3], "crusty_chunks:refinery", false);
		scene.world.setBlock([4, 2, 3], "crusty_chunks:thermal_furnace", false);
		scene.world.setBlock([2, 2, 3], "crusty_chunks:blast_furnace", false);
		scene.world.setBlock([0, 2, 3], "crusty_chunks:foundry", false);
		scene.world.showSection([0, 2, 3, 6, 2, 3], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([6, 3, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 3, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 3, 3], "crusty_chunks:blast_funnel", false);
		scene.world.showSection([2, 3, 3, 6, 3, 3], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([6, 4, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 4, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 4, 3], "crusty_chunks:blast_funnel", false);
		scene.world.showSection([2, 4, 3, 6, 4, 3], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([6, 5, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 5, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 5, 3], "crusty_chunks:blast_funnel", false);
		scene.world.showSection([2, 5, 3, 6, 5, 3], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([6, 6, 3], "crusty_chunks:refinery_tower", false);
		scene.world.showSection([6, 6, 3], Facing.DOWN);

		scene.idle(10);

		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [0, 1, 3, 0, 2, 3], 55);
		scene.text(50, "Firebox is needed for Foundry", [0.5, 2.2, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 3, 2, 5, 3], 45);
		scene.text(40, "Alloy Furnace", [2.5, 2.5, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(50);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [4, 1, 3, 4, 5, 3], 45);
		scene.text(40, "Thermal Furnace", [4.5, 2.5, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(50);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [5, 1, 2, 7, 6, 4], 55);
		scene.text(50, "And Oil Refinery", [6.5, 3.5, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		scene.text(50, "Firebox consumes Coal", [0.5, 1.2, 3],).placeNearTarget().attachKeyFrame();
		scene.showControls(50, [1, 1.5, 3], "right").withItem("coal");

		scene.idle(60);

		scene.text(40, "Or Charcoal", [0.5, 1.2, 3],).placeNearTarget().attachKeyFrame();
		scene.showControls(50, [1, 1.5, 3], "right").withItem("charcoal");

		scene.idle(50);

		scene.text(80, "Firebox starts working as soon as there is fuel in it", [0.5, 1.2, 3],).placeNearTarget().attachKeyFrame();
		for (let e = 0; e < 2; e++) {
			scene.particles.simple(1, "flame", [-1, 1, 2]).density(8).area([2, 3, 5]);
			for (let i = 0; i < 6; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [-2, 1.5, 2]).motion([
					0.01 * Math.random() - 0.005,
					0.01 * Math.random() - 0.005,
					0.01 * Math.random() - 0.005
				]).density(1).area([2, 3.5, 5]);
			}
			scene.idle(40);
		}

		scene.particles.simple(1, "flame", [-1, 1, 2]).density(8).area([2, 3, 5]);
		for (let e = 0; e < 6; e++) {
			scene.particles.simple(1, "campfire_cosy_smoke", [-2, 1.5, 2]).motion([
				0.01 * Math.random() - 0.005,
				0.01 * Math.random() - 0.005,
				0.01 * Math.random() - 0.005
			]).density(1).area([2, 3.5, 5]);
		}

		scene.idle(20);

		scene.text(80, "1 Coal is consumed every 2 seconds", [0.5, 1.2, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(20);
		
		scene.markAsFinished();
		for (let e = 0; e < 16; e++) {
			scene.particles.simple(1, "flame", [-1, 1, 2]).density(8).area([2, 3, 5]);
			for (let i = 0; i < 6; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [-1, 1.5, 2]).motion([
					0.01 * Math.random() - 0.005,
					0.01 * Math.random() - 0.005,
					0.01 * Math.random() - 0.005
				]).density(1).area([2, 3.5, 5]);
			}
			scene.idle(40);
		}
	}).scene("firebox_oil_firebox", "Kerosene Firebox", "kubejs:7x7", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		scene.world.setBlock([0, 1, 3], "crusty_chunks:oil_firebox", false);
		scene.world.setBlock([2, 1, 3], "crusty_chunks:oil_firebox", false);
		scene.world.setBlock([4, 1, 3], "crusty_chunks:oil_firebox", false);
		scene.world.setBlock([6, 1, 3], "crusty_chunks:oil_firebox", false);
		scene.world.setBlock([6, 2, 3], "crusty_chunks:refinery", false);
		scene.world.setBlock([4, 2, 3], "crusty_chunks:thermal_furnace", false);
		scene.world.setBlock([2, 2, 3], "crusty_chunks:blast_furnace", false);
		scene.world.setBlock([0, 2, 3], "crusty_chunks:foundry", false);
		scene.world.setBlock([6, 3, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 3, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 3, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([6, 4, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 4, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 4, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([6, 5, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 5, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 5, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([6, 6, 3], "crusty_chunks:refinery_tower", false);
		scene.world.showSection([0, 1, 3, 6, 6, 3], Facing.UP);

		scene.idle(20);

		scene.text(120, "Kerosene Firebox produces heat from Kerosene and is optional instead of regular Firebox", [0.5, 1.2, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(130);

		scene.text(60, "it consumes Kerosene instead of Coal", [0.5, 1.2, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.showControls(140, [1, 1.5, 3], "right").rightClick().withItem("crusty_chunks:kerosene_bucket");
		scene.text(140, "1 Kerosene bucket keeps it working for 4 minutes and 10 seconds (stacks if more added)", [0.5, 1.2, 3.5]).placeNearTarget().attachKeyFrame();
		for (let e = 0; e < 28; e++) {
			scene.particles.simple(1, "flame", [-0.2, 1.2, 2.8]).density(1).area([1.2, 2.2, 4.2]);
			for (let i = 0; i < 3; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [0.5, 1, 3.5]).motion([
					0.04 * Math.random() - 0.02,
					0.015 * Math.random() + 0.005,
					0.04 * Math.random() - 0.02
				]);
			}
			scene.idle(5);
		}

		scene.markAsFinished();

		for (let e = 0; e < 972; e++) {
			scene.particles.simple(1, "flame", [-0.2, 1.2, 2.8]).density(1).area([1.2, 2.2, 4.2]);
			for (let i = 0; i < 3; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [0.5, 1, 3.5]).motion([
					0.04 * Math.random() - 0.02,
					0.015 * Math.random() + 0.005,
					0.04 * Math.random() - 0.02
				]);
			}
			scene.idle(5);
		}
	}).scene("firebox_electric_firebox", "Electric Firebox", "kubejs:7x7", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		scene.world.setBlock([0, 1, 3], "crusty_chunks:electric_firebox", false);
		scene.world.setBlock([2, 1, 3], "crusty_chunks:electric_firebox", false);
		scene.world.setBlock([4, 1, 3], "crusty_chunks:electric_firebox", false);
		scene.world.setBlock([6, 1, 3], "crusty_chunks:electric_firebox", false);
		scene.world.setBlock([6, 2, 3], "crusty_chunks:refinery", false);
		scene.world.setBlock([4, 2, 3], "crusty_chunks:thermal_furnace", false);
		scene.world.setBlock([2, 2, 3], "crusty_chunks:blast_furnace", false);
		scene.world.setBlock([0, 2, 3], "crusty_chunks:foundry", false);
		scene.world.setBlock([6, 3, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 3, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 3, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([6, 4, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 4, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 4, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([6, 5, 3], "crusty_chunks:refinery_tower", false);
		scene.world.setBlock([4, 5, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([2, 5, 3], "crusty_chunks:blast_funnel", false);
		scene.world.setBlock([6, 6, 3], "crusty_chunks:refinery_tower", false);
		scene.world.showSection([0, 1, 3, 6, 6, 3], Facing.UP);

		scene.idle(20);

		scene.text(80, "Electric Firebox produces heat from electricity", [0.5, 1.2, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(100);

		scene.world.setBlock([0, 1, 2], "crusty_chunks:energy_node", false);
		scene.world.showSection([0, 1, 2], Facing.SOUTH);

		scene.idle(20);

		scene.text(100, "it must be powered to work", [0.5, 1.4, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.particles.simple(1, "lava", [0, 1, 3]).density(7).scale(0.8).area([1, 2, 4]);

		scene.markAsFinished();

		for (let e = 0; e < 50; e++) {
			scene.particles.simple(1, "lava", [0, 1, 3]).density(7).scale(0.8).area([1, 2, 4]);
			scene.idle(40);
		}
	});
});