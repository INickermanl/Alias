package com.nickrman.alias.screens.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.BaseFragment;

public class InfoFragment extends BaseFragment {

    InfoContract.View view;
    InfoContract.Presenter presenter;
    BaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        activity = (BaseActivity) getActivity();
        view = new InfoView(root);
        presenter = new InfoPresenter();

        return root;
    }
}
