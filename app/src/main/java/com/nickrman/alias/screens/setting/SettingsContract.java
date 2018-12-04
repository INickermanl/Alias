package com.nickrman.alias.screens.setting;

import android.support.v7.widget.RecyclerView;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.dialogs.BaseDialog;
import com.nickrman.alias.data.models.TeamAvatarItem;
import com.nickrman.alias.data.models.VocabularyItem;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.data.models.TeamItem;

import java.util.List;

import io.reactivex.Observable;

public interface SettingsContract {
    interface View {
        Observable<Object> startGameButtonAction();


        Observable<Object> selectVocabularyButtonAction();

        Observable<Object> addTeamButtonAction();

        Observable<Object> addTenSecondsButtonAction();

        Observable<Object> takeAwayTenSecondButtonAction();

        Observable<Object> addTenWordsButtonAction();

        Observable<Object> takeAwayTenWordsButtonAction();


        int getCurrentTimeMinute();

        void setCurrentTimeMinute(int min);

        int getCurrentTimeSecond();

        void setCurrentTimeSecond(int sec);

        void setVisibleTimeSecond(Boolean visible);

        void setEnabledAddTenSecondButton(Boolean enabled);

        void setEnabledTakeAwayTenSecondButton(Boolean enabled);

        int getCurrentCountWords();

        void setCurrentCountWords(int countWords);

        void setEnabledAddWordButton(Boolean enabled);

        void setEnabledTakeAwayWordsButton(Boolean enabled);

        void setTeamList(List<TeamItem> items, TeamCallback callback);

        void updateItemList(List<?> item);

        void setCurrentVocabularyName(String nameVocabulary);

        void showVocabularyDialog(List<VocabularyItem> itemList, SelectVocabularyCallback callback);


        void showSelectTeamDialog(List<TeamAvatarItem> teamAvatarItemList,
                                  SelectAvatarCallback callback,
                                  Runnable runnable,
                                  Runnable runnableOkButton,
                                  Runnable runnableAddTeamNameDialogButton);

        void showChangeNameTeamDialog(Runnable runnable);


        String getTeamNameFromDialog();


        void setTeamNameDialogField(String nameTeam);

        BaseDialog getDialogVocabulary();

        BaseDialog getDialogAddTeam();

        BaseDialog getDialogChangeUserTeamName();

        void hideDialog(BaseDialog dialog);

        String getTeamNameFromDialogChangeUserName();

        String getCurrentVocabularyName();

        void setStartButtonClickable(boolean clickable);

    }

    interface Presenter {

        void start();

        void stop();

        void setNavigation(Navigator navigator);

        void setBackNavigator(BackNavigator navigator);

        void startGame();

    }
}
