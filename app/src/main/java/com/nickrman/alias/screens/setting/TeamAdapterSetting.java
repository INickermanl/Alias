package com.nickrman.alias.screens.setting;

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

public class TeamAdapterSetting extends RecyclerView.Adapter<TeamAdapterSetting.ViewHolder> {
    private List<TeamItem> listTeam;
    private TeamCallback callback;

    public TeamAdapterSetting(List<TeamItem> list, TeamCallback callback) {
        this.listTeam = list;
        this.callback = callback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.team_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeamItem item = listTeam.get(position);


        holder.teamName.setText(item.getNameTeam());


        Picasso.get()
                .load(item.getImageTeam())
                .resize(128, 128)
                .into(holder.teamImage);


        Picasso.get()
                .load(R.drawable.ic_cross)
                .into(holder.crossButton);


        holder.crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deleteTeam(position, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTeam.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView teamImage;
        TextView teamName;
        ImageView crossButton;

        public ViewHolder(View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.team_name);
            teamImage = itemView.findViewById(R.id.image_team_icon);
            crossButton = itemView.findViewById(R.id.cross_button);

        }
    }
}
