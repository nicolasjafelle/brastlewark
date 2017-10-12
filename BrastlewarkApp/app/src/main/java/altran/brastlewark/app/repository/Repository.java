package altran.brastlewark.app.repository;

import com.google.inject.Inject;

import altran.brastlewark.app.api.response.PopulationResponse;
import rx.Observable;

/**
 * Created by nicolas on 10/5/17.
 */

public class Repository {

    @Inject
    private ApiClient apiClient;

    public Observable<PopulationResponse> populationResponse() {
        return apiClient.populationResponse();
    }

}
