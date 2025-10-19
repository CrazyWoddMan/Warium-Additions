Ponder.registry((event) => {
    event.create("crusty_chunks:rac_barrel").scene("rac_barrel", "Rotary Autocannon Barrel", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:rac_barrel", false);
		
		scene.idle(10);
		
		let barrel = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(80, "Rotary Autocannon Barrel is required for Rotary Auto Cannon to shoot", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.world.moveSection(barrel, [0, 0, -1], 10);
		
		scene.idle(10);
		
		scene.world.setBlock([2, 1, 3], "crusty_chunks:rotary_auto_cannon", false);
		let cannon = scene.world.showIndependentSection([2, 1, 3], Facing.NORTH);
		scene.world.moveSection(cannon, [0, 0, -1], 0);
		
		scene.idle(10);
		
		scene.showControls(60, [2.5, 2, 2.5], "down").withItem("crusty_chunks:huge_bullet");
		scene.text(60, "Rotary Auto Cannon shoots Small Shells", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.text(100, "Barrel can be extended up to 5 blocks long to increase shooting accuracy and distance", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();
		let ext1 = scene.world.showIndependentSectionImmediately([2, 1, 2]);
		scene.world.moveSection(ext1, [0, 0, -3], 0);
		scene.world.moveSection(ext1, [0, 0, 1], 10);
		
		scene.idle(5);
		
		let ext2 = scene.world.showIndependentSectionImmediately([2, 1, 2]);
		scene.world.moveSection(ext2, [0, 0, -4], 0);
		scene.world.moveSection(ext2, [0, 0, 1], 10);
		
		scene.idle(5);
		
		let ext3 = scene.world.showIndependentSectionImmediately([2, 1, 2]);
		scene.world.moveSection(ext3, [0, 0, -5], 0);
		scene.world.moveSection(ext3, [0, 0, 1], 10);
		
		scene.idle(5);
		
		let ext4 = scene.world.showIndependentSectionImmediately([2, 1, 2]);
		scene.world.moveSection(ext4, [0, 0, -6], 0);
		scene.world.moveSection(ext4, [0, 0, 1], 10);
		
		scene.idle(105);
		
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
		
		scene.text(80, "To insert shells in Rotary Auto Cannon, Autocannon Drum should be placed on top", [2.5, 2.5, 2.5]).placeNearTarget().attachKeyFrame();
		
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
		scene.world.rotateSection(barrel, 0, 0, -2160, 20);
		scene.world.rotateSection(ext1, 0, 0, -2160, 20);
		scene.world.rotateSection(ext2, 0, 0, -2160, 20);
		scene.world.rotateSection(ext3, 0, 0, -2160, 20);
		scene.world.rotateSection(ext4, 0, 0, -2160, 20);
		for (let e = 0; e < 19; e++) {
			for (let i = 0; i < 3; i++) {
				scene.particles.simple(1, "campfire_cosy_smoke", [2.5, 2.5, -4]).motion([
					0.08 * Math.random() - 0.04,
					0.08 * Math.random() - 0.04,
					-0.06 * Math.random() + 0.02
				]);
			}
			scene.world.createEntity("crusty_chunks:huge_bullet_fire", [2.5, 2.5, -3.1], e => {
				e.setDeltaMovement([0, 0.3, -2]);
			});
			scene.idle(1);
		}
		scene.world.toggleRedstonePower([1, 2, 2]);
	});
});