package com.nickrman.alias.screens.start;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.nickrman.alias.base.App;
import com.nickrman.alias.data.db.MyAppDatabase;
import com.nickrman.alias.data.db.model.Vocabulary;
import com.nickrman.alias.data.db.repository.Repository;
import com.nickrman.alias.data.db.repository.RepositorySource;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.utils.Constants;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class StartPresenter implements StartContract.Presenter {

    private StartContract.View view;
    private CompositeDisposable subscriptions;
    private Navigator navigator;
    private SharedPreferences mSetting;
    private Repository mRepository;

    public StartPresenter(SharedPreferences mSetting, MyAppDatabase myAppDatabase) {
        this.mSetting = mSetting;

        mRepository = Repository.getINSTANCE(
                RepositorySource.getINSTANCE(
                        myAppDatabase.vocabularyDAO(),
                        myAppDatabase.wordDao())
        );
    }

    private void testInsert() {

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                Vocabulary vocabulary = new Vocabulary("Test");

                mRepository.insertVocabulary(vocabulary);
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("accept", "inserted test vocabulary in db");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("error", "error into insert test vocabulary in db");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Disposable disposable1 = Observable.create(new ObservableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                                mRepository.deleteAllVocabulary();
                                emitter.onComplete();
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Consumer<Object>() {
                                    @Override
                                    public void accept(Object o) throws Exception {
                                        Log.d("accept", "accept destroyed db");
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.d("error", "error destroyed db");
                                    }
                                });

                        subscriptions.add(disposable1);
                    }
                });
        subscriptions.add(disposable);
    }

    @Override
    public void start(StartContract.View view) {
        this.view = view;
        subscriptions = new CompositeDisposable();
        setupView();
        testInsert();
        setupAction();

    }


    private void setupView() {
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
