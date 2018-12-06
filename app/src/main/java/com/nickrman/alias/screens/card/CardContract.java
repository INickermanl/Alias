package com.nickrman.alias.screens.card;

import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

public interface CardContract {

    interface View {

    }

    interface Presenter {

        void start();

        void stop();

        void setupView();

        void setupAction();

        void setNavigator(Navigator navigator);

        void setBackNavigator(BackNavigator backNavigator);
    }
}
