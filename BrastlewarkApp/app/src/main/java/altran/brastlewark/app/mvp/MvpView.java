package altran.brastlewark.app.mvp;

/**
 * Interface that defines actions for your views. This is the V in MVP architecture. You will have to extends this interface
 * and add your custom actions (methods) to handle your use cases like show data in a list or handle particular errors.
 * <br><br>
 * Typically this is attached in your presenter and implemented in your UI like Fragments or Activities (not Android Widgets).
 */
public interface MvpView {

    void onError(Throwable throwable);
}
