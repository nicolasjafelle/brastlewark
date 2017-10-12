package altran.brastlewark.app.mvp;

import com.google.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Base Presenter class (P from MVP). This type of class typically handles all the real logic (the one that you want to unit test).
 * It also has a ViewState attribute for store state during screen recreation like rotating, but is your responsibility to handle states.
 * <br> <br>
 * Typically you have to attach this class in your onCreate Fragment method and detach in your onDestroy Fragment method. You also need to
 * use this class with a View Interface in the constructor declaration. @see {@link MvpView}.
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    protected T mvpView;

    protected CompositeSubscription compositeSubscription;

    @Inject
    protected ViewState viewState;

    @Override
    public void attachMvpView(T mvpView) {
        this.mvpView = mvpView;
        this.compositeSubscription = new CompositeSubscription();
        viewState.currentState = ViewState.State.IDLE;
    }

    @Override
    public void detachMvpView() {
        this.mvpView = null;

        if (this.compositeSubscription != null) {
            this.compositeSubscription.clear();
            this.compositeSubscription.unsubscribe();
        }
    }

    public boolean isViewAttached() {
        return this.mvpView != null;
    }

    public T getMvpView() {
        return this.mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public void setCurrentState(ViewState.State newState) {
        this.viewState.currentState = newState;
    }

    public boolean isIdle() {
        return this.viewState.currentState != ViewState.State.FINISH &&
                this.viewState.currentState == ViewState.State.IDLE;
    }

    public boolean isLoading() {
        return this.viewState.currentState == ViewState.State.LOADING;
    }

    public boolean isFinished() {
        return this.viewState.currentState != ViewState.State.ERROR &&
                this.viewState.currentState != ViewState.State.IDLE &&
                this.viewState.currentState != ViewState.State.LOADING &&
                this.viewState.currentState == ViewState.State.FINISH;
    }

    public boolean hasFailed() {
        return this.viewState.currentState != ViewState.State.FINISH &&
                this.viewState.currentState != ViewState.State.IDLE &&
                this.viewState.currentState != ViewState.State.LOADING &&
                this.viewState.currentState == ViewState.State.ERROR;
    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        private MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

}
