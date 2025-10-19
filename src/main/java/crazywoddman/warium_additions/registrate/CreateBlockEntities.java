package crazywoddman.warium_additions.registrate;

import crazywoddman.warium_additions.block.converter.ConverterInBlockEntity;
import crazywoddman.warium_additions.block.converter.ConverterInInstance;
import crazywoddman.warium_additions.block.converter.ConverterInRenderer;
import crazywoddman.warium_additions.block.converter.ConverterOutBlockEntity;
import crazywoddman.warium_additions.block.converter.ConverterOutInstance;
import crazywoddman.warium_additions.block.converter.ConverterOutRenderer;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CreateBlockEntities {
    public static final BlockEntityEntry<ConverterOutBlockEntity> CONVERTER_OUT_BE = Registrate.REGISTRATE
        .blockEntity("converter_out", ConverterOutBlockEntity::new)
        .instance(() -> ConverterOutInstance::new, false)
        .validBlocks(CreateBlocks.CONVERTER_OUT)
        .renderer(() -> ConverterOutRenderer::new)
        .register();

    public static final BlockEntityEntry<ConverterInBlockEntity> CONVERTER_IN_BE = Registrate.REGISTRATE
        .blockEntity("converter_in", ConverterInBlockEntity::new)
        .instance(() -> ConverterInInstance::new, false)
        .validBlocks(CreateBlocks.CONVERTER_IN)
        .renderer(() -> ConverterInRenderer::new)
        .register();

    public static void register() {}
}
