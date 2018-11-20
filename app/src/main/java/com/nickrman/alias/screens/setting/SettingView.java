package com.nickrman.alias.screens.setting;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;
import com.nickrman.alias.utils.TeamItem;

import java.util.List;

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

    private TextView currentGameTimeMinute;
    private TextView currentGameTimeSecond;
    private TextView currentCountGameWords;

    private TeamAdapter adapter;
    private RecyclerView recyclerView;


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

        currentGameTimeMinute = root.findViewById(R.id.time_in_game_min);
        currentGameTimeSecond = root.findViewById(R.id.time_in_game_sec);

        currentCountGameWords = root.findViewById(R.id.count_words);

        recyclerView = root.findViewById(R.id.recycler_view);


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

    @Override
    public int getCurrentTimeMinute() {
        int currentTimeMinute = Integer.valueOf(currentGameTimeMinute.getText().toString().trim());
        return currentTimeMinute;
    }

    @Override
    public void setCurrentTimeMinute(int min) {
        currentGameTimeMinute.setText(String.valueOf(min));
    }

    @Override
    public int getCurrentTimeSecond() {
        int currentTimeSecond = Integer.valueOf(currentGameTimeSecond.getText().toString().trim());
        return currentTimeSecond;
    }

    @Override
    public void setCurrentTimeSecond(int sec) {
        currentGameTimeSecond.setText(String.valueOf(sec));

    }

    @Override
    public void setVisibleTimeSecond(Boolean visible) {
        currentGameTimeSecond.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setEnabledAddTenSecondButton(Boolean enabled) {
        addTenSecondsButton.setEnabled(enabled);
    }

    @Override
    public void setEnabledTakeAwayTenSecondButton(Boolean enabled) {
        takeAwayTenSecondButton.setEnabled(enabled);
    }

    @Override
    public int getCurrentCountWords() {

        return Integer.valueOf(currentCountGameWords.getText().toString().trim());
    }

    @Override
    public void setCurrentCountWords(int countWords) {
        currentCountGameWords.setText(String.valueOf(countWords));
    }

    @Override
    public void setEnabledAddWordButton(Boolean enabled) {
        addTenWordsButton.setEnabled(enabled);

    }

    @Override
    public void setEnabledTakeAwayWordsButton(Boolean enabled) {
        takeAwayTenWordsButton.setEnabled(enabled);
    }

    @Override
    public void setTeamList(List<TeamItem> items, TeamCallback callback) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL,false);
        adapter = new TeamAdapter(items, callback);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void updateItemList(List<TeamItem> item) {
        adapter.notifyDataSetChanged();
    }
}
