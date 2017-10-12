package altran.brastlewark.app.mvp;

/**
 * Created by nicolas on 12/14/15.
 */
public interface Presenter<MvpView> {

    void attachMvpView(MvpView view);

    void detachMvpView();
}
