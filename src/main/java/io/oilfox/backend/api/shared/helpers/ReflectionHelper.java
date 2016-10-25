package io.oilfox.backend.api.shared.helpers;

import java.lang.reflect.Field;

/**
 * Created by ben on 24/01/16.
 */
public final class ReflectionHelper {
    public static final Object getPrivateField(Object instance, String name) {
        try {
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);

            return field.get(instance);

        } catch (NoSuchFieldException e) {

            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {

            e.printStackTrace();
            return null;
        }
    }
}
