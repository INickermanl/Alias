package com.nickrman.alias.screens.result;

import com.nickrman.alias.data.models.ItemAnswer;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

import java.util.List;

public interface ResultContract {
    interface View {

        void setTeamList(List<ItemAnswer> answerList);

    }

    interface Presenter {
        void start();

        void stop();

        void setNavigator(Navigator navigator);

        void setBackNavigator(BackNavigator backNavigator);

    }
}
