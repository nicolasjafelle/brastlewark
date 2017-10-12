package altran.brastlewark.app.mvp;


/**
 * Simple View State class that helps you manage your current state of your UI. It is already in the {@link BasePresenter}.
 */

public class ViewState {

    State currentState;

    public enum State {
        LOADING,
        ERROR,
        IDLE,
        FINISH
    }

}
