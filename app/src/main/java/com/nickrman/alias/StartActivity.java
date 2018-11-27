package com.nickrman.alias;

import android.os.Bundle;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.screens.start.StartContract;
import com.nickrman.alias.screens.start.StartPresenter;
import com.nickrman.alias.screens.start.StartView;


public class StartActivity extends BaseActivity {
    private StartContract.View view;
    private StartContract.Presenter presenter;
    private View root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        root = findViewById(R.id.root);


        view = new StartView(root);
        presenter = new StartPresenter();
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
}
