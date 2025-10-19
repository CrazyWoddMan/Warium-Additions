Ponder.registry((event) => {
    event.create("crusty_chunks:blast_funnel").scene("blast_funnel", "Blast Funnel", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:blast_funnel", false);
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(60, "Blast Funnels are used to build Alloy Furnace", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		scene.world.hideSection([2, 1, 2], Facing.UP);

		scene.idle(20);

		scene.world.setBlock([2, 1, 2], "crusty_chunks:firebox", false);
		let firebox = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 2, 2], "crusty_chunks:blast_furnace", false);
		let furnace = scene.world.showIndependentSection([2, 2, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlocks([2, 3, 2, 2, 5, 2], "crusty_chunks:blast_funnel", false);
		let funnel1 = scene.world.showIndependentSection([2, 3, 2], Facing.DOWN);

		scene.idle(5);

		let funnel2 = scene.world.showIndependentSection([2, 4, 2], Facing.DOWN);

		scene.idle(5);

		let funnel3 = scene.world.showIndependentSection([2, 5, 2], Facing.DOWN);

		scene.idle(10);

		let funnels = {};
		scene.overlay.showOutline(PonderPalette.BLUE, funnels, [2, 3, 2, 2, 5, 2], 130);

		scene.text(120, "3 Blast Funnels must be ontop of the Alloy Furnace", [2.5, 4, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(60);

		let fireboxout = {};
		scene.overlay.showOutline(PonderPalette.BLUE, fireboxout, [2, 1, 2], 75);
		scene.text(65, "And a Firebox below", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

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

		scene.text(80, "Even if Alloy Furnace is idle", [2.5, 2.2, 2.5]).placeNearTarget().attachKeyFrame();

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
		scene.world.moveSection(funnel1, [2, 0, 0], 5);
		scene.world.moveSection(funnel2, [2, 0, 0], 5);
		scene.world.moveSection(funnel3, [2, 0, 0], 5);

		scene.idle(10);

		scene.world.setBlock([1, 1, 2], "crusty_chunks:oil_firebox", false);
		let firebox2 = scene.world.showIndependentSection([1, 1, 2], Facing.DOWN);
		scene.world.moveSection(firebox2, [1, 0, 0], 0);

		scene.idle(5);

		scene.world.setBlock([1, 2, 2], "crusty_chunks:blast_furnace", false);
		let furnace2 = scene.world.showIndependentSection([1, 2, 2], Facing.DOWN);
		scene.world.moveSection(furnace2, [1, 0, 0], 0);
		
		scene.idle(5);

		scene.world.setBlocks([1, 3, 2, 2, 5, 2], "crusty_chunks:blast_funnel", false);
		let funnel4 = scene.world.showIndependentSection([1, 3, 2], Facing.DOWN);
		scene.world.moveSection(funnel4, [1, 0, 0], 0);

		scene.idle(5);

		let funnel5 = scene.world.showIndependentSection([1, 4, 2], Facing.DOWN);
		scene.world.moveSection(funnel5, [1, 0, 0], 0);

		scene.idle(5);

		let funnel6 = scene.world.showIndependentSection([1, 5, 2], Facing.DOWN);
		scene.world.moveSection(funnel6, [1, 0, 0], 0);

		scene.idle(5);

		scene.world.setBlock([0, 1, 2], "crusty_chunks:electric_firebox", false);
		scene.world.showIndependentSection([0, 1, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([0, 2, 2], "crusty_chunks:blast_furnace", false);
		scene.world.showIndependentSection([0, 2, 2], Facing.DOWN);
		
		scene.idle(5);

		scene.world.setBlocks([0, 3, 2, 2, 5, 2], "crusty_chunks:blast_funnel", false);
		scene.world.showIndependentSection([0, 3, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.showIndependentSection([0, 4, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.showIndependentSection([0, 5, 2], Facing.DOWN);

		scene.idle(10);

		scene.text(60, "Kerosene Firebox", [2.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.text(100, "Or Electric Firebox", [0.5, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(20);
	});
});