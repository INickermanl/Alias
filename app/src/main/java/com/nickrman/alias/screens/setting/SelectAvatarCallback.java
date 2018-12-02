package com.nickrman.alias.screens.setting;

import com.nickrman.alias.data.models.TeamAvatarItem;

public interface SelectAvatarCallback {
    void selectAvatarCallback(TeamAvatarItem item, int position, Runnable runnable);
}
