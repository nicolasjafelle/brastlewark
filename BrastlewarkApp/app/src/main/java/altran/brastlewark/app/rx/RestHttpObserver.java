package altran.brastlewark.app.rx;

import android.support.annotation.Nullable;

import java.net.UnknownHostException;

import altran.brastlewark.app.mvp.BasePresenter;
import altran.brastlewark.app.mvp.RestHttpView;
import altran.brastlewark.app.mvp.ViewState;
import rx.Observer;


public abstract class RestHttpObserver<T> implements Observer<T> {

    private BasePresenter<? extends RestHttpView> presenter;


    public RestHttpObserver(@Nullable BasePresenter<? extends RestHttpView> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof retrofit2.HttpException) {
            try {
                String response = ((retrofit2.HttpException) e).response().errorBody().string();
                int errorCode = ((retrofit2.HttpException) e).code();
                onHttpErrorCode(errorCode, response);

            } catch (Exception e1) {
                e1.printStackTrace();
                onUnknownError(e1);
            }
        } else if (e instanceof UnknownHostException) {
            onHostUnreachable();
        } else {
            onUnknownError(e);
            e.printStackTrace();
        }
        onCompleted();

    }


    private void onUnknownError(Throwable e) {
        if (presenter != null) {
            presenter.getMvpView().onError(e);
            presenter.setCurrentState(ViewState.State.ERROR);
        }
    }

    private void onHostUnreachable() {
        if (presenter != null) {
            presenter.getMvpView().onHostUnreachable();
            presenter.setCurrentState(ViewState.State.ERROR);
        }
    }

    private void onHttpErrorCode(int errorCode, String response) {
        if (presenter != null) {
            presenter.getMvpView().onHttpErrorCode(errorCode, response);
            presenter.setCurrentState(ViewState.State.ERROR);
        }
    }

    @Override
    public void onCompleted() {
        //Do nothing
    }
}
