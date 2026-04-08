package crazywoddman.warium_additions.mixins.supplementaries;

import net.mehvahdjukaar.supplementaries.common.utils.SoapWashableHelper;
import net.mehvahdjukaar.supplementaries.common.utils.BlockPredicate;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import crazywoddman.warium_additions.data.recipes.ColoringRecipe;
import crazywoddman.warium_additions.data.recipes.ColoringRecipeRegistry;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

import java.util.Map;
import java.util.function.Supplier;

@Restriction(require = @Condition("supplementaries"))
@Mixin(value = SoapWashableHelper.class, remap = false)
public class SoapWashableHelperMixin {

    @Redirect(
        method = "tryCleanFromConfig",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/function/Supplier;get()Ljava/lang/Object;",
            ordinal = 0
        )
    )
    private static Object redirectSoapSpecialGet(Supplier<Map<BlockPredicate, ResourceLocation>> instance) {
        BiMap<BlockPredicate, ResourceLocation> extended = HashBiMap.create();
        
        for (ColoringRecipe template : ColoringRecipeRegistry.CACHED) {
            String base = template.basePattern;

            if (base.startsWith("#")) {
                ITag<Item> items = ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(ResourceLocation.tryParse(base.substring(1))));

                if (!items.isEmpty()) {
                    String firstItemId = ForgeRegistries.ITEMS.getKey(items.iterator().next()).toString();
                    extended.put(BlockPredicate.create(base), ResourceLocation.tryParse(firstItemId));
                }
            }
        }
    
        return extended;
    }
}
