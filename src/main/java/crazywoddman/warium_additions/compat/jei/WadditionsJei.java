package crazywoddman.warium_additions.compat.jei;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.create.WariumCreateJei;
import crazywoddman.warium_additions.utils.WadditionsUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class WadditionsJei implements IModPlugin {
    private static final ResourceLocation ID = WadditionsUtil.location("jei");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (WariumAdditions.CREATE && WariumAdditions.WARIUM_PONDER_JEI)
            WariumCreateJei.registerRecipes(registration);
    }
}
