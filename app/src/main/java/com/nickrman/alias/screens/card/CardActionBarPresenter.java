package com.nickrman.alias.screens.card;

import android.support.annotation.Nullable;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.action_bar.GeneralActionBarPresenter;

public class CardActionBarPresenter extends GeneralActionBarPresenter {
    private ActionBarContract.View view;
    private BaseActivity activity;

    public CardActionBarPresenter(BaseActivity activity, ActionBarContract.View view,@Nullable int titleText) {
        super(activity, view, titleText);
        this.view = view;
        this.activity = activity;
    }

    public CardActionBarPresenter(BaseActivity activity, ActionBarContract.View view, String titleStringText) {
        super(activity, view, titleStringText);
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void setupView() {
        super.setupView();
        //hide action bar
        view.showAB(false);
    }
}
