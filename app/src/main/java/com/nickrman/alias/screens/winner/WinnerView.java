package com.nickrman.alias.screens.winner;

import android.view.View;
import android.widget.TextView;

import com.nickrman.alias.R;

public class WinnerView implements WinnerContract.View {
    View root;
    private TextView nameWinnerTeam;
    private TextView scoreWinnerTeam;


    public WinnerView(View root) {
        this.root = root;
        initView();

    }

    private void initView() {
        nameWinnerTeam = root.findViewById(R.id.name_win_team);
        scoreWinnerTeam = root.findViewById(R.id.score_win_team);
    }

    @Override
    public void setWinnerTeamName(String winnerTeam) {
        nameWinnerTeam.setText(winnerTeam);
    }

    @Override
    public void setWinnerScore(String score) {
        scoreWinnerTeam.setText(score);

    }
}
