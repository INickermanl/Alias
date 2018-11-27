package com.nickrman.alias;

import android.os.Bundle;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.action_bar.ActionBarView;
import com.nickrman.alias.screens.setting.SettingActionBarPresenter;
import com.nickrman.alias.screens.setting.SettingsPresenter;
import com.nickrman.alias.screens.setting.SettingView;
import com.nickrman.alias.screens.setting.SettingsContract;

public class SettingsActivity extends BaseActivity {

    private View root;
    private SettingsContract.View view;
    private SettingsContract.Presenter presenter;
    private View actionBar;
    private ActionBarContract.View actionBarView;
    private ActionBarContract.Presenter actionBarPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        root = findViewById(R.id.root);
        actionBar = root.findViewById(R.id.action_bar);
        actionBarView = new ActionBarView(actionBar);
        actionBarPresenter = new SettingActionBarPresenter(this,actionBarView,R.string.setting_text_toolbar);

        view = new SettingView(root);
        presenter = new SettingsPresenter();






    }

    @Override
    protected void onStart() {
        super.onStart();
        actionBarPresenter.start();
        presenter.start(view);
        presenter.setNavigation(getNavigator());
        presenter.setBackNavigator(getNavigationBackManager());



    }

    @Override
    protected void onStop() {
        super.onStop();
        actionBarPresenter.stop();
        presenter.stop();
    }



}
