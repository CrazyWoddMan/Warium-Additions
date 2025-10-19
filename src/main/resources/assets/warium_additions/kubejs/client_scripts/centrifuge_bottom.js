Ponder.registry((event) => {
    event.create("crusty_chunks:centrifuge_bottom").scene("centrifuge_bottom", "Centrifuge Bottom", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:centrifuge_bottom", false);
		scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(100, "Centrifuge Bottom outputs Depleted Uranium Dust from the centrifuge", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(120);

		scene.text(60, "To build centrifuge...", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.world.setBlock([2, 2, 2], "crusty_chunks:centrifuge_core", false);
		scene.world.showIndependentSection([2, 2, 2], Facing.DOWN);

		scene.idle(5);

		scene.world.setBlock([2, 3, 2], "crusty_chunks:centrifuge_top", false);
		scene.world.showIndependentSection([2, 3, 2], Facing.DOWN);

		scene.idle(20);

		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 2, 2], 65);
		scene.text(60, "Centrifuge Core is required above", [2.5, 2.2, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 3, 2], 65);
		scene.text(60, "Centrifuge Top above Centifuge Core", [2.5, 3.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.world.setBlock([1, 1, 2], "crusty_chunks:giant_coil", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("axis", "x"), false);
		scene.world.setBlock([3, 1, 2], "crusty_chunks:giant_coil", false);
		scene.world.modifyBlock([3, 1, 2], (curState) => curState.with("axis", "x"), false);
		scene.world.setBlock([2, 1, 1], "crusty_chunks:giant_coil", false);
		scene.world.modifyBlock([2, 1, 1], (curState) => curState.with("axis", "z"), false);
		scene.world.setBlock([2, 1, 3], "crusty_chunks:giant_coil", false);
		scene.world.modifyBlock([2, 1, 3], (curState) => curState.with("axis", "z"), false);
		scene.world.setBlock([1, 3, 2], "crusty_chunks:giant_coil", false);
		scene.world.modifyBlock([1, 3, 2], (curState) => curState.with("axis", "x"), false);
		scene.world.setBlock([3, 3, 2], "crusty_chunks:giant_coil", false);
		scene.world.modifyBlock([3, 3, 2], (curState) => curState.with("axis", "x"), false);
		scene.world.setBlock([2, 3, 1], "crusty_chunks:giant_coil", false);
		scene.world.modifyBlock([2, 3, 1], (curState) => curState.with("axis", "z"), false);
		scene.world.setBlock([2, 3, 3], "crusty_chunks:giant_coil", false);
		scene.world.modifyBlock([2, 3, 3], (curState) => curState.with("axis", "z"), false);
		scene.world.showIndependentSection([1, 1, 2], Facing.EAST);

		scene.idle(3);

		scene.world.showIndependentSection([2, 1, 3], Facing.NORTH);

		scene.idle(3);

		scene.world.showIndependentSection([3, 1, 2], Facing.WEST);

		scene.idle(3);

		let coilbottom = scene.world.showIndependentSection([2, 1, 1], Facing.SOUTH);

		scene.idle(5);

		scene.world.showIndependentSection([1, 3, 2], Facing.EAST);

		scene.idle(3);

		scene.world.showIndependentSection([2, 3, 3], Facing.NORTH);

		scene.idle(3);

		scene.world.showIndependentSection([3, 3, 2], Facing.WEST);

		scene.idle(3);

		let coiltop = scene.world.showIndependentSection([2, 3, 1], Facing.SOUTH);

		scene.idle(10);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [1, 3, 2], 65);
		scene.text(60, "And 8 Giant Coils from the sides", [1.5, 3.5, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);
		
		scene.addKeyframe();
		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 2, 2], 45);
		scene.showControls(40, [2, 2.5, 2], "left").withItem("crusty_chunks:uranium_neutral_dust");

		scene.idle(50);

		scene.text(100, "Centrifuge processes 1 dust for 3 minutes and 20 seconds", [2.5, 2.2, 2.5]).placeNearTarget().attachKeyFrame();

		for (let e = 0; e < 11; e++) {
			for (let i = 0; i < 16; i++) {
				scene.particles.simple(1, "firework", [2.5, 2.5, 2.5]).motion([
					0.4 * Math.random() - 0.2,
					0.2 * Math.random(),
					0.4 * Math.random()- 0.2
				]);
			}
			scene.idle(10);
		}

		scene.world.hideIndependentSection(coiltop, Facing.NORTH);
		scene.world.hideIndependentSection(coilbottom, Facing.NORTH);

		for (let e = 0; e < 2; e++) {
			for (let i = 0; i < 16; i++) {
				scene.particles.simple(1, "firework", [2.5, 2.5, 2.5]).motion([
					0.4 * Math.random() - 0.2,
					0.2 * Math.random(),
					0.4 * Math.random()- 0.2
				]);
			}
			scene.idle(10);
		}

		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 3, 2], 85);
		scene.showControls(80, [2.5, 4, 2.5], "down").withItem("crusty_chunks:uranium_enriched_tiny_dust");
		scene.text(80, "Enriched material will output in Centrifuge Top", [2.5, 3.6, 2.5]).placeNearTarget().attachKeyFrame();

		for (let e = 0; e < 9; e++) {
			for (let i = 0; i < 16; i++) {
				scene.particles.simple(1, "firework", [2.5, 2.5, 2.5]).motion([
					0.4 * Math.random() - 0.2,
					0.2 * Math.random(),
					0.4 * Math.random()- 0.2
				]);
			}
			scene.idle(10);
		}

		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 2], 85);
		scene.showControls(80, [2.9, 0.6, 2.5], "up").withItem("crusty_chunks:uranium_depleted_dust");
		scene.text(100, "And for Uranium, Depleted Uranium Dust in Centrifuge Bottom", [2.8, 1.2, 2.5]).placeNearTarget().attachKeyFrame();

		for (let e = 0; e < 11; e++) {
			for (let i = 0; i < 16; i++) {
				scene.particles.simple(1, "firework", [2.5, 2.5, 2.5]).motion([
					0.4 * Math.random() - 0.2,
					0.2 * Math.random(),
					0.4 * Math.random()- 0.2
				]);
			}
			scene.idle(10);
		}

		scene.world.showIndependentSection([2, 1, 1], Facing.SOUTH);
		scene.world.showIndependentSection([2, 3, 1], Facing.SOUTH);

		for (let e = 0; e < 2; e++) {
			for (let i = 0; i < 16; i++) {
				scene.particles.simple(1, "firework", [2.5, 2.5, 2.5]).motion([
					0.4 * Math.random() - 0.2,
					0.2 * Math.random(),
					0.4 * Math.random()- 0.2
				]);
			}
			scene.idle(10);
		}

		scene.markAsFinished();

		for (let e = 0; e < 365; e++) {
			for (let i = 0; i < 16; i++) {
				scene.particles.simple(1, "firework", [2.5, 2.5, 2.5]).motion([
					0.4 * Math.random() - 0.2,
					0.2 * Math.random(),
					0.4 * Math.random()- 0.2
				]);
			}
			scene.idle(10);
		}
	});
});