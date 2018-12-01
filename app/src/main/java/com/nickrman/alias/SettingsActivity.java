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
import com.squareup.otto.Bus;

public class SettingsActivity extends BaseActivity {

    private View root;
    private SettingsContract.View view;
    private SettingsContract.Presenter presenter;
    private View actionBar;
    private ActionBarContract.View actionBarView;
    private ActionBarContract.Presenter actionBarPresenter;
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bus = new Bus();
        root = findViewById(R.id.root);
        actionBar = root.findViewById(R.id.action_bar);
        actionBarView = new ActionBarView(actionBar);
        actionBarPresenter = new SettingActionBarPresenter(this,actionBarView,R.string.setting_text_toolbar,bus);

        view = new SettingView(root,this);
        presenter = new SettingsPresenter(this);







    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(actionBarPresenter);

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
        bus.unregister(presenter);
        bus = null;
    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return actionBarView;
    }
}
