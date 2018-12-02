package com.nickrman.alias.data.models;

public class TeamItem {
    private int imageTeam;
    private String nameTeam;
    private int scoreTeam ;


    public TeamItem(int imageTeam, String nameTeam) {
        this.imageTeam = imageTeam;
        this.nameTeam = nameTeam;
        this.scoreTeam = 0;
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

    public int getScoreTeam() {
        return scoreTeam;
    }

    public void setScoreTeam(int scoreTeam) {
        this.scoreTeam = scoreTeam;
    }
}
