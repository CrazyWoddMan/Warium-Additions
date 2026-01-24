package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import crazywoddman.warium_additions.config.Config;
import net.mcreator.crustychunks.block.entity.ElectricFireboxBlockEntity;
import net.minecraftforge.energy.EnergyStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ElectricFireboxBlockEntity.class)
public class ElectricFireboxBlockEntityMixin {

    @Shadow(remap = false)
    private EnergyStorage energyStorage;

    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 2000),
        remap = false
    )
    private int modifyCapacity(int original) {
        return Config.SERVER.electricFireboxCapacity.get();
    }
    
    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 1000, ordinal = 0),
        remap = false
    )
    private int modifyMaxReceive(int original) {
        return Config.SERVER.energyTransferLimit.get();
    }

    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 1000, ordinal = 1),
        remap = false
    )
    private int modifyMaxExtract(int original) {
        return Math.max(Config.SERVER.energyTransferLimit.get(), Config.SERVER.electricFireboxConsumption.get());
    }
}
