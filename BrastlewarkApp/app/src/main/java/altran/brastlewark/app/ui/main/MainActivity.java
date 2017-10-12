package altran.brastlewark.app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import altran.brastlewark.app.R;
import altran.brastlewark.app.domain.Citizen;
import altran.brastlewark.app.ui.AbstractAppCompatActivity;
import altran.brastlewark.app.ui.detail.DetailActivity;
import altran.brastlewark.app.ui.view.MaterialSearchView;

/**
 * Created by nicolas on 9/30/17.
 */

public class MainActivity extends AbstractAppCompatActivity implements MainFragment.Callback {

    private MaterialSearchView searchView;

    public static void launchActivity(FragmentActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSearchView();
    }

    @Override
    protected void setInitialFragment() {
        setInitialFragment(MainFragment.newInstance());
    }

    @Override
    protected int getBaseLayoutResId() {
        return R.layout.activity_single_search_fragment;
    }

    private void initSearchView() {
        searchView = findViewById(R.id.activity_single_fragment_search_view);

        toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                searchView.toolbarPosition(toolbar.getWidth(), toolbar.getHeight());
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return resolveSearch(query);
            }
        });
    }


    private boolean resolveSearch(String query) {
        if (getCurrentFragment() instanceof MainFragment) {
            ((MainFragment) getCurrentFragment()).performSearch(query);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onItemSelect(Citizen citizen, View view) {
        DetailActivity.launchActivity(this, view, citizen);
    }
}
