package com.nickrman.alias.screens.setting;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.action_bar.GeneralActionBarPresenter;

public class SettingActionBarPresenter extends GeneralActionBarPresenter {
    private BaseActivity activity;
    private ActionBarContract.View view ;
    private int titleText;


    public SettingActionBarPresenter(BaseActivity activity, ActionBarContract.View view, @Nullable int titleText) {
        super(activity, view, titleText);
        this.activity = activity;
        this.view = view;
        this.titleText = titleText;
    }

    @Override
    public void setupView() {
        View backLeftIcon = activity.getLayoutInflater().inflate(R.layout.ab_back, null);

        view.setupLeftButton(backLeftIcon);
        view.setupCenterTitleText(titleText);
        View infoRightIcon = activity.getLayoutInflater().inflate(R.layout.ab_info,null);
        view.setupRightButton(infoRightIcon);
    }
}
