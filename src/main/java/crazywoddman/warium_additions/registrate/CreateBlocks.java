package crazywoddman.warium_additions.registrate;

import crazywoddman.warium_additions.block.converter.ConverterIn;
import crazywoddman.warium_additions.block.converter.ConverterOut;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;

public class CreateBlocks {
    
    public static final BlockEntry<ConverterOut> CONVERTER_OUT = Registrate.REGISTRATE
        .block("converter_out", ConverterOut::new)
        .properties(p -> p.strength(1.0F).mapColor(MapColor.METAL).sound(SoundType.ANVIL))
        .simpleItem()
        .register();

    public static final BlockEntry<ConverterIn> CONVERTER_IN = Registrate.REGISTRATE
        .block("converter_in", ConverterIn::new)
        .properties(p -> p.strength(1.0F).mapColor(MapColor.METAL).sound(SoundType.ANVIL))
        .simpleItem()
        .register();

    public static void register() {}
}
