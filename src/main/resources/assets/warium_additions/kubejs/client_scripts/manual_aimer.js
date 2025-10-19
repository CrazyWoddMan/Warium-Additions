Ponder.registry((event) => {
    event.create("crusty_chunks:manual_aimer").scene("manual_aimer", "Manual Aiming Handle", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:manual_aimer", false);;
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.text(120, "Manual Aiming Handle combines the functions of Aimer Node, Node Trigger and Weapon Aimer", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(135);
		
		scene.world.setBlock([2, 1, 1], "crusty_chunks:machine_gun", false);
		scene.world.showSection([2, 1, 1], Facing.DOWN);
		
		scene.idle(10);
		
		scene.world.setBlock([2, 1, 0], "crusty_chunks:machine_gun_barrel", false);
		scene.world.showSection([2, 1, 0], Facing.SOUTH);

		scene.idle(15);
		
		scene.text(80, "If placed next to any weapon that can be aimed...", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(90);

		scene.text(200, "Player can aim the weapon in direction they are looking and shoot with left mouse click while using the Manual Aiming Handle", [2.5, 1.5, 2.5]).placeNearTarget();
    });
});