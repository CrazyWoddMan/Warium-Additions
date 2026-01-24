package crazywoddman.warium_additions.ponder;

import crazywoddman.warium_additions.WariumAdditions;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.MOD)
public class WariumPonderDataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        WariumPonder.register();
        generator.addProvider(event.includeClient(), new LangProvider(generator.getPackOutput(), "ponder", "en_us"));
    }

    public static class LangProvider extends LanguageProvider {
        public LangProvider(PackOutput output, String modid, String locale) {
            super(output, modid, locale);
        }

        @Override
        protected void addTranslations() {
            PonderIndex.getLangAccess().provideLang(WariumAdditions.MODID, this::add);
        }
    }
}
