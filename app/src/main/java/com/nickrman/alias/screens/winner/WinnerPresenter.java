package com.nickrman.alias.screens.winner;

import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;

public class WinnerPresenter implements WinnerContract.Presenter {

    Navigator navigator;

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void backPress() {
        this.navigator.navigateTo(Screen.START, ScreenType.ACTIVITY);
    }
}
