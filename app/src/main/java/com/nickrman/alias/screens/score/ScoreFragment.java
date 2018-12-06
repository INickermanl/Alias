package com.nickrman.alias.screens.score;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.BaseFragment;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.utils.Constants;

public class ScoreFragment extends BaseFragment {


    private ScoreContract.View view;
    private ScoreContract.Presenter presenter;
    private BaseActivity activity;
    private ActionBarContract.Presenter presenterActionBar;
    private SharedPreferences mSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_score, container, false);

        activity = (BaseActivity) getActivity();
        mSettings = activity.getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE);

        view = new ScoreView(root, activity);
        presenter = new ScorePresenter(activity, view);
        int round = mSettings.getInt(Constants.SETTING_ROUND, 2);

        presenterActionBar = new ScoreActionBarPresenter(activity, activity.getActionBarView(), "Round " + ++round);

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setNavigator(activity.getNavigator());
        presenter.setBackNavigator(activity.getNavigationBackManager());
        presenter.start();
        presenterActionBar.start();

    }

    @Override
    public void onStop() {
        super.onStop();
        presenterActionBar.stop();
        presenter.stop();
    }
}
