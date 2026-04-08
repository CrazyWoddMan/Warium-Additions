package crazywoddman.warium_additions.mixins.valkyrien_warium;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition("valkyrien_warium"))
@Mixin(targets = {
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$1",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$2",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$3",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$4",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$5",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$6",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$7",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$8",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$9",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$10",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$11",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$12",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$13",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$14",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$15",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$16",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$17",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$18",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$19",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$20",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$21",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$22",
    "net.mcreator.valkyrienwarium.init.ValkyrienWariumModKeyMappings$23",
})
public class ValkyrienWariumModKeyMappingsMixin {
    @ModifyArg(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/KeyMapping;<init>(Ljava/lang/String;ILjava/lang/String;)V"
        ),
        index = 2
    )
    private static String modifyCategory(String category) {
        return "key.categories.valkyrien_warium";
    }
}