package com.example.amnuaychaipoosri.bg_parallax_carousel_view.manager;

import android.util.Log;

import com.example.amnuaychaipoosri.bg_parallax_carousel_view.constant.Constant;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.manager.http.ApiService;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpManager {

    private String url = null;
    private static int currentMode = 0;

    private static HttpManager instance;
    private ApiService service;
    private Retrofit retrofit;
    private static Interceptor myInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            // Logging, active only in debug mode
            long t1 = System.nanoTime();
            Log.d("retrofit", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d("retrofit", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            final String responseString = new String(response.body().bytes());

            Log.d("retrofit", "Response: " + responseString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), responseString))
                    .build();
        }
    };

    public static HttpManager getInstance(int mode) {
        if (instance == null) {
            instance = new HttpManager(Constant.Mode.MODE_DEVELOPMENT);
        } else if (currentMode != Constant.Mode.MODE_DEVELOPMENT) {
            instance = new HttpManager(Constant.Mode.MODE_DEVELOPMENT);
        }
        currentMode = Constant.Mode.MODE_DEVELOPMENT;
        return instance;
    }

    public ApiService getService() {
        return service;
    }

    private HttpManager(int mode) {

        Type valuesClassListType = new TypeToken<BaseResponse>() {}.getType();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(valuesClassListType, new ValueTypeAdapter())
                .create();

        switch (mode) {
            case 1:
                url = "http://www.mocky.io/v2/";
                break;
            case 2:
                url = "http://www.mocky.io/v2/";
                break;

        }

        if (url != null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(myInterceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            service = retrofit.create(ApiService.class);
        }
    }


    private static class ValueTypeAdapter implements JsonDeserializer<BaseResponse> {

        public BaseResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {

            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.get("responseObject").isJsonNull()) {
                Integer code = jsonObject.get("code").getAsInt();
                String message = jsonObject.get("message").getAsString();
                return new BaseResponse(code, message);
            }
            return new Gson().fromJson(json, typeOfT);
        }
    }
}
