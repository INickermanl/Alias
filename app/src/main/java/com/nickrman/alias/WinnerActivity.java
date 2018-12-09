package com.nickrman.alias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.screens.winner.WinnerContract;
import com.nickrman.alias.screens.winner.WinnerPresenter;
import com.nickrman.alias.screens.winner.WinnerView;

public class WinnerActivity extends BaseActivity {
    private View root;
    private WinnerContract.View view;
    private WinnerContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        root = findViewById(R.id.root);
        view = new WinnerView();
        presenter = new WinnerPresenter();
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
}
