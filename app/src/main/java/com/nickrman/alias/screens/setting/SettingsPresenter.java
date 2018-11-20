package com.nickrman.alias.screens.setting;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.utils.TeamItem;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View view;
    private CompositeDisposable subscription;
    private Navigator navigator;
    private BaseActivity activity;
    private BackNavigator backNavigator;

    //create listItems and set him to adapter and create callback for item in adapter
    private List<TeamItem> list;


    @Override
    public void start(SettingsContract.View view) {
        this.view = view;
        setupView();
        setupAction();


        //setTeamList(list);

    }

    private void setupView() {
        subscription = new CompositeDisposable();
    }

    private void setupAction() {
        view.backButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
                backNavigator.navigateBack();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        view.addTenSecondsButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {

                logicAddTenSecondButton();

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        view.takeAwayTenSecondButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {

                logicTakeAwayTenSecondButton();

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.addTenWordsButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
               subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
               if(view.getCurrentCountWords() == 190){

                   view.setEnabledAddWordButton(false);
                   addCurrentCountWords();

               }else if(view.getCurrentCountWords() == 10){
                   addCurrentCountWords();
                   view.setEnabledTakeAwayWordsButton(true);

               }else{
                   addCurrentCountWords();

               }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.takeAwayTenWordsButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
               subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
                if(view.getCurrentCountWords() == 20){
                    view.setEnabledTakeAwayWordsButton(false);
                    takeAwayCurrentCountWords();
                }else if(view.getCurrentCountWords() == 200){
                    view.setEnabledAddWordButton(true);
                    takeAwayCurrentCountWords();
                }else{
                    takeAwayCurrentCountWords();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void takeAwayCurrentCountWords() {
        view.setCurrentCountWords(view.getCurrentCountWords() - 10);
    }

    private void addCurrentCountWords() {
        view.setCurrentCountWords(view.getCurrentCountWords() + 10);
    }

    private void logicTakeAwayTenSecondButton() {
        if (view.getCurrentTimeMinute() == 0 & view.getCurrentTimeSecond() == 40) {
            takeAwayCurrentTime();
            view.setEnabledTakeAwayTenSecondButton(false);
        } else if (view.getCurrentTimeMinute() == 5 & view.getCurrentTimeSecond() == 0) {
            view.setEnabledAddTenSecondButton(true);
            takeAwayCurrentTime();

        } else {
            takeAwayCurrentTime();
        }
    }

    private void logicAddTenSecondButton() {
        if (view.getCurrentTimeMinute() == 4 & view.getCurrentTimeSecond() == 50) {
            addCurrentTime();
            view.setEnabledAddTenSecondButton(false);
        } else if (view.getCurrentTimeMinute() == 0 & view.getCurrentTimeSecond() == 30) {
            view.setEnabledTakeAwayTenSecondButton(true);
            addCurrentTime();
        } else {
            addCurrentTime();
        }
    }

    private void addCurrentTime() {
        if (view.getCurrentTimeSecond() == 50) {

            view.setCurrentTimeMinute(view.getCurrentTimeMinute() + 1);
            view.setCurrentTimeSecond(0);
            view.setVisibleTimeSecond(false);

        } else if (view.getCurrentTimeSecond() == 0) {

            view.setCurrentTimeSecond(10);
            view.setVisibleTimeSecond(true);

        } else {

            view.setCurrentTimeSecond(view.getCurrentTimeSecond() + 10);
        }
    }

    private void takeAwayCurrentTime() {

        if (view.getCurrentTimeSecond() == 0) {
            view.setCurrentTimeMinute(view.getCurrentTimeMinute() - 1);
            view.setCurrentTimeSecond(50);
            view.setVisibleTimeSecond(true);
        } else if (view.getCurrentTimeSecond() == 10) {
            view.setCurrentTimeSecond(0);
            view.setVisibleTimeSecond(false);

        } else {
            view.setCurrentTimeSecond(view.getCurrentTimeSecond() - 10);
        }
    }

    @Override
    public void stop() {
        subscription.dispose();
        subscription = null;
    }

    @Override
    public void setNavigation(Navigator navigator) {
        this.navigator = navigator;

    }

    @Override
    public void startGame() {

    }

    @Override
    public void setBackNavigator(BackNavigator backNavigator) {
        this.backNavigator = backNavigator;
    }

    private void setTeamList(List<TeamItem> items){
        view.setTeamList(items, new TeamCallback() {
            @Override
            public void deleteTeam(int position) {

            }

            @Override
            public void addTeam(TeamItem item) {

            }
        });
    }




}
