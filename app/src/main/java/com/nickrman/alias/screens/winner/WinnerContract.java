package com.nickrman.alias.screens.winner;

import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

public interface WinnerContract {
    interface View {

    }

    interface Presenter {
        void backPress();
        void setNavigator(Navigator navigator);
    }
}
