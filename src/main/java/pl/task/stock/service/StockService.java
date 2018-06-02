package pl.task.stock.service;


import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import org.apache.commons.configuration2.Configuration;
import pl.task.stock.config.PropertiesConfig;
import pl.task.stock.config.RetrofitConfig;
import pl.task.stock.model.TimeSeries;
import retrofit2.Response;

import java.util.Optional;
import java.util.function.UnaryOperator;

@Singleton
public class StockService {

    private StockDataClient stockDataClient;
    private Configuration properties;
    private UnaryOperator<String> dataKeyName;

    @Inject
    public StockService(PropertiesConfig properties, RetrofitConfig retrofit) {
        this.stockDataClient = retrofit.getRetrofit().create(StockDataClient.class);
        this.properties = properties.getConfig();
        String keyTemplate = this.properties.getString("app.stock.response.json.key");
        this.dataKeyName = (suffix) -> String.format(keyTemplate, suffix);
    }

    @SneakyThrows
    public Optional<JsonObject> getStockRates(TimeSeries timeSeries, Optional<String> apiKeyOpt, String equityName) {
        String apiKey = apiKeyOpt.orElse(properties.getString("app.stock.api.key"));
        Response<JsonObject> response = stockDataClient.getStockRates(timeSeries, apiKey, equityName).execute();

        return Optional.of(response).filter(Response::isSuccessful).
                map(Response::body).
                map(it -> it.getAsJsonObject(dataKeyName.apply(timeSeries.getSuffix())));
    }





}
