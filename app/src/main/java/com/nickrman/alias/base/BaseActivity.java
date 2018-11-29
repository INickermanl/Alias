package com.nickrman.alias.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.nickrman.alias.R;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.dialogs.DialogShower;
import com.nickrman.alias.base.dialogs.events.DialogWasDissmisedEvent;
import com.nickrman.alias.base.dialogs.events.HideDialogEvent;
import com.nickrman.alias.base.dialogs.events.ShowDialogEvent;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.services.navigation.managers.ScreenNavigationBackManager;
import com.nickrman.alias.services.navigation.managers.ScreenNavigationManager;
import com.nickrman.alias.services.navigation.managers.events.BackPressEvent;
import com.squareup.otto.Bus;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private Bus bus;
    private CompositeDisposable subscriptions;
    private CompositeDisposable imageSubs;
    private Handler handler;
    private Navigator navigator;
    private BackNavigator navigationBackManager;
    private DialogShower dialogShower;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, " onCreate()" + (savedInstanceState != null ? " recreating" : ""));
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        bus = new Bus();
        dialogShower = new DialogShower(this);
        navigator = new ScreenNavigationManager(this);
        navigationBackManager = new ScreenNavigationBackManager(this);
        bus.register(navigator);
        bus.register(navigationBackManager);
        bus.register(dialogShower);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, " onStart()");
        if (subscriptions == null) {
            subscriptions = new CompositeDisposable();
        }
        imageSubs = new CompositeDisposable();
    }


    @Override
    protected void onStop() {
        Log.i(TAG, " onStop()");
        if (subscriptions != null && !subscriptions.isDisposed()) {
            subscriptions.dispose();
            subscriptions.clear();
            subscriptions = null;
        }
        if (imageSubs != null && !imageSubs.isDisposed()) {
            imageSubs.dispose();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        bus.post(new BackPressEvent());
    }


    @Override
    protected void onResume() {
        Log.i(TAG, " onResume()");
        super.onResume();
        //checkConnectivity();
    }

    @Override
    protected void onPostResume() {
        Log.i(TAG, " onPostResume()");
        super.onPostResume();
    }


    @Override
    protected void onPause() {
        Log.i(TAG, " onPause()");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, " onDestroy()");
        bus.unregister(navigationBackManager);
        super.onDestroy();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent() Intent=" + intent);
    }

    public void showInfoDialog(String message){
        View view = getLayoutInflater().inflate(R.layout.dialog_rules, null);
        TextView messageLabel = view.findViewById(R.id.message);
        View okBtn = view.findViewById(R.id.ok_button);
        messageLabel.setText(message);
        okBtn.setOnClickListener(v -> getBus().post(new HideDialogEvent()));
        bus.post(new ShowDialogEvent(view, R.layout.dialog_base_info,R.id.message));

    }
    public void dialogDismissed(){

        getBus().post(new DialogWasDissmisedEvent());
    }

    public Bus getBus() {
        return bus;
    }


    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public void hideKeyboard() {
        try {
            IBinder windowToken = getWindow().getDecorView().getRootView().getWindowToken();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
    }

    public void showKeyboard(EditText editText) {
        try {
            IBinder windowToken = getWindow().getDecorView().getRootView().getWindowToken();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
    }


    public Navigator getNavigator() {
        return navigator;
    }

    public BackNavigator getNavigationBackManager() {
        return navigationBackManager;
    }

    public abstract ActionBarContract.View getActionBarView();
}

