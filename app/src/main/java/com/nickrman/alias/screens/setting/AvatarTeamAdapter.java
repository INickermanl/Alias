package com.nickrman.alias.screens.setting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nickrman.alias.R;
import com.nickrman.alias.data.models.TeamAvatarItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AvatarTeamAdapter extends RecyclerView.Adapter<AvatarTeamAdapter.ViewHolder> {

    private List<TeamAvatarItem> teamAvatarItemList = new ArrayList<>();
    private SelectAvatarCallback callback;

    public AvatarTeamAdapter(List<TeamAvatarItem> teamAvatarItemList, SelectAvatarCallback callback) {
        this.teamAvatarItemList = teamAvatarItemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avatar_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TeamAvatarItem item = teamAvatarItemList.get(position);

        if(item.isBackground()){
            holder.avatarBG.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(R.mipmap.fon_avatar)
                    .resize(48, 48)
                    .into(holder.avatarBG);
        }else{
           holder.avatarBG.setVisibility(View.INVISIBLE);
        }


        Picasso.get()
                .load(item.getAvatar())
                .resize(124, 124)
                .into(holder.avatar);


        holder.container.setOnClickListener(v -> callback.selectAvatarCallback(item, position, new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }));

    }

    @Override
    public int getItemCount() {
        return teamAvatarItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView avatarBG;
        private CircleImageView avatar;
        private View container;


        public ViewHolder(View itemView) {
            super(itemView);
            avatarBG = itemView.findViewById(R.id.background_image_item);
            avatar = itemView.findViewById(R.id.team_image);
            container = itemView.findViewById(R.id.container);
        }


    }
}
