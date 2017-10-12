package altran.brastlewark.app;

import android.app.Application;
import android.content.Context;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by nicolas on 9/30/17.
 */

public class BrastlewarkApplication extends Application {


    private static final Injector INJECTOR = Guice.createInjector(new AppModule());
    private static BrastlewarkApplication appContext = null;


    public static Context getContext() {
        return appContext.getBaseContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void injectMembers(final Object object) {
        INJECTOR.injectMembers(object);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        injectMembers(this);
    }

}
