package com.nickrman.alias.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SettingItem implements Serializable {
    private String teamsNames;
    private String teamsAvatar;
    private String vocabularyName;
    private int roundTime;
    private int countWords;
    private int round;
    private int currentGameUser;

    public SettingItem(String teamsNames, String teamsAvatar, String vocabularyName, int roundTime, int countWords) {
        this.teamsNames = teamsNames;
        this.teamsAvatar = teamsAvatar;
        this.vocabularyName = vocabularyName;
        this.roundTime = roundTime;
        this.countWords = countWords;
        this.round = 1;
        this.currentGameUser = 0;
    }

    public String getTeamsNames() {
        return teamsNames;
    }

    public void setTeamsNames(String teamsNames) {
        this.teamsNames = teamsNames;
    }

    public String getTeamsAvatar() {
        return teamsAvatar;
    }

    public void setTeamsAvatar(String teamsAvatar) {
        this.teamsAvatar = teamsAvatar;
    }

    public String getVocabularyName() {
        return vocabularyName;
    }

    public void setVocabularyName(String vocabularyName) {
        this.vocabularyName = vocabularyName;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public int getCountWords() {
        return countWords;
    }

    public void setCountWords(int countWords) {
        this.countWords = countWords;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getCurrentGameUser() {
        return currentGameUser;
    }

    public void setCurrentGameUser(int currentGameUser) {
        this.currentGameUser = currentGameUser;
    }
}
