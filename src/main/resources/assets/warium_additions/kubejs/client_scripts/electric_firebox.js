Ponder.registry((event) => {
    event.create("crusty_chunks:electric_firebox").scene("electric_firebox", "Kerosene Firebox", "kubejs:7x7", (scene, util) => {
        scene.showBasePlate();
		scene.setSceneOffsetY(-1);
		
		scene.world.setBlock([3, 1, 3], "crusty_chunks:electric_firebox", false);
		scene.world.showSection([3, 1, 3], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(80, "Electric Firebox produces heat from electricity", [3.5, 1.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.world.hideSection([3, 1, 3], Facing.UP);

		scene.idle(20);

		scene.world.setBlock([0, 1, 3], "crusty_chunks:electric_firebox", false);
		scene.world.setBlock([2, 1, 3], "crusty_chunks:electric_firebox", false);
		scene.world.setBlock([4, 1, 3], "crusty_chunks:electric_firebox", false);
		scene.world.setBlock([6, 1, 3], "crusty_chunks:electric_firebox", false);
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
		scene.text(60, "Electric Firebox can be used for Foundry", [0.5, 2.2, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [2, 1, 3, 2, 5, 3], 45);
		scene.text(40, "Alloy Furnace", [2.5, 2.5, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(50);
		
		scene.overlay.showOutline(PonderPalette.BLUE, out, [4, 1, 3, 4, 5, 3], 45);
		scene.text(40, "Thermal Furnace", [4.5, 2.5, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(50);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [5, 1, 2, 7, 6, 4], 55);
		scene.text(50, "And Oil Refinery", [6.5, 3.5, 3],).placeNearTarget().attachKeyFrame();

		scene.idle(70);

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