package altran.brastlewark.app.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import altran.brastlewark.app.R;
import altran.brastlewark.app.domain.Citizen;

/**
 * Created by nicolas on 10/8/17.
 */

public class CitizenItemView extends CardView {

    public ImageView imageView;

    private TextView fullnameView, ageView;

    public CitizenItemView(Context context) {
        super(context);
        init();
    }

    public CitizenItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CitizenItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.citizen_item_view, this);

        imageView = findViewById(R.id.citizen_item_view_image);
        fullnameView = findViewById(R.id.citizen_item_view_fullname);
        ageView = findViewById(R.id.citizen_item_view_age);

        CardView.LayoutParams params = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

        this.setCardElevation(getResources().getDimensionPixelSize(R.dimen.card_elevation));
        setRadius(getResources().getDimensionPixelSize(R.dimen.card_radius));
        setForeground(ContextCompat.getDrawable(getContext(), R.drawable.common_background_drawable));
    }

    public void loadData(Citizen citizen) {

        if (citizen != null) {
            if (citizen.getThumbnail() != null) {
                Picasso.with(getContext())
                        .load(citizen.getThumbnail())
                        .fit()
                        .centerCrop()
                        .error(R.drawable.ic_image_black)
                        .placeholder(R.drawable.ic_image_black)
                        .into(imageView);
            }

            fullnameView.setText(citizen.getName());
            ageView.setText(getResources().getString(R.string.years_old, citizen.getAge()));

        } else {
            fullnameView.setText(R.string.no_info_available);
        }

    }
}
