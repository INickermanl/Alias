package com.nickrman.alias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.services.navigation.Screen;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getNavigator().setScreen(Screen.SETTINGS);
    }
}
