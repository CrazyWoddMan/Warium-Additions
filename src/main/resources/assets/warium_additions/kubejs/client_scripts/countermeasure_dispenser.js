Ponder.registry((event) => {
    event.create("crusty_chunks:countermeasure_dispenser").scene("countermeasure_dispenser", "How to use Countermeasure Launcher", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:countermeasure_dispenser", false);
		scene.world.modifyBlock([2, 1, 2], (curState) => curState.with("facing", "up"), false);
		scene.world.setBlock([1, 1, 2], "minecraft:stone_button", false);
		scene.world.modifyBlock([1, 1, 2], (curState) => curState.with("face", "floor"), false);
		
		scene.idle(10);
		
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(80, "Countermeasure Launcher can launch and store up to 9 flares at a time", [2.5, 2.0, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.text(80, "Meanwhile, flares can be used as a countermeasure to IR missles", [2.5, 2.0, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.showControls(60, [0.5, 1, 0.5], "down").withItem("crusty_chunks:aimer");
		scene.text(60, "Weapon Aimer can be used to change shooting direction", [-0.1, 2.7, 0.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(60, [2.5, 2.0, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click with Weapon Aimer in second hand to attach", [2.5, 2.0, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(80, [0.5, 2, 2.5], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 3.7, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.addKeyframe();
		scene.showControls(20, [2.5, 2.0, 2.5], "down").rightClick().withItem("crusty_chunks:flare_charge");
		
		scene.idle(30);
		
		scene.world.showSection([1, 1, 2], Facing.DOWN);
		
		scene.idle(20);

		scene.text(85, "Redstone signal is needed to shoot", [1.5, 1, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.world.toggleRedstonePower([1, 1, 2]);
		scene.effects.createRedstoneParticles([1, 1, 2], 0xFF0000, 3);
		
		scene.world.createEntity("crusty_chunks:flare_projectile", [2.5, 2.0, 2.5], e => {
			e.setDeltaMovement([0, 3, 0]);
        });
		
		scene.idle(20);
		
		
		scene.world.toggleRedstonePower([1, 1, 2]);
    });
});