package com.nickrman.alias;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.data.db.MyAppDatabase;
import com.nickrman.alias.screens.start.StartContract;
import com.nickrman.alias.screens.start.StartPresenter;
import com.nickrman.alias.screens.start.StartView;
import com.nickrman.alias.utils.Constants;


public class StartActivity extends BaseActivity {
    private StartContract.View view;
    private StartContract.Presenter presenter;
    private View root;
    private SharedPreferences mSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        root = findViewById(R.id.root);
        mSetting = getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE);

        MyAppDatabase myAppDatabase = MyAppDatabase.getINSTANCE(this);
                view = new StartView(root);
        presenter = new StartPresenter(mSetting, myAppDatabase);
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.start(view);
        getBus().register(presenter);
        presenter.setNavigation(getNavigator());

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return null;
    }
}
