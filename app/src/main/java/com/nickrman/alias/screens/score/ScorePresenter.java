package com.nickrman.alias.screens.score;

import android.os.Bundle;
import android.util.Log;

import com.nickrman.alias.data.models.SettingItem;
import com.nickrman.alias.data.models.TeamItem;
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
import timber.log.Timber;

public class ScorePresenter implements ScoreContract.Presenter {
    private Navigator navigator;
    private BackNavigator backNavigator;
    private CompositeDisposable subscription;
    private ScoreContract.View view;
    private Bundle data;
    private List<TeamItem> listTeams = new ArrayList<>();

    public ScorePresenter(ScoreContract.View view, Bundle bundle) {
        this.view = view;
        this.data = bundle;
        setupView();

    }

    @Override
    public void start() {
        subscription = new CompositeDisposable();
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
        navigator.navigateTo(Screen.GAME, ScreenType.FRAGMENT);
    }

    @Override
    public void setupView() {


        createList();

        view.setTeamList(listTeams);
        view.setImageTeam(listTeams.get(0).getImageTeam());
        view.setScoreTeam(listTeams.get(0).getScoreTeam());
        view.setTeamName(listTeams.get(0).getNameTeam());
    }


    void createList() {
        SettingItem settingItem = (SettingItem) data.getSerializable(Constants.SETTING);

        List<String> listTeamName = new ArrayList<>();
        List<String> listAvatars = new ArrayList<>();

        for (String retval : settingItem.getTeamsNames().split(",")) {
            Log.d("LOG", retval);
            listTeamName.add(retval);
        }
        for (String retval : settingItem.getTeamsAvatar().split(",")) {
            Log.d("LOG", retval);
            listAvatars.add(retval);
        }

        for (int i = 0; i < listTeamName.size(); i++) {
            listTeams.add(new TeamItem(Integer.valueOf(listAvatars.get(i)), listTeamName.get(i)));
        }


    }
}
