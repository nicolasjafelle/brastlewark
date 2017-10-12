package altran.brastlewark.app.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import altran.brastlewark.app.R;

public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected FrameLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialFragment();
        setToolbar();
    }

    protected void setToolbar() {
        toolbar = findViewById(R.id.material_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }


    protected void hideToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected void showToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }


    protected void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

    }

    protected void setToolbarColor(@ColorRes int colorRes) {
        if (toolbar != null) {
            toolbar.setBackgroundResource(android.R.color.transparent);
        }
    }

    protected void setToolbarTitle(@StringRes int titleResId) {
        setToolbarTitle(getString(titleResId));
    }


    protected void setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected int getStatusBarHeight() {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
        }

        return result;
    }

    protected void setMaterialStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    protected void setMaterialStatusBarColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
        }
    }

    protected void setStatusBarTransparent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    /**
     * This method defines which is the initial Fragment. Classes that extends
     * this Class should override it and tells the Parent Class which is the
     * initial fragment to load.
     */
    protected abstract void setInitialFragment();


    /**
     * This method loads the initial fragment, it should be called inside
     * setInitialFragment().
     *
     * @param layoutResId the activity layout
     * @param viewId      the Main view id.
     * @param fragment    the initial Fragment
     */
    protected void setInitialFragment(int layoutResId, int viewId, Fragment fragment) {
        setContentView(layoutResId);
        mainLayout = findViewById(viewId);
        setInitialFragment(mainLayout, fragment);
    }

    /**
     * This method loads the initial fragment, it should be called inside
     * setInitialFragment().
     *
     * @param viewId   the Main view id.
     * @param fragment the initial Fragment
     */
    protected void setInitialFragment(int viewId, Fragment fragment) {
        mainLayout = findViewById(viewId);
        setInitialFragment(mainLayout, fragment);
    }

    /**
     * This method loads the initial fragment, it should be called inside
     * setInitialFragment().
     *
     * @param fragment the initial Fragment
     */
    protected void setInitialFragment(Fragment fragment) {
        setInitialFragment(getBaseLayoutResId(), R.id.fragment_container, fragment);
    }

    protected int getBaseLayoutResId() {
        //return R.layout.activity_single_fragment;
        return R.layout.activity_scroll_single_fragment;
    }

    /**
     * This method loads the initial fragment
     *
     * @param view     the Main View, it is recommended to use a FrameLayout
     * @param fragment the initial fragment
     */
    protected void setInitialFragment(View view, Fragment fragment) {
        if (getCurrentFragment() == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(view.getId(), fragment).commit();
        }
    }


    /**
     * This method replace the existing fragment with a current one. This new
     * fragment is added to the back stack.
     *
     * @param newFragment - The new Fragment that will replace the current visible fragment.
     */
    public void replaceFragment(Fragment newFragment) {
        this.replaceFragment(newFragment, true);
    }

    /**
     * This method replace the existing fragment with a current one. This new
     * fragment could be added or not to the back stack.
     *
     * @param newFragment    The new Fragment that will replace the current visible fragment.
     * @param addToBackStack True if the new Fragment should be added to the back stack, false
     *                       otherwise.
     */
    public void replaceFragment(Fragment newFragment, boolean addToBackStack) {
        this.replaceFragment(newFragment, addToBackStack, R.anim.fragment_fade_in, R.anim.fragment_fade_in);
    }

    public void replaceFragment(Fragment newFragment, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        this.replaceFragment(newFragment, true, enterAnim, exitAnim);
    }


    public void replaceFragment(Fragment newFragment, boolean addToBackStack,
                                @AnimRes int enterAnim, @AnimRes int exitAnim,
                                @AnimRes int popEnterAnim, @AnimRes int popExitAnim) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack("replace");
        }

        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
        fragmentTransaction.replace(mainLayout.getId(), newFragment).commit();
    }


    public void replaceFragment(Fragment newFragment, boolean addToBackStack, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        replaceFragment(newFragment, addToBackStack, enterAnim, 0, exitAnim, 0);
    }

    public void addAndHideFragment(Fragment newFragment) {
        this.addAndHideFragment(newFragment, true);
    }

    public void addAndHideFragment(Fragment newFragment, boolean addToBackStack) {
        this.addAndHideFragment(newFragment, addToBackStack, R.anim.fragment_fade_in, R.anim.fragment_fade_in);
    }

    public void addAndHideFragment(Fragment newFragment, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        this.addAndHideFragment(newFragment, true, enterAnim, exitAnim);
    }


    public void addAndHideFragment(Fragment newFragment, boolean addToBackStack,
                                   @AnimRes int enterAnim, @AnimRes int exitAnim,
                                   @AnimRes int popEnterAnim, @AnimRes int popExitAnim) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack("add_and_hide");
        }

//        if (enterAnim > 0 && exitAnim > 0) {
//            fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
//        } else {
////            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
//            fragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, 0, R.anim.fragment_fade_in, 0);
//        }

        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);

        Fragment f = getCurrentFragment();
        fragmentTransaction.hide(f);
        fragmentTransaction.add(mainLayout.getId(), newFragment).commit();
    }


    public void addAndHideFragment(Fragment newFragment, boolean addToBackStack, @AnimRes int enterAnim, @AnimRes int exitAnim) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack("add_and_hide");
        }

        if (enterAnim > 0 && exitAnim > 0) {
            fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
        } else {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
            fragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, 0, R.anim.fragment_fade_in, 0);
        }

        Fragment f = getCurrentFragment();
        fragmentTransaction.hide(f);
        fragmentTransaction.add(mainLayout.getId(), newFragment).commit();
    }

    protected boolean popBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.popBackStackImmediate();
    }

    /**
     * Returns the current showing fragment or null if no fragment is added.
     *
     * @return the fragment previously added or null
     */
    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(mainLayout.getId());
    }
}
