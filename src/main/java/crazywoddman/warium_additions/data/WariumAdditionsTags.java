package crazywoddman.warium_additions.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class WariumAdditionsTags {
    public static class Fluids {
        public static final TagKey<Fluid> GASOLINE = create("gasoline");
        
        public static ITag<Fluid> get(TagKey<Fluid> tagkey) {
            return ForgeRegistries.FLUIDS.tags().getTag(tagkey);
        }

        private static TagKey<Fluid> create(String path) {
            return FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", path));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> KINETIC_OUTPUT_FRONT = create("kineticoutputfront");
        public static final TagKey<Block> FUEL_ROD = create("fuelrod");

        public static ITag<Block> get(TagKey<Block> tagkey) {
            return ForgeRegistries.BLOCKS.tags().getTag(tagkey);
        }

        private static TagKey<Block> create(String path) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("crusty_chunks", path));
        }
    }
}
