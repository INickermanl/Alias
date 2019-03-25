package com.nickrman.alias.screens.winner;

import android.view.View;

import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

public interface WinnerContract {
    interface View {
        void setWinnerTeamName(String winnerTeam);

        void setWinnerScore(String score);

    }

    interface Presenter {
        void stop();

        void start(View view);

        void backPress();

        void setNavigator(Navigator navigator);
    }
}
