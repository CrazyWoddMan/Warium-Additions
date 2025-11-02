package crazywoddman.warium_additions.mixins.crusty_chunks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = {
    "net.mcreator.crustychunks.init.CrustyChunksModKeyMappings$1",
    "net.mcreator.crustychunks.init.CrustyChunksModKeyMappings$2",
    "net.mcreator.crustychunks.init.CrustyChunksModKeyMappings$3",
    "net.mcreator.crustychunks.init.CrustyChunksModKeyMappings$4",
    "net.mcreator.crustychunks.init.CrustyChunksModKeyMappings$5",
    "net.mcreator.crustychunks.init.CrustyChunksModKeyMappings$6",
    "net.mcreator.crustychunks.init.CrustyChunksModKeyMappings$7"
})
public class CrustyChunksModKeyMappingsMixin {
    @ModifyArg(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/KeyMapping;<init>(Ljava/lang/String;ILjava/lang/String;)V"
        ),
        index = 2
    )
    private static String modifyCategory(String category) {
        return "key.categories.crusty_chunks";
    }
}