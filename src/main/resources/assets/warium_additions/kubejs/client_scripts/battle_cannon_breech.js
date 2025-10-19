Ponder.registry((event) => {
    event.create("crusty_chunks:battle_cannon_breech").scene("battle_cannon_breech", "Battle Cannon", (scene, util) => {
        scene.showBasePlate();
		scene.scaleSceneView(0.8);
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:battle_cannon_breech", false);
		
		scene.idle(10);
		
		let cannon = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.showControls(60, [2.5, 2, 2.5], "down").withItem("crusty_chunks:solid_shell");
		scene.text(60, "Battle Cannon shoots different types of Large Shells", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.setBlock([2, 1, 1], "crusty_chunks:battle_cannon_barrel", false);
		let barrel = scene.world.showIndependentSection([2, 1, 1], Facing.SOUTH);
		
		scene.idle(10);
		
		scene.text(60, "Battle Cannon Barrel is required to shoot", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.text(100, "Barrel can be extended up to 11 blocks long to increase shooting accuracy and distance", [2.5, 1.5, 1.5]).placeNearTarget().attachKeyFrame();
		let ext1 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext1, [0, 0, -2], 0);
		scene.world.moveSection(ext1, [0, 0, 1], 5);
		scene.rotateCameraY(-20);
		
		scene.idle(3);
		
		let ext2 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext2, [0, 0, -3], 0);
		scene.world.moveSection(ext2, [0, 0, 1], 5);
		
		scene.idle(3);
		
		let ext3 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext3, [0, 0, -4], 0);
		scene.world.moveSection(ext3, [0, 0, 1], 5);
		
		scene.idle(3);
		
		let ext4 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext4, [0, 0, -5], 0);
		scene.world.moveSection(ext4, [0, 0, 1], 5);
		
		scene.idle(3);
		
		let ext5 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext5, [0, 0, -6], 0);
		scene.world.moveSection(ext5, [0, 0, 1], 5);
		
		scene.idle(3);
		
		let ext6 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext6, [0, 0, -7], 0);
		scene.world.moveSection(ext6, [0, 0, 1], 5);
		
		scene.idle(3);
		
		let ext7 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext7, [0, 0, -8], 0);
		scene.world.moveSection(ext7, [0, 0, 1], 5);
		
		scene.idle(3);
		
		let ext8 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext8, [0, 0, -9], 0);
		scene.world.moveSection(ext8, [0, 0, 1], 5);
		
		scene.idle(3);
		
		let ext9 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext9, [0, 0, -10], 0);
		scene.world.moveSection(ext9, [0, 0, 1], 5);
		
		scene.idle(3);
		
		let ext10 = scene.world.showIndependentSectionImmediately([2, 1, 1]);
		scene.world.moveSection(ext10, [0, 0, -11], 0);
		scene.world.moveSection(ext10, [0, 0, 1], 5);
		
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
		
		scene.showControls(60, [2.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:solid_shell");
		scene.text(60, "Right-click to the insert shell", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.setBlock([2, 0, 2], "crusty_chunks:autoloader", false);
		let drum = scene.world.showIndependentSection([2, 0, 2], Facing.UP);
		scene.world.moveSection(drum, [0, 1, 0], 5);
		scene.world.moveSection(cannon, [0, 1, 0], 5);
		scene.world.moveSection(barrel, [0, 1, 0], 5);
		scene.world.moveSection(ext1, [0, 1, 0], 5);
		scene.world.moveSection(ext2, [0, 1, 0], 5);
		scene.world.moveSection(ext3, [0, 1, 0], 5);
		scene.world.moveSection(ext4, [0, 1, 0], 5);
		scene.world.moveSection(ext5, [0, 1, 0], 5);
		scene.world.moveSection(ext6, [0, 1, 0], 5);
		scene.world.moveSection(ext7, [0, 1, 0], 5);
		scene.world.moveSection(ext8, [0, 1, 0], 5);
		scene.world.moveSection(ext9, [0, 1, 0], 5);
		scene.world.moveSection(ext10, [0, 1, 0], 5);
		
		scene.idle(10);
		
		scene.text(80, "Alternatively, Battle Cannon Autoloader can be placed below Battle Cannon", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.setBlock([2, 1, 3], "minecraft:hopper", false);
		scene.world.modifyBlock([2, 1, 3], (curState) => curState.with("facing", "north"), false);
		let hopper = scene.world.showIndependentSection([2, 1, 3], Facing.NORTH);
		
		scene.idle(10);
		
		scene.text(80, "Hoppers or any other modded item suppliers can be connected to it", [2.5, 1.5, 3.8]).placeNearTarget().attachKeyFrame();
		
		scene.idle(90);
		
		scene.world.hideIndependentSection(hopper, Facing.SOUTH);	
		
		scene.idle(10);
		
		scene.world.setBlock([1, 2, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 2, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([1, 2, 2], Facing.EAST);
		
		scene.idle(20);
		
		scene.text(85, "Redstone signal is needed to shoot", [2, 2.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.toggleRedstonePower([1, 2, 2]);
		scene.effects.createRedstoneParticles([1, 2, 2], 0xFF0000, 3);
		for (let i = 0; i < 20; i++) {
			scene.particles.simple(1, "crusty_chunks:camp_smoke", [2.5, 2.5, -10]).motion([
				0.1 * Math.random() - 0.05,
				0.1 * Math.random() - 0.05,
				-0.08 * Math.random() + 0.03
			]).scale(1.7);
		}
		scene.particles.simple(5, "explosion", [1, 1, -9]).density(3).scale(0.5).area([4, 4, -11]);
		scene.world.createEntity("crusty_chunks:large_solid_projectile", [2.5, 2.5, -10], e => {
			e.setDeltaMovement([0, 0.3, -2]);
        });
		
		scene.idle(20);
		
		scene.world.toggleRedstonePower([1, 2, 2]);
	});
});