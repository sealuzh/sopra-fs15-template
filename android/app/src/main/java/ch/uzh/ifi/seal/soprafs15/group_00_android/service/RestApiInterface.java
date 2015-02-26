package ch.uzh.ifi.seal.soprafs15.group_00_android.service;

import java.util.List;

import ch.uzh.ifi.seal.soprafs15.group_00_android.models.RestUri;
import ch.uzh.ifi.seal.soprafs15.group_00_android.models.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

public interface RestApiInterface {

    /**
     * Returns all users
     *
     * @param cb
     */
    @GET("/user")
    void getUsers(Callback<List<User>> cb);

    /**
     * Register a new user on the server
     * @User user: User to register
     * @param cb
     */
    @PUT("/user")
    void createUser(@Body User user, Callback<RestUri> cb);


}
