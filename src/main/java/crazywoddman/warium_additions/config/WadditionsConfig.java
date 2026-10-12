package crazywoddman.warium_additions.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class WadditionsConfig {
    public static final List<ConfigValueHolder> VALUES = new ArrayList<>();
    private static final ForgeConfigSpec SPEC;

    public static final IntValue FUEL_TANKS_CAPACITY;
    public static final IntValue REFINERY_CAPACITY;
    public static final IntValue REFINERY_TOWER_CAPACITY;

    public static final IntValue MEDIUM_DIESEL_ENGINE_POWER;
    public static final IntValue SMALL_DIESEL_ENGINE_POWER;
    public static final IntValue MEDIUM_PETROL_ENGINE_POWER;
    public static final IntValue SMALL_PETROL_ENGINE_POWER;
    public static final IntValue LARGE_ENGINE_POWER;
    public static final IntValue JET_TURBINE_POWER;
    public static final IntValue LIGHT_TURBINE_ENGINE_POWER;

    public static final BooleanValue CREATIVE_AMMO_CONSUME;

    public static final IntValue KINETIC_TO_STRESS;
    public static final IntValue KINETIC_TO_SPEED;
    public static final ConfigValue<String> FIREBOX_HEAT;
    public static final ConfigValue<String> KEROSENE_FIREBOX_HEAT;
    public static final ConfigValue<String> ELECTRIC_FIREBOX_HEAT;

    static {
        Builder builder = new Builder();

        builder.push("Warium");

        CREATIVE_AMMO_CONSUME = builder
        .comment("Consume ammo in Creative Mode")
        .define("creativeAmmoConsume", false);
        
        builder.push("Fluid");
        FUEL_TANKS_CAPACITY = builder
        .comment("Fluid capacity of fuel tanks (mB)")
        .defineInRange("fuelTanksCapacity", 1000, 1, Integer.MAX_VALUE);
        REFINERY_CAPACITY = builder
        .comment("Fluid capacity of Oil Refinery (mB)")
        .defineInRange("refineryCapacity", 1000, 1000, Integer.MAX_VALUE);
        REFINERY_TOWER_CAPACITY = builder
        .comment("Fluid capacity of Refinery Tower (mB)")
        .defineInRange("refineryTowerCapacity", 16000, 1, Integer.MAX_VALUE);
        builder.pop();
        
        builder.push("Power");
        MEDIUM_DIESEL_ENGINE_POWER = builder.defineInRange("mediumDieselEnginePower", 50, 1, Integer.MAX_VALUE);
        SMALL_DIESEL_ENGINE_POWER = builder.defineInRange("smallDieselEnginePower", 35, 1, Integer.MAX_VALUE);
        MEDIUM_PETROL_ENGINE_POWER = builder.defineInRange("mediumPetrolEnginePower", 60, 1, Integer.MAX_VALUE);
        SMALL_PETROL_ENGINE_POWER = builder.defineInRange("smallPetrolEnginePower", 40, 1, Integer.MAX_VALUE);
        LARGE_ENGINE_POWER = builder.defineInRange("largeEnginePower", 50, 1, Integer.MAX_VALUE);
        JET_TURBINE_POWER = builder.defineInRange("jetTurbinePower", 51, 1, Integer.MAX_VALUE);
        LIGHT_TURBINE_ENGINE_POWER = builder.defineInRange("lightTurbineEnginePower", 65, 1, Integer.MAX_VALUE);
        builder.pop();
        
        builder.pop();

        builder.push("Create");

        builder.push("Heat");
        SelectorValidator validator = new SelectorValidator("KINDLED", "SEETHING");
        FIREBOX_HEAT = builder
        .comment("Blaze Burner type heat level for fireboxes")
        .comment("Allowed values are KINDLED or SEETHING")
        .define("fireboxHeatLevel", "KINDLED", validator);
        KEROSENE_FIREBOX_HEAT = builder.define("keroseneFireboxHeatLevel", "SEETHING", validator);
        ELECTRIC_FIREBOX_HEAT = builder.define("electricFireboxHeatLevel", "SEETHING", validator);
        builder.pop();

        builder.push("Ratios");
        KINETIC_TO_STRESS = builder
        .comment("How many Stress Units will be equivalent to 1 Kinetic Power unit")
        .defineInRange("kineticToStressRatio", 40, 1, Integer.MAX_VALUE);
        KINETIC_TO_SPEED = builder
        .comment("What Rotation Speed is equivalent to 1 Kinetic Power unit")
        .defineInRange("kineticToSpeedRatio", 2, 1, 5);
        builder.pop();

        builder.pop();

        SPEC = builder.build();
    }

    public static void register(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.SERVER, SPEC);
    }

    private static class Builder extends ForgeConfigSpec.Builder {

        @Override
        public <T> ConfigValue<T> define(List<String> path, Supplier<T> defaultSupplier, Predicate<Object> validator, Class<?> clazz) {
            ConfigValue<T> value = super.define(path, defaultSupplier, validator, clazz);
            VALUES.add(new ConfigValueHolder(value, validator));
            return value;
        }
    }
}
