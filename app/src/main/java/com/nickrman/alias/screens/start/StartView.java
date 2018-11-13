package com.nickrman.alias.screens.start;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;

import io.reactivex.Observable;

public class StartView implements StartContract.View {

    View root;
    View newGameButton;

    public StartView(View root) {
        this.root = root;
        init();
    }

    private void init() {
        newGameButton = root.findViewById(R.id.new_game_button);

    }

    @Override
    public Observable<Object> newGameButtonAction() {
        return RxView.clicks(newGameButton);
    }


}
