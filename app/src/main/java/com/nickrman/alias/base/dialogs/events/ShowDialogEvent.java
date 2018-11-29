package com.nickrman.alias.base.dialogs.events;

import android.view.View;

/**
 * Created by bohdan on 23.09.16.
 */
public class ShowDialogEvent {
    public View view;
    public int resLayout;
    public int resBaseId;

    public ShowDialogEvent(View view, int resLayout, int resBaseId) {
        this.view = view;
        this.resLayout = resLayout;
        this.resBaseId = resBaseId;
    }
}
