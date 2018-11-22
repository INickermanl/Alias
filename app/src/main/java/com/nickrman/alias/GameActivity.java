package com.nickrman.alias;

import android.os.Bundle;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;

public class GameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getNavigator().navigateTo(Screen.SCORE, ScreenType.FRAGMENT);
    }
}
