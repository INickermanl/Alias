package com.nickrman.alias.screens.result;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.BaseFragment;
import com.nickrman.alias.utils.Constants;

public class ResultFragment extends BaseFragment {
    private BaseActivity activity;
    private ResultContract.View view;
    private ResultContract.Presenter presenter;
    private SharedPreferences mSetting;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_result, container, false);
        activity = (BaseActivity) getActivity();
        Bundle bundle = getArguments();
        mSetting = activity.getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE);
        view = new ResultView(root);
        presenter = new ResultPresenter(view, bundle, mSetting);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
        presenter.setNavigator(activity.getNavigator());
        presenter.setBackNavigator(activity.getNavigationBackManager());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
