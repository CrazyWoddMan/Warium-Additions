Ponder.registry((event) => {
    event.create("crusty_chunks:foundry").scene("foundry", "Foundry", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:foundry", false);
		let furnace = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(60, "Foundry casts materials into certain components", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.world.setBlock([2, 2, 2], "crusty_chunks:firebox", false);
		let firebox = scene.world.showIndependentSectionImmediately([2, 2, 2]);
		scene.world.moveSection(firebox, [0, -2, 0], 0);
		scene.world.moveSection(furnace, [0, 1, 0], 10);
		scene.world.moveSection(firebox, [0, 1, 0], 10);

		scene.idle(20);

		let fireboxout = {};
		scene.overlay.showOutline(PonderPalette.BLUE, fireboxout, [2, 1, 2], 65);
		scene.text(60, "Firebox must be below in order to work", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.text(50, "It consumes Coal", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();
		scene.showControls(50, [3, 1.5, 2], "right").withItem("coal");

		scene.idle(60);

		scene.text(50, "Or Charcoal", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();
		scene.showControls(50, [3, 1.5, 2], "right").withItem("charcoal");

		scene.idle(70);

		scene.text(80, "Firebox starts working as soon as there is fuel in it", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();
		for (let e = 0; e < 2; e++) {
			scene.particles.simple(1, "flame", [1, 1, 1]).density(7).area([4, 3, 4]);
			for (let i = 0; i < 6; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [0.5, 1.5, 0.5]).motion([
					0.01 * Math.random() - 0.005,
					0.01 * Math.random() - 0.005,
					0.01 * Math.random() - 0.005
				]).density(1).area([4.5, 3.5, 4.5]);
			}
			scene.idle(40);
		}

		scene.particles.simple(1, "flame", [1, 1, 1]).density(8).area([4, 3, 4]);
		for (let i = 0; i < 6; i++) {
			scene.particles.simple(1, "campfire_cosy_smoke", [0.5, 1.5, 0.5]).motion([
				0.01 * Math.random() - 0.005,
				0.01 * Math.random() - 0.005,
				0.01 * Math.random() - 0.005
			]).density(1).area([4.5, 3.5, 4.5]);
		}

		scene.idle(20);

		scene.text(80, "1 Coal is consumed every 2 seconds", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.particles.simple(1, "flame", [1, 1, 1]).density(8).area([4, 3, 4]);
		for (let i = 0; i < 6; i++) {
			scene.particles.simple(1, "campfire_cosy_smoke", [0.5, 1.5, 0.5]).motion([
				0.01 * Math.random() - 0.005,
				0.01 * Math.random() - 0.005,
				0.01 * Math.random() - 0.005
			]).density(1).area([4.5, 3.5, 4.5]);
		}

		scene.idle(40);

		for (let e = 0; e < 6; e++) {
			scene.particles.simple(1, "campfire_cosy_smoke", [0.5, 1.5, 0.5]).motion([
				0.01 * Math.random() - 0.005,
				0.01 * Math.random() - 0.005,
				0.01 * Math.random() - 0.005
			]).density(1).area([4.5, 3.5, 4.5]);
		}

		scene.idle(40);

		scene.text(80, "Even if Foundry is idle", [2.5, 2.2, 2.5]).placeNearTarget().attachKeyFrame();

		for (let e = 0; e < 2; e++) {
			scene.particles.simple(1, "flame", [1, 1, 1]).density(8).area([4, 3, 4]);
			for (let i = 0; i < 6; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [0.5, 1.5, 0.5]).motion([
					0.01 * Math.random() - 0.005,
					0.01 * Math.random() - 0.005,
					0.01 * Math.random() - 0.005
				]).density(1).area([4.5, 3.5, 4.5]);
			}
			scene.idle(40);
		}

		scene.idle(10);

		scene.text(100, "There are also more efficient alternatives to Firebox...", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		scene.world.moveSection(firebox, [2, 0, 0], 5);
		scene.world.moveSection(furnace, [2, 0, 0], 5);

		scene.idle(10);

		scene.world.setBlock([1, 1, 2], "crusty_chunks:oil_firebox", false);
		let firebox2 = scene.world.showIndependentSection([1, 1, 2], Facing.DOWN);
		scene.world.moveSection(firebox2, [1, 0, 0], 0);

		scene.idle(5);

		scene.world.setBlock([1, 2, 2], "crusty_chunks:foundry", false);
		let furnace2 = scene.world.showIndependentSection([1, 2, 2], Facing.DOWN);
		scene.world.moveSection(furnace2, [1, 0, 0], 0);
		
		scene.idle(5);

		scene.world.setBlock([0, 1, 2], "crusty_chunks:electric_firebox", false);
		scene.world.showIndependentSection([0, 1, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([0, 2, 2], "crusty_chunks:foundry", false);
		scene.world.showIndependentSection([0, 2, 2], Facing.DOWN);

		scene.idle(10);

		scene.text(60, "Kerosene Firebox", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.text(100, "Or Electric Firebox", [0.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);
	});
});