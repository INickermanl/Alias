package com.nickrman.alias;

import android.os.Bundle;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.screens.setting.SettingsPresenter;
import com.nickrman.alias.screens.setting.SettingView;
import com.nickrman.alias.screens.setting.SettingsContract;

public class SettingsActivity extends BaseActivity {

    private View root;
    private SettingsContract.View view;
    private SettingsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        root = findViewById(R.id.root);
        view = new SettingView(root);
        presenter = new SettingsPresenter();


    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start(view);
        presenter.setNavigation(getNavigator());
        presenter.setBackNavigator(getNavigationBackManager());



    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }



}
