package crazywoddman.warium_additions.ponder;

import crazywoddman.warium_additions.WariumAdditions;

public class WariumPonder {
    public static void register() {
        if (WariumAdditions.create)
            WariumPonderUtils.register();
    }
}
