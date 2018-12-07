package com.nickrman.alias.screens.card;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

import io.reactivex.Observable;

public interface CardContract {

    interface View {

        Observable<Object> flipCard();

        Observable<MotionEvent> currentCard();

        void startAnimation();


        void acceptCard(String explainWords);

        void dismissCard(String explainWords);

        void setCardWords(String word);

        void setTimeToEndGame(String time);

        String getTimeToEndGame();

        void setCountRightAnswer(String rightAnswer);

        void setCountWrongAnswer(String wrongAnswer);

    }

    interface Presenter {

        void start();

        void stop();

        void setupView();

        void setupAction();

        void setNavigator(Navigator navigator);

        void setBackNavigator(BackNavigator backNavigator);

        void startAnimation();

    }
}
