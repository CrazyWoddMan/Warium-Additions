Ponder.registry((event) => {
    event.create("crusty_chunks:breeder_reactor_interface").scene("breeder_reactor_interface", "Breeder Reactor Interface", "kubejs:7x7", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([3, 1, 3], "crusty_chunks:breeder_reactor_interface", false);
		let main = scene.world.showIndependentSection([3, 1, 3], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(120, "Breeder Reactor Interface is used to input items in the breeder reactor", [3.5, 1.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(140);

		scene.world.hideIndependentSection(main, Facing.UP);

		scene.idle(20);

		scene.world.setBlock([6, 1, 3], "crusty_chunks:breeder_reactor_port", false);
		let port = scene.world.showIndependentSection([6, 1, 3], Facing.DOWN);

		scene.idle(3);

		scene.world.setBlock([6, 2, 3], "crusty_chunks:breeder_reactor_core", false);
		let core = scene.world.showIndependentSection([6, 2, 3], Facing.DOWN);

		scene.idle(3);

		scene.world.setBlock([6, 3, 3], "crusty_chunks:breeder_reactor_interface", false);
		let top = scene.world.showIndependentSection([6, 3, 3], Facing.DOWN);
		
		scene.idle(3);

		scene.world.setBlocks([4, 1, 3, 4, 2, 3], "crusty_chunks:reaction_chamber", false);
		let chamber1 = scene.world.showIndependentSection([4, 1, 3], Facing.DOWN);

		scene.idle(3);
		let chamber2 = scene.world.showIndependentSection([4, 2, 3], Facing.DOWN);

		scene.idle(3);

		scene.world.setBlock([4, 3, 3], "crusty_chunks:empty_fuel_rods", false);
		let rods = scene.world.showIndependentSection([4, 3, 3], Facing.DOWN);

		scene.idle(3);

		scene.world.setBlocks([2, 1, 3, 2, 2, 3], "crusty_chunks:control_rod", false);
		let rod1 = scene.world.showIndependentSection([2, 1, 3], Facing.DOWN);

		scene.idle(3);

		let rod2 = scene.world.showIndependentSection([2, 2, 3], Facing.DOWN);

		scene.idle(3);

		scene.world.setBlocks([0, 1, 3, 0, 2, 3], "crusty_chunks:reactor_casing", false);
		let casing1= scene.world.showIndependentSection([0, 1, 3], Facing.DOWN);

		scene.idle(3);

		let casing2 = scene.world.showIndependentSection([0, 2, 3], Facing.DOWN);

		scene.idle(10);

		let out = {};
		let out2 = {};
		let out3 = {};
		let out4 = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [6, 1, 3, 6, 3, 3], 85);
		scene.overlay.showOutline(PonderPalette.BLUE, out2, [4, 1, 3, 4, 2, 3], 85);
		scene.overlay.showOutline(PonderPalette.BLUE, out3, [2, 1, 3, 2, 2, 3], 85);
		scene.overlay.showOutline(PonderPalette.BLUE, out4, [0, 1, 3, 0, 2, 3], 85);
		scene.text(80, "Breeder reactor consists of 4 main parts", [0, 2.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(100);

		scene.world.hideIndependentSection(chamber1, Facing.UP);
		scene.world.hideIndependentSection(chamber2, Facing.UP);
		scene.world.hideIndependentSection(rods, Facing.UP);
		scene.world.hideIndependentSection(rod1, Facing.UP);
		scene.world.hideIndependentSection(rod2, Facing.UP);
		scene.world.hideIndependentSection(casing1, Facing.UP);
		scene.world.hideIndependentSection(casing2, Facing.UP);

		scene.idle(20);

		scene.world.moveSection(port, [-3, 0, 0], 10);
		scene.world.moveSection(core, [-3, 0, 0], 10);
		scene.world.moveSection(top, [-3, 0, 0], 10);

		scene.idle(10);

		scene.text(60, "Core part consists of 3 blocks", [3.5, 2.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(80);

		scene.world.moveSection(core, [0, 1, 0], 10);
		scene.world.moveSection(top, [0, 2, 0], 10);

		scene.idle(10);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 5, 3], 85);
		scene.text(80, "Breeder Reactor Interface for input", [3.5, 5.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 3, 3], 65);
		scene.text(60, "Reactor Core", [3.5, 3.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 1, 3], 85);
		scene.text(80, "Breeder Reactor Port for output", [3.5, 1.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.world.moveSection(core, [0, -1, 0], 10);
		scene.world.moveSection(top, [0, -2, 0], 10);

		scene.idle(15);

		scene.world.hideIndependentSection(port, Facing.WEST);
		scene.world.hideIndependentSection(core, Facing.WEST);
		scene.world.hideIndependentSection(top, Facing.WEST);

		scene.idle(15);

		let chamber3 = scene.world.showIndependentSection([4, 1, 3], Facing.WEST);
		let chamber4 = scene.world.showIndependentSection([4, 2, 3], Facing.WEST);
		let empty_rods = scene.world.showIndependentSection([4, 3, 3], Facing.WEST);
		scene.world.moveSection(chamber3, [-1, 0, 0], 0);
		scene.world.moveSection(chamber4, [-1, 0, 0], 0);
		scene.world.moveSection(empty_rods, [-1, 0, 0], 0);

		scene.idle(20);

		scene.text(80, "Chamber part consists of 3 blocks too", [3.5, 2, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 1, 3, 3, 2, 3], 65);
		scene.text(60, "2 Reaction Chambers", [3.5, 2, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.world.moveSection(empty_rods, [0, 0.9, 0], 10);

		scene.idle(20);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 3, 3], 65);
		scene.text(60, "And Empty Fuel Rods", [3.5, 3.5, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.world.moveSection(empty_rods, [0, -0.9, 0], 10);

		scene.idle(20);

		scene.world.hideIndependentSection(chamber3, Facing.WEST);
		scene.world.hideIndependentSection(chamber4, Facing.WEST);
		scene.world.hideIndependentSection(empty_rods, Facing.WEST);

		scene.idle(15);

		let rod3 = scene.world.showIndependentSection([2, 1, 3], Facing.WEST);
		let rod4 = scene.world.showIndependentSection([2, 2, 3], Facing.WEST);
		scene.world.moveSection(rod3, [1, 0, 0], 0);
		scene.world.moveSection(rod4, [1, 0, 0], 0);

		scene.idle(20);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 1, 3, 3, 2, 3], 65);
		scene.text(60, "2 Control Rods", [3.5, 2, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.world.hideIndependentSection(rod3, Facing.WEST);
		scene.world.hideIndependentSection(rod4, Facing.WEST);

		scene.idle(15);

		let casing3 = scene.world.showIndependentSection([0, 1, 3], Facing.WEST);
		let casing4 = scene.world.showIndependentSection([0, 2, 3], Facing.WEST);
		scene.world.moveSection(casing3, [3, 0, 0], 0);
		scene.world.moveSection(casing4, [3, 0, 0], 0);

		scene.idle(20);

		scene.overlay.showOutline(PonderPalette.BLUE, out, [3, 1, 3, 3, 2, 3], 65);
		scene.text(60, "And 2 Reactor Casings", [3.5, 2, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(70);

		scene.world.hideIndependentSection(casing3, Facing.UP);
		scene.world.hideIndependentSection(casing4, Facing.UP);

		scene.idle(20);
		
		scene.addKeyframe();
		let port1 = scene.world.showIndependentSection([6, 1, 3], Facing.DOWN);
		scene.world.moveSection(port1, [-3, 0, 0], 0);

		scene.idle(3);

		let core1 = scene.world.showIndependentSection([6, 2, 3], Facing.DOWN);
		scene.world.moveSection(core1, [-3, 0, 0], 0);

		scene.idle(3);

		let top1 = scene.world.showIndependentSection([6, 3, 3], Facing.DOWN);
		scene.world.moveSection(top1, [-3, 0, 0], 0);

		scene.idle(15);

		let rods1 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.SOUTH);
		scene.world.moveSection(rods1, [1, 1, -1], 0);

		scene.idle(15);

		let casings1 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.EAST);
		scene.world.moveSection(casings1, [2, 1, -1], 0);
		let casings2 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.WEST);
		scene.world.moveSection(casings2, [4, 1, -1], 0);

		scene.idle(20);

		let chambers1 = scene.world.showIndependentSection([4, 1, 3, 4, 2, 3], Facing.SOUTH);
		scene.world.moveSection(chambers1, [-1, 1, -2], 0);

		scene.idle(15);

		let rods2 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.EAST);
		scene.world.moveSection(rods2, [0, 1, -2], 0);
		let rods3 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.WEST);
		scene.world.moveSection(rods3, [2, 1, -2], 0);

		scene.idle(20);

		let rods4 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.SOUTH);
		scene.world.moveSection(rods4, [1, 1, -3], 0);
		let casings3 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.SOUTH);
		scene.world.moveSection(casings3, [2, 1, -3], 0);
		let casings4 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.SOUTH);
		scene.world.moveSection(casings4, [4, 1, -3], 0);

		scene.idle(20);

		scene.rotateCameraY(-35);

		scene.idle(20);

		let rods5 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.NORTH);
		scene.world.moveSection(rods5, [1, 1, 1], 0);
		let casings5 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.NORTH);
		scene.world.moveSection(casings5, [2, 1, 1], 0);
		let casings6 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.NORTH);
		scene.world.moveSection(casings6, [4, 1, 1], 0);
		let chambers2 = scene.world.showIndependentSection([4, 1, 3, 4, 2, 3], Facing.NORTH);
		scene.world.moveSection(chambers2, [-1, 1, 2], 0);
		let rods6 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.NORTH);
		scene.world.moveSection(rods6, [0, 1, 2], 0);
		let rods7 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.NORTH);
		scene.world.moveSection(rods7, [2, 1, 2], 0);
		let rods8 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.NORTH);
		scene.world.moveSection(rods8, [1, 1, 3], 0);
		let casings7 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.NORTH);
		scene.world.moveSection(casings7, [2, 1, 3], 0);
		let casings8 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.NORTH);
		scene.world.moveSection(casings8, [4, 1, 3], 0);

		scene.idle(20);

		let rods9 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.WEST);
		scene.world.moveSection(rods9, [2, 1, 0], 0);
		let chambers3 = scene.world.showIndependentSection([4, 1, 3, 4, 2, 3], Facing.WEST);
		scene.world.moveSection(chambers3, [1, 1, 0], 0);
		let rods10 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.WEST);
		scene.world.moveSection(rods10, [3, 1, 1], 0);
		let rods11 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.WEST);
		scene.world.moveSection(rods11, [3, 1, -1], 0);
		let rods12 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.WEST);
		scene.world.moveSection(rods12, [4, 1, 0], 0);
		let casings9 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.WEST);
		scene.world.moveSection(casings9, [6, 1, -1], 0);
		let casings10 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.WEST);
		scene.world.moveSection(casings10, [6, 1, 1], 0);

		scene.idle(20);

		let rods13 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.EAST);
		scene.world.moveSection(rods13, [0, 1, 0], 0);
		let chambers4 = scene.world.showIndependentSection([4, 1, 3, 4, 2, 3], Facing.EAST);
		scene.world.moveSection(chambers4, [-3, 1, 0], 0);
		let rods14 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.EAST);
		scene.world.moveSection(rods14, [-1, 1, 1], 0);
		let rods15 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.EAST);
		scene.world.moveSection(rods15, [-1, 1, -1], 0);
		let rods16 = scene.world.showIndependentSection([2, 1, 3, 2, 2, 3], Facing.EAST);
		scene.world.moveSection(rods16, [-2, 1, 0], 0);
		let casings11 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.EAST);
		scene.world.moveSection(casings11, [0, 1, -1], 0);
		let casings12 = scene.world.showIndependentSection([0, 1, 3, 0, 2, 3], Facing.EAST);
		scene.world.moveSection(casings12, [0, 1, 1], 0);

		scene.idle(20);

		let empty_rods1 = scene.world.showIndependentSection([4, 3, 3], Facing.DOWN);
		scene.world.moveSection(empty_rods1, [-1, 1, -2], 0);
		let empty_rods2 = scene.world.showIndependentSection([4, 3, 3], Facing.DOWN);
		scene.world.moveSection(empty_rods2, [-1, 1, 2], 0);
		let empty_rods3 = scene.world.showIndependentSection([4, 3, 3], Facing.DOWN);
		scene.world.moveSection(empty_rods3, [1, 1, 0], 0);
		let empty_rods4 = scene.world.showIndependentSection([4, 3, 3], Facing.DOWN);
		scene.world.moveSection(empty_rods4, [-3, 1, 0], 0);
	}).scene("breeder_reactor", "Breeder Reactor", "kubejs:reactor", (scene, util) => {
		scene.showStructure();
		scene.configureBasePlate(3, 3, 1);

		scene.idle(20);

		scene.text(100, "First step to start the reactor, is to warm it up with Uranium Fuel Rods", [3.5, 3, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(110);

		let out = {};
		let out2 = {};
		let out3 = {};
		let out4 = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [1, 2, 3], 135);
		scene.overlay.showOutline(PonderPalette.BLUE, out2, [5, 2, 3], 135);
		scene.overlay.showOutline(PonderPalette.BLUE, out3, [3, 2, 1], 135);
		scene.overlay.showOutline(PonderPalette.BLUE, out4, [3, 2, 5], 135);
		scene.text(120, "Put 1 Uranium Fuel Rod into each Empty Fuel Rods Section", [1.5, 2.7, 4.2]).placeNearTarget().attachKeyFrame();

		scene.idle(10);

		scene.showControls(20, [1.5, 2.9, 4.1], "down").rightClick().withItem("crusty_chunks:fuel_rod");

		scene.idle(5);

		scene.world.setBlock([1, 3, 3], "crusty_chunks:fuel_rods_1", false);

		scene.idle(5);

		scene.world.setBlock([1, 3, 3], "crusty_chunks:empty_fuel_rods", false);

		scene.idle(20);
		
		scene.showControls(20, [3.5, 2.9, 6.1], "down").rightClick().withItem("crusty_chunks:fuel_rod");

		scene.idle(5);

		scene.world.setBlock([3, 3, 5], "crusty_chunks:fuel_rods_1", false);

		scene.idle(5);

		scene.world.setBlock([3, 3, 5], "crusty_chunks:empty_fuel_rods", false);

		scene.idle(20);

		scene.showControls(20, [5.5, 2.9, 4.1], "down").rightClick().withItem("crusty_chunks:fuel_rod");

		scene.idle(5);

		scene.world.setBlock([5, 3, 3], "crusty_chunks:fuel_rods_1", false);

		scene.idle(5);

		scene.world.setBlock([5, 3, 3], "crusty_chunks:empty_fuel_rods", false);

		scene.idle(20);

		scene.showControls(20, [3.5, 2.9, 2.1], "down").rightClick().withItem("crusty_chunks:fuel_rod");

		scene.idle(5);

		scene.world.setBlock([3, 3, 1], "crusty_chunks:fuel_rods_1", false);

		scene.idle(5);

		scene.world.setBlock([3, 3, 1], "crusty_chunks:empty_fuel_rods", false);

		scene.idle(20);

		scene.text(80, "Now that each section has consumed first rod for warmup...", [1.5, 2.7, 4.2]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.text(120, "You can actually fuel them", [1.5, 2.7, 4.2]).placeNearTarget().attachKeyFrame();

		scene.idle(10);

		scene.showControls(20, [1.5, 2.9, 4.1], "down").rightClick().withItem("crusty_chunks:fuel_rod");

		scene.idle(5);

		scene.world.setBlock([1, 3, 3], "crusty_chunks:fuel_rods_1", false);
		scene.particles.simple(60000, "soul_fire_flame", [1, 1, 3]).density(1).area([2, 3.8, 4]);

		scene.idle(25);
		
		scene.showControls(20, [3.5, 2.9, 6.1], "down").rightClick().withItem("crusty_chunks:fuel_rod");

		scene.idle(5);

		scene.world.setBlock([3, 3, 5], "crusty_chunks:fuel_rods_1", false);
		scene.particles.simple(60000, "soul_fire_flame", [3, 1, 5]).density(1).area([4, 3.8, 6]);

		scene.idle(25);

		scene.showControls(20, [5.5, 2.9, 4.1], "down").rightClick().withItem("crusty_chunks:fuel_rod");

		scene.idle(5);

		scene.world.setBlock([5, 3, 3], "crusty_chunks:fuel_rods_1", false);
		scene.particles.simple(60000, "soul_fire_flame", [5, 1, 3]).density(1).area([6, 3.8, 4]);

		scene.idle(25);

		scene.showControls(20, [3.5, 2.9, 2.1], "down").rightClick().withItem("crusty_chunks:fuel_rod");

		scene.idle(5);

		scene.world.setBlock([3, 3, 1], "crusty_chunks:fuel_rods_1", false);
		scene.particles.simple(490, "soul_fire_flame", [3, 1, 1]).density(1).area([4, 3.8, 2]);

		scene.idle(25);

		scene.text(80, "Each section can hold up to 4 rods", [1.5, 2.7, 4.2]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.text(80, "One rod powers the reactor for 50 minutes", [1.5, 2.7, 4.2]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.text(120, "Once all sections have started working, enriched material can be inputted", [3.5, 2.8, 3.8]).placeNearTarget().attachKeyFrame();

		scene.idle(20);

		scene.showControls(100, [3.5, 3, 3.5], "down").withItem("crusty_chunks:uranium_enriched_dust");

		scene.idle(20);

		scene.particles.simple(8000, "soul_fire_flame", [3, 1, 3]).density(1).area([4, 3.8, 4]);

		scene.idle(140);

		scene.text(100, "It takes 6 minutes and 40 seconds to process 1 item", [3.5, 2.8, 3.8]).placeNearTarget().attachKeyFrame();

		scene.idle(120);

		let part = scene.world.makeSectionIndependent([2, 1, 0, 4, 3, 2]);
		scene.world.hideIndependentSection(part, Facing.NORTH);

		scene.idle(20);

		scene.text(120, "Output will appear in Breeder Reactor Port", [3.5, 0, 3.5]).placeNearTarget().attachKeyFrame();

		scene.idle(140);

		scene.world.showIndependentSection([2, 1, 0, 4, 3, 2], Facing.SOUTH);

		scene.idle(10);

		scene.particles.simple(59325, "soul_fire_flame", [3, 1, 1]).density(1).area([4, 3.8, 2]);
	});
});