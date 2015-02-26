package ch.uzh.ifi.seal.soprafs15.group_00_android.models.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/*
 * A Gson TypeAdapterFactory which allows serialization of @AutoValue types. Apache 2 licensed.
 * According to: https://gist.github.com/JakeWharton/0d67d01badcee0ae7bc9
 */

public final class AutoValueAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")

    @Override public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if (!rawType.isAnnotationPresent(AutoGson.class)) {
            return null;
        }

        String packageName = rawType.getPackage().getName();
        String className = rawType.getName().substring(packageName.length() + 1).replace('$', '_');
        String autoValueName = packageName + ".AutoParcel_" + className;

        try {
            Class<?> autoValueType = Class.forName(autoValueName);
            return (TypeAdapter<T>) gson.getAdapter(autoValueType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not load AutoParcel type " + autoValueName, e);
        }
    }
}