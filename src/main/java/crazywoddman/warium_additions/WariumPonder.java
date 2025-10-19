package crazywoddman.warium_additions;

import dev.latvian.mods.kubejs.client.KubeJSClient;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class WariumPonder {
    public static void kubeJSreloadScripts(FMLClientSetupEvent event) {
        event.enqueueWork(KubeJSClient::reloadClientScripts);
    }
}
