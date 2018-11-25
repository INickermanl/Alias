package com.nickrman.alias.screens.start;

import com.nickrman.alias.base.App;
import com.nickrman.alias.data.db.AppDatabase;
import com.nickrman.alias.data.db.model.Book;
import com.nickrman.alias.data.db.model.Word;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class StartPresenter implements StartContract.Presenter {

    private StartContract.View view;
    private CompositeDisposable subscriptions;
    private Navigator navigator;
    private AppDatabase db;



    //insertBookInBD();
    // insertWordInDB();


    public StartPresenter() {
        db = AppDatabase.getFileDatabase(App.getInstance());
    }

    @Override
    public void start(StartContract.View view) {
        this.view = view;
        setupView();
        setupAction();
    }
    private void insertWordInDB() {



    }


    private void insertBookInBD() {






    }

    private void setupView() {
        subscriptions = new CompositeDisposable();
    }

    private void setupAction() {

        view.newGameButtonAction()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscriptions.add(d);
                    }

                    @Override
                    public void onNext(Object o) {
                        newGame();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void stop() {
        subscriptions.dispose();
        subscriptions = null;

    }

    @Override
    public void setNavigation(Navigator navigation) {
        this.navigator = navigation;

    }

    @Override
    public void newGame() {
        navigator.navigateTo(Screen.SETTINGS, ScreenType.ACTIVITY);
    }

}
