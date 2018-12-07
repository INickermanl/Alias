package com.nickrman.alias.services.navigation.managers;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.services.navigation.managers.events.BackPressEvent;
import com.nickrman.alias.services.navigation.managers.events.TryExitActivityEvent;
import com.nickrman.alias.services.navigation.managers.events.TryNavigateBackEvent;
import com.squareup.otto.Subscribe;


import timber.log.Timber;

public class ScreenNavigationBackManager implements BackNavigator {

    private BaseActivity activity;
    private boolean couldNavigateBack = true;
    private boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_OUT = 2000;
    private Handler handler;
    private FragmentManager fragmentManager;

    public ScreenNavigationBackManager(BaseActivity activity) {
        this.activity = activity;
        handler = new Handler(Looper.getMainLooper());
    }

    public boolean isCouldNavigateBack() {
        return couldNavigateBack;
    }

    public void setCouldNavigateBack(boolean couldNavigateBack) {
        this.couldNavigateBack = couldNavigateBack;
    }

    public void navigateBack() {
       fragmentManager = activity.getSupportFragmentManager();


        if (fragmentManager.getBackStackEntryCount() > 1) {
            Timber.d("pop fragment from backstack");

            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            String fragmentName = backEntry.getName();
            fragmentManager.popBackStackImmediate(fragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else {
            tryExitActivity();
        }
    }

    public void tryExitActivity() {
        activity.hideKeyboard();

        //fragmentManager.getBackStackEntryCount() == 1
        if (Screen.SCORE.equals(activity.getNavigator().getScreen())) {
            if (doubleBackToExitPressedOnce) {
                exitGame();
            } else {
                doubleBackToExitPressedOnce = true;
                Snackbar.make(activity.findViewById(R.id.root), R.string.back_message, Snackbar.LENGTH_LONG)
                        .show();
                handler.postDelayed(() -> doubleBackToExitPressedOnce = false, TIME_OUT);
            }
        } else {
            exit();
        }
    }

    private void exitGame(){
        activity.getNavigator().navigateTo(Screen.START,ScreenType.ACTIVITY);
    }

    private void exit() {
        activity.finish();
        activity.freeMemory();

    }

    @Subscribe
    public void onEvent(BackPressEvent event) {
        if (couldNavigateBack) {
            navigateBack();
        } else {
            activity.getBus().post(new TryNavigateBackEvent());
        }
    }

    @Subscribe
    public void onEvent(TryExitActivityEvent event) {
        tryExitActivity();
    }
    @Subscribe
    public void onEvent(TryNavigateBackEvent event){
        tryExitActivity();
    }

}
