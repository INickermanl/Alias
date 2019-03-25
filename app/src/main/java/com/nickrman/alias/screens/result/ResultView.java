package com.nickrman.alias.screens.result;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;
import com.nickrman.alias.data.models.ItemAnswer;

import java.util.List;

import io.reactivex.Observable;

public class ResultView implements ResultContract.View {
    private View root;
    private AdapterResult adapter;
    private RecyclerView recyclerView;
    private TextView teamPoint;
    private View endButton;

    public ResultView(View root) {
        this.root = root;
        initView();
    }

    private void initView() {
        recyclerView = root.findViewById(R.id.recycler_view);
        teamPoint = root.findViewById(R.id.team_point);
        endButton = root.findViewById(R.id.end_game);
    }

    @Override
    public void setTeamList(List<ItemAnswer> answerList, ListItemCallback callback) {
        LinearLayoutManager llm = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterResult(answerList, callback);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void setTeamPoint(String score) {
        teamPoint.setText(score.trim());
    }

    @Override
    public Observable<Object> endButtonAction() {
        return RxView.clicks(endButton);
    }

    @Override
    public String getTeamPoint() {
        return teamPoint.getText().toString().trim();
    }


}
