package com.nickrman.alias.screens.setting;

import android.view.View;
import android.widget.TextView;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.action_bar.GeneralActionBarPresenter;
import com.nickrman.alias.base.dialogs.DialogShower;
import com.nickrman.alias.base.dialogs.events.HideDialogEvent;
import com.nickrman.alias.base.dialogs.events.ShowDialogEvent;
import com.squareup.otto.Bus;

public class SettingActionBarPresenter extends GeneralActionBarPresenter {
    private BaseActivity activity;
    private ActionBarContract.View view;
    private int titleText;
    private DialogShower dialogShower;
    private Bus bus;


    public SettingActionBarPresenter(BaseActivity activity, ActionBarContract.View view, int titleText, Bus bus) {
        super(activity, view, titleText);
        this.activity = activity;
        this.view = view;
        dialogShower = new DialogShower(activity);
        view.showRightButton(true);
        this.titleText = titleText;
        this.bus = bus;
    }

    @Override
    public void start() {
        super.start();
        bus.register(dialogShower);
    }

    @Override
    public void stop() {
        super.stop();
        bus.unregister(dialogShower);
    }

    @Override
    public void setupView() {
        super.setupView();

        view.setupCenterTitleText(titleText);
        View infoRightIcon = activity.getLayoutInflater().inflate(R.layout.ab_info, null);
        view.setupRightButton(infoRightIcon);

    }

    @Override
    public void setupAction() {
        super.setupAction();
        view.rightButtonAction()
                .subscribe(v -> {
                    rightButtonAction();
                });
    }

    @Override
    public void rightButtonAction() {

        View view = activity.getLayoutInflater().inflate(R.layout.dialog_rules, null);
        TextView messageLabel = view.findViewById(R.id.message);
        View okBtn = view.findViewById(R.id.ok_button);
        messageLabel.setText("test");
        okBtn.setOnClickListener(v -> bus.post(new HideDialogEvent()));
        bus.post(new ShowDialogEvent(view, R.layout.dialog_base_for_rules, R.id.dialog_container));

    }
}
