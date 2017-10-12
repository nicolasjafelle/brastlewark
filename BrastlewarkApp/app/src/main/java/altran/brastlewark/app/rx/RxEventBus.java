package altran.brastlewark.app.rx;

import android.os.Handler;
import android.os.Looper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import rx.Subscription;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by nicolas on 7/21/16.
 */
@Singleton
public class RxEventBus {

    private final Subject<Object, Object> eventBus = new SerializedSubject<>(PublishSubject.create());
    private final Handler mainThread = new Handler(Looper.getMainLooper());
    private SubscriptionList subscriptionList;

    @Inject
    private RxEventBus() {
        subscriptionList = new SubscriptionList();
    }

    public void post(final Object event) {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            eventBus.onNext(event);
        } else {
            mainThread.post(() -> eventBus.onNext(event));
        }

    }

    public <T> Subscription register(final Class<T> eventClass, final Action1<T> onNext) {

        Subscription subs = eventBus
//                .observeOn(AndroidSchedulers.mainThread())
                .filter(event -> event.getClass().equals(eventClass))
                .map(object -> (T) object)
                .subscribe(onNext);

        subscriptionList.add(subs);

        return subs;
    }

    public void unregister(Subscription subscription) {
        subscriptionList.remove(subscription);

    }

    public void unregisterAll() {
        subscriptionList.unsubscribe();
    }


}
