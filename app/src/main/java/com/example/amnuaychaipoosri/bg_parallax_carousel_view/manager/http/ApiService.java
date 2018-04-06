package com.example.amnuaychaipoosri.bg_parallax_carousel_view.manager.http;


import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.BaseResponse;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel.ItemsListModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("5ac4a8192f00003600f5faed")
    Call<BaseResponse<ItemsListModel>> loadHomeData();
}
