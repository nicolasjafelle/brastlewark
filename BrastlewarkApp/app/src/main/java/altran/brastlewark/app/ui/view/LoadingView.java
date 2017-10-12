package altran.brastlewark.app.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

import altran.brastlewark.app.R;

public class LoadingView extends FrameLayout implements View.OnClickListener {

    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private TextView textView;
    private TextView retryButton;
    private Callback callback;

    private ViewGroup mainContentView;

    @Inject
    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loading_view, this);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        setClickable(true);

        progressBar = findViewById(R.id.loading_view_progress_bar);
        linearLayout = findViewById(R.id.loading_view_linear_layout);
        textView = findViewById(R.id.loading_view_text);
        retryButton = findViewById(R.id.loading_view_retry_button);

        progressBar.getIndeterminateDrawable()
                .setColorFilter(fetchThemeColor(), android.graphics.PorterDuff.Mode.SRC_ATOP);
        setBackgroundResource(android.R.color.transparent);

        retryButton.setOnClickListener(this);
    }

    public void setHeightParams(@DimenRes int resId) {
        int size = getResources().getDimensionPixelSize(resId);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
        setLayoutParams(params);
    }

    public int fetchThemeColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    private int fetchThemeBackgroundColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{android.R.attr.windowBackground});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public void setThemeBackgroundColor() {
        setBackgroundColor(fetchThemeBackgroundColor());
    }


    /**
     * Computes all the width and height of the component.
     *
     * @param rootView        - the ViewGroup where this component will be added
     * @param mainContentView - the mainContent to display when the {@link LoadingView#dismiss()} is called.
     * @param fullHeight      - Compute Full Screen Height or just the rootView's height
     */
    private void attach(final ViewGroup rootView, @Nullable final ViewGroup mainContentView, boolean fullHeight) {
        if (mainContentView != null) {
            this.mainContentView = mainContentView;
        }

        final ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        if (rootView instanceof RelativeLayout || rootView instanceof FrameLayout) {
            rootView.addView(this, layoutParams);

        } else if (rootView instanceof LinearLayout) {
            rootView.addView(this, 0, layoutParams);
        }

        if (fullHeight) {
            computeFullHeight();
        }

    }

    /**
     * Attach the loading view inside the rootView and show it.
     *
     * @param rootView        - the ViewGroup where this component will be added
     * @param mainContentView - the mainContent to display when the {@link LoadingView#dismiss()} is called.
     * @param show            - show or not the loading at boot
     * @param fullHeight      - Compute Full Screen Height or just the rootView's height
     */
    public void attach(final ViewGroup rootView, final ViewGroup mainContentView, boolean show, boolean fullHeight) {
        attach(rootView, mainContentView, fullHeight);

        if (show) {
            show();
        } else {
            dismiss();
        }
    }

    /**
     * Attach the loading view inside the rootView and show it.
     *
     * @param rootView        - the ViewGroup where this component will be added
     * @param mainContentView - the mainContent to display when the {@link LoadingView#dismiss()} is called.
     * @param show            - show or not the loading at boot
     * @param fullHeight      - Compute Full Screen Height or just the rootView's height
     * @param callback        - listener for retry click
     */
    public void attach(final ViewGroup rootView, final ViewGroup mainContentView, boolean show, boolean fullHeight, Callback callback) {
        attach(rootView, mainContentView, show, fullHeight);
        this.callback = callback;
    }

    /**
     * Attach the loading view inside the rootView and show it.
     *
     * @param rootView - the ViewGroup where this component will be added
     * @param show     - show or not the loading at boot
     */
    public void attach(final ViewGroup rootView, boolean show) {
        attach(rootView, null, false);

        if (show) {
            show();
        }
    }

    /**
     * Attach the loading view inside the rootView. It will use the rootView's height
     *
     * @param rootView - the ViewGroup where this component will be added
     * @param show     - show or not the loading at boot
     * @param callback - listener for retry click
     */
    public void attach(final ViewGroup rootView, boolean show, Callback callback) {
        attach(rootView, null, show, false);
        this.callback = callback;
    }

    /**
     * Attach the loading view inside the rootView.
     *
     * @param rootView   - the ViewGroup where this component will be added
     * @param show       - to show the loading at boot
     * @param fullHeight - Compute Full Screen Height or just the rootView's height
     * @param callback   - listener for retry click
     */
    public void attach(final ViewGroup rootView, boolean show, boolean fullHeight, Callback callback) {
        attach(rootView, null, show, fullHeight);
        this.callback = callback;
    }


    private void computeFullHeight() {
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Resources resources = getResources();
                int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

                int statusBarHeight = 0;
                int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = resources.getDimensionPixelSize(resourceId);
                }


                int actionBarHeight = 0;
                TypedValue tv = new TypedValue();
                if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
                }

                //Previously we need to compute the softButton Height now it seems is not necessary.
//                int softButtonHeight = 0;
//                resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//                if (resourceId > 0) {
//                    softButtonHeight = resources.getDimensionPixelSize(resourceId);
//                }

//                getLayoutParams().height = screenHeight - statusBarHeight - softButtonHeight - actionBarHeight;
                getLayoutParams().height = screenHeight - statusBarHeight - actionBarHeight;
                requestLayout();

                getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    public void setLoadingViewListener(Callback callback) {
        this.callback = callback;
    }

    public void show() {
        setAlpha(1f);
        setVisibility(VISIBLE);
        progressBar.setVisibility(VISIBLE);
        linearLayout.setVisibility(GONE);

        if (mainContentView != null) {
            mainContentView.setVisibility(GONE);
        }
    }

    public void showErrorView() {
        showErrorView(R.string.connection_error);
    }

    public void showErrorView(@StringRes int stringResId) {

        String errorMessage = getResources().getString(stringResId);
        showErrorView(errorMessage);
    }

    public void showErrorView(String errorMessage) {
        textView.setText(errorMessage);
        retryButton.setText(R.string.retry);
        setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        linearLayout.setVisibility(VISIBLE);

        if (mainContentView != null) {
            mainContentView.setVisibility(GONE);
        }
    }

    public void showNoContentView() {
        showNoContentView(R.string.no_content);
    }

    public void showNoContentView(@StringRes int stringResId) {
        setVisibility(VISIBLE);
        textView.setText(stringResId);
        retryButton.setText(R.string.refresh);
        progressBar.setVisibility(GONE);
        linearLayout.setVisibility(VISIBLE);

        if (mainContentView != null) {
            mainContentView.setVisibility(GONE);
        }
    }

    public void showLabel(@StringRes int stringResId) {
        setVisibility(VISIBLE);
        textView.setText(stringResId);
        retryButton.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        linearLayout.setVisibility(VISIBLE);

        if (mainContentView != null) {
            mainContentView.setVisibility(GONE);
        }
    }


    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    /**
     * Dismiss the loading view immediately.
     *
     * @param animated - dismiss with a fade animation or not.
     */
    public void dismiss(boolean animated) {

        if (animated) {
            animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setVisibility(GONE);

                    if (mainContentView != null) {
                        mainContentView.setVisibility(VISIBLE);
                    }
                }
            });

        } else {
            setVisibility(GONE);
        }
    }

    /**
     * Dismiss the loading view immediately, with no animation.
     */
    public void dismiss() {
        dismiss(mainContentView != null);
    }

    @Override
    public void onClick(View v) {
        show();

        if (callback != null) {
            callback.onRetryClick();
        }
    }

    public interface Callback {
        void onRetryClick();
    }
}
