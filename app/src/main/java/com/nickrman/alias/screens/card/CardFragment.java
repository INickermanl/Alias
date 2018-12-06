package com.nickrman.alias.screens.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.BaseFragment;

public class CardFragment extends BaseFragment {

    private BaseActivity activity;
    private CardPresenter presenter;
    private CardView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_card, container, false);
        activity = (BaseActivity) getActivity();

        view = new CardView(root,activity);
        presenter = new CardPresenter(view);

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
        presenter.setNavigator(activity.getNavigator());
        presenter.setBackNavigator(activity.getNavigationBackManager());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }
}
