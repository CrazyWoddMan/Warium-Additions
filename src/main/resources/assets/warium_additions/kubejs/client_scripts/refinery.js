Ponder.registry((event) => {
    event.create("crusty_chunks:refinery").scene("refinery", "Oil Refinery", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:refinery", false);
		let furnace = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(60, "Oil Refinery turns Shale Oil into various fuels", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.world.setBlock([2, 2, 2], "crusty_chunks:firebox", false);
		let firebox = scene.world.showIndependentSectionImmediately([2, 2, 2]);
		scene.world.moveSection(firebox, [0, -2, 0], 0);
		scene.world.moveSection(furnace, [0, 1, 0], 5);
		scene.world.moveSection(firebox, [0, 1, 0], 5);

		scene.idle(10);

		scene.world.setBlocks([2, 3, 2, 2, 6, 2], "crusty_chunks:refinery_tower", false);
		let tower1 = scene.world.showIndependentSection([2, 3, 2], Facing.DOWN);

		scene.idle(5);

		let tower2 = scene.world.showIndependentSection([2, 4, 2], Facing.DOWN);

		scene.idle(5);

		let tower3 = scene.world.showIndependentSection([2, 5, 2], Facing.DOWN);

		scene.idle(5);

		let tower4 = scene.world.showIndependentSection([2, 6, 2], Facing.DOWN);

		scene.idle(10);

		scene.text(40, "In order to work...", [2.5, 2.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		scene.text(120, "4 Oil Refinery Tower sections must be ontop", [2.5, 4.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		let fireboxout = {};
		scene.overlay.showOutline(PonderPalette.BLUE, fireboxout, [2, 1, 2], 75);
		scene.text(65, "And a Firebox below", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.text(100, "Each Oil Refinery Tower section will produce different type of fuel", [2.5, 4.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(3);

		scene.showControls(100, [3.2, 3.5, 1.8], "right").withItem("crusty_chunks:oil_bucket");

		scene.idle(3);

		scene.showControls(100, [3.2, 4.5, 1.8], "right").withItem("crusty_chunks:diesel_bucket");

		scene.idle(3);

		scene.showControls(100, [3.2, 5.5, 1.8], "right").withItem("crusty_chunks:kerosene_bucket");

		scene.idle(3);

		scene.showControls(100, [3.2, 6.5, 1.8], "right").withItem("crusty_chunks:petrolium_bucket");

		scene.idle(108);

		scene.text(50, "Firebox consumes Coal", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();
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

		scene.text(80, "Even if Oil Refinery is idle", [2.5, 2.2, 2.5]).placeNearTarget().attachKeyFrame();

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
		scene.world.moveSection(tower1, [2, 0, 0], 5);
		scene.world.moveSection(tower2, [2, 0, 0], 5);
		scene.world.moveSection(tower3, [2, 0, 0], 5);
		scene.world.moveSection(tower4, [2, 0, 0], 5);

		scene.idle(10);

		scene.world.setBlock([1, 1, 2], "crusty_chunks:oil_firebox", false);
		let firebox2 = scene.world.showIndependentSection([1, 1, 2], Facing.DOWN);
		scene.world.moveSection(firebox2, [1, 0, 0], 0);

		scene.idle(5);

		scene.world.setBlock([1, 2, 2], "crusty_chunks:refinery", false);
		let furnace2 = scene.world.showIndependentSection([1, 2, 2], Facing.DOWN);
		scene.world.moveSection(furnace2, [1, 0, 0], 0);
		
		scene.idle(5);

		scene.world.setBlocks([1, 3, 2, 2, 6, 2], "crusty_chunks:refinery_tower", false);
		let tower5 = scene.world.showIndependentSection([1, 3, 2], Facing.DOWN);
		scene.world.moveSection(tower5, [1, 0, 0], 0);

		scene.idle(5);

		let tower6 = scene.world.showIndependentSection([1, 4, 2], Facing.DOWN);
		scene.world.moveSection(tower6, [1, 0, 0], 0);

		scene.idle(5);

		let tower7 = scene.world.showIndependentSection([1, 5, 2], Facing.DOWN);
		scene.world.moveSection(tower7, [1, 0, 0], 0);

		scene.idle(5);

		let tower8 = scene.world.showIndependentSection([1, 6, 2], Facing.DOWN);
		scene.world.moveSection(tower8, [1, 0, 0], 0);

		scene.idle(5);

		scene.world.setBlock([0, 1, 2], "crusty_chunks:electric_firebox", false);
		scene.world.showIndependentSection([0, 1, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([0, 2, 2], "crusty_chunks:refinery", false);
		scene.world.showIndependentSection([0, 2, 2], Facing.DOWN);
		
		scene.idle(5);

		scene.world.setBlocks([0, 3, 2, 2, 6, 2], "crusty_chunks:refinery_tower", false);
		scene.world.showIndependentSection([0, 3, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.showIndependentSection([0, 4, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.showIndependentSection([0, 5, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.showIndependentSection([0, 6, 2], Facing.DOWN);

		scene.idle(10);

		scene.text(60, "Kerosene Firebox", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.text(100, "Or Electric Firebox", [0.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);
	});
});