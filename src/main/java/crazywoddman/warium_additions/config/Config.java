package crazywoddman.warium_additions.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.ValueSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class Config {
    public enum ValueTypes {INTEGER, DOUBLE, BOOLEAN, STRING}
    private static final List<ConfigValue<?>> VALUES = new ArrayList<>();
    private static final List<String> NAMES = new ArrayList<>();
    private static final Builder builder = new Builder() {
        @Override
        public <T> ConfigValue<T> define(List<String> path, ValueSpec value, Supplier<T> defaultSupplier) {
            ConfigValue<T> configValue = super.define(path, value, defaultSupplier);
            VALUES.add(configValue);
            NAMES.add(path.get(path.size() - 1));
            return configValue;
        }
    };
    public static final Server SERVER = new Server(builder);
    private static final ForgeConfigSpec SERVER_SPEC = builder.build();

    public static void register(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
    }

    public static int getIndex(ConfigValue<?> value) {
        List<String> path = value.getPath();
        return NAMES.indexOf(path.get(path.size() - 1));
    }

    @SuppressWarnings("unchecked")
    public static void set(int index, Object value) {
        ((ConfigValue<Object>)VALUES.get(index)).set(value);
    }

    public static class Server {
        public final IntValue fuelTanksCapacity;
        public final IntValue refineryCapacity;
        public final IntValue refineryTowerCapacity;

        public final IntValue machinesMinPower;
        public final IntValue mediumDieselEnginePower;
        public final IntValue smallDieselEnginePower;
        public final IntValue mediumPetrolEnginePower;
        public final IntValue smallPetrolEnginePower;
        public final IntValue largeEnginePower;
        public final IntValue jetTurbinePower;

        public final DoubleValue kineticToFeRate;
        public final IntValue energyTransferLimit;
        public final IntValue batteryCapacity;
        public final IntValue electricFireboxCapacity;
        public final IntValue electricFireboxConsume;
        public final IntValue powerReactorCapacity;
        public final IntValue powerReactorGeneration;
        public final IntValue solarGeneration;
        public final IntValue motorConsumeLimit;

        public final BooleanValue creativeAmmoConsume;
        public final IntValue heatRequirement;

        public final IntValue maxThrottle;

        public final IntValue kineticToStressRatio;
        public final IntValue kineticToSpeedRatio;
        public final ConfigValue<String> fireboxHeatLevel;
        public final ConfigValue<String> keroseneFireboxHeatLevel;
        public final ConfigValue<String> electricFireboxHeatLevel;

        public Server(Builder builder) {
            builder.push("Warium").push("Fluid");
            fuelTanksCapacity = builder
            .comment("Fluid capacity of fuel tanks (mB)")
            .defineInRange("fuelTanksCapacity", 1000, 1, Integer.MAX_VALUE);
            refineryCapacity = builder
            .comment("Fluid capacity of Oil Refinery (mB)")
            .defineInRange("refineryCapacity", 1000, 1000, Integer.MAX_VALUE);
            refineryTowerCapacity = builder
            .comment("Fluid capacity of Refinery Tower (mB)")
            .defineInRange("refineryTowerCapacity", 16000, 1, Integer.MAX_VALUE);
            builder.pop().push("Power");
            machinesMinPower = builder
            .comment("REQUIRES «WARIUM PONDER & JEI» TO TAKE EFFECT")
            .comment("Minimal Kinetic Power required for assembly machines to function")
            .defineInRange("machinesMinPower", 30, 0, Integer.MAX_VALUE);
            mediumDieselEnginePower = builder.defineInRange("mediumDieselEnginePower", 50, 1, Integer.MAX_VALUE);
            smallDieselEnginePower = builder.defineInRange("smallDieselEnginePower", 35, 1, Integer.MAX_VALUE);
            mediumPetrolEnginePower = builder.defineInRange("mediumPetrolEnginePower", 60, 1, Integer.MAX_VALUE);
            smallPetrolEnginePower = builder.defineInRange("smallPetrolEnginePower", 40, 1, Integer.MAX_VALUE);
            largeEnginePower = builder.defineInRange("largeEnginePower", 50, 1, Integer.MAX_VALUE);
            jetTurbinePower = builder.defineInRange("jetTurbinePower", 51, 1, Integer.MAX_VALUE);
            builder.pop().push("Energy");
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
            electricFireboxConsume = builder
            .comment("Electric Firebox energy consumption (fe/40t)")
            .defineInRange("electricFireboxConsume", 1000, 1, Integer.MAX_VALUE);
            powerReactorCapacity = builder
            .comment("Power Reactor energy capacity (fe)")
            .defineInRange("powerReactorCapacity", 800_000, 1, Integer.MAX_VALUE);
            powerReactorGeneration = builder
            .comment("Power Reactor energy generation (fe/t)")
            .defineInRange("powerReactorGeneration", 300, 1, Integer.MAX_VALUE);
            solarGeneration = builder
            .comment("Solar Generator energy generation (fe/t)")
            .defineInRange("solarGeneration", 5, 1, Integer.MAX_VALUE);
            motorConsumeLimit = builder
            .comment("Electric Motor max consumption (fe/t)")
            .defineInRange("motorConsumeLimit", 50, 1, Integer.MAX_VALUE);
            builder.pop();
            creativeAmmoConsume = builder
            .comment("Consume ammo in Creative Mode")
            .define("creativeAmmoConsume", false);
            heatRequirement = builder
            .comment("REQUIRES «WARIUM PONDER & JEI» TO TAKE EFFECT")
            .comment("Heat amount needed for resource processors that require heat to function")
            .defineInRange("heatRequirement", 200, 0, 1000);
            builder.pop();
            
            builder.push("Valkyrien Warium");
            maxThrottle = builder
            .comment("Maximum throttle value that can be set using Control Seat")
            .defineInRange("maxThrottle", 10, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("Create").push("Heat");
            fireboxHeatLevel = builder
            .comment("Blaze Burner type heat level for fireboxes")
            .comment("Allowed values are KINDLED or SEETHING")
            .define("fireboxHeatLevel", "KINDLED", value -> "KINDLED".equals(value) || "SEETHING".equals(value));
            keroseneFireboxHeatLevel = builder.define("keroseneFireboxHeatLevel", "SEETHING", value -> "KINDLED".equals(value) || "SEETHING".equals(value));
            electricFireboxHeatLevel = builder.define("electricFireboxHeatLevel", "SEETHING", value -> "KINDLED".equals(value) || "SEETHING".equals(value));
            builder.pop();
            kineticToStressRatio = builder
            .comment("How many Stress Units will be equivalent to 1 Kinetic Power unit")
            .defineInRange("kineticToStressRatio", 40, 1, Integer.MAX_VALUE);
            kineticToSpeedRatio = builder
            .comment("What Rotation Speed is equivalent to 1 Kinetic Power unit")
            .defineInRange("kineticToSpeedRatio", 2, 1, 5);
            builder.pop();
        }
    }
}
