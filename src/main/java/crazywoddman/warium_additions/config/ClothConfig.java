package crazywoddman.warium_additions.config;

import crazywoddman.warium_additions.WariumAdditions;
import java.util.ArrayList;
import java.util.List;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

public class ClothConfig {
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
            ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();
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
                
                fluidcap.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Fluid capacity of fuel tanks (mB)"),
                        Config.SERVER.fuelTanksCapacity.get()
                    )
                    .setDefaultValue(Config.SERVER.fuelTanksCapacity.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.fuelTanksCapacity.set(newValue))
                    .build()
                );
                fluidcap.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Fluid capacity of Refinery Tower (mB)"),
                        Config.SERVER.refineryTowerCapacity.get()
                    )
                    .setDefaultValue(Config.SERVER.refineryTowerCapacity.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.refineryTowerCapacity.set(newValue))
                    .build()
                );
                fluidcap.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Fluid capacity of Kerosene Firebox (mB)"),
                        Config.SERVER.oilFireboxCapacity.get()
                    )
                    .setDefaultValue(Config.SERVER.oilFireboxCapacity.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.oilFireboxCapacity.set(newValue))
                    .build()
                );
                fluidcap.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Fluid capacity of Block Miner (mB)"),
                        Config.SERVER.blockMinerCapacity.get()
                    )
                    .setDefaultValue(Config.SERVER.blockMinerCapacity.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.blockMinerCapacity.set(newValue))
                    .build()
                );

                category.addEntry(fluidcap.build());
                SubCategoryBuilder power = entryBuilder.startSubCategory(Component.literal("Engines Power"));

                power.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Medium Diesel Engine power"),
                        Config.SERVER.mediumDieselEnginePower.get()
                    )
                    .setDefaultValue(Config.SERVER.mediumDieselEnginePower.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.mediumDieselEnginePower.set(newValue))
                    .build()
                );
                power.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Small Diesel Engine power"),
                        Config.SERVER.smallDieselEnginePower.get()
                    )
                    .setDefaultValue(Config.SERVER.smallDieselEnginePower.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.smallDieselEnginePower.set(newValue))
                    .build()
                );
                power.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Medium Petrol Engine power"),
                        Config.SERVER.mediumPetrolEnginePower.get()
                    )
                    .setDefaultValue(Config.SERVER.mediumPetrolEnginePower.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.mediumPetrolEnginePower.set(newValue))
                    .build()
                );
                power.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Small Petrol Engine power"),
                        Config.SERVER.smallPetrolEnginePower.get()
                    )
                    .setDefaultValue(Config.SERVER.smallPetrolEnginePower.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.smallPetrolEnginePower.set(newValue))
                    .build()
                );
                power.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Jet Turbine power"),
                        Config.SERVER.turbinePower.get()
                    )
                    .setDefaultValue(Config.SERVER.turbinePower.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.turbinePower.set(newValue))
                    .build()
                );

                category.addEntry(power.build());
                SubCategoryBuilder energy = entryBuilder.startSubCategory(Component.literal("Energy"));

                energy.add(
                    entryBuilder
                    .startDoubleField(
                        Component.literal("How much Forge Energy = 1 Kinetic Power unit"),
                        Config.SERVER.kineticToFeRate.get()
                    )
                    .setTooltip(Component.literal("This affects Rotation Generator and Electric Motor"))
                    .setDefaultValue(Config.SERVER.kineticToFeRate.getDefault())
                    .setMin(0.01)
                    .setSaveConsumer(newValue -> Config.SERVER.kineticToFeRate.set(newValue))
                    .build()
                );
                energy.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Energy transfer limit"),
                        Config.SERVER.energyTransferLimit.get()
                    )
                    .setTooltip(Component.literal("Maximum amount of energy that can be transfered to/from Warium blocks per tick"))
                    .setDefaultValue(Config.SERVER.energyTransferLimit.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.energyTransferLimit.set(newValue))
                    .build()
                );
                energy.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Cable transfer limit"),
                        Config.SERVER.cableLimit.get()
                    )
                    .setTooltip(Component.literal("Maximum amount of energy that can be transfered to/from Warium blocks per tick"))
                    .setDefaultValue(Config.SERVER.cableLimit.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.cableLimit.set(newValue))
                    .build()
                );
                energy.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Energy Battery capacity (fe)"),
                        Config.SERVER.batteryCapacity.get()
                    )
                    .setDefaultValue(Config.SERVER.batteryCapacity.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.batteryCapacity.set(newValue))
                    .build()
                );
                energy.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Electric Firebox capacity (fe)"),
                        Config.SERVER.electricFireboxCapacity.get()
                    )
                    .setDefaultValue(Config.SERVER.electricFireboxCapacity.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.electricFireboxCapacity.set(newValue))
                    .build()
                );
                energy.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Electric Firebox consumption (fe/t)"),
                        Config.SERVER.electricFireboxConsumption.get()
                    )
                    .setDefaultValue(Config.SERVER.electricFireboxConsumption.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.electricFireboxConsumption.set(newValue))
                    .build()
                );
                energy.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Power Reactor capacity (fe)"),
                        Config.SERVER.reactorCapacity.get()
                    )
                    .setDefaultValue(Config.SERVER.reactorCapacity.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.reactorCapacity.set(newValue))
                    .build()
                );
                energy.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Power Reactor generation (fe/t)"),
                        Config.SERVER.reactorGeneration.get()
                    )
                    .setDefaultValue(Config.SERVER.reactorGeneration.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.reactorGeneration.set(newValue))
                    .build()
                );

                energy.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Solar Generator generation (fe/t)"),
                        Config.SERVER.solarGeneration.get()
                    )
                    .setDefaultValue(Config.SERVER.solarGeneration.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.solarGeneration.set(newValue))
                    .build()
                );

                category.addEntry(energy.build());
            }

            case "Valkyrien Warium" -> {
                category.addEntry(
                    entryBuilder
                    .startIntField(
                        Component.literal("Minimal throttle value"),
                        Config.SERVER.minThrottle.get()
                    )
                    .setDefaultValue(Config.SERVER.minThrottle.getDefault())
                    .setMax(-1)
                    .setSaveConsumer(newValue -> Config.SERVER.minThrottle.set(newValue))
                    .build()
                );
                category.addEntry(
                    entryBuilder
                    .startIntField(
                        Component.literal("Maximum throttle value"),
                        Config.SERVER.maxThrottle.get()
                    )
                    .setDefaultValue(Config.SERVER.maxThrottle.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.maxThrottle.set(newValue))
                    .build()
                );
            }

            case "Create" -> {
                SubCategoryBuilder heat = entryBuilder.startSubCategory(Component.literal("Heat Level"));

                heat.add(
                    entryBuilder
                    .startSelector(
                        Component.literal("Firebox Heat Level"),
                        new String[] { "KINDLED", "SEETHING" },
                        Config.SERVER.fireboxHeat.get()
                    )
                    .setDefaultValue(Config.SERVER.fireboxHeat.getDefault())
                    .setSaveConsumer(newValue -> Config.SERVER.fireboxHeat.set(newValue))
                    .build()
                );
                heat.add(
                    entryBuilder
                    .startSelector(
                        Component.literal("Kerosene Firebox Heat Level"),
                        new String[] { "KINDLED", "SEETHING" },
                        Config.SERVER.oilFireboxHeat.get()
                    )
                    .setDefaultValue(Config.SERVER.oilFireboxHeat.getDefault())
                    .setSaveConsumer(newValue -> Config.SERVER.oilFireboxHeat.set(newValue))
                    .build()
                );
                heat.add(
                    entryBuilder
                    .startSelector(
                        Component.literal("Electric Firebox Heat Level"),
                        new String[] { "KINDLED", "SEETHING" },
                        Config.SERVER.electricFireboxHeat.get()
                    )
                    .setDefaultValue(Config.SERVER.electricFireboxHeat.getDefault())
                    .setSaveConsumer(newValue -> Config.SERVER.electricFireboxHeat.set(newValue))
                    .build()
                );

                category.addEntry(heat.build());

                SubCategoryBuilder ratios = entryBuilder.startSubCategory(Component.literal("Ratios"));

                ratios.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Kinetic Power to su ratio"),
                        Config.SERVER.kineticToStressRatio.get()
                    )
                    .setTooltip(Component.literal("How many Stress Untis will be equivalent to 1 Kinetic Power unit"))
                    .setDefaultValue(Config.SERVER.kineticToStressRatio.getDefault())
                    .setMin(1)
                    .setSaveConsumer(newValue -> Config.SERVER.kineticToStressRatio.set(newValue))
                    .build()
                );
                ratios.add(
                    entryBuilder
                    .startIntField(
                        Component.literal("Kinetic Power to speed ratio"),
                        Config.SERVER.kineticToSpeedRatio.get()
                    )
                    .setTooltip(Component.literal("What Rotation Speed is equivalent to 1 Kinetic Power unit"))
                    .setDefaultValue(Config.SERVER.kineticToSpeedRatio.getDefault())
                    .setMin(1)
                    .setMax(5)
                    .setSaveConsumer(newValue -> Config.SERVER.kineticToSpeedRatio.set(newValue))
                    .build()
                );

                category.addEntry(ratios.build());
                
                category.addEntry(
                    entryBuilder
                    .startBooleanToggle(
                        Component.literal("Kinetic Converter speed control"),
                        Config.SERVER.converterSpeedControl.get()
                    )
                    .setTooltip(Component.literal("Whether Kinetic Converter value box allows to select generating speed"))
                    .setDefaultValue(Config.SERVER.converterSpeedControl.getDefault())
                    .setSaveConsumer(newValue -> Config.SERVER.converterSpeedControl.set(newValue))
                    .build()
                );
                category.addEntry(
                    entryBuilder
                    .startIntField(
                        Component.literal("Kinetic Converter response delay"),
                        Config.SERVER.kineticConverterReponse.get()
                    )
                    .setTooltip(
                        Component.literal("Tick-measured Kinetic Converter response delay when changing throttle"),
                        Component.literal("WARNING: lowering this value may cause shafts to break when changing throttle too fast")
                    )
                    .setDefaultValue(Config.SERVER.kineticConverterReponse.getDefault())
                    .setMin(0)
                    .setMax(40)
                    .setSaveConsumer(newValue -> Config.SERVER.kineticConverterReponse.set(newValue))
                    .build()
                );

                if (WariumAdditions.valkyrien_warium)
                    category.addEntry(
                        entryBuilder
                        .startBooleanToggle(
                            Component.literal("Throttle Rotation Direction Reverse"),
                            Config.SERVER.throttleToRotationDirection.get()
                        )
                        .setTooltip(
                            Component.literal("Whether negative throttle should reverse Create rotation direction"),
                            Component.literal("for Kinetic Converter and TMFG/Diesel Generators engines")
                        )
                        .setDefaultValue(Config.SERVER.throttleToRotationDirection.getDefault())
                        .setSaveConsumer(newValue -> Config.SERVER.throttleToRotationDirection.set(newValue))
                        .build()
                    );
            }
        }
    }
}
