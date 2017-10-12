package altran.brastlewark.app.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import altran.brastlewark.app.ui.main.MainActivity;

/**
 * Created by nicolas on 2/25/16.
 */
public class StarterActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.launchActivity(this);
        finish();
    }

}
