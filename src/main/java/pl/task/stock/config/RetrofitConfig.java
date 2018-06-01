package pl.task.stock.config;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Getter
public class RetrofitConfig {

    private Retrofit retrofit;

    @Inject
    public RetrofitConfig(PropertiesConfig properties) {
        retrofit = new Retrofit.Builder()
                .baseUrl(properties.getConfig().getString("app.stock.base.url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
