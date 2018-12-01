package com.nickrman.alias.base.dialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nickrman.alias.R;


/**
 * Created by max_ermakov on 1/18/17.
 */

public class BaseDialogView extends FrameLayout {
    private static final String TAG = BaseDialogView.class.getSimpleName();

    private ViewGroup contentContainer;
    private ViewGroup actionsContainer;
    private int resLayout;
    private int resBaseId;

    public BaseDialogView(Context context, int resLayout, int reBaseId) {
        super(context);
        this.resLayout = resLayout;
        this.resBaseId = reBaseId;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resLayout, this, true);
        contentContainer = view.findViewById(resBaseId);
    }

    public ViewGroup getContentContainer() {
        return contentContainer;
    }
}
