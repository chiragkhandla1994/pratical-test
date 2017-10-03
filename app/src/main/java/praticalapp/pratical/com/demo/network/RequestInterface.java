package praticalapp.pratical.com.demo.network;


import java.util.List;

import io.reactivex.Observable;
import praticalapp.pratical.com.demo.model.ResponseData;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    /*@GET("android/jsonarray/")
    Observable<List<Android>> register();*/


    @GET("/v1/gifs/trending") Observable<ResponseData> getTrendingResults(@Query("limit") final int limit,
                                                                          @Query("api_key") final String apiKey);
}
