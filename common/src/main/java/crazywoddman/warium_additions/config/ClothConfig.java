package crazywoddman.warium_additions.config;

import crazywoddman.warium_additions.WariumAdditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static ConfigEntryBuilder entryBuilder;

    @SuppressWarnings("removal")
    public static void registerConfigScreen() {
        FMLJavaModLoadingContext.get().registerExtensionPoint(ConfigScreenFactory.class, () -> new ConfigScreenFactory((mc, screen) -> {

            ConfigBuilder builder = ConfigBuilder
                .create()
                .setParentScreen(screen)
                .setTitle(Component.literal("Warium Additions Config"));

            builder.setGlobalized(false);
            builder.setTransparentBackground(true);
            
            List<ConfigCategory> categories = new ArrayList<>(3);
            categories.add(builder.getOrCreateCategory(Component.literal("Warium")));

            if (WariumAdditions.valkyrien_warium) {
                ConfigCategory valkyrien = builder.getOrCreateCategory(Component.literal("Valkyrien Warium"));
                categories.add(valkyrien);
            }

            if (WariumAdditions.create) {
                ConfigCategory create = builder.getOrCreateCategory(Component.literal("Create"));
                categories.add(create);
            }
            
            entryBuilder = ConfigEntryBuilder.create();
            String levelType = Minecraft.getInstance().level == null ? "null" : (Minecraft.getInstance().getSingleplayerServer() == null ? "client" : "host");

            for (ConfigCategory category : categories) {
                category.addEntry(entryBuilder
                    .startTextDescription(
                        Component.literal("§eChanges " + switch (levelType) {
                            case "null" -> "can't be made from the main menu. Enter a world";
                            case "host" -> "only apply for the current world. Re-enter the world for some changes to take effect";
                            case "client" -> "on server can only be made by editing config file in world/serverconfig/. Alternatively, you can use Create «Access Configs of other mods» feature. Server restart is required for some changes to apply";
                            default -> "can't be made due to unknown error";
                        })
                    )
                    .build()
                );

                if (levelType.equals("host"))
                    addCategory(category, entryBuilder);
            }

            return builder.build();
        }));
    }

    private static void addCategory(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        switch (category.getCategoryKey().getString()) {
            case "Warium" -> {
                SubCategoryBuilder fluidcap = entryBuilder.startSubCategory(Component.literal("Fluid Capacities"));

                fluidcap.add(entry(Config.SERVER.fuelTanksCapacity, 1));
                fluidcap.add(entry(Config.SERVER.refineryTowerCapacity, 1));

                category.addEntry(fluidcap.build());
                SubCategoryBuilder power = entryBuilder.startSubCategory(Component.literal("Power"));

                power.add(entry(Config.SERVER.mediumDieselEnginePower, 1));
                power.add(entry(Config.SERVER.smallDieselEnginePower, 1));
                power.add(entry(Config.SERVER.mediumPetrolEnginePower, 1));
                power.add(entry(Config.SERVER.smallPetrolEnginePower, 1));
                power.add(entry(Config.SERVER.jetTurbinePower, 1));
                
                category.addEntry(power.build());
                SubCategoryBuilder energy = entryBuilder.startSubCategory(Component.literal("Energy"));

                energy.add(entry(Config.SERVER.kineticToFeRate, 0.01));
                energy.add(entry(Config.SERVER.energyTransferLimit, 1));
                energy.add(entry(Config.SERVER.batteryCapacity, 1));
                energy.add(entry(Config.SERVER.electricFireboxCapacity, 1));
                energy.add(entry(Config.SERVER.electricFireboxConsumption, 1));
                energy.add(entry(Config.SERVER.powerReactorCapacity, 1));
                energy.add(entry(Config.SERVER.powerReactorGeneration, 1));
                energy.add(entry(Config.SERVER.solarGeneration, 1));
                energy.add(entry(Config.SERVER.motorConsumptionLimit, 1));

                category.addEntry(energy.build());
            }

            case "Valkyrien Warium" -> {
                category.addEntry(entry(Config.SERVER.minThrottle, null, -1));
                category.addEntry(entry(Config.SERVER.maxThrottle, 1));
            }

            case "Create" -> {
                SubCategoryBuilder heat = entryBuilder.startSubCategory(Component.literal("Heat Level"));
                String[] heatLevels = new String[]{"KINDLED", "SEETHING"};

                heat.add(entry(Config.SERVER.fireboxHeatLevel, heatLevels));
                heat.add(entry(Config.SERVER.keroseneFireboxHeatLevel, heatLevels));
                heat.add(entry(Config.SERVER.electricFireboxHeatLevel, heatLevels));

                category.addEntry(heat.build());
                SubCategoryBuilder ratios = entryBuilder.startSubCategory(Component.literal("Ratios"));

                ratios.add(entry(Config.SERVER.kineticToStressRatio, 1));
                ratios.add(entry(Config.SERVER.kineticToSpeedRatio, 1, 5));

                category.addEntry(ratios.build());
                
                category.addEntry(entry(Config.SERVER.kineticConverterSpeedControl));
                category.addEntry(entry(Config.SERVER.kineticConverterResponse, 0, 40));
            }
        }
    }

    private static AbstractConfigListEntry<?> entry(ConfigValue<?> configValue) {
        return simpleEntry(configValue).build();
    }

    private static AbstractConfigListEntry<?> entry(ConfigValue<?> configValue, Object minValue) {
        return putLimits(simpleEntry(configValue), minValue, null);
    }

    private static AbstractConfigListEntry<?> entry(ConfigValue<?> configValue, Object minValue, Object maxValue) {
        return putLimits(simpleEntry(configValue), minValue, maxValue);
    }

    private static AbstractConfigListEntry<?> entry(ConfigValue<String> configValue, String[] options) {
        return selectorEntry(configValue, options).build();
    }

    private static AbstractConfigListEntry<?> putLimits(AbstractFieldBuilder<?, ?, ?> builder, Object minValue, Object maxValue) {
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
        } else 
            throw new IllegalStateException("Unsupported builder or limits type");

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

    private static AbstractFieldBuilder<?, ?, ?> simpleEntry(ConfigValue<?> configValue) {
        String path = getPath(configValue);
        Component name = Component.translatable(path);
        AbstractFieldBuilder<?, ?, ?> builder;

        if (configValue instanceof IntValue value)
            builder = entryBuilder
                .startIntField(name, value.get())
                .setDefaultValue(value.getDefault())
                .setSaveConsumer(value::set);
        else if (configValue instanceof BooleanValue value)
            builder = entryBuilder
                .startBooleanToggle(name, value.get())
                .setDefaultValue(value.getDefault())
                .setSaveConsumer(value::set);
        else if (configValue instanceof DoubleValue value)
            builder = entryBuilder
                .startDoubleField(name, value.get())
                .setDefaultValue(value.getDefault())
                .setSaveConsumer(value::set);
        else
            throw new IllegalStateException("Method can't accept such ConfigValue type!");

        return prepareTooltip(builder, path);
    }

    private static AbstractFieldBuilder<?, ?, ?> selectorEntry(ConfigValue<String> configValue, String[] options) {
        String path = getPath(configValue);

        return prepareTooltip(
            entryBuilder
                .startSelector(
                    Component.translatable(path),
                    options,
                    configValue.get()
                )
                .setDefaultValue(configValue.getDefault())
                .setSaveConsumer(configValue::set),
            path
        );
    }
}
