package com.nickrman.alias.data.models;

public class TeamAvatarItem {
    private int avatar;
    private boolean background;

    public TeamAvatarItem(int avatar) {
        this.avatar = avatar;
        this.background = false;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }
}
