package crazywoddman.warium_additions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final Server SERVER = new Server(builder);
    public static final ForgeConfigSpec SERVER_SPEC = builder.build();

    public static class Server {
        public final ForgeConfigSpec.IntValue fuelTanksCapacity;
        public final ForgeConfigSpec.IntValue refineryTowerCapacity;

        public final ForgeConfigSpec.IntValue mediumDieselEnginePower;
        public final ForgeConfigSpec.IntValue smallDieselEnginePower;
        public final ForgeConfigSpec.IntValue mediumPetrolEnginePower;
        public final ForgeConfigSpec.IntValue smallPetrolEnginePower;
        public final ForgeConfigSpec.IntValue largeEnginePower;
        public final ForgeConfigSpec.IntValue jetTurbinePower;

        public final ForgeConfigSpec.DoubleValue kineticToFeRate;
        public final ForgeConfigSpec.IntValue energyTransferLimit;
        public final ForgeConfigSpec.IntValue batteryCapacity;
        public final ForgeConfigSpec.IntValue electricFireboxCapacity;
        public final ForgeConfigSpec.IntValue electricFireboxConsumption;
        public final ForgeConfigSpec.IntValue powerReactorCapacity;
        public final ForgeConfigSpec.IntValue powerReactorGeneration;
        public final ForgeConfigSpec.IntValue solarGeneration;
        public final ForgeConfigSpec.IntValue motorConsumptionLimit;

        public final ForgeConfigSpec.IntValue minThrottle;
        public final ForgeConfigSpec.IntValue maxThrottle;

        public final ForgeConfigSpec.IntValue kineticToStressRatio;
        public final ForgeConfigSpec.IntValue kineticToSpeedRatio;
        public final ForgeConfigSpec.BooleanValue kineticConverterSpeedControl;
        public final ForgeConfigSpec.IntValue kineticConverterResponse;
        public final ForgeConfigSpec.ConfigValue<String> fireboxHeatLevel;
        public final ForgeConfigSpec.ConfigValue<String> keroseneFireboxHeatLevel;
        public final ForgeConfigSpec.ConfigValue<String> electricFireboxHeatLevel;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("Warium");
                builder.push("Fluid");
                    fuelTanksCapacity = builder
                        .comment("Fluid capacity of fuel tanks (mB)")
                        .defineInRange("fuelTanksCapacity", 1000, 1, Integer.MAX_VALUE);
                    refineryTowerCapacity = builder
                        .comment("Fluid capacity of Refinery Tower (mB)")
                        .defineInRange("refineryTowerCapacity", 16000, 1, Integer.MAX_VALUE);
                builder.pop();
                builder.push("Power");
                    mediumDieselEnginePower = builder.defineInRange(
                        "mediumDieselEnginePower",
                        50,
                        1,
                        Integer.MAX_VALUE
                    );
                    smallDieselEnginePower = builder.defineInRange(
                        "smallDieselEnginePower",
                        35,
                        1,
                        Integer.MAX_VALUE
                    );
                    mediumPetrolEnginePower = builder.defineInRange(
                        "mediumPetrolEnginePower",
                        60,
                        1,
                        Integer.MAX_VALUE
                    );
                    smallPetrolEnginePower = builder.defineInRange(
                        "smallPetrolEnginePower",
                        40,
                        1,
                        Integer.MAX_VALUE
                    );
                    largeEnginePower = builder.defineInRange(
                        "largeEnginePower",
                        50,
                        1,
                        Integer.MAX_VALUE
                    );
                    jetTurbinePower = builder.defineInRange(
                        "jetTurbinePower",
                        51,
                        1,
                        Integer.MAX_VALUE
                    );
                builder.pop();
                builder.push("Energy");
                    kineticToFeRate = builder
                        .comment("How much Forge Energy = 1 Kinetic Power unit (for Rotation Generator and Electric Motor)")
                        .defineInRange("kineticToFeRate", 1, 0.01, Double.MAX_VALUE);
                    energyTransferLimit = builder
                        .comment("Maximum amount of energy that can be transfered to/from Warium blocks per tick")
                        .defineInRange("energyTransferLimit", 1_000, 1, Integer.MAX_VALUE);
                    batteryCapacity = builder
                        .comment("Energy Battery energy capacity (fe)")
                        .defineInRange("batteryCapacity", 800_000, 1, Integer.MAX_VALUE);
                    electricFireboxCapacity = builder
                        .comment("Electric Firebox energy capacity (fe)")
                        .defineInRange("electricFireboxCapacity", 2_000, 1, Integer.MAX_VALUE);
                    electricFireboxConsumption = builder
                        .comment("Electric Firebox energy consumption (fe/40t)")
                        .defineInRange("electricFireboxConsumption", 1000, 1, Integer.MAX_VALUE);
                    powerReactorCapacity = builder
                        .comment("Power Reactor energy capacity (fe)")
                        .defineInRange("powerReactorCapacity", 800_000, 1, Integer.MAX_VALUE);
                    powerReactorGeneration = builder
                        .comment("Power Reactor energy generation (fe/t)")
                        .defineInRange("powerReactorGeneration", 200, 1, Integer.MAX_VALUE);
                    solarGeneration = builder
                        .comment("Solar Generator energy generation (fe/t)")
                        .defineInRange("solarGeneration", 5, 1, Integer.MAX_VALUE);
                    motorConsumptionLimit = builder
                        .comment("Electric Motor max consumption (fe/t)")
                        .defineInRange("motorConsumptionLimit", 50, 1, Integer.MAX_VALUE);
                builder.pop();
            builder.pop();

            builder.push("Valkyrien Warium");
                minThrottle = builder
                    .comment("Minimal throttle value that can be set using Control Seat")
                    .defineInRange("minThrottle", -10, Integer.MIN_VALUE, -1);
                maxThrottle = builder
                    .comment("Maximum throttle value that can be set using Control Seat")
                    .defineInRange("maxThrottle", 10, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("Create");
                builder.push("Heat");
                    fireboxHeatLevel = builder
                        .comment("Blaze Burner type heat level for fireboxes")
                        .comment("Allowed values are KINDLED or SEETHING")
                        .define("fireboxHeatLevel", "KINDLED", value ->
                            "KINDLED".equals(value) || "SEETHING".equals(value)
                        );
                    keroseneFireboxHeatLevel = builder
                        .define("keroseneFireboxHeatLevel", "SEETHING", value ->
                            "KINDLED".equals(value) || "SEETHING".equals(value)
                        );
                    electricFireboxHeatLevel = builder
                        .define("electricFireboxHeatLevel", "SEETHING", value ->
                            "KINDLED".equals(value) || "SEETHING".equals(value)
                    );
                builder.pop();
                kineticToStressRatio = builder
                    .comment("How many Stress Units will be equivalent to 1 Kinetic Power unit")
                    .defineInRange("kineticToStressRatio", 40, 1, Integer.MAX_VALUE);
                kineticToSpeedRatio = builder
                    .comment("What Rotation Speed is equivalent to 1 Kinetic Power unit")
                    .defineInRange("kineticToSpeedRatio", 2, 1, 5);
                // TODO Deprecated?
                kineticConverterSpeedControl = builder
                    .comment("Whether Kinetic Converter value box allows to select generating speed")
                    .define("kineticConverterSpeedControl", false);
                kineticConverterResponse = builder
                    .comment("Tick-measured Kinetic Converter response delay when changing throttle")
                    .comment("WARNING: lowering this value may cause shafts to break when changing throttle too fast")
                    .defineInRange("kineticConverterResponse", 8, 0, 40);
            builder.pop();
        }
    }
}
