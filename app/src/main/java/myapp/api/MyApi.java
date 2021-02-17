package myapp.api;

import java.util.List;

import myapp.model.Employee;
import myapp.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;

import static myapp.api.URLs.URL_GET_ALL_EMPLOYEES;
import static myapp.api.URLs.URL_GET_ALL_PRODUCTS;

public interface MyApi {

    @GET(URL_GET_ALL_EMPLOYEES)
    Call<ResponseContainer<List<Employee>>> getEmployees();

    @GET(URL_GET_ALL_PRODUCTS)
    Call<ResponseContainer<List<Product>>> getProducts();

    // @Query("apicall") String apiCall
    /*
    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<User> createUser(@Body User user);
    */
}
