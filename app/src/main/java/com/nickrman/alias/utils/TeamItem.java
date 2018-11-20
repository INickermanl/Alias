package com.nickrman.alias.utils;

import android.graphics.drawable.Drawable;

public class TeamItem {
    private int imageTeam;
    private String nameTeam;


    public TeamItem(int imageTeam, String nameTeam) {
        this.imageTeam = imageTeam;
        this.nameTeam = nameTeam;
    }

    public TeamItem() {
    }

    public int getImageTeam() {
        return imageTeam;
    }

    public void setImageTeam(int imageTeam) {
        this.imageTeam = imageTeam;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }
}
