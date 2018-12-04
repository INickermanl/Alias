package com.nickrman.alias.screens.score;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.data.models.TeamItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Observable;

public class ScoreView implements ScoreContract.View {

    private View root;
    private TextView teamNameInBoard;
    private RecyclerView recyclerView;
    private ImageView imageTeam;
    private TextView scoreTeam;
    private View startGameButton;
    private AdapterTeamItemScore adapter;
    private BaseActivity activity;

    public ScoreView(View root, BaseActivity activity) {
        this.root = root;
        this.activity = activity;
        initView();
    }


    private void initView() {
        teamNameInBoard = root.findViewById(R.id.team_name);
        startGameButton = root.findViewById(R.id.start_game);
        recyclerView = root.findViewById(R.id.recycler_view);
        scoreTeam = root.findViewById(R.id.team_score);
        imageTeam = root.findViewById(R.id.team_image);

    }

    @Override
    public Observable<Object> startGameButtonAction() {
        return RxView.clicks(startGameButton);
    }


    @Override
    public void setScoreTeam(String score) {
        scoreTeam.setText(score);
    }


    @Override
    public void setImageTeam(int res) {
        Picasso.get()
                .load(res)
                .resize(100,100)
                .into(imageTeam);
    }


    @Override
    public void setTeamName(String name) {
            teamNameInBoard.setText(name);
    }

    @Override
    public void setTeamList(List<TeamItem> items) {
        LinearLayoutManager llm = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterTeamItemScore(items);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }
}
