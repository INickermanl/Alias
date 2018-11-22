package com.nickrman.alias.services.navigation.factories;


import android.support.v4.app.Fragment;

import com.nickrman.alias.base.App;
import com.nickrman.alias.screens.score.ScoreFragment;
import com.nickrman.alias.services.navigation.Screen;


public class ScreenFragmentFactory {

    public Fragment getFragmentByType(Screen screen) {
        Class<? extends Fragment> clazz = getFragmentClassByType(screen);
        return Fragment.instantiate(App.getInstance(), clazz.getName());
    }

    public Class<? extends Fragment> getFragmentClassByType(Screen screen) {
        switch (screen) {
            case SCORE:
                return ScoreFragment.class;

            default:
                return ScoreFragment.class;

        }
    }
}
