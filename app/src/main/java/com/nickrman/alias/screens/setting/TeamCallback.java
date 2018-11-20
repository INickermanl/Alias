package com.nickrman.alias.screens.setting;

import com.nickrman.alias.utils.TeamItem;

public interface TeamCallback {
    void deleteTeam(int position);
    void addTeam(TeamItem item);
}
