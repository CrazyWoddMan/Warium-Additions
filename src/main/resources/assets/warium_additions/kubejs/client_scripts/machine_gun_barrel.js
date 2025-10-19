Ponder.registry((event) => {
    event.create("crusty_chunks:machine_gun_barrel").scene("machine_gun_barrel", "Machine Gun Barrel", (scene, util) => {
        /**
         * Shows the whole structure.
         * Alternatively, `scene.showBasePlate()` can be used to show the base plate.
         * Useful for animating different parts of the structure.
         */
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:machine_gun_barrel", false);
		
		scene.idle(10);
		
		let barrel = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(80, "Machine Gun Barrel is required for Machine Gun to shoot", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.moveSection(barrel, [0, 0, -1], 10);
		
		scene.idle(10);
		
		scene.world.setBlock([2, 1, 3], "crusty_chunks:machine_gun", false);
		let cannon = scene.world.showIndependentSection([2, 1, 3], Facing.NORTH);
		scene.world.moveSection(cannon, [0, 0, -1], 0);
		
		scene.idle(10);
		
		scene.showControls(60, [2.5, 2, 2.5], "down").withItem("crusty_chunks:large_bullet");
		scene.text(60, "Machine Gun shoots Large Bullets", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(60, [0.5, 2, 3], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change shooting direction", [-0.1, 3.7, 3]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(60, [2.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click with Weapon Aimer in second hand to attach it", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 2, 3], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 3.7, 3]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.showControls(320, [0.5, 2, 3], "down").withItem("crusty_chunks:machine_gun_box");
		scene.text(100, "To insert bullets in Machine Gun, Machine Gun Ammunition Box must be used", [-0.1, 3.7, 3]).placeNearTarget().attachKeyFrame();
		
		scene.idle(120);
		
		scene.showControls(100, [-0.1, 3.125, 3], "left").withItem("crusty_chunks:large_bullet");
		scene.text(100, "Hold Ammunition Box in main hand and Large Bullets in second", [-0.1, 2.5, 5.1]).placeNearTarget().attachKeyFrame();
		
		scene.idle(120);
		
		scene.showControls(80, [-0.1, 3.125, 3], "left").rightClick().withItem("crusty_chunks:large_bullet");
		scene.text(80, "Right click to put bullets in the box", [-0.1, 0.5, 8.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.showControls(40, [2.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:machine_gun_box");
		
		scene.idle(50);
		
		scene.world.setBlock([1, 1, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("face", "floor"), false);
		scene.world.showSection([1, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(85, "Redstone signal is needed to shoot", [1.5, 1.0, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
		scene.effects.createRedstoneParticles([1, 1, 2], 0xFF0000, 3);
		let items = [];
		for (let e = 0; e < 9; e++) {
			for (let i = 0; i < 2; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [2.5, 1.5, -0.5]).motion([
					0.03 * Math.random() - 0.015,
					0.03 * Math.random() - 0.015,
					-0.04 * Math.random() + 0.015
				]);
			}
			scene.world.createEntity("crusty_chunks:genericlarge_bullet", [2.5, 1.4, 0.9], (e) => {
				e.setDeltaMovement([0, 0.3, -2]);
			});
			if (e % 3 === 0) {
				items.push(
					scene.world.createItemEntity([2, 1, 2], [
						-0.2 * Math.random(),
						0.17,
						0.4 * Math.random() - 0.2
					], "crusty_chunks:large_casing")
				);
				scene.world.modifyEntity(items[e / 3], e => {
					e.setItem(Item.of("crusty_chunks:large_casing", 2));
				});
			}
			if (e === 4 || e === 7) {
				scene.world.modifyEntity(items[(e - 4) / 3], (e) => {
					e.setDeltaMovement([0, 0, 0]);
					e.setNoGravity(true);
				});
			}
			scene.idle(2);
		}

		items.push(
			scene.world.createItemEntity([2, 1, 2], [
				-0.2 * Math.random(),
				0.17,
				0.4 * Math.random() - 0.2
			], "crusty_chunks:large_casing")
		);
		scene.world.modifyEntity(items[3], e => {
			e.setItem(Item.of("crusty_chunks:large_casing", 2));
		});

		scene.idle(2);

		scene.world.toggleRedstonePower([1, 1, 2]);
		scene.world.modifyEntity(items[2], (e) => {
			e.setDeltaMovement([0, 0, 0]);
			e.setNoGravity(true);
		});

		scene.idle(6);

		scene.world.modifyEntity(items[3], (e) => {
			e.setDeltaMovement([0, 0, 0]);
			e.setNoGravity(true);
		});
	});
});