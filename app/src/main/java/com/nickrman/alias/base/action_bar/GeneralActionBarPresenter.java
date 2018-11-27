package com.nickrman.alias.base.action_bar;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.services.navigation.managers.events.BackPressEvent;

import io.reactivex.disposables.CompositeDisposable;

public class GeneralActionBarPresenter implements ActionBarContract.Presenter {
    private BaseActivity activity;
    private ActionBarContract.View view;
    private CompositeDisposable disposable;
    private int titleText;


    public GeneralActionBarPresenter(BaseActivity activity, ActionBarContract.View view, @Nullable int titleText) {
        this.activity = activity;
        this.view = view;
        this.disposable = new CompositeDisposable();
        this.titleText = titleText;
    }

    @Override
    public void setupView() {
        view.showRightButton(false);
        View backLeftIcon = activity.getLayoutInflater().inflate(R.layout.ab_back, null);

        view.setupLeftButton(backLeftIcon);
        view.setupCenterTitleText(titleText);
    }

    @Override
    public void setupAction() {
        view.leftButtonAction().subscribe(
                o -> leftButtonAction()
        );
        view.rightButtonAction().subscribe(
                o -> rightButtonAction()
        );
    }

    @Override
    public void leftButtonAction() {
        activity.getBus().post(new BackPressEvent());
    }

    @Override
    public void rightButtonAction() {

    }

    @Override
    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }

    }

    @Override
    public void start() {
        setupView();
        setupAction();

    }

    @Override
    public void stop() {
        dispose();
    }
}
