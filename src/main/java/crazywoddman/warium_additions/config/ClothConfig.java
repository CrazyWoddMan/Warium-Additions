package crazywoddman.warium_additions.config;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.network.ConfigSyncPacket;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.AbstractFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;

public class ClothConfig {
    private enum Side {
        NONE, HOST, CLIENT, CLIENT_OP
    }

    public static void register(FMLJavaModLoadingContext context) {
         context.registerExtensionPoint(ConfigScreenFactory.class, () -> new ConfigScreenFactory((mc, screen) -> {
            ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(screen).setTitle(Component.literal("Warium Additions Config"));
            configBuilder.setGlobalized(false);
            configBuilder.setTransparentBackground(true);
            ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();
            Side side;

            if (Minecraft.getInstance().level == null)
                side = Side.NONE;
            else if (Minecraft.getInstance().getSingleplayerServer() == null)
                side = (mc.player != null && mc.player.hasPermissions(4)) ? Side.CLIENT_OP : Side.CLIENT;
            else side = Side.HOST;

            BuildData data = new BuildData(configBuilder, entryBuilder, side == Side.CLIENT_OP);

            Category warium = new Category(data, "Warium");
            warium.add(entryBuilder.startTextDescription(
                Component.literal(switch (side) {
                    case NONE -> "§eChanges can't be made from the main menu. Enter a world";
                    case HOST -> "§eChanges only apply for the current world. Re-enter the world for some changes to take effect";
                    case CLIENT -> "§cYou don't have enough permissions on this server to change this config";
                    case CLIENT_OP -> "§eServer restart is required for some changes to apply";
                })
            ).build());

            if (side == Side.NONE || side == Side.CLIENT)
                return configBuilder.build();

            warium.add("Fluid Capacities", category -> {
                category.add(Config.SERVER.fuelTanksCapacity, 1);
                category.add(Config.SERVER.refineryCapacity, 1);
                category.add(Config.SERVER.refineryTowerCapacity, 1);
            });
            warium.add("Power", category -> {
                if (WariumAdditions.WARIUM_PONDER_JEI)
                    category.add(Config.SERVER.machinesMinPower, 0);
                
                category.add(Config.SERVER.mediumDieselEnginePower, 1);
                category.add(Config.SERVER.smallDieselEnginePower, 1);
                category.add(Config.SERVER.mediumPetrolEnginePower, 1);
                category.add(Config.SERVER.smallPetrolEnginePower, 1);
                
                if (!WariumAdditions.MODLIST.isLoaded("wariumtactics"))
                    category.add(Config.SERVER.jetTurbinePower, 1);
            });
            warium.add("Energy", category -> {
                category.add(Config.SERVER.kineticToFeRate, 0.01);
                category.add(Config.SERVER.energyTransferLimit, 1);
                category.add(Config.SERVER.batteryCapacity, 1);
                category.add(Config.SERVER.electricFireboxCapacity, 1);
                category.add(Config.SERVER.electricFireboxConsume, 1);
                category.add(Config.SERVER.powerReactorCapacity, 1);
                category.add(Config.SERVER.powerReactorGeneration, 1);
                category.add(Config.SERVER.solarGeneration, 1);
                category.add(Config.SERVER.motorConsumeLimit, 1);
            });

            warium.add(Config.SERVER.creativeAmmoConsume);

            if (WariumAdditions.WARIUM_PONDER_JEI)
                warium.add(Config.SERVER.heatRequirement, 0, 1000);

            if (WariumAdditions.VALKYRIEN_WARIUM) {
                Category wariumVS = new Category(data, "Valkyrien Warium");
                wariumVS.add(Config.SERVER.maxThrottle, 1);
            }

            if (WariumAdditions.CREATE) {
                Category create = new Category(data, "Create");
                create.add("Heat Level", category -> {
                    String[] heatLevels = new String[]{"KINDLED", "SEETHING"};
                    category.add(Config.SERVER.fireboxHeatLevel, heatLevels);
                    category.add(Config.SERVER.keroseneFireboxHeatLevel, heatLevels);
                    category.add(Config.SERVER.electricFireboxHeatLevel, heatLevels);
                });
                create.add("Ratios", category -> {
                    category.add(Config.SERVER.kineticToStressRatio, 1);
                    category.add(Config.SERVER.kineticToSpeedRatio, 1, 5);
                });
            }

            return configBuilder.build();
        }));
    }

    private record BuildData(ConfigBuilder configBuilder, ConfigEntryBuilder entryBuilder, boolean isClient) {}

    private static class Category extends AbstractCategory {
        private final ConfigCategory category;

        protected Category(BuildData data, String name) {
            super(data.entryBuilder, data.isClient);
            this.category = data.configBuilder.getOrCreateCategory(Component.literal(name));
        }

        @Override
        public void add(AbstractConfigListEntry<?> entry) {
            this.category.addEntry(entry);
        }

        private void add(String name, Consumer<SubCategory> builder) {
            SubCategory subCategory = new SubCategory(this.builder, name, this.isClient);
            builder.accept(subCategory);
            this.category.addEntry(subCategory.builder.build());
        }
    }

    private static class SubCategory extends AbstractCategory {
        protected final SubCategoryBuilder builder;

        protected SubCategory(ConfigEntryBuilder builder, String name, boolean isClient) {
            super(builder, isClient);
            this.builder = builder.startSubCategory(Component.literal(name));
        }

        @Override
        public void add(AbstractConfigListEntry<?> entry) {
            this.builder.add(entry);
        }
    }

    private static abstract class AbstractCategory {
        protected final ConfigEntryBuilder builder;
        protected final boolean isClient;

        protected AbstractCategory(ConfigEntryBuilder builder, boolean isClient) {
            this.builder = builder;
            this.isClient = isClient;
        }

        protected abstract void add(AbstractConfigListEntry<?> entry);

        protected void add(BooleanValue value) {
            add(simpleEntry(value).build());
        }

        protected void add(ConfigValue<?> configValue, Object minValue) {
            add(putLimits(simpleEntry(configValue), minValue, null));
        }

        protected void add(ConfigValue<?> configValue, Object minValue, Object maxValue) {
            add(putLimits(simpleEntry(configValue), minValue, maxValue));
        }

        protected void add(ConfigValue<String> configValue, String[] options) {
            add(selectorEntry(configValue, options).build());
        }

        private AbstractConfigListEntry<?> putLimits(AbstractFieldBuilder<?, ?, ?> builder, Object minValue, Object maxValue) {
            if (builder instanceof IntFieldBuilder build) {
                if (minValue instanceof Integer min)
                    build = build.setMin(min);
                if (maxValue instanceof Integer max)
                    build = build.setMax(max);
                builder = build;
            } else if (builder instanceof DoubleFieldBuilder fieldBuilder) {
                if (minValue instanceof Double min)
                    fieldBuilder = fieldBuilder.setMin(min);
                if (maxValue instanceof Double max)
                    fieldBuilder = fieldBuilder.setMax(max);
                builder = fieldBuilder;
            }
            else throw new IllegalStateException("Unsupported builder or limits type");

            return builder.build();
        }

        private static String getPath(ConfigValue<?> configValue) {
            List<String> pathList = configValue.getPath();
            String path = WariumAdditions.MODID + ".config";

            for (String part : pathList)
                path += "." + part.replaceAll(" ", "_");

            return path;
        }

        private static AbstractFieldBuilder<?, ?, ?> prepareTooltip(AbstractFieldBuilder<?, ?, ?> builder, String path) {
            Component[] tooltips = new Component[0];
            String description = path + ".desc";

            for (int i = 0; I18n.exists(description + i); i++) {
                tooltips = Arrays.copyOf(
                    tooltips,
                    tooltips.length + 1
                );
                tooltips[tooltips.length - 1] = Component.translatable(description + i);
            }

            if (tooltips.length > 0)
                builder = (AbstractFieldBuilder<?, ?, ?>) builder.setTooltip(tooltips);

            return builder;
        }

        private AbstractFieldBuilder<?, ?, ?> simpleEntry(ConfigValue<?> configValue) {
            String path = getPath(configValue);
            Component name = Component.translatable(path);
            AbstractFieldBuilder<?, ?, ?> builder;

            if (configValue instanceof IntValue value)
                builder = this.builder
                    .startIntField(name, value.get())
                    .setDefaultValue(value.getDefault())
                    .setSaveConsumer(newValue -> save(value, newValue));
            else if (configValue instanceof BooleanValue value)
                builder = this.builder
                    .startBooleanToggle(name, value.get())
                    .setDefaultValue(value.getDefault())
                    .setSaveConsumer(newValue -> save(value, newValue));
            else if (configValue instanceof DoubleValue value)
                builder = this.builder
                    .startDoubleField(name, value.get())
                    .setDefaultValue(value.getDefault())
                    .setSaveConsumer(newValue -> save(value, newValue));
            else throw new IllegalStateException("Method can't accept such ConfigValue type!");

            return prepareTooltip(builder, path);
        }

        private AbstractFieldBuilder<?, ?, ?> selectorEntry(ConfigValue<String> value, String[] options) {
            String path = getPath(value);

            return prepareTooltip(
                this.builder
                    .startSelector(
                        Component.translatable(path),
                        options,
                        value.get()
                    )
                    .setDefaultValue(value.getDefault())
                    .setSaveConsumer(newValue -> save(value, newValue)),
                path
            );
        }

        private <T> void save(ConfigValue<T> value, T newValue) {
            if (!value.get().equals(newValue)) {
                value.set(newValue);

                if (this.isClient)
                    ConfigSyncPacket.send(value);
            }
        }
    }
}
