package altran.brastlewark.app.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import java.util.List;

import altran.brastlewark.app.R;
import altran.brastlewark.app.api.response.PopulationResponse;
import altran.brastlewark.app.domain.Citizen;
import altran.brastlewark.app.ui.AbstractFragment;
import altran.brastlewark.app.ui.view.CitizenItemView;
import altran.brastlewark.app.ui.view.LoadingView;
import altran.brastlewark.app.utils.SpacesItemDecoration;

/**
 * Created by nicolas on 9/30/17.
 */

public class MainFragment extends AbstractFragment<MainFragment.Callback>
        implements MainView,
        LoadingView.Callback,
        RecyclerOnItemClickListener {

    private LoadingView loadingView;

    @Inject
    private MainPresenter presenter;

    private RecyclerView recyclerView;
    private CitizenAdapter adapter;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getMainLayoutResId() {
        return R.layout.fragment_common_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_common_list_recycler);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attachMvpView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachMvpView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_show_all) {
            loadingView.show();
            presenter.showAll();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingView = new LoadingView(getContext());
        loadingView.attach((ViewGroup) view, false, this);
        loadingView.setThemeBackgroundColor();

        setupRecyclerView();

        if (presenter.isIdle()) {
            fetchData();
        } else if (presenter.isFinished()) {
            if (presenter.filteredList != null) {
                onSearchResult(presenter.filteredList);
            } else {
                onGetData(presenter.response);
            }

        } else if (presenter.isLoading()) {
            loadingView.show();
        } else if (presenter.hasFailed()) {
            loadingView.showErrorView();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                getResources().getInteger(R.integer.span_count));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getInteger(R.integer.span_count), 40));

        adapter = new CitizenAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    public void performSearch(String textToSearch) {
        loadingView.show();
        presenter.searchCitizen(textToSearch);
    }

    @Override
    public void onItemClickListener(int itemPosition) {
        CitizenItemView view = (CitizenItemView) recyclerView.findViewHolderForLayoutPosition(itemPosition).itemView;
        callbacks.onItemSelect(adapter.getItemAt(itemPosition), view.imageView);
    }

    private void fetchData() {
        loadingView.show();
        presenter.getPopulationList();
    }

    @Override
    public void onHostUnreachable() {
        loadingView.showErrorView(R.string.connection_error_try_again);
    }

    @Override
    public void onHttpErrorCode(int errorCode, String response) {
        loadingView.showErrorView(getString(R.string.problem_while_connecting, errorCode, response));
    }

    @Override
    public void onGetData(PopulationResponse response) {
        if (response != null) {
            loadingView.dismiss(true);
            adapter.addList(response.citizenList);
        } else {
            loadingView.showNoContentView();
        }

    }

    @Override
    public void onSearchResult(List<Citizen> filteredList) {
        if (!filteredList.isEmpty()) {
            loadingView.dismiss(true);
            adapter.addList(filteredList);
        } else {
            loadingView.showLabel(R.string.no_matches);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        loadingView.showErrorView();
    }

    @Override
    public void onRetryClick() {
        fetchData();
    }

    public interface Callback {
        void onItemSelect(Citizen citizen, View view);
    }
}
