package crazywoddman.warium_additions.ponder;

import java.lang.reflect.Field;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil.PositionUtil;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil.SelectionUtil;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil.VectorUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction.Emitter;
import com.simibubi.create.foundation.utility.Pointing;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.ItemProviderEntry;

import crazywoddman.warium_additions.WariumAdditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WariumPonderUtils {
    private static final WariumPonderHelper HELPER = new WariumPonderHelper();
    private static final WariumRegistrate WARIUM_REGISTRATE = new WariumRegistrate();
    public enum Point {
        UP, LEFT, DOWN, RIGHT;
    }

    protected static void register() {
        WariumPonderScenes.register(new WariumPonderUtils());
    }

    @SafeVarargs
    public final void addStoryBoard(String schematicPath, CustomStoryBoard storyBoard, Supplier<Item>... items) {
        for (Supplier<Item> item : items)
            HELPER
            .forComponents(new ItemProviderEntry<Item>(WARIUM_REGISTRATE, RegistryObject.create(ForgeRegistries.ITEMS.getKey(item.get()), ForgeRegistries.ITEMS)))
            .addStoryBoard(schematicPath, (scene, util) -> storyBoard.program(new CustomSceneBuilder(scene, util, item)));
    }

    private static class WariumPonderHelper extends PonderRegistrationHelper {
        private WariumPonderHelper() {
            super(WariumAdditions.MODID);
        }
    }

    private static class WariumRegistrate extends AbstractRegistrate<WariumRegistrate> {
        public WariumRegistrate() {
            super("crusty_chunks");
        }
    }

    @FunctionalInterface
    public interface CustomStoryBoard {
        void program(CustomSceneBuilder scene);
    }

    protected static PonderPalette colorOf(String COLOR) {
        return PonderPalette.valueOf(COLOR);
    }

    public static class CustomSceneBuildingUtil {
        private final SceneBuildingUtil util;

        private CustomSceneBuildingUtil(SceneBuildingUtil util) {
            this.util = util;
        }

        public SelectionUtil select() {
            return util.select;
        };

        public VectorUtil vector() {
            return util.vector;
        };

        public PositionUtil grid() {
            return util.grid;
        };
    }

    public static class CustomSceneBuilder extends SceneBuilder {
        public final CustomSceneBuilder scene = this;
        public final CustomSceneBuildingUtil util;
        public final Supplier<Item> item;
        public final Extras extras = new Extras();

        private CustomSceneBuilder(SceneBuilder scene, SceneBuildingUtil util, Supplier<Item> item) {
            super(getSceneField(scene));
            this.util = new CustomSceneBuildingUtil(util);
            this.item = item;
        }

        private static PonderScene getSceneField(SceneBuilder scene) {
            try {
                Field field = SceneBuilder.class.getDeclaredField("scene");
                field.setAccessible(true);
                return (PonderScene) field.get(scene);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to access scene field", e);
            }
        }

        public SceneBuilder.OverlayInstructions overlay() {
            return this.overlay;
        }

        public SceneBuilder.WorldInstructions world() {
            return this.world;
        }

        public SceneBuilder.DebugInstructions debug() {
            return this.debug;
        }
        
        public SceneBuilder.EffectInstructions effects() {
            return this.effects;
        }

        public SceneBuilder.SpecialInstructions special() {
            return this.special;
        }

        public class Extras {
            public ElementLink<EntityElement> createEntity(EntityType<?> entityType, String compound) {
                return world().createEntity(level -> {
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
                world().moveSection(link, new Vec3(x, y, z), duration);
            }

            public void setBlock(int x, int y, int z, Supplier<Block> block) {
                setBlock(x, y, z, block, UnaryOperator.identity());
            }

            public void setBlock(int x, int y, int z, Supplier<Block> block, UnaryOperator<BlockState> state) {
                world().setBlock(new BlockPos(x, y, z), state.apply(block.get().defaultBlockState()), false);
            }

            public void showControls(int duration, double x, double y, double z, String POINTING, UnaryOperator<InputWindowElement> func) {
                overlay().showControls(func.apply(new InputWindowElement(new Vec3(x, y, z), Pointing.valueOf(POINTING))), duration);
            }

            public void showOutline(String COLOR, int x, int y, int z, int duration) {
                scene.overlay().showOutline(PonderPalette.valueOf(COLOR), null, sel(x, y, z), duration);
            }

            public void showOutline(String COLOR, int x, int y, int z, int x2, int y2, int z2, int duration) {
                scene.overlay().showOutline(PonderPalette.valueOf(COLOR), null, sel(x, y, z, x2, y2, z2), duration);
            }

            public void showLine(String COLOR, double x, double y, double z, double x2, double y2, double z2, int duration) {
                scene.overlay().showLine(colorOf(COLOR), new Vec3(x, y, z), new Vec3(x, y, z), 645);
            }

            public void emitParticles(double x, double y, double z, SimpleParticleType particle, double motionX, double motionY, double motionZ) {
                effects().emitParticles(
                    new Vec3(x, y, z),
                    Emitter.simple(particle, new Vec3(motionX, motionY, motionZ)),
                    1, 1
                );
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
