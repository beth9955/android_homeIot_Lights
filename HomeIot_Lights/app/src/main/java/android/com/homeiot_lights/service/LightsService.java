package android.com.homeiot_lights.service;

import android.com.homeiot_lights.model.Lights;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LightsService {
    @GET("/lights/list")
    Call<List<Lights>> getLightsList();

    @GET("/lights/{id}")
    Call<Lights>  getLights(@Path("id") int id);

    @POST("/lights/new")
    Call<Lights> createLights(@Body Lights lights);

    @POST("/lights/update")
    Call<Lights> updateLights(@Body Lights lights);

    @POST("/lights/delete")
    Call<Integer> deleteLights(@Body Lights lights);
}
