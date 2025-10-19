Ponder.registry((event) => {
    event.create("crusty_chunks:light_autocannon").scene("light_autocannon", "Light Auto Cannon", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:light_autocannon", false);
		
		scene.idle(10);
		
		let cannon = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.showControls(60, [2.5, 2, 2.5], "down").withItem("crusty_chunks:huge_bullet");
		scene.text(60, "Light Auto Cannon shoots Small Shells", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.setBlock([2, 1, 1], "crusty_chunks:autocannon_barrel", false);
		let barrel = scene.world.showIndependentSection([2, 1, 1], Facing.SOUTH);
		
		scene.idle(10);
		
		scene.text(60, "Autocannon Barrel is required to shoot", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.text(100, "Barrel can be extended up to 6 blocks long to increase shooting accuracy and distance", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();
		let ext1 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext1, [0, 0, -2], 0);
		scene.world.moveSection(ext1, [0, 0, 1], 10);
		
		scene.idle(5);
		
		let ext2 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext2, [0, 0, -3], 0);
		scene.world.moveSection(ext2, [0, 0, 1], 10);
		
		scene.idle(5);
		
		let ext3 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext3, [0, 0, -4], 0);
		scene.world.moveSection(ext3, [0, 0, 1], 10);
		
		scene.idle(5);
		
		let ext4 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext4, [0, 0, -5], 0);
		scene.world.moveSection(ext4, [0, 0, 1], 10);
		
		scene.idle(5);
		
		let ext5 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext5, [0, 0, -6], 0);
		scene.world.moveSection(ext5, [0, 0, 1], 10);
		
		scene.idle(100);
		
		scene.showControls(60, [0.5, 2, 3], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change shooting direction", [-0.1, 3.7, 3]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(60, [2.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click with Weapon Aimer in second hand to attach it", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 2, 3], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 3.7, 3]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.setBlock([2, 2, 2], "crusty_chunks:autocannon_drum", false);
		scene.world.showSection([2, 2, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.text(80, "To insert shells in Light Auto Cannon, Autocannon Drum should be placed on top", [2.5, 2.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.hideSection([2, 2, 2], Facing.UP);
		scene.idle(20);
		scene.world.setBlock([2, 0, 2], "crusty_chunks:autocannon_drum", false);
		let drum = scene.world.showIndependentSection([2, 0, 2], Facing.UP);
		scene.world.moveSection(drum, [0, 1, 0], 5);
		scene.world.moveSection(cannon, [0, 1, 0], 5);
		scene.world.moveSection(barrel, [0, 1, 0], 5);
		scene.world.moveSection(ext1, [0, 1, 0], 5);
		scene.world.moveSection(ext2, [0, 1, 0], 5);
		scene.world.moveSection(ext3, [0, 1, 0], 5);
		scene.world.moveSection(ext4, [0, 1, 0], 5);
		scene.world.moveSection(ext5, [0, 1, 0], 5);
		
		scene.idle(10);
		
		scene.text(40, "Or below", [2.5, 1.5, 2.5]).placeNearTarget();
		
		scene.idle(60);
		
		scene.addKeyframe();
		scene.showControls(30, [2.0, 1.5, 2.5], "left").rightClick().withItem("crusty_chunks:huge_bullet");
		
		scene.idle(40);
		
		scene.world.setBlock([1, 2, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 2, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([1, 2, 2], Facing.EAST);
		
		scene.idle(20);
		
		scene.text(85, "Redstone signal is needed to shoot", [2, 2.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.toggleRedstonePower([1, 2, 2]);
		scene.effects.createRedstoneParticles([1, 2, 2], 0xFF0000, 3);
		let items = [];
		for (let e = 0; e < 6; e++) {
			for (let i = 0; i < 4; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [2.5, 2.5, -5]).motion([
					0.1 * Math.random() - 0.05,
					0.1 * Math.random() - 0.05,
					-0.08 * Math.random() + 0.03
				]);
			}
			scene.world.createEntity("crusty_chunks:huge_bullet_fire", [2.5, 2.5, -4.1], e => {
				e.setDeltaMovement([0, 0.3, -2]);
			})
			if (e % 2 === 0) {
				items.push(
					scene.world.createItemEntity([2.5, 2.5, 3.5], [
						0.2 * Math.random() - 0.1,
						0.13,
						0.1 * Math.random()
					], "crusty_chunks:huge_casing")
				);
				scene.world.modifyEntity(items[e / 2], e => {
					e.setItem(Item.of("crusty_chunks:huge_casing", 2));
				});
			}
			if (e === 4) {
				scene.world.modifyEntity(items[0], e => {
					e.setDeltaMovement([0, 0, 0]);
					e.setNoGravity(true);
				});
			}
			scene.idle(3);
		}

		for (let i = 0; i < 4; i++) {
			scene.particles.simple(1, "campfire_cosy_smoke", [2.5, 2.5, -5]).motion([
				0.1 * Math.random() - 0.05,
				0.1 * Math.random() - 0.05,
				-0.08 * Math.random() + 0.03
			]);
		}
		scene.world.createEntity("crusty_chunks:huge_bullet_fire", [2.5, 2.5, -4.1], e => {
			e.setDeltaMovement([0, 0.3, -2]);
		})
		items.push(
			scene.world.createItemEntity([2.5, 2.5, 3.5], [
				0.2 * Math.random() - 0.1,
				0.13,
				0.1 * Math.random()
			], "crusty_chunks:huge_casing")
		);
		scene.world.modifyEntity(items[1], e => {
			e.setDeltaMovement([0, 0, 0]);
			e.setNoGravity(true);
		});

		scene.idle(2);

		scene.world.toggleRedstonePower([1, 2, 2]);

		scene.idle(4);

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