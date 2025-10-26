package crazywoddman.warium_additions.mixins.supplementaries;

import net.mehvahdjukaar.supplementaries.common.utils.SoapWashableHelper;
import net.mehvahdjukaar.supplementaries.common.utils.BlockPredicate;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import crazywoddman.warium_additions.recipe.ColoringRecipe;
import crazywoddman.warium_additions.recipe.ColoringRecipeRegistry;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Restriction(require = @Condition("supplementaries"))
@Mixin(value = SoapWashableHelper.class, remap = false)
public class SoapWashableHelperMixin {

    @Redirect(
        method = "tryChangingColor",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/function/Supplier;get()Ljava/lang/Object;",
            ordinal = 0
        ),
        require = 0
    )
    private static Object redirectSoapBlacklistGet(Supplier<List<String>> instance) {
        List<String> original = instance.get();
        List<String> extended = ColoringRecipeRegistry.getPatternsForSoapBlacklist();

        if (extended.size() > 0)
            original.addAll(extended);
        
        return original;
    }

    @Redirect(
        method = {"tryUnoxidise", "tryCleanFromConfig"},
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/function/Supplier;get()Ljava/lang/Object;",
            ordinal = 0
        )
    )
    private static Object redirectSoapSpecialGet(Supplier<Map<BlockPredicate, ResourceLocation>> instance) {
        BiMap<BlockPredicate, ResourceLocation> extended = HashBiMap.create();
        
        for (ColoringRecipe template : ColoringRecipeRegistry.getCachedRecipes()) {
            String base = template.baseIngredient;

            if (base.startsWith("#")) {
                TagKey<Item> tag = ItemTags.create(ResourceLocation.tryParse(base.substring(1)));
                ITag<Item> items = ForgeRegistries.ITEMS.tags().getTag(tag);

                if (!items.isEmpty()) {
                    String firstItemId = ForgeRegistries.ITEMS.getKey(items.iterator().next()).toString();
                    extended.put(BlockPredicate.create(base), ResourceLocation.tryParse(firstItemId));
                }
            }
        }
    
        return extended;
    }
}
