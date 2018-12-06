package com.nickrman.alias.data.models;

public class TeamItem {
    private int imageTeam;
    private String nameTeam;
    private String scoreTeam ;


    public TeamItem(int imageTeam, String nameTeam) {
        this.imageTeam = imageTeam;
        this.nameTeam = nameTeam;
        this.scoreTeam = "0";
    }

    public TeamItem(int imageTeam, String nameTeam, String scoreTeam) {
        this.imageTeam = imageTeam;
        this.nameTeam = nameTeam;
        this.scoreTeam = scoreTeam;
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

    public String getScoreTeam() {
        return scoreTeam;
    }

    public void setScoreTeam(String scoreTeam) {
        this.scoreTeam = scoreTeam;
    }
}
