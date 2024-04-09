package minhnqph38692.fpoly.slide5.service;

import java.util.ArrayList;

import minhnqph38692.fpoly.slide5.modal.Distributor;
import minhnqph38692.fpoly.slide5.modal.Fruit;
import minhnqph38692.fpoly.slide5.modal.Response;
import minhnqph38692.fpoly.slide5.modal.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    public static String BASE_URL="http://10.0.2.2:3000/api/";

    @GET("get-list-distributor")
    Call<Response<ArrayList<Distributor>>> getListDistributor();

    @GET("search-distributor")
    Call<Response<ArrayList<Distributor>>> searchDistributor(@Query("key") String key);

    @DELETE("delete-distributor-by-id/{id}")
    Call<Response<Distributor>> deleteDistributorById(@Path("id") String id);

    @POST("add-distributor")
    Call<Response<Distributor>> addDistributor(@Body Distributor distributor);

    @PUT("update-distributor-by-id/{id}")
    Call<Response<Distributor>> updateDistributorById(@Path("id") String id,@Body Distributor distributor);

    @Multipart
    @POST("register-send-email")
    Call<Response<User>> register(@Part("username")RequestBody username,
                                  @Part("password")RequestBody password,
                                  @Part("email")RequestBody email,
                                  @Part("name")RequestBody name,
                                  @Part MultipartBody.Part avatar);

    @POST("login")
    Call<Response<User>> login(@Body User user);

    @GET("get-list-fruit")
    Call<Response<ArrayList<Fruit>>> getListFruit(@Header("Authorization") String token);



}
