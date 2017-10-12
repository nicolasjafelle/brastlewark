package altran.brastlewark.app.ui.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.parceler.Parcels;

import altran.brastlewark.app.R;
import altran.brastlewark.app.domain.Citizen;
import altran.brastlewark.app.ui.AbstractAppCompatActivity;
import altran.brastlewark.app.utils.AnimationUtils;

/**
 * Created by nicolas on 10/8/17.
 */

public class DetailActivity extends AbstractAppCompatActivity {

    private ImageView imageView;

    public static void launchActivity(FragmentActivity activity, View view, Citizen citizen) {

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailFragment.SELECTED_CITIZEN, Parcels.wrap(citizen));

        AnimationUtils.launchSceneTransitionAnimation(activity, intent, view);
    }

    @Override
    protected int getBaseLayoutResId() {
        return R.layout.activity_collapse_single_fragment;
    }

    @Override
    protected void setInitialFragment() {
        setInitialFragment(DetailFragment.newInstance(getIntent().getExtras()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If we set this in the root viewgroup will brake the transition animations...
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        setupToolbar();
        ActivityCompat.postponeEnterTransition(this);

        imageView = findViewById(R.id.activity_collapse_single_fragment_image);

        if (getIntent().getExtras() != null) {

            Citizen citizen = Parcels.unwrap(getIntent().getExtras().getParcelable(DetailFragment.SELECTED_CITIZEN));
            setToolbarTitle(citizen.getName());

            RequestCreator creator = Picasso.with(this).load(citizen.getThumbnail()).fit().centerCrop();

            Callback callback = new Callback() {
                @Override
                public void onSuccess() {
                    ActivityCompat.startPostponedEnterTransition(DetailActivity.this);
                }

                @Override
                public void onError() {
                    ActivityCompat.startPostponedEnterTransition(DetailActivity.this);
                }
            };

            creator.into(imageView, callback);
        }
    }

    private void setupToolbar() {
        setToolbarColor(android.R.color.transparent);
        setMaterialStatusBarColor(android.R.color.transparent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
