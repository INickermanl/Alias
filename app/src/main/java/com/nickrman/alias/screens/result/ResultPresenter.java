package com.nickrman.alias.screens.result;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.nickrman.alias.data.models.ItemAnswer;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ResultPresenter implements ResultContract.Presenter {
    private ResultContract.View view;
    private List<ItemAnswer> listItemAnswer = new ArrayList<>();
    private CompositeDisposable subscription;
    private Bundle data;
    private Navigator navigator;
    private BackNavigator backNavigator;
    private SharedPreferences mSetting;


    public ResultPresenter(ResultContract.View view, Bundle data, SharedPreferences mSetting) {
        this.view = view;
        this.data = data;
        this.mSetting = mSetting;
        makeListAnswer();

    }

    private void makeListAnswer() {
        String words = data.getString(Constants.USER_WORDS);
        String answers = data.getString(Constants.USER_ANSWER);


        List<Boolean> listAnswer = new ArrayList<>();
        List<String> listWords = new ArrayList<>();

        for (String answer : answers.split(",")) {
            listAnswer.add(Boolean.valueOf(answer));
        }
        for (String word : words.split(",")) {
            listWords.add(word);
        }
        int teamRightPoint = 0;
        int teamWrongPoint = 0;

        for (int i = 0; i < listWords.size(); i++) {

            listItemAnswer.add(new ItemAnswer(listWords.get(i), listAnswer.get(i)));
            if (listAnswer.get(i).equals(true)) {
                ++teamRightPoint;
            } else {
                ++teamWrongPoint;
            }
        }


        view.setTeamPoint(String.valueOf(teamRightPoint - teamWrongPoint));

    }

    @Override
    public void start() {
        subscription = new CompositeDisposable();
        setupAction();

    }

    private void setupAction() {
        view.setTeamList(listItemAnswer);

        view.endButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
                List<String> listScores = new ArrayList<>();
                SharedPreferences.Editor editor = mSetting.edit();

                String teamScores = mSetting.getString(Constants.SETTING_SCORES, "0");
                String newTeamScore = "";
                int currentPlayTeam = mSetting.getInt(Constants.SETTING_PLAY_TEAM, 0);
                int winScore = mSetting.getInt(Constants.SETTING_COUNT_WORDS, 30);
                int currentRound = mSetting.getInt(Constants.SETTING_ROUND, 50);
                int challenger = 0;
                int winner = 0;
                int counterTeam = 0;


                for (String score : teamScores.split(",")) {
                    listScores.add(score);
                }

                int currentScorePlayTeam = Integer.valueOf(view.getTeamPoint()) + Integer.valueOf(listScores.get(currentPlayTeam));

                if (currentScorePlayTeam < 0) {

                    listScores.set(currentPlayTeam, "0");
                } else {
                    listScores.set(currentPlayTeam, String.valueOf(currentScorePlayTeam));
                }


                for (int i = 0; i < listScores.size(); i++) {
                    newTeamScore += listScores.get(i) + ",";
                }

                if (currentPlayTeam < listScores.size() - 1) {
                    ++currentPlayTeam;
                } else {

                    //logic and all game fix

                    for (String score : listScores) {
                        if (Integer.valueOf(score) >= winScore) {
                            challenger = Integer.valueOf(score);

                            try {
                                for (int i = 0; i < listScores.size() - 1; i++) {

                                    if (challenger <= Integer.valueOf(listScores.get(i))) {
                                        challenger = Integer.valueOf(listScores.get(i));
                                        counterTeam = i;
                                        winner = Integer.valueOf(listScores.get(i));
                                    }
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                Log.d("LOG", e.getLocalizedMessage());
                            }
                            editor.putInt(Constants.WINNERtEAMnAME, counterTeam);
                            editor.putInt(Constants.WINNER, winner);
                            editor.putInt(Constants.SETTING_COUNT_WORDS, 9);
                            navigator.navigateTo(Screen.WINNER, ScreenType.ACTIVITY);
                            break;

                        }

                        /*if (Integer.valueOf(score) >= winScore) {

                            challenger = Integer.valueOf(score);



                            *//*challenger = Integer.valueOf(score);
                            editor.putInt(Constants.SETTING_COUNT_WORDS, 9);
                            navigator.navigateTo(Screen.WINNER, ScreenType.ACTIVITY);*//*

                        }*/
                    }


                    currentPlayTeam = 0;
                    ++currentRound;

                }


                editor.putString(Constants.SETTING_SCORES, newTeamScore);
                editor.putInt(Constants.SETTING_PLAY_TEAM, currentPlayTeam);
                editor.putInt(Constants.SETTING_ROUND, currentRound);

                editor.apply();


                navigator.navigateTo(Screen.SCORE, ScreenType.FRAGMENT);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void stop() {
        subscription.dispose();
        subscription = null;
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void setBackNavigator(BackNavigator backNavigator) {
        this.backNavigator = backNavigator;
    }
}
