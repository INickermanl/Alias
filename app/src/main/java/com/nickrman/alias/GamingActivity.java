package com.nickrman.alias;

import android.os.Bundle;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.action_bar.ActionBarView;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;

public class GamingActivity extends BaseActivity {
    private ActionBarContract.View actionBarView;
    private View actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        actionBar = findViewById(R.id.action_bar);
        actionBarView = new ActionBarView(actionBar);


        getNavigator().navigateTo(Screen.SCORE, ScreenType.FRAGMENT);


    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return actionBarView;
    }
}
