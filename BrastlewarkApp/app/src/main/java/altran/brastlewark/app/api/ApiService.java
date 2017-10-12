package altran.brastlewark.app.api;


import altran.brastlewark.app.api.response.PopulationResponse;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {

    @GET(Endpoints.DATA)
    Observable<PopulationResponse> populationResponse();

}
