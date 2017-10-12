package altran.brastlewark.app.ui.main;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import altran.brastlewark.app.api.response.PopulationResponse;
import altran.brastlewark.app.domain.Citizen;
import altran.brastlewark.app.mvp.BasePresenter;
import altran.brastlewark.app.mvp.ViewState;
import altran.brastlewark.app.repository.Repository;
import altran.brastlewark.app.rx.RestHttpObserver;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nicolas on 9/30/17.
 */

class MainPresenter extends BasePresenter<MainView> {

    private static final int MIN_LENGHT = 2;

    PopulationResponse response;

    List<Citizen> filteredList;

    String lastQuery;

    @Inject
    private Repository repository;

    void getPopulationList() {
        setCurrentState(ViewState.State.LOADING);

        final Subscription subscription = repository.populationResponse()
                .delay(1000, TimeUnit.MILLISECONDS) //just to simulate a big transaction...
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RestHttpObserver<PopulationResponse>(this) {

                    @Override
                    public void onNext(PopulationResponse response) {
                        setCurrentState(ViewState.State.FINISH);
                        MainPresenter.this.response = response;
                        getMvpView().onGetData(response);
                    }
                });

        super.compositeSubscription.add(subscription);
    }

    void showAll() {
        MainPresenter.this.filteredList = null;
        MainPresenter.this.lastQuery = null;
        getMvpView().onGetData(response);
    }

    void searchCitizen(String textToSearch) {
        setCurrentState(ViewState.State.LOADING);

        Subscription subscription = Observable.just(textToSearch)
                .subscribeOn(Schedulers.io())
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap(search -> {
                    lastQuery = search;
                    if (search.length() <= MIN_LENGHT) {
                        return Observable.just(response.citizenList);
                    } else {
                        List<Citizen> filteredList = filterByCitizenName(search);
                        return Observable.just(filteredList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RestHttpObserver<List<Citizen>>(this) {
                    @Override
                    public void onNext(List<Citizen> filteredList) {
                        setCurrentState(ViewState.State.FINISH);
                        MainPresenter.this.filteredList = filteredList;
                        getMvpView().onSearchResult(filteredList);
                    }
                });

        compositeSubscription.add(subscription);
    }

    private List<Citizen> filterByCitizenName(String name) {
        List<Citizen> filteredList = new ArrayList<>(0);

        for (Citizen citizen : response.citizenList) {
            if (citizen.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredList.add(citizen);
            }
        }
        return filteredList;
    }
}
