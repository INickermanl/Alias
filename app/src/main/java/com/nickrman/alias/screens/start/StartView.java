package com.nickrman.alias.screens.start;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;

import io.reactivex.Observable;

public class StartView implements StartContract.View {

    private View root;
    private View newGameButton;
    private View resumeGameButton;

    public StartView(View root) {
        this.root = root;
        init();
    }

    private void init() {
        newGameButton = root.findViewById(R.id.new_game_button);
        resumeGameButton = root.findViewById(R.id.resume_game_button);


    }

    @Override
    public Observable<Object> newGameButtonAction() {
        return RxView.clicks(newGameButton);
    }

    @Override
    public void showResumeButton(boolean show) {
        resumeGameButton.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public Observable<Object> resumeGameButtonAction() {
        return RxView.clicks(resumeGameButton);
    }
}
