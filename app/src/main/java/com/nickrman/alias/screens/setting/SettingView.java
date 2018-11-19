package com.nickrman.alias.screens.setting;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;

import io.reactivex.Observable;

public class SettingView implements SettingsContract.View {


    private View root;
    private View startGameButton;
    private View backButton;
    private View selectBookButton;
    private View addTeamButton;
    private View infoButton;

    private View addTenSecondsButton;
    private View takeAwayTenSecondButton;

    private View addTenWordsButton;
    private View takeAwayTenWordsButton;




    public SettingView(View root) {
        this.root = root;

        initView();
    }

    private void initView() {
        startGameButton = root.findViewById(R.id.start_game);
        backButton = root.findViewById(R.id.navigation_back);
        selectBookButton = root.findViewById(R.id.select_vocabulary);
        addTeamButton = root.findViewById(R.id.add_team_button);
        infoButton = root.findViewById(R.id.info);
        addTenSecondsButton = root.findViewById(R.id.add_count_time_in_game);
        takeAwayTenSecondButton = root.findViewById(R.id.take_away_count_time_in_game);
        addTenWordsButton = root.findViewById(R.id.add_count_words_in_game);
        takeAwayTenWordsButton = root.findViewById(R.id.take_away_count_words_in_game);


    }

    @Override
    public Observable<Object> startGameButtonAction() {
        return RxView.clicks(startGameButton);
    }

    @Override
    public Observable<Object> backButtonAction() {
        return RxView.clicks(backButton);
    }

    @Override
    public Observable<Object> selectBookButtonAction() {
        return RxView.clicks(selectBookButton);
    }

    @Override
    public Observable<Object> addTeamButtonAction() {
        return RxView.clicks(addTeamButton);
    }

    @Override
    public Observable<Object> addTenSecondsButtonAction() {
        return RxView.clicks(addTenSecondsButton);
    }

    @Override
    public Observable<Object> takeAwayTenSecondButtonAction() {
        return RxView.clicks(takeAwayTenSecondButton);
    }

    @Override
    public Observable<Object> addTenWordsButtonAction() {
        return RxView.clicks(addTenWordsButton);
    }

    @Override
    public Observable<Object> takeAwayTenWordsButtonAction() {
        return RxView.clicks(takeAwayTenWordsButton);
    }

    @Override
    public Observable<Object> infoButtonAction() {
        return RxView.clicks(infoButton);
    }
}
