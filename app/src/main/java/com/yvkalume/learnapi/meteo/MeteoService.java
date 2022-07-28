package com.yvkalume.learnapi.meteo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeteoService {

    @GET("bin/astro.php")
    Call<Meteo> getMeteo(
            @Query("lon") Double longitude,
            @Query("lat") Double latitude,
            @Query("ac") int acceleration,
            @Query("unit") String unit,
            @Query("output") String output,
            @Query("tzshift") int tzshift
    );
}
