package altran.brastlewark.app.mvp;


/**
 * Created by nicolas on 12/14/15.
 */
public interface RestHttpView extends MvpView {

    void onHostUnreachable();

    void onHttpErrorCode(int errorCode, String response);
}
