package ch.uzh.ifi.seal.soprafs15.group_00_android.models;

import android.os.Parcelable;

import auto.parcel.AutoParcel;
import ch.uzh.ifi.seal.soprafs15.group_00_android.models.gson.AutoGson;

@AutoParcel
@AutoGson
public abstract class RestUri implements Parcelable {

    public abstract String uri();

    public static RestUri create(String uri) {
        return new AutoParcel_RestUri(uri);
    }

}

