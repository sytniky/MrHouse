package com.sytniky.mrhouse.network;

import com.sytniky.mrhouse.model.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yuriy on 02.10.16.
 */
public interface MrHouseClient {

    @GET("city")
    Call<List<City>> getCities();
}
