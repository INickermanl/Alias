package com.nickrman.alias.screens.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.BaseFragment;

public class ResultFragment extends BaseFragment {
    private BaseActivity activity;
    private ResultContract.View view;
    private ResultContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_result, container, false);
        activity = (BaseActivity) getActivity();
        Bundle bundle = getArguments();
        view = new ResultView(root);
        presenter = new ResultPresenter(view, bundle);

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
}
