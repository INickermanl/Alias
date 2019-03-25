package com.nickrman.alias.screens.setting

import android.view.View
import android.widget.TextView
import com.nickrman.alias.R
import com.nickrman.alias.base.BaseActivity
import com.nickrman.alias.base.action_bar.ActionBarContract
import com.nickrman.alias.base.action_bar.GeneralActionBarPresenter
import com.nickrman.alias.base.dialogs.DialogShower
import com.nickrman.alias.base.dialogs.events.HideDialogEvent
import com.nickrman.alias.base.dialogs.events.ShowDialogEvent
import com.squareup.otto.Bus

class SettingActionBarPresenterI(var baseActivity: BaseActivity,
                                 var view: ActionBarContract.View,
                                 var tittle: Int,
                                 var bus: Bus) : GeneralActionBarPresenter(baseActivity, view, tittle) {


    var dialogShower = DialogShower(baseActivity)

    override fun setupView() {
        super.setupView()
        view.showRightButton(true)
        view.setupCenterTitleText(tittle)
        view.setupRightButton(baseActivity.layoutInflater.inflate(R.layout.ab_info, null))
    }

    override fun setupAction() {
        super.setupAction()
        view.rightButtonAction().subscribe { rightButtonAction() }
    }

    override fun rightButtonAction() {
        val view = baseActivity.layoutInflater.inflate(R.layout.dialog_rules, null)
        val messageLabel = view.findViewById<TextView>(R.id.message)
        val okBtn = view.findViewById<View>(R.id.ok_button)
        messageLabel.text = "test"
        okBtn.setOnClickListener {bus.post(HideDialogEvent()) }
        bus.post(ShowDialogEvent(view, R.layout.dialog_base_for_rules, R.id.dialog_container))
    }

    override fun start() {
        super.start()
        bus.register(dialogShower)
    }

    override fun stop() {
        super.stop()
        bus.unregister(dialogShower)
    }
}