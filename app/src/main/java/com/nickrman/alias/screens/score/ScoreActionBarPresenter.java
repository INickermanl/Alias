package com.nickrman.alias.screens.score;

import android.support.annotation.Nullable;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.action_bar.GeneralActionBarPresenter;

public class ScoreActionBarPresenter extends GeneralActionBarPresenter {

    private ActionBarContract.View presenterView;
    private BaseActivity activity;

    public ScoreActionBarPresenter(BaseActivity activity, ActionBarContract.View view, @Nullable int titleText) {
        super(activity, view, titleText);
        this.presenterView = view;
        this.activity = activity;
    }

    public ScoreActionBarPresenter(BaseActivity activity, ActionBarContract.View view, String titleStringText) {
        super(activity, view, titleStringText);
        this.presenterView = view;
        this.activity = activity;

    }

    @Override
    public void setupView() {
        super.setupView();
        presenterView.setBackgroundAB(activity.getResources().getColor(R.color.colorAccent));
    }
}
