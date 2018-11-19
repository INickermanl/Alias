package com.nickrman.alias.screens.setting;

import io.reactivex.Observable;

public interface SettingsContract {
    interface View {
        Observable<Object> startGameButtonAction();

        Observable<Object> backButtonAction();

        Observable<Object> selectBookButtonAction();

        Observable<Object> addTeamButtonAction();

        Observable<Object> addTenSecondsButtonAction();

        Observable<Object> takeAwayTenSecondButtonAction();

        Observable<Object> addTenWordsButtonAction();

        Observable<Object> takeAwayTenWordsButtonAction();

        Observable<Object> infoButtonAction();
    }

    interface Presenter {

        void start(View view);

        void stop();

        void setNavigation();

        void startGame();
    }
}
