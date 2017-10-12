package altran.brastlewark.app.ui.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import altran.brastlewark.app.R;
import altran.brastlewark.app.domain.Citizen;
import altran.brastlewark.app.ui.AbstractFragment;

/**
 * Created by nicolas on 10/8/17.
 */

public class DetailFragment extends AbstractFragment<Void> {

    public static final String SELECTED_CITIZEN = "selected_citizen";

    private LinearLayout rootLinear, secondLinear;

    private TextView ageView, weightView, heightView, friendsLabel, professionLabel;

    private RecyclerView friendRecyclerView, professionRecyclerView;

    public static Fragment newInstance(Bundle args) {
        Fragment f = new DetailFragment();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        rootLinear = view.findViewById(R.id.fragment_detail_linear);
        secondLinear = view.findViewById(R.id.fragment_detail_second_linear);
        friendRecyclerView = view.findViewById(R.id.fragment_detail_friend_recycler);
        professionRecyclerView = view.findViewById(R.id.fragment_detail_profession_recycler);
        ageView = view.findViewById(R.id.fragment_detail_age);
        weightView = view.findViewById(R.id.fragment_detail_weight);
        heightView = view.findViewById(R.id.fragment_detail_height);
        friendsLabel = view.findViewById(R.id.fragment_detail_friends_label);
        professionLabel = view.findViewById(R.id.fragment_detail_profession_label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootLinear.setTransitionGroup(false);
            secondLinear.setTransitionGroup(true);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Citizen citizen = Parcels.unwrap(getArguments().getParcelable(SELECTED_CITIZEN));

        ageView.setText(getResources().getString(R.string.age_years_old, citizen.getAge()));
        weightView.setText(getResources().getString(R.string.weight, citizen.getWeight()));
        heightView.setText(getResources().getString(R.string.height, citizen.getHeight()));

        showOrNotRecycler(friendRecyclerView, friendsLabel, citizen.getFriendList(), R.string.no_friends);
        showOrNotRecycler(professionRecyclerView, professionLabel, citizen.getProfessionList(), R.string.no_profession);

    }

    private void setupRecyclerView(RecyclerView recyclerView, List<String> stringList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        ChipAdapter adapter = new ChipAdapter(stringList);
        recyclerView.setAdapter(adapter);
    }

    private void showOrNotRecycler(RecyclerView recyclerView, TextView textView, List<String> stringList, @StringRes int resId) {

        if (stringList != null && !stringList.isEmpty()) {
            setupRecyclerView(recyclerView, stringList);
        } else {
            textView.setText(resId);
        }
    }

    @Override
    protected int getMainLayoutResId() {
        return R.layout.fragment_detail;
    }
}
