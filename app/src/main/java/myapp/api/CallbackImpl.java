package myapp.api;

import android.util.Log;

import java.util.List;

import myapp.model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackImpl<T> implements Callback<T> {

    private T responseBody;
    private String tag;

    public CallbackImpl() {
        responseBody = null;
        tag = "RETROFIT_RESPONSE";
    }

    public CallbackImpl(String tag) {
        responseBody = null;
        this.tag = tag;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        int statusCode = response.code();
        this.responseBody = response.body();

        Log.d(tag, response.message());
        Log.d(tag, String.valueOf(statusCode));
        Log.d(tag, response.toString());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(tag, "FAIL");
        Log.d(tag, t.getMessage());
        Log.d(tag, t.getLocalizedMessage());
        t.printStackTrace();
    }

    public String getTag() {
        return tag;
    }

    public T getResponseBody() {
        return responseBody;
    }

}
