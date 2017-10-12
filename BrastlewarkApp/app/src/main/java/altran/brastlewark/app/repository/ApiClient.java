package altran.brastlewark.app.repository;

import com.google.inject.Inject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import altran.brastlewark.app.BrastlewarkApplication;
import altran.brastlewark.app.BuildConfig;
import altran.brastlewark.app.api.ApiService;
import altran.brastlewark.app.api.response.PopulationResponse;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by nicolas on 6/27/17.
 */

class ApiClient {

    private static final int TIMEOUT_MILLIS = 40000;
    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;

    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;


    @Inject
    private HttpLoggingInterceptor loggingInterceptor;

    private Retrofit retrofit;

    private ApiService apiService;


    public ApiClient() {
        if (retrofit == null || apiService == null) {

            BrastlewarkApplication.injectMembers(this);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.HOST)
                    .client(createDefaultOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
    }

    private OkHttpClient createDefaultOkHttpClient() {

        final File cacheDir = new File(BrastlewarkApplication.getAppContext().getCacheDir(), "http");
        final Cache cacheInstance = new Cache(cacheDir, DISK_CACHE_SIZE);

        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true)
                .cache(cacheInstance)
                .connectTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
                .readTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
                .writeTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT);
//            builder.addNetworkInterceptor(loggingInterceptor);
        builder.addInterceptor(loggingInterceptor);

        return builder.build();
    }

    Observable<PopulationResponse> populationResponse() {
        return apiService.populationResponse();
    }


}
