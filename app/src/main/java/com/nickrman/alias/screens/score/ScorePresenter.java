package com.nickrman.alias.screens.score;

import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ScorePresenter implements ScoreContract.Presenter {
    private Navigator navigator;
    private BackNavigator backNavigator;
    private CompositeDisposable subscription;
    private ScoreContract.View view;

    public ScorePresenter() {

    }

    @Override
    public void start(ScoreContract.View view) {
        this.view = view;
        setupView();
        setupAction();

    }

    @Override
    public void setupAction() {
        view.startGameButtonAction()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                       subscription.add(d);
                    }

                    @Override
                    public void onNext(Object o) {
                        startGame();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);
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

    @Override
    public void startGame() {
        navigator.navigateTo(Screen.GAME,ScreenType.FRAGMENT);
    }

    @Override
    public void setupView() {
        subscription = new CompositeDisposable();
    }


}
