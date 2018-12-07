package com.nickrman.alias.screens.result;

import com.nickrman.alias.data.models.ItemAnswer;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

import java.util.List;

import io.reactivex.Observable;

public interface ResultContract {
    interface View {

        void setTeamList(List<ItemAnswer> answerList);

        void setTeamPoint(String score);
        String getTeamPoint();

        Observable<Object> endButtonAction();



    }

    interface Presenter {
        void start();

        void stop();

        void setNavigator(Navigator navigator);

        void setBackNavigator(BackNavigator backNavigator);

    }
}
