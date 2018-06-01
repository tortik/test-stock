package pl.task.stock.service;


import com.google.gson.JsonObject;
import pl.task.stock.model.TimeSeries;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface StockDataClient {

    @GET("/query?outputsize=full&interval=60min")
    Call<JsonObject> getStockRates(@Query("function") TimeSeries timeSeries,
                                   @Query("apikey") String apiKey,
                                   @Query("symbol") String equityName);

}
