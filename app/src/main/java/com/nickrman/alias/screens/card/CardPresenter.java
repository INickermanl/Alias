package com.nickrman.alias.screens.card;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.nickrman.alias.base.App;
import com.nickrman.alias.base.BaseActivity;
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

public class CardPresenter implements CardContract.Presenter {

    private CardContract.View view;
    private Navigator navigator;
    private BackNavigator backNavigator;
    private BaseActivity activity;
    private CompositeDisposable subscription;
    private GestureDetector gd;
    private List<String> listWords = new ArrayList<>();
    private int counterListWords = 0;
    private int counterRightAnswer = 0;
    private int counterWrongAnswer = 0;
    private int countTime = 0;
    private CountDownTimer timer;
    private SharedPreferences mSetting;
    private List<ItemAnswer> listUserAnswer = new ArrayList<>();

    public CardPresenter(CardContract.View view, BaseActivity activity) {
        this.view = view;
        mSetting = activity.getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE);
        this.activity = activity;
        makeStringList();
        setupView();
        view.setCardWords(listWords.get(0).trim());
        int countSecond = mSetting.getInt(Constants.SETTING_COUNT_SECONDS, 3);
        view.setTimeToEndGame(String.valueOf(countSecond));


    }

    @Override
    public void start() {
        subscription = new CompositeDisposable();
        setupAction();

        int time = Integer.valueOf(view.getTimeToEndGame());
        if (countTime > 0) {
            timer = new CountDownTimer(time * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    view.setTimeToEndGame(String.valueOf(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    navigator.navigateTo(Screen.RESULT, ScreenType.FRAGMENT);
                }
            }.start();
        }
    }

    @Override
    public void stop() {
        subscription.dispose();
        subscription = null;
        countTime = 1;
        timer.cancel();
    }

    @Override
    public void setupView() {


        gd = new GestureDetector(App.getInstance(), new CardGestureDetectorListener(new SwipeCallback() {
            @Override
            public void swipeRight() {
                if (counterListWords != 0) {
                    listUserAnswer.add(new ItemAnswer(listWords.get(counterListWords), true));
                } else {
                    Log.d("LOG", String.valueOf(listUserAnswer.size()));

                    if (listUserAnswer.size() == 1)
                        listUserAnswer.set(0, new ItemAnswer(listWords.get(counterListWords), true));
                    else
                        listUserAnswer.add(new ItemAnswer(listWords.get(counterListWords), true));


                }
                ++counterRightAnswer;

                view.setCountRightAnswer(String.valueOf(counterRightAnswer));
                validation();


                view.acceptCard(listWords.get(counterListWords));

            }

            @Override
            public void swipeLeft() {
                if (counterListWords != 0) {
                    listUserAnswer.add(new ItemAnswer(listWords.get(counterListWords), false));
                } else {
                    Log.d("LOG", String.valueOf(listUserAnswer.size()));
                    if (listUserAnswer.size() != 1) {
                        listUserAnswer.add(new ItemAnswer(listWords.get(counterListWords), false));
                    }
                }

                ++counterWrongAnswer;
                view.setCountWrongAnswer("-" + String.valueOf(counterWrongAnswer));
                validation();

                view.dismissCard(listWords.get(counterListWords));

            }
        }));
    }

    @Override
    public void setupAction() {

        view.flipCard().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
                listUserAnswer.add(new ItemAnswer(listWords.get(counterListWords), false));
                startAnimation();

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.currentCard().subscribe(new Observer<MotionEvent>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(MotionEvent motionEvent) {

                gd.onTouchEvent(motionEvent);

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
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void setBackNavigator(BackNavigator backNavigator) {
        this.backNavigator = backNavigator;
    }

    @Override
    public void startAnimation() {
        view.startAnimation();

        int time = mSetting.getInt(Constants.SETTING_COUNT_SECONDS, 5);

        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setTimeToEndGame(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Bundle args = new Bundle();
                String words = "";
                String answer = "";
                for (int i = 0; i < listUserAnswer.size(); i++) {
                    words += listUserAnswer.get(i).getWord() + ",";
                    answer += listUserAnswer.get(i).isAnswer() + ",";


                }

                words += listWords.get(counterListWords) + ",";
                answer += "false" + ",";
                args.putString(Constants.USER_WORDS, words);
                args.putString(Constants.USER_ANSWER, answer);
                navigator.navigateTo(Screen.RESULT, ScreenType.FRAGMENT, args);
            }
        }.start();


    }

    void makeStringList() {
        listWords.add("Злость");
        listWords.add("Гнев");
        listWords.add("Возмущение");
        listWords.add("Обида");
        listWords.add("Ненависть");
    /*    listWords.add("Сердитость");
        listWords.add("Досада");
        listWords.add("Раздражение");
        listWords.add("Мстительность");
        listWords.add("Оскорбленность");
        listWords.add("Воинственность");*/
    }

    void validation() {
        ++counterListWords;
        if (!(counterListWords < listWords.size())) {
            counterListWords = 0;
        }
    }
}
