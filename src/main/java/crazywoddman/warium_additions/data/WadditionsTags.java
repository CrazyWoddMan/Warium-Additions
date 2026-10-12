package crazywoddman.warium_additions.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class WadditionsTags {
    public static class Fluids {
        public static final TagKey<Fluid>
        DIESEL = create("diesel"),
        PETROL = create("gasoline"),
        KEROSENE = create("kerosene"),
        GASOLINE = create("gasoline");

        private static TagKey<Fluid> create(String path) {
            return FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", path));
        }
    }

    public static class Items {
        public static final TagKey<Item> FIREARMS = create("firearms");

        private static TagKey<Item> create(String path) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("crusty_chunks", path));
        }
    }

    public static class Blocks {
        public static final TagKey<Block>
        KINETIC_OUTPUT_FRONT = create("kineticoutputfront"),
        FUEL_ROD = create("fuelrod");

        private static TagKey<Block> create(String path) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("crusty_chunks", path));
        }
    }
}
