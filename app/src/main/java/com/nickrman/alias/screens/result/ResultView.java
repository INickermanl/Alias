package com.nickrman.alias.screens.result;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nickrman.alias.R;
import com.nickrman.alias.data.models.ItemAnswer;

import java.util.List;

public class ResultView implements ResultContract.View {
    private View root;
    private AdapterResult adapter;
    private RecyclerView recyclerView;

    public ResultView(View root) {
        this.root = root;
        initView();
    }

    private void initView() {
        recyclerView = root.findViewById(R.id.recycler_view);
    }

    @Override
    public void setTeamList(List<ItemAnswer> answerList) {
        LinearLayoutManager llm = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterResult(answerList);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

    }
}
