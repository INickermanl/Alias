package com.nickrman.alias.screens.score;

import android.content.pm.PackageInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickrman.alias.R;
import com.nickrman.alias.data.models.TeamItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTeamItemScore extends RecyclerView.Adapter<AdapterTeamItemScore.ViewHolder> {

    private List<TeamItem> listTeam;

    public AdapterTeamItemScore(List<TeamItem> listTeam) {
        this.listTeam = listTeam;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_score_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TeamItem item = listTeam.get(position);

        Picasso.get()
                .load(item.getImageTeam())
                .resize(124,124)
                .into(holder.avatar);

        holder.teamName.setText(item.getNameTeam());

        holder.score.setText(item.getScoreTeam());

    }

    @Override
    public int getItemCount() {
        return listTeam.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView avatar;
        private TextView teamName;
        private TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            teamName = itemView.findViewById(R.id.team_name);
            score = itemView.findViewById(R.id.score_player);
        }
    }
}
