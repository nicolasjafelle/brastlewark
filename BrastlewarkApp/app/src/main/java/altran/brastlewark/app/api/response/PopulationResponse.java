package altran.brastlewark.app.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import altran.brastlewark.app.domain.Citizen;

/**
 * Created by nicolas on 9/30/17.
 */

public class PopulationResponse {

    @SerializedName("Brastlewark")
    public List<Citizen> citizenList;

}
