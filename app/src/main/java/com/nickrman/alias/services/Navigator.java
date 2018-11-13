package com.nickrman.alias.services;

import android.os.Bundle;

import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;

public interface Navigator {
    void navigateTo(Screen screen, ScreenType type);
    void navigateTo(Screen screen, ScreenType type, Bundle args);
    Screen getScreen();
    void setScreen(Screen screen);
}
