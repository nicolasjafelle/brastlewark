package altran.brastlewark.app.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import altran.brastlewark.app.R;

/**
 * Created by nicolas on 10/12/17.
 */

public class ChipView extends AppCompatTextView {

    public ChipView(Context context) {
        super(context);
        init();
    }

    public ChipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        int margin = getResources().getDimensionPixelOffset(R.dimen.base_separation);
        params.setMargins(0, 0, margin, 0);

        setLayoutParams(params);

        int padding = getResources().getDimensionPixelSize(R.dimen.base_separation);
        setPadding(padding, padding, padding, padding);
        setBackgroundResource(R.drawable.chips_drawable);

        setTextColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    public void setFirsMargins() {

    }
}
