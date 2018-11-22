package com.nickrman.alias.screens.score;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.BaseFragment;
import com.nickrman.alias.services.navigation.Screen;

public class ScoreFragment extends BaseFragment {


    private ScoreContract.View view;
    private ScoreContract.Presenter presenter;
    private BaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_score,container,false);
        activity = (BaseActivity) getActivity();
        view = new ScoreView(root);
        presenter = new ScorePresenter();
        return root;

    }


}
