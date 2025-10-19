Ponder.registry((event) => {
    event.create("crusty_chunks:aimer_node").scene("aimer_node", "Aimer Node", "kubejs:aimer", (scene, util) => {
        scene.showBasePlate();
		scene.scaleSceneView(0.5);
		scene.setSceneOffsetY(-2.0);
		scene.idle(5);
		
		scene.world.showIndependentSection([2, 1, 12], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([2, 2, 12], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([2, 3, 12], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([5, 1, 11], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([5, 1, 12], Facing.NORTH);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([5, 1, 13], Facing.NORTH);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([9, 1, 12], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([12, 1, 12], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([12, 1, 9], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([11, 1, 9], Facing.EAST);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([12, 1, 6], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([10, 1, 3], Facing.DOWN);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([11, 1, 3], Facing.WEST);
		
		scene.idle(3);
		
		scene.world.showIndependentSection([12, 1, 3], Facing.WEST);
		
		scene.idle(10);
		
		scene.world.showIndependentSection([7, 1, 7], Facing.DOWN);
		
		scene.idle(10);
		
		let zone = {};
		scene.overlay.showOutline(PonderPalette.BLUE, zone, [1, -3, 1, 13, 3, 13], 265);
		let node = {};
		scene.overlay.showOutline(PonderPalette.FAST, node, [7, 1, 7], 265);
		scene.text(100, "Aimer Node creates 13x7x13 zone around it", [7.5, 1.5, 7.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(120);
		
		let rocket = {};
		scene.overlay.showOutline(PonderPalette.GREEN, rocket, [2, 1, 12, 2, 3, 12], 145);
		let rocket2 = {};
		scene.overlay.showOutline(PonderPalette.GREEN, rocket2, [5, 1, 11, 5, 1, 13], 145);
		let dispenser = {};
		scene.overlay.showOutline(PonderPalette.GREEN, dispenser, [9, 1, 12], 145);
		let mortar = {};
		scene.overlay.showOutline(PonderPalette.GREEN, mortar, [12, 1, 12], 145);
		let cannon = {};
		scene.overlay.showOutline(PonderPalette.GREEN, cannon, [11, 1, 9, 12, 1, 9], 145);
		let smoke = {};
		scene.overlay.showOutline(PonderPalette.GREEN, smoke, [12, 1, 6], 145);
		let pod = {};
		scene.overlay.showOutline(PonderPalette.GREEN, pod, [10, 1, 3, 12, 1, 3], 145);
		scene.overlay.showLine(PonderPalette.GREEN, [2.3, 2.5, 12.5], [7.3, 1.5, 7.1], 145);
		scene.overlay.showLine(PonderPalette.GREEN, [5.3, 1.5, 11.5], [7.5, 1.5, 7.5], 145);
		scene.overlay.showLine(PonderPalette.GREEN, [9.2, 2, 11.5], [7.5, 1.5, 7.5], 145);
		scene.overlay.showLine(PonderPalette.GREEN, [12.2, 1.8, 12.1], [7.5, 1.5, 7.5], 145);
		scene.overlay.showLine(PonderPalette.GREEN, [12.5, 1.5, 9.5], [7.5, 1.5, 7.5], 145);
		scene.overlay.showLine(PonderPalette.GREEN, [12.5, 1.1, 6.5], [7.5, 1.5, 7.5], 145);
		scene.overlay.showLine(PonderPalette.GREEN, [12.5, 1.5, 3.5], [7.5, 1.5, 7.5], 145);
		scene.text(140, "Any stationary weapon from Warium in this zone will automatically connect to Aimer Node", [7.5, 1.5, 7.5]).attachKeyFrame();
		
		scene.idle(160);
		
		scene.text(120, "While holding Weapon Aimer in second hand, right click Aimer Node to connect it", [7.5, 1.5, 7.5]).placeNearTarget().attachKeyFrame();
		scene.showControls(120, [7.5, 2, 7.5], "down").rightClick().withItem("crusty_chunks:aimer");
		
		scene.idle(130);
		
		scene.text(120, "You can now change pitch and yaw values of all weapons in the area at the same time", [2.5, 3.2, 4.5]).placeNearTarget().attachKeyFrame();
		scene.showControls(260, [2.5, 1.5, 2.5], "down").withItem("crusty_chunks:aimer");
		
		scene.idle(140);
		
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.2d, -1.5d], Pos: [2.0d, 3.0d, 11.99d]}')
        });		
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.2d, -1.5d], Pos: [5.5d, 1.5d, 10.99d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 1.5d, 0.0d], Pos: [12.2d, 2.1d, 12.5d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 0.0d, 0.0d], Pos: [11.5d, 1.5d, 9.5d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 0.0d, 0.0d], Pos: [9.99d, 1.5d, 3.5d]}')
        });
		scene.text(120, "And while you changing them, shooting trajectory is displayed", [2.5, 3.2, 4.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(40);
		
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.2d, -1.5d], Pos: [2.0d, 3.0d, 11.99d]}')
        });		
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.2d, -1.5d], Pos: [5.5d, 1.5d, 10.99d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 1.5d, 0.0d], Pos: [12.2d, 2.1d, 12.5d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 0.0d, 0.0d], Pos: [11.5d, 1.5d, 9.5d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 0.0d, 0.0d], Pos: [9.99d, 1.5d, 3.5d]}')
        });
		
		scene.idle(40);
		
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.2d, -1.5d], Pos: [2.0d, 3.0d, 11.99d]}')
        });		
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.2d, -1.5d], Pos: [5.5d, 1.5d, 10.99d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 1.5d, 0.0d], Pos: [12.2d, 2.1d, 12.5d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 0.0d, 0.0d], Pos: [11.5d, 1.5d, 9.5d]}')
        });
		scene.world.createEntity("crusty_chunks:aimer_beam", [0, 0, 0], e => {
			e.setNbt('{Motion: [-1.0d, 0.0d, 0.0d], Pos: [9.99d, 1.5d, 3.5d]}')
        });
		
		scene.idle(60);
		
		scene.overlay.showOutline(PonderPalette.BLUE, dispenser, [9, 1, 12], 125);
		scene.overlay.showOutline(PonderPalette.BLUE, smoke, [12, 1, 6], 125);
		scene.text(120, "For Countermeasure Launcher and Smoke Launcher trajectory is not displaying for some reason", [9.5, 1.5, 12.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(140);
		
		scene.world.showIndependentSection([1, 1, 12], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showIndependentSection([1, 2, 12], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showIndependentSection([4, 1, 12], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showIndependentSection([8, 1, 12], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showIndependentSection([12, 1, 8], Facing.DOWN);
		
		scene.idle(5);
		
		scene.world.showIndependentSectionImmediately([12, 1, 5]);
		
		scene.idle(10);
		
		let trigger = scene.world.showIndependentSection([10, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		let out = {};
		scene.overlay.showOutline(PonderPalette.BLUE, out, [10, 1, 2], 285);
		scene.text(160, "Node Trigger can shoot or activate any weaponary from Warium if located in the Aimer Node coverage area and placed next to the object", [10.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		scene.overlay.showOutline(PonderPalette.BLUE, zone, [1, -3, 1, 13, 3, 13], 165);
		scene.overlay.showOutline(PonderPalette.FAST, node, [7, 1, 7], 165);
		
		scene.idle(180);
		
		scene.overlay.showOutline(PonderPalette.GREEN, pod, [10, 1, 3], 105);
		scene.text(100, "For Rocket Pod it must be placed next to the Armor Peeler Rocket Pod", [10.7, 1.8, 3.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(120);
		
		scene.world.moveSection(trigger, [1, 0, 0], 10);
		
		scene.idle(10);
		
		scene.overlay.showOutline(PonderPalette.RED, out, [11, 1, 2], 105);
		scene.overlay.showOutline(PonderPalette.RED, pod, [11, 1, 3], 105);
		scene.text(100, "If Trigger placed next to the Rocket Pod Chamber it won't work", [11.7, 1.8, 3.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(120);
		
		scene.world.moveSection(trigger, [-1, 0, 0], 10);
		
		scene.idle(20);
		
		scene.overlay.showOutline(PonderPalette.GREEN, pod, [2, 2, 12], 105);
		scene.overlay.showOutline(PonderPalette.BLUE, rocket, [1, 2, 12], 105);
		scene.text(100, "Same logic goes for the rockets, but with Ordinance Core", [2.5, 2.2, 12.5]).placeNearTarget().attachKeyFrame();
		scene.overlay.showOutline(PonderPalette.GREEN, mortar, [5, 1, 12], 105);
		scene.overlay.showOutline(PonderPalette.BLUE, rocket2, [4, 1, 12], 105);
		
		scene.idle(115);
		
		scene.addKeyframe();
		scene.showControls(40, [2.5, 1.5, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		
		scene.idle(20);
		
		scene.world.setBlocks([2, 1, 12, 2, 3, 12], "minecraft:air", false);
		scene.world.setBlocks([5, 1, 11, 5, 1, 13], "minecraft:air", false);
		scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 3.0d, -1.0d], Rotation: [0.0f, 90.0f], Pos: [2.5d, 4.0d, 12.5d]}')
        });
		scene.world.createEntity("crusty_chunks:large_rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.3d, -3.0d], Rotation: [180.0f, 0.0f], Pos: [5.5d, 1.5d, 11.0d]}')
        });
		scene.world.createEntity("crusty_chunks:flare_projectile", [9.5, 3, 12.5], e => {
			e.setDeltaMovement([0, 3, 0]);
        });
		scene.world.createEntity("crusty_chunks:rocket", [0, 0, 0], e => {
			e.setNbt('{Motion: [-5.0d, 0.0d, 0.0d], Rotation: [-90.0f, 0.0f], Pos: [9.9d, 1.5d, 3.6d]}')
        });
		scene.world.createEntity("crusty_chunks:smoke_mortar_projectile", [0, 0, 0], e => {
			e.setNbt('{crit: 1b, Motion: [-2.0d, 1.0d, 0.0d], Pos: [12.1d, 2.1d, 6.5d]}')
        });
		let items = [];
		for (let e = 0; e < 9; e++) {
			for (let i = 0; i < 2; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [10, 2.4, 9.5]).motion([
					0.03 * Math.random() - 0.015,
					0.03 * Math.random() - 0.015,
					-0.04 * Math.random() + 0.015
				]);
			}
			scene.world.createEntity("crusty_chunks:genericlarge_bullet", [10.5, 2.4, 9.5], e => {
				e.setDeltaMovement([-4, 0, 0]);
			});
			if (e % 3 === 0) {
				items.push(
					scene.world.createItemEntity([12, 2, 10], [
						0.3 * Math.random() - 0.15,
						0.05,
						0.15 * Math.random()
					], "crusty_chunks:large_casing")
				);
				scene.world.modifyEntity(items[e / 3], e => {
					e.setItem(Item.of("crusty_chunks:large_casing", 2));
				});
			}
			if (e === 4 || e === 7) {
				scene.world.modifyEntity(items[(e - 4) / 3], e => {
					e.setDeltaMovement([0, 0, 0]);
					e.setNoGravity(true);
				});
			}
			scene.idle(2);
		}

		items.push(
			scene.world.createItemEntity([12, 2, 10], [
				0.3 * Math.random() - 0.15,
				0.05,
				0.15 * Math.random()
			], "crusty_chunks:large_casing")
		);
		scene.world.modifyEntity(items[3], e => {
			e.setItem(Item.of("crusty_chunks:large_casing", 2));
		});

		scene.idle(2);

		scene.world.toggleRedstonePower([1, 1, 2]);
		scene.world.modifyEntity(items[2], e => {
			e.setDeltaMovement([0, 0, 0]);
			e.setNoGravity(true);
		});

		scene.idle(6);

		scene.world.modifyEntity(items[3], e => {
			e.setDeltaMovement([0, 0, 0]);
			e.setNoGravity(true);
		});
	});
});