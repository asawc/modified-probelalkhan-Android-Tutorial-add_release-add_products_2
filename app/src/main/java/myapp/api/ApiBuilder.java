package myapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {

    private final String BASE_URL = URLs.BASE_URL;
    private Retrofit mRetrofit;
    private Gson mGson;

    public ApiBuilder() {
        mGson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ss")
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    public MyApi getApiService() {
        return mRetrofit.create(MyApi.class);
    }
}
