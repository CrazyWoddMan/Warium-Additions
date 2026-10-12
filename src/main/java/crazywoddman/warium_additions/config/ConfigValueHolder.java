package crazywoddman.warium_additions.config;

import java.util.List;
import java.util.function.Predicate;

import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public record ConfigValueHolder(ConfigValue<?> value, Predicate<Object> validator) {

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T)this.value.get();
    }

    @SuppressWarnings("unchecked")
    public <T> T getDefault() {
        return (T)this.value.getDefault();
    }

    @SuppressWarnings("unchecked")
    public <T> void set(T value) {
        ((ConfigValue<T>)this.value).set(value);
    }

    public boolean test(Object value) {
        return this.validator.test(value);
    }

    public List<String> getPath() {
        return this.value.getPath();
    }
}