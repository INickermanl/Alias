package com.nickrman.alias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.screens.setting.SettingPresenter;
import com.nickrman.alias.screens.setting.SettingView;
import com.nickrman.alias.screens.setting.SettingsContract;
import com.nickrman.alias.services.navigation.Screen;

public class SettingsActivity extends BaseActivity {

    private View root;
    private SettingsContract.View view;
    private SettingsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getNavigator().setScreen(Screen.SETTINGS);

        root = findViewById(R.id.root);
        view = new SettingView(root);
        presenter = new SettingPresenter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }
}
