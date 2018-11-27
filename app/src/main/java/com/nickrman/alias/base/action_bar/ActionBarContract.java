package com.nickrman.alias.base.action_bar;

import io.reactivex.Observable;

public interface ActionBarContract {
    interface View {
    void showAB( boolean show);
    void setupLeftButton(android.view.View view);
    void showLeftButton(boolean show);
    void setupRightButton(android.view.View view);
    void showRightButton(boolean show);

    void showCenterTitleText(boolean show);
    void setupCenterTitleText(int res);
    void setupCenterTitleText(String string);


    void showCenterSubtitleText(boolean show);
    void setupCenterSubtitleText(int res);
    void setupCenterSubtitleText(String string);



    android.view.View getAB();

    Observable<Object> leftButtonAction();
    Observable<Object> rightButtonAction();

    }

    interface Presenter {
        void setupView();
        void setupAction();
        void leftButtonAction();
        void rightButtonAction();
        void dispose();
        void start();
        void stop();

    }

}
