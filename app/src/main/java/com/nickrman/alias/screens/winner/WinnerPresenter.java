package com.nickrman.alias.screens.winner;

import android.content.SharedPreferences;

import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class WinnerPresenter implements WinnerContract.Presenter {

    private Navigator navigator;
    private WinnerContract.View view;
    private SharedPreferences mSetting;

    public WinnerPresenter(SharedPreferences mSetting) {
        this.mSetting = mSetting;

    }
    @Override
    public void start(WinnerContract.View view) {
        this.view = view;
        initView();
    }

    private void initView() {

        String teamNames = mSetting.getString(Constants.SETTING_TEAM_NAMES, "l");
        int winnerScore = mSetting.getInt(Constants.WINNER, 4);
        int counterNameTeamInList = mSetting.getInt(Constants.WINNERtEAMnAME, 5555);
        List<String> listTeamName = new ArrayList<>();
        --counterNameTeamInList;

        for (String teamName : teamNames.split(",")) {
            listTeamName.add(teamName);
            if ((listTeamName.size() - 1) == counterNameTeamInList){
                view.setWinnerTeamName(teamName);
                listTeamName = null;
                break;
            }
        }


        view.setWinnerScore(Integer.toString(winnerScore));
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void backPress() {
        this.navigator.navigateTo(Screen.START, ScreenType.ACTIVITY);
    }

    @Override
    public void stop() {

    }


}
