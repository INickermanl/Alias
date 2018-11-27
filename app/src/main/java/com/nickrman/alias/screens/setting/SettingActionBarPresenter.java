package com.nickrman.alias.screens.setting;

import android.support.annotation.Nullable;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.action_bar.GeneralActionBarPresenter;

public class SettingActionBarPresenter extends GeneralActionBarPresenter {


    public SettingActionBarPresenter(BaseActivity activity, ActionBarContract.View view, @Nullable int titleText) {
        super(activity, view, titleText);
    }

}
