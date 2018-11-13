package com.nickrman.alias.screens.start;

import com.nickrman.alias.services.Navigator;

import io.reactivex.Observable;

public interface StartContract {
    interface View {

        Observable<Object> newGameButtonAction();

    }

    interface Presenter {

        void stop();

        void start(View view);

        void setNavigation(Navigator navigation);

        void newGame();
    }
}
