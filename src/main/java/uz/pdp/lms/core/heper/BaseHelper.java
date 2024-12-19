package uz.pdp.lms.core.heper;

import java.util.function.Consumer;

public class BaseHelper {

    public static <T> void updateIfPresent(T value, Consumer<T> updater) {
        if (value != null && !(value instanceof String && ((String) value).isBlank())) {
            updater.accept(value);
        }
    }

}
