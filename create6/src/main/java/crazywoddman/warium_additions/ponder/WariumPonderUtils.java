package crazywoddman.warium_additions.ponder;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import crazywoddman.warium_additions.WariumAdditions;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.element.InputElementBuilder;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class WariumPonderUtils implements PonderPlugin {
    private PonderSceneRegistrationHelper<Supplier<Item>> helper;

    protected static void register() {
        PonderIndex.addPlugin(new WariumPonderUtils());
    }

    @Override
    public String getModId() {
        return WariumAdditions.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderPlugin.super.registerScenes(helper);
        this.helper = helper.withKeyFunction(item -> ForgeRegistries.ITEMS.getKey(item.get()));
        WariumPonderScenes.register(this);
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public final void addStoryBoard(String schematicPath, CustomStoryBoard storyBoard, Supplier<Item>... items) {
        for (Supplier<Item> item : items)
            this.helper.forComponents(item).addStoryBoard(schematicPath, (scene, util) -> storyBoard.program(new CustomSceneBuilder(scene, util, item)));
    }

    @FunctionalInterface
    public interface CustomStoryBoard {
        void program(CustomSceneBuilder scene);
    }

    protected static PonderPalette colorOf(String COLOR) {
        return PonderPalette.valueOf(COLOR);
    }

    public static class CustomSceneBuilder {
        public final SceneBuilder scene;
        public final SceneBuildingUtil util;
        public final Supplier<Item> item;
        public final Extras extras = new Extras();

        private CustomSceneBuilder(SceneBuilder scene, SceneBuildingUtil util, Supplier<Item> item) {
            this.scene = scene;
            this.util = util;
            this.item = item;
        }

        public class Extras {
            public ElementLink<EntityElement> createEntity(EntityType<?> entityType, String compound) {
                return scene.world().createEntity(level -> {
                    Entity entity = entityType.create(level);
                    CompoundTag tag;
                    
                    try {
                        tag = TagParser.parseTag(compound);
                        entity.load(tag);
                    } catch (CommandSyntaxException e) {
                        e.printStackTrace();
                    }

                    return entity;
                });
            }

            public ElementLink<WorldSectionElement> showIndependentSection(int x, int y, int z, Direction fadeInDirection) {
                return scene.world().showIndependentSection(sel(x, y, z), fadeInDirection);
            }

            public ElementLink<WorldSectionElement> showIndependentSection(int x, int y, int z, int x2, int y2, int z2, Direction fadeInDirection) {
                return scene.world().showIndependentSection(sel(x, y, z, x2, y2, z2), fadeInDirection);
            }

            public ElementLink<WorldSectionElement> showIndependentSection(int x, int y, int z) {
                return scene.world().showIndependentSectionImmediately(sel(x, y, z));
            }

            public ElementLink<WorldSectionElement> showIndependentSection(int x, int y, int z, int x2, int y2, int z2) {
                return scene.world().showIndependentSectionImmediately(sel(x, y, z, x2, y2, z2));
            }

            public void moveSection(ElementLink<WorldSectionElement> link, double x, double y, double z, int duration) {
                scene.world().moveSection(link, new Vec3(x, y, z), duration);
            }

            public void setBlock(int x, int y, int z, Supplier<Block> block) {
                setBlock(x, y, z, block, UnaryOperator.identity());
            }

            public void setBlock(int x, int y, int z, Supplier<Block> block, UnaryOperator<BlockState> state) {
                scene.world().setBlock(new BlockPos(x, y, z), state.apply(block.get().defaultBlockState()), false);
            }

            public void showControls(int duration, double x, double y, double z, String POINTING, UnaryOperator<InputElementBuilder> func) {
                func.apply(scene.overlay().showControls(new Vec3(x, y, z), Pointing.valueOf(POINTING), duration));
            }

            public void showOutline(String COLOR, int x, int y, int z, int duration) {
                scene.overlay().showOutline(PonderPalette.valueOf(COLOR), null, sel(x, y, z), duration);
            }

            public void showOutline(String COLOR, int x, int y, int z, int x2, int y2, int z2, int duration) {
                scene.overlay().showOutline(colorOf(COLOR), null, sel(x, y, z, x2, y2, z2), duration);
            }

            public void showLine(String COLOR, double x, double y, double z, double x2, double y2, double z2, int duration) {
                scene.overlay().showLine(colorOf(COLOR), new Vec3(x, y, z), new Vec3(x, y, z), 645);
            }

            public void emitParticles(double x, double y, double z, SimpleParticleType particle, double motionX, double motionY, double motionZ) {
                scene.effects().emitParticles(
                    new Vec3(x, y, z),
                    scene.effects().simpleParticleEmitter(particle, new Vec3(motionX, motionY, motionZ)),
                    1, 1
                );
            }

            public Selection sel(BlockPos pos) {
                return util.select().position(pos);
            }

            public Selection sel(int x, int y, int z) {
                return util.select().position(x, y, z);
            }

            public Selection sel(int x, int y, int z, int x2, int y2, int z2) {
                return util.select().fromTo(x, y, z, x2, y2, z2);
            }
        }
    }
}
