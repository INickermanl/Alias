package com.nickrman.alias.screens.start;

import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.nickrman.alias.StartActivity;
import com.nickrman.alias.base.App;
import com.nickrman.alias.data.MySecondDB;
import com.nickrman.alias.data.db.MyAppDatabase;
import com.nickrman.alias.data.db.model.Vocabulary;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.utils.Constants;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class StartPresenter implements StartContract.Presenter {

    private StartContract.View view;
    private CompositeDisposable subscriptions;
    private Navigator navigator;
    private MySecondDB db;
    private SharedPreferences mSetting;
    private Handler handler = new Handler();


    public StartPresenter(SharedPreferences mSetting) {
        //    db = MyAppDatabase.getFileDatabase(App.getInstance());
        this.mSetting = mSetting;
    }

    @Override
    public void start(StartContract.View view) {
        this.view = view;


        final Vocabulary vocabulary = new Vocabulary();
        vocabulary.setId(1);
        vocabulary.setNameBook("test");
        Vocabulary vocabulary1 = new Vocabulary();
        vocabulary.setId(2);
        vocabulary.setNameBook("test1");

        /*db = MySecondDB.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.vocabularyDAO().insertBook(vocabulary);
            }
        }).start();
        db.vocabularyDAO().insertBook(vocabulary);
*/

        setupView();
        setupAction();
    }


    private void setupView() {
        subscriptions = new CompositeDisposable();

        int countWords = mSetting.getInt(Constants.SETTING_COUNT_WORDS, 9);
        if (countWords >= 10) {
            Log.d("LOG", String.valueOf(countWords));
            view.showResumeButton(true);
        } else {
            Log.d("LOG", String.valueOf(countWords));
            view.showResumeButton(false);
        }


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

        view.resumeGameButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(Object o) {
                navigator.navigateTo(Screen.GAMING, ScreenType.ACTIVITY);
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
