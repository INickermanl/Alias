package com.nickrman.alias.services.navigation;

public interface BackNavigator {
    void navigateBack();
    void tryExitActivity();
    void setCouldNavigateBack(boolean couldNavigateBack);
}
