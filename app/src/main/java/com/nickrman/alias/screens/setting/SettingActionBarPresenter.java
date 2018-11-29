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


    public SettingActionBarPresenter(BaseActivity activity, ActionBarContract.View view, int titleText) {
        super(activity, view, titleText);
        this.activity = activity;
        this.view = view;
        view.showRightButton(true);
        this.titleText = titleText;
    }


    @Override
    public void setupView() {
        super.setupView();

        view.setupCenterTitleText(titleText);
        View infoRightIcon = activity.getLayoutInflater().inflate(R.layout.ab_info,null);
        view.setupRightButton(infoRightIcon);

    }

    @Override
    public void setupAction() {
        super.setupAction();
        view.rightButtonAction()
                .subscribe(v ->{
                    rightButtonAction();
                });
    }

    @Override
    public void rightButtonAction() {
        activity.showInfoDialog("Game Rules");
    }
}
