package crazywoddman.warium_additions.util;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class WariumAdditionsUtil {

    public static ItemStack getWariumStack(String id) {
        return new ItemStack(
            ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath("crusty_chunks", id)
            )
        );
    }

    public static FluidStack getWariumFluid(String id, int amount) {
        return new FluidStack(
            ForgeRegistries.FLUIDS.getValue(ResourceLocation.fromNamespaceAndPath("crusty_chunks", id)),
            amount
        );
    }

    public static Ingredient getIngredientTag(String namespace, String id) {
        return Ingredient.of(
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, id))
        );
    }

    /**
     * @param fluid
     * @return Warium's {@code FuelType} of <b>fluid<b>
     */
    public static String getFuelType(FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.isEmpty()) {
            Fluid fluid = fluidStack.getFluid();
            String path = ForgeRegistries.FLUIDS.getKey(fluid).getPath();
            
            for (String fuelType : List.of("diesel", "kerosene")) {
                if (
                    path.equals(fuelType) ||
                    ForgeRegistries.FLUIDS
                        .tags()
                        .getTag(FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", fuelType)))
                        .contains(fluid)
                )

                return fuelType;
            }
        }

        return "";
    }

    /**
     * @param first fluid
     * @param second fluid to compare with
     * @return {@code true} if fluids are similar
     */
    public static boolean compareFluids(Fluid first, Fluid second) {
        if (first.equals(second))
            return true;

        String firstPath = ForgeRegistries.FLUIDS.getKey(first).getPath();
        String secondPath = ForgeRegistries.FLUIDS.getKey(second).getPath();

        if (
            firstPath.equals(secondPath) ||
            ForgeRegistries.FLUIDS.tags().getTag(FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", firstPath))).contains(second) ||
            ForgeRegistries.FLUIDS.tags().getTag(FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", secondPath))).contains(first)
        )
            return true;
        
        return false;
    }

    /**
     * Formats time to {@code mm:ss} or {@code hh:mm:ss}
     * @param totalSeconds time in seconds
     * @return formatted <b>totalSeconds<b>
     */
    public static String formatSeconds(int totalSeconds) {
        int seconds = totalSeconds % 60;

        if (totalSeconds >= 3600) {
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            int minutes = totalSeconds / 60;
            return String.format("%d:%02d", minutes, seconds);
        }
    }

    public static String formatFE(int fe) {
        if (fe >= 1_000_000_000)
            return Math.round(fe / 100_000_000) / 10.0 + "Gfe";

        else if (fe >= 1_000_000)
            return Math.round(fe / 100_000) / 10.0 + "Mfe";
        
        else if (fe >= 1_000)
            return Math.round(fe / 100) / 10.0 + "Kfe";
            
        return fe + "fe";
    }
}
