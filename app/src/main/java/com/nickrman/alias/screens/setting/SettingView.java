package com.nickrman.alias.screens.setting;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.dialogs.BaseDialog;
import com.nickrman.alias.base.dialogs.BaseDialogView;
import com.nickrman.alias.data.models.TeamAvatarItem;
import com.nickrman.alias.data.models.TeamItem;
import com.nickrman.alias.data.models.VocabularyItem;

import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

public class SettingView implements SettingsContract.View {


    private View root;
    private View startGameButton;
    private View selectVocabularyButton;
    private View addTeamButton;
    private View infoButton;

    private View addTenSecondsButton;
    private View takeAwayTenSecondButton;

    private View addTenWordsButton;
    private View takeAwayTenWordsButton;

    private TextView currentGameTimeMinute;
    private TextView currentGameTimeSecond;
    private TextView currentCountGameWords;
    private View content;
    private TextView currentVocabularyName;

    private TeamAdapterSetting adapter;
    private RecyclerView recyclerViewTeam;

    private BaseActivity baseActivity;

    private TextView teamNameDialogField;
    private View addTeamNameDialogButton;
    private AppCompatEditText userNameTeam;

    private BaseDialog dialogVocabulary;
    private BaseDialog dialogAddTeam;
    private BaseDialog  dialogChangeUserTeamName;


    public SettingView(View root, BaseActivity baseActivity) {
        this.root = root;
        this.baseActivity = baseActivity;
        initView();
    }

    private void initView() {
        startGameButton = root.findViewById(R.id.start_game);
        selectVocabularyButton = root.findViewById(R.id.select_vocabulary_button);
        addTeamButton = root.findViewById(R.id.add_team_button);
        infoButton = root.findViewById(R.id.info);

        currentVocabularyName = root.findViewById(R.id.name_vocabulary);

        addTenSecondsButton = root.findViewById(R.id.add_count_time_in_game);
        takeAwayTenSecondButton = root.findViewById(R.id.take_away_count_time_in_game);

        addTenWordsButton = root.findViewById(R.id.add_count_words_in_game);
        takeAwayTenWordsButton = root.findViewById(R.id.take_away_count_words_in_game);

        currentGameTimeMinute = root.findViewById(R.id.time_in_game_min);
        currentGameTimeSecond = root.findViewById(R.id.time_in_game_sec);

        currentCountGameWords = root.findViewById(R.id.count_words);

        recyclerViewTeam = root.findViewById(R.id.recycler_view);

        content = root.findViewById(R.id.content);
    }

    @Override
    public Observable<Object> startGameButtonAction() {
        return RxView.clicks(startGameButton);
    }


    @Override
    public Observable<Object> selectVocabularyButtonAction() {
        return RxView.clicks(selectVocabularyButton);
    }

    @Override
    public Observable<Object> addTeamButtonAction() {
        return RxView.clicks(addTeamButton);
    }

    @Override
    public Observable<Object> addTenSecondsButtonAction() {
        return RxView.clicks(addTenSecondsButton);
    }

    @Override
    public Observable<Object> takeAwayTenSecondButtonAction() {
        return RxView.clicks(takeAwayTenSecondButton);
    }

    @Override
    public Observable<Object> addTenWordsButtonAction() {
        return RxView.clicks(addTenWordsButton);
    }

    @Override
    public Observable<Object> takeAwayTenWordsButtonAction() {
        return RxView.clicks(takeAwayTenWordsButton);
    }


    @Override
    public int getCurrentTimeMinute() {
        int currentTimeMinute = Integer.valueOf(currentGameTimeMinute.getText().toString().trim());
        return currentTimeMinute;
    }

    @Override
    public void setCurrentTimeMinute(int min) {
        currentGameTimeMinute.setText(String.valueOf(min));
    }

    @Override
    public int getCurrentTimeSecond() {
        int currentTimeSecond = Integer.valueOf(currentGameTimeSecond.getText().toString().trim());
        return currentTimeSecond;
    }

    @Override
    public void setCurrentTimeSecond(int sec) {
        currentGameTimeSecond.setText(String.valueOf(sec));

    }

    @Override
    public void setVisibleTimeSecond(Boolean visible) {
        currentGameTimeSecond.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setEnabledAddTenSecondButton(Boolean enabled) {
        addTenSecondsButton.setEnabled(enabled);
    }

    @Override
    public void setEnabledTakeAwayTenSecondButton(Boolean enabled) {
        takeAwayTenSecondButton.setEnabled(enabled);
    }

    @Override
    public int getCurrentCountWords() {

        return Integer.valueOf(currentCountGameWords.getText().toString().trim());
    }

    @Override
    public void setCurrentCountWords(int countWords) {
        currentCountGameWords.setText(String.valueOf(countWords));
    }

    @Override
    public void setEnabledAddWordButton(Boolean enabled) {
        addTenWordsButton.setEnabled(enabled);

    }

    @Override
    public void setEnabledTakeAwayWordsButton(Boolean enabled) {
        takeAwayTenWordsButton.setEnabled(enabled);
    }

    @Override
    public void setTeamList(List<TeamItem> items, TeamCallback callback) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new TeamAdapterSetting(items, callback);
        recyclerViewTeam.setLayoutManager(linearLayoutManager);
        recyclerViewTeam.setAdapter(adapter);
    }

    @Override
    public void showVocabularyDialog(List<VocabularyItem> itemList, SelectVocabularyCallback callback) {

        View view = baseActivity.getLayoutInflater().inflate(R.layout.dialog_vocabulary, null);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        RecyclerView.Adapter adapter = new VocabularyAdapter(itemList, callback);
        recyclerView.setAdapter(adapter);
        dialogVocabulary = new BaseDialog(baseActivity);

        createBaseDialog(dialogVocabulary, view, R.layout.dialog_base_vocabulary, R.id.dialog_container);

    }


    @Override
    public void showSelectTeamDialog(List<TeamAvatarItem> teamAvatarItemList, SelectAvatarCallback callback,
                                     Runnable runnable, Runnable runnableOkButton, Runnable runnableAddTeamNameDialogButton) {

        View view = baseActivity.getLayoutInflater().inflate(R.layout.dialog_select_team, null);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        teamNameDialogField = view.findViewById(R.id.name_team_text_view);
        addTeamNameDialogButton = view.findViewById(R.id.add_teamName);
        View cancel = view.findViewById(R.id.cancel_btn);
        View ok = view.findViewById(R.id.ok_btn);
        View leftBackButton = view.findViewById(R.id.left_container);

        cancel.setOnClickListener(v -> runnable.run());
        ok.setOnClickListener(v -> runnableOkButton.run());
        leftBackButton.setOnClickListener(v -> runnable.run());
        addTeamNameDialogButton.setOnClickListener(v ->runnableAddTeamNameDialogButton.run());


        GridLayoutManager gridLayoutManager = new GridLayoutManager(root.getContext(), 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerView.Adapter adapter = new AvatarTeamAdapter(teamAvatarItemList, callback);
        recyclerView.setAdapter(adapter);

        dialogAddTeam = new BaseDialog(baseActivity);

        createBaseDialog(dialogAddTeam, view, R.layout.dialog_base_choose_team, R.id.dialog_content_container);
    }

    @Override
    public void showChangeNameTeamDialog(Runnable runnable) {
        View view = baseActivity.getLayoutInflater().inflate(R.layout.dialog_change_team_name, null);

         userNameTeam = view.findViewById(R.id.team_name_et_dialog_change_team_name);
        View ok = view.findViewById(R.id.ok_button_dialog_change_team_name);

        dialogChangeUserTeamName = new BaseDialog(baseActivity);

        ok.setOnClickListener(v -> runnable.run());

        createBaseDialog(dialogChangeUserTeamName,view,R.layout.dialog_base_change_team_name,R.id.dialog_content_container);
    }


    @Override
    public String getTeamNameFromDialog() {

        return teamNameDialogField.getText().toString().trim();
    }

    private void createBaseDialog(BaseDialog baseDialog, View view, int resBaseDialog, int resContainerForData) {
        if(baseDialog == dialogChangeUserTeamName){
            baseActivity.showKeyboard(userNameTeam);
        }

        baseDialog.setCanceledOnTouchOutside(false);
        BaseDialogView dialogView = new BaseDialogView(baseActivity, resBaseDialog, resContainerForData);

        final ViewGroup contentContainer = dialogView.getContentContainer();
        contentContainer.addView(view);


        baseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        baseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        baseDialog.setContentView(dialogView);
        baseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                contentContainer.removeAllViews();
            }
        });
        baseDialog.show();
    }


    @Override
    public void hideDialog(BaseDialog dialog) {

        baseActivity.hideKeyboard();

        try {
            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
            Timber.e(e.getLocalizedMessage());
        } catch (final Exception e) {
            // Handle or log or ignore
            Timber.e(e.getLocalizedMessage());
        } finally {
            dialog = null;
        }
    }

    @Override
    public void setCurrentVocabularyName(String nameVocabulary) {
        currentVocabularyName.setText(nameVocabulary);
    }

    @Override
    public void updateItemList(List<?> item) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateItemTeamAvatar(int position, TeamAvatarItem item) {
        adapter.notifyItemChanged(position);
    }


    @Override
    public void setNameTimeIntoDialog(String nameTeam) {
        teamNameDialogField.setText(nameTeam.toString().trim());
    }

    @Override
    public BaseDialog getDialogVocabulary() {
        return this.dialogVocabulary;
    }

    @Override
    public BaseDialog getDialogAddTeam() {
        return dialogAddTeam;
    }

    @Override
    public String getUserTeamName() {
        return userNameTeam.getText().toString().trim();
    }
}
