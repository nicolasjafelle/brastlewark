package altran.brastlewark.app.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import altran.brastlewark.app.R;

/**
 * Created by nicolas on 10/8/17.
 */

public class AnimationUtils {

    public static final int ANIMATION_DURATION_SHORT = 150;
    public static final int ANIMATION_DURATION_MEDIUM = 400;
    public static final int ANIMATION_DURATION_LONG = 800;

    public static void launchSceneTransitionAnimation(FragmentActivity activity, Intent intent, View view) {
        ActivityOptionsCompat options;
        if (view == null) {
            options = ActivityOptionsCompat.makeBasic();
        } else {
            if (ViewCompat.getTransitionName(view) == null || ViewCompat.getTransitionName(view).isEmpty()) {
                ViewCompat.setTransitionName(view, activity.getString(R.string.transition_name));
            }

            List<Pair<View, String>> pairList = new ArrayList<>();

            if (activity.findViewById(android.R.id.statusBarBackground) != null) {
                pairList.add(Pair.create(activity.findViewById(android.R.id.statusBarBackground), Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)); //status bar
            }

            if (activity.findViewById(android.R.id.navigationBarBackground) != null) {
                pairList.add(Pair.create(activity.findViewById(android.R.id.navigationBarBackground), Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)); //navigation bar
            }

            pairList.add(Pair.create(view, ViewCompat.getTransitionName(view))); //navigation bar

            options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairList.toArray(new Pair[pairList.size()]));
        }

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void fadeInView(View view) {
        fadeInView(view, ANIMATION_DURATION_MEDIUM);
    }

    public static void fadeInView(View view, int duration) {
        fadeInView(view, duration, null);
    }

    public static void fadeInView(View view, int duration, final AnimationListener listener) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        ViewPropertyAnimatorListener vpListener = null;

        if (listener != null) {
            vpListener = new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {
                    if (!listener.onAnimationStart(view)) {
                        //execute Parent MEthod
                        view.setDrawingCacheEnabled(true);
                    }
                }

                @Override
                public void onAnimationEnd(View view) {
                    if (!listener.onAnimationEnd(view)) {
                        //execute Parent MEthod
                        view.setDrawingCacheEnabled(false);
                    }
                }

                @Override
                public void onAnimationCancel(View view) {
                    if (!listener.onAnimationCancel(view)) {
                        //execute Parent MEthod
                    }
                }
            };
        }
        ViewCompat.animate(view).alpha(1f).setDuration(duration).setListener(vpListener);
    }

    public static void fadeOutView(View view) {
        fadeOutView(view, ANIMATION_DURATION_SHORT);
    }

    public static void fadeOutView(View view, int duration) {
        fadeOutView(view, duration, null);
    }

    public static void fadeOutView(View view, int duration, final AnimationListener listener) {
        ViewCompat.animate(view).alpha(0f).setDuration(duration).setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
                if (listener == null || !listener.onAnimationStart(view)) {
                    //execute Parent MEthod
                    view.setDrawingCacheEnabled(true);
                }
            }

            @Override
            public void onAnimationEnd(View view) {
                if (listener == null || !listener.onAnimationEnd(view)) {
                    //execute Parent MEthod
                    view.setVisibility(View.GONE);
                    //view.setAlpha(1f);
                    view.setDrawingCacheEnabled(false);
                }
            }

            @Override
            public void onAnimationCancel(View view) {
                if (listener == null || !listener.onAnimationCancel(view)) {
                    //execute Parent MEthod
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void revealInAnimation(final View view, int initialWidth, int initialHeight, int duration, final AnimationListener listener) {

        int cx = initialWidth - view.getResources().getDimensionPixelSize(R.dimen.reveal);
        int cy = initialHeight / 2;


        if (cx != 0 && cy != 0) {
            float finalRadius = (float) Math.hypot(cx, cy);

            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0.0f, finalRadius);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(duration);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (listener == null || !listener.onAnimationStart(view)) {
                        //execute Parent MEthod
                        view.setDrawingCacheEnabled(true);
                        view.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (listener == null || !listener.onAnimationEnd(view)) {
                        view.setDrawingCacheEnabled(false);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    if (listener == null || !listener.onAnimationCancel(view)) {
                        //execute Parent MEthod
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            view.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void revealOutAnimation(final View view, int duration, final AnimationListener listener) {

        int cx = view.getMeasuredWidth() - view.getResources().getDimensionPixelSize(R.dimen.reveal);
        int cy = view.getMeasuredHeight() / 2;

        if (cx != 0 && cy != 0) {
            float initialRadius = (float) Math.hypot(cx, cy);

            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0.0f);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(duration);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (listener == null || !listener.onAnimationStart(view)) {
                        //execute Parent MEthod
                        view.setDrawingCacheEnabled(true);

                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (listener == null || !listener.onAnimationEnd(view)) {
                        view.setVisibility(View.GONE);
                        view.setDrawingCacheEnabled(false);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    if (listener == null || !listener.onAnimationCancel(view)) {
                        //execute Parent MEthod
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    }


    public interface AnimationListener {
        /**
         * @return true to override parent. Else execute Parent method
         */
        boolean onAnimationStart(View view);

        boolean onAnimationEnd(View view);

        boolean onAnimationCancel(View view);
    }
}
