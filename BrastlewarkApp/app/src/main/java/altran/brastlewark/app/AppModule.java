package altran.brastlewark.app;

import android.content.Context;

import com.google.inject.AbstractModule;

/**
 * Created by nicolas on 12/14/15.
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Context.class).toProvider(BrastlewarkApplication::getContext);

        bind(BrastlewarkApplication.class).toProvider(() -> (BrastlewarkApplication) BrastlewarkApplication.getAppContext());
    }
}
