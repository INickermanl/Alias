package com.nickrman.alias.screens.score;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickrman.alias.R;
import com.nickrman.alias.data.models.TeamItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamAdapterScore extends RecyclerView.Adapter<TeamAdapterScore.ViewHolder> {

    List<TeamItem> listTeams;

    public TeamAdapterScore(List<TeamItem> listTeams) {
        this.listTeams = listTeams;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_score_item,null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TeamItem item = listTeams.get(position);

        holder.score.setText(String.valueOf(item.getScoreTeam()));

        holder.score.setText(item.getNameTeam());

        Picasso.get()
                .load(item.getImageTeam())
                .resize(100,100)
                .into(holder.imageTeam);
    }

    @Override
    public int getItemCount() {
        return listTeams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageTeam;
        TextView teamName;
        TextView score;

        public ViewHolder(View itemView) {
            super(itemView);

            teamName = itemView.findViewById(R.id.team_name);
            score = itemView.findViewById(R.id.score_player);
            imageTeam = itemView.findViewById(R.id.image_team_icon);

        }
    }
}
