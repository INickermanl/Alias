package com.nickrman.alias.screens.card;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.nickrman.alias.base.App;
import com.nickrman.alias.base.BaseActivity;
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
    private int count = 0;
    private int countTime = 0;
    private CountDownTimer timer;
    private SharedPreferences mSetting;

    public CardPresenter(CardContract.View view, BaseActivity activity) {
        this.view = view;
        makeStringList();
        setupView();
        view.setCardWords(listWords.get(0).trim());
        mSetting = activity.getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE);

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

                validation();
                view.acceptCard(listWords.get(count));
            }

            @Override
            public void swipeLeft() {


                validation();
                view.dismissCard(listWords.get(count));
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
                navigator.navigateTo(Screen.RESULT, ScreenType.FRAGMENT);
            }
        }.start();


    }

    void makeStringList() {
        listWords.add("hello");
        listWords.add("noyp");
        listWords.add("test");
        listWords.add("yep");
        listWords.add("noyp");
        listWords.add("test");
        listWords.add("last");
    }

    void validation() {
        ++count;
        if (!(count < listWords.size())) {
            count = 0;


        }
    }
}
