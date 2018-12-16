package com.nickrman.alias;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.screens.winner.WinnerContract;
import com.nickrman.alias.screens.winner.WinnerPresenter;
import com.nickrman.alias.screens.winner.WinnerView;
import com.nickrman.alias.utils.Constants;

public class WinnerActivity extends BaseActivity {
    private View root;
    private WinnerContract.View view;
    private WinnerContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        root = findViewById(R.id.root);
        view = new WinnerView(root);
        presenter = new WinnerPresenter(getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE));
        presenter.setNavigator(getNavigator());

    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.backPress();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }
}
