Ponder.registry((event) => {
    event.create("crusty_chunks:empty_missile_hardpoint").scene("empty_missile_hardpoint", "Medium Rocket Hardpoint", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:empty_missile_hardpoint", false,);
		let hardpoint = scene.world.showIndependentSection([2, 1, 2], Facing.DOWN);

		scene.idle(15);

		scene.text(80, "Medium Rocket Hardpoint is used for mounting and launching medium-sized rockets and missiles", [2.5, 1.8, 2.5]).placeNearTarget().attachKeyFrame();

		scene.idle(90);

		scene.world.setBlock([2, 3, 2], "crusty_chunks:steel_block", false);
		scene.world.showSection([2, 3, 2], Facing.DOWN);
		
		scene.idle(10);
		
		scene.world.moveSection(hardpoint, [0, 1, 0], 5);

		scene.idle(20);

		scene.addKeyframe();
		scene.showControls(20, [3, 2.8, 2], "right").rightClick().withItem("crusty_chunks:fire_spear_rocket");

		scene.idle(3);

		scene.world.setBlock([2, 1, 2], "crusty_chunks:fire_spear_missile_hardpoint", false);

		scene.idle(20);

		scene.world.setBlock([1, 3, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 3, 2], (curState) => curState.with("facing", "west"), false);
		scene.world.showSection([1, 3, 2], Facing.EAST);

		scene.idle(20);

		scene.text(60, "Redstone signal is needed launch the missile", [2, 3.4, 2.6]).placeNearTarget();

		scene.idle(20);

		scene.world.toggleRedstonePower([1, 3, 2]);
		scene.effects.createRedstoneParticles([1, 3, 2], 0xFF0000, 3);

		scene.idle(5);

		scene.world.setBlock([2, 1, 2], "crusty_chunks:empty_missile_hardpoint", false,);
		scene.world.createEntity("crusty_chunks:fire_spear_rocket_projectile", [0, 0, 0], e => {
			e.setNbt('{Motion: [0.0d, 0.0d, -4.0d], Rotation: [180.0f, 0.0f], Pos: [2.5d, 2.5d, 2.5d]}')
        });

		scene.idle(15);
		
		scene.world.toggleRedstonePower([1, 3, 2]);
	});
});