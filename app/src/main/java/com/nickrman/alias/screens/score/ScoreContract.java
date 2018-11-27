package com.nickrman.alias.screens.score;

import com.nickrman.alias.data.models.TeamItem;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

import java.util.List;

import io.reactivex.Observable;

public interface ScoreContract {
    interface View {

        void setScoreTeam(String score);


        void setImageTeam(int res);


        void setTeamName(String name);

        void setTeamList(List<TeamItem> items);


        Observable<Object> startGameButtonAction();


    }

    interface Presenter {

        void start(View view);

        void stop();

        void setNavigator(Navigator navigator);

        void setBackNavigator(BackNavigator backNavigator);

        void startGame();

        void setupView();

        void setupAction();


    }
}
