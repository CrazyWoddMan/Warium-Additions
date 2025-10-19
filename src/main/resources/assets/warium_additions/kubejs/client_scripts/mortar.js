Ponder.registry((event) => {
    event.create("crusty_chunks:mortar").scene("mortar", "How to use mortar", (scene, util) => {
        scene.showBasePlate();
		
		scene.world.setBlock([2, 1, 2], "crusty_chunks:mortar", false);
		
		scene.idle(10);
		
		scene.world.showSection([2, 1, 2], Facing.DOWN);
		
		scene.idle(20);
		
		scene.showControls(60, [2.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:aimer");
		scene.text(60, "Right-click with Weapon Aimer in second hand to attach it", [2.5, 1.5, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(80);
		
		scene.showControls(1040, [0.5, 2, 2.5], "down").withItem("crusty_chunks:aimer");
		scene.text(80, "LEFT/RIGHT arrow for yaw UP/DOWN arrow for pitch", [-0.1, 3.7, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.text(100, "Pitch is responsible for the vertical angle of the projectile shot and therefore the coverage distance", [-0.1, 3.7, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(120);
		
		scene.text(80, "While yaw is responsible for the left/right rotation", [-0.1, 3.7, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(100);
		
		scene.text(60, "Pitch range from -21.5 (30-100 blocks)", [-0.1, 2.7, 2.5]).placeNearTarget().attachKeyFrame();
		scene.overlay.showLine(PonderPalette.BLUE, [2.5, 2.001, 2.2], [2.5, 2.59, 0.7], 735);
		
		scene.idle(80);
		
		scene.text(60, "To 44.5 (60-120 blocks)", [-0.1, 3.6, 2.5]).placeNearTarget().attachKeyFrame();
		scene.overlay.showLine(PonderPalette.RED, [2.5, 2.0, 2.2], [2.5, 3.5, 2.175], 645);
		
		scene.idle(80);
		
		scene.text(80, "And default pitch is 0 (150-210 blocks)", [-0.1, 3.2, 2.5]).placeNearTarget().attachKeyFrame();
		scene.overlay.showLine(PonderPalette.GREEN, [2.5, 2.002, 2.2], [2.5, 3.5, 0.7], 570);
		
		scene.idle(100);
		
		scene.text(140, "Conclusion: 0 pitch has the longest range. Both above and below 0 pitch decreases the distance", [-0.1, 3.1, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(160);
		
		scene.text(140, "Lower pitch results in a less accurate shot, but projectile reaches the final destination faster", [-0.1, 3.1, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(160);
		
		scene.text(140, "Higher pitch shot is more accurate, but projectile takes longer to reach the end point", [-0.1, 3.1, 2.5]).placeNearTarget().attachKeyFrame();
		
		scene.idle(160);
		
		scene.addKeyframe();
		scene.showControls(20, [2.5, 2, 2.5], "down").rightClick().withItem("crusty_chunks:mortar_shell");
		
		scene.idle(20);
		
		scene.world.createEntity("crusty_chunks:mortar_projectile", [0, 0, 0], entity => {
			entity.setNbt('{crit: 1b, Motion: [0.0d, 3.0d, -1.5d], Pos: [2.5d, 2.1d, 2.1d]}')
        });
		scene.particles.simple(5, "campfire_cosy_smoke", [2, 3, 1.2]);
	});
});