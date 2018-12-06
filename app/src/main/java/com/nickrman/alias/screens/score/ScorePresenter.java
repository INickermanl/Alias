package com.nickrman.alias.screens.score;

import android.content.Context;
import android.content.SharedPreferences;

import com.nickrman.alias.base.BaseActivity;
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
    private List<TeamItem> listTeams = new ArrayList<>();
    private SharedPreferences mSettings;

    public ScorePresenter(BaseActivity activity, ScoreContract.View view) {
        this.view = view;
        mSettings = activity.getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE);

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
        navigator.navigateTo(Screen.CARD, ScreenType.FRAGMENT);
    }

    @Override
    public void setupView() {


        int playTeam = mSettings.getInt(Constants.SETTING_PLAY_TEAM, 1);


        createList();



        view.setTeamList(listTeams);
        view.setImageTeam(listTeams.get(playTeam).getImageTeam());
        view.setScoreTeam(listTeams.get(playTeam).getScoreTeam());
        view.setTeamName(listTeams.get(playTeam).getNameTeam());
    }


    void createList() {
        String myTeamNamesFromSetting = mSettings.getString(Constants.SETTING_TEAM_NAMES, "");
        String myTeamScoreFromSetting = mSettings.getString(Constants.SETTING_SCORES, "");
        String myTeamsAvatarsFromSetting = mSettings.getString(Constants.SETTING_TEAM_AVATARS, "");


        List<String> listTeamName = new ArrayList<>();
        List<String> listAvatars = new ArrayList<>();
        List<String> listScore = new ArrayList<>();

        for (String data : myTeamNamesFromSetting.split(",")) {
            listTeamName.add(data);
        }

        for (String data : myTeamsAvatarsFromSetting.split(",")) {
            listAvatars.add(data);

        }
        for (String data : myTeamScoreFromSetting.split(",")) {
            listScore.add(data);
        }


        for (int i = 0; i < listTeamName.size(); i++) {
            listTeams.add(new TeamItem(Integer.valueOf(listAvatars.get(i)), listTeamName.get(i), listScore.get(i)));
        }


    }
}
