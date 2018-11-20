package com.nickrman.alias.screens.setting;

import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.utils.TeamItem;

import java.util.List;

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

        int getCurrentTimeMinute();

        void setCurrentTimeMinute(int min);

        int getCurrentTimeSecond();

        void setCurrentTimeSecond(int sec);

        void setVisibleTimeSecond(Boolean visible);
        void setEnabledAddTenSecondButton(Boolean enabled);
        void setEnabledTakeAwayTenSecondButton(Boolean enabled);

        int getCurrentCountWords();
        void setCurrentCountWords(int countWords);

        void setEnabledAddWordButton(Boolean enabled);
        void setEnabledTakeAwayWordsButton(Boolean enabled);

        void setTeamList(List<TeamItem> items,TeamCallback callback);

        //void removeItemFromList(int position);
        void updateItemList(List<TeamItem> item);
    }

    interface Presenter {

        void start(View view);

        void stop();

        void setNavigation(Navigator navigator);

        void setBackNavigator(BackNavigator navigator);

        void startGame();

    }
}
