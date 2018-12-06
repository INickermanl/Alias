package com.nickrman.alias.screens.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.BaseFragment;
import com.nickrman.alias.base.action_bar.ActionBarContract;

public class CardFragment extends BaseFragment {

    private BaseActivity activity;
    private CardPresenter presenter;
    private CardView view;
    private ActionBarContract.Presenter presenterActionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_card, container, false);
        activity = (BaseActivity) getActivity();

        view = new CardView(root, activity);
        presenter = new CardPresenter(view);
        presenterActionBar = new CardActionBarPresenter(activity, activity.getActionBarView(), "hello");

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
        presenter.setNavigator(activity.getNavigator());
        presenter.setBackNavigator(activity.getNavigationBackManager());
        presenterActionBar.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
        presenterActionBar.stop();
    }
}
