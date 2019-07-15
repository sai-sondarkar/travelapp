package edu.itm.natravelapp.Network;

import com.google.gson.JsonObject;

import java.util.List;

import edu.itm.natravelapp.Model.RequestModel;
import edu.itm.natravelapp.Model.RetroPhoto;
import edu.itm.natravelapp.Model.TravelModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface GetDataService {

    @Headers({"Content-Type: application/json"})
    @POST("search")
    Call<List<TravelModel>> savePost(@Body JsonObject jsonRequest);

}