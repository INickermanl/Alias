package com.nickrman.alias.base.action_bar;

import android.view.View;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.services.navigation.managers.events.BackPressEvent;

import io.reactivex.disposables.CompositeDisposable;

public class GeneralActionBarPresenter implements ActionBarContract.Presenter {
    private BaseActivity activity;
    private ActionBarContract.View view;
    private CompositeDisposable disposable;
    private int titleResText = R.string.empty_string;
    private String titleStringText = "";


    public GeneralActionBarPresenter(BaseActivity activity, ActionBarContract.View view, int titleResText) {
        this.activity = activity;
        this.view = view;
        view.showRightButton(false);
        this.disposable = new CompositeDisposable();
        this.titleResText = titleResText;
    }

    public GeneralActionBarPresenter(BaseActivity activity, ActionBarContract.View view, String titleStringText) {
        this.activity = activity;
        this.view = view;
        view.showRightButton(false);
        this.titleStringText = titleStringText;
    }

    @Override
    public void setupView() {

        View backLeftIcon = activity.getLayoutInflater().inflate(R.layout.ab_back, null);

        view.setupLeftButton(backLeftIcon);

        if (titleStringText.isEmpty() && titleStringText == null) {
            view.setupCenterTitleText(titleResText);
        } else {
            view.setupCenterTitleText(titleStringText);
        }

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
