package ch.uzh.ifi.seal.soprafs15.group_00_android.models.gson;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import auto.parcel.AutoParcel;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks an {@link AutoParcel @AutoValue}-annotated type for proper Gson serialization.
 * <p>
 * This annotation is needed because the {@linkplain java.lang.annotation.Retention retention} of {@code @AutoParcel}
 * does not allow reflection at runtime.
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface AutoGson {
}
