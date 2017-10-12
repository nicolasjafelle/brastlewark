package altran.brastlewark.app.ui.main;

import java.util.List;

import altran.brastlewark.app.api.response.PopulationResponse;
import altran.brastlewark.app.domain.Citizen;
import altran.brastlewark.app.mvp.RestHttpView;

/**
 * Created by nicolas on 9/30/17.
 */

public interface MainView extends RestHttpView {

    void onGetData(PopulationResponse response);

    void onSearchResult(List<Citizen> filteredList);
}
