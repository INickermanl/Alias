package com.nickrman.alias.screens.card;


import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.nickrman.alias.base.App;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CardPresenter implements CardContract.Presenter {

    private CardContract.View view;
    private Navigator navigator;
    private BackNavigator backNavigator;
    private CompositeDisposable subscription;
    private GestureDetector gd;

    public CardPresenter(CardContract.View view) {
        this.view = view;
        setupView();

    }

    @Override
    public void start() {
        subscription = new CompositeDisposable();
        setupAction();
    }

    @Override
    public void stop() {
        subscription.dispose();
        subscription = null;
    }

    @Override
    public void setupView() {

        gd = new GestureDetector(App.getInstance(), new CardGestureDetectorListener(new SwipeCallback() {
            @Override
            public void swipeRight() {
                view.acceptCard();
            }

            @Override
            public void swipeLeft() {
                view.dismissCard();
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
    }

    @Override
    public void startFlipAnimation() {


    }
}
