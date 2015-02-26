package ch.uzh.ifi.seal.soprafs15.group_00_android.service;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.uzh.ifi.seal.soprafs15.group_00_android.models.gson.AutoValueAdapterFactory;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class RestService {

    private final static String baseUrl = "http://10.0.2.2:26011/"; // 10.0.2.2 for Android Emulator

    public RestApiInterface restApiInterface;
    private static RestService instance;

    private RestService(Context context, Client client) {

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoValueAdapterFactory()).create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(client)
                .setConverter(new GsonConverter(gson))
                .build();

        boolean isDebuggable = (0 != (context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));

        if (isDebuggable) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
        }

        restApiInterface = restAdapter.create(RestApiInterface.class);
    }

    public static RestApiInterface getInstance(Context context) {

        if (instance == null) {
            instance = new RestService(context, new OkClient());
        }

        return instance.restApiInterface;
    }

}
