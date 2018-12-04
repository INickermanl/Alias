package com.nickrman.alias.screens.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.dialogs.DialogShower;
import com.nickrman.alias.data.db.AppDatabase;
import com.nickrman.alias.data.models.CollectionImage;
import com.nickrman.alias.data.models.CollectionTeamName;
import com.nickrman.alias.data.models.SettingItem;
import com.nickrman.alias.data.models.TeamAvatarItem;
import com.nickrman.alias.data.models.VocabularyItem;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.data.models.TeamItem;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View view;
    private CompositeDisposable subscription;
    private Navigator navigator;
    private BaseActivity activity;
    private BackNavigator backNavigator;
    private Handler handler;
    private TeamItem teamItem = new TeamItem();

    private List<TeamItem> teamItemList = new ArrayList<>();
    private List<VocabularyItem> vocabularyItemList = new ArrayList();
    private List<TeamAvatarItem> avatarItemList = new ArrayList<>();


    public SettingsPresenter(BaseActivity activity) {
        this.handler = new Handler(Looper.getMainLooper());
        initVocabularyList();
        initAvatarItemList();
        this.activity = activity;

    }


    @Override
    public void start(SettingsContract.View view) {
        this.view = view;
        setupView();

        setupAction();
    }

    private void setupView() {
        subscription = new CompositeDisposable();
    }

    public void setupAction() {

        view.setTeamList(teamItemList, new TeamCallback() {
            @Override
            public void deleteTeam(int position, TeamItem item) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (teamItemList.size() > 0) {
                            teamItemList.remove(item);


                            TeamAvatarItem teamAvatarItem = new TeamAvatarItem(item.getImageTeam());
                            avatarItemList.add(0, teamAvatarItem);

                            for (int i = 0; i < avatarItemList.size(); i++) {
                                avatarItemList.get(i).setBackground(false);
                            }
                            view.updateItemList(teamItemList);
                        }
                    }
                }, 40);


            }
        });
        view.addTeamButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
                avatarItemList.get(0).setBackground(true);
                view.showSelectTeamDialog(avatarItemList, new SelectAvatarCallback() {

                            @Override
                            public void selectAvatarCallback(TeamAvatarItem item, int position, Runnable runnable) {

                                for (int i = 0; i < avatarItemList.size(); i++) {
                                    if (i != position) {
                                        avatarItemList.get(i).setBackground(false);
                                    } else {

                                        avatarItemList.get(position).setBackground(true);
                                    }
                                }
                                runnable.run();
                            }
                        },
                        () -> {//cancel
                            for (int i = 0; i < avatarItemList.size(); i++) {
                                avatarItemList.get(i).setBackground(false);
                            }
                            view.hideDialog(view.getDialogAddTeam());
                        },
                        () -> {//ok button
                            TeamItem itemForItemList = new TeamItem();

                            if (teamItem.getImageTeam() == 0) {
                                for (TeamAvatarItem avatarItem : avatarItemList) {
                                    if (avatarItem.isBackground()) {
                                        itemForItemList.setImageTeam(avatarItem.getAvatar());

                                    }
                                }

                            } else {
                                itemForItemList.setImageTeam(teamItem.getImageTeam());
                            }

                            itemForItemList.setNameTeam(view.getTeamNameFromDialog());
                            teamItemList.add(itemForItemList);
                            view.updateItemList(teamItemList);

                            TeamAvatarItem avatarItemMain = new TeamAvatarItem();

                            for (TeamAvatarItem avatarItem : avatarItemList) {
                                if (avatarItem.getAvatar() == itemForItemList.getImageTeam()) {
                                    avatarItemMain = avatarItem;
                                } else {
                                    avatarItemList.get(0).setBackground(false);
                                }

                            }


                            avatarItemList.remove(avatarItemMain);
                           /* for (int i = 0; i < avatarItemList.size(); i++) {

                            }*/

                            view.hideDialog(view.getDialogAddTeam());


                        },
                        () ->//add teamName dialog
                                view.showChangeNameTeamDialog(
                                        () -> {
                                            if (!view.getTeamNameFromDialogChangeUserName().equals("")) {
                                                view.setTeamNameDialogField(view.getTeamNameFromDialogChangeUserName());
                                            }


                                            view.hideDialog(view.getDialogChangeUserTeamName());
                                        }));


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        view.selectVocabularyButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {


                view.showVocabularyDialog(vocabularyItemList, new SelectVocabularyCallback() {
                    @Override
                    public void itemVocabularyCallback(VocabularyItem item) {
                        Log.d("LOG", item.getNameVocabulary());
                        view.setCurrentVocabularyName(item.getNameVocabulary());
                        view.hideDialog(view.getDialogVocabulary());
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        view.addTenSecondsButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {

                logicAddTenSecondButton();

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        view.takeAwayTenSecondButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {

                logicTakeAwayTenSecondButton();

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.addTenWordsButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
                if (view.getCurrentCountWords() == 190) {

                    view.setEnabledAddWordButton(false);
                    addCurrentCountWords();

                } else if (view.getCurrentCountWords() == 10) {
                    addCurrentCountWords();
                    view.setEnabledTakeAwayWordsButton(true);

                } else {
                    addCurrentCountWords();

                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.takeAwayTenWordsButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
                if (view.getCurrentCountWords() == 20) {
                    view.setEnabledTakeAwayWordsButton(false);
                    takeAwayCurrentCountWords();
                } else if (view.getCurrentCountWords() == 200) {
                    view.setEnabledAddWordButton(true);
                    takeAwayCurrentCountWords();
                } else {
                    takeAwayCurrentCountWords();
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }

            @Override
            public void onComplete() {

            }
        });

        view.startGameButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {
                startGame();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void takeAwayCurrentCountWords() {
        view.setCurrentCountWords(view.getCurrentCountWords() - 10);
    }

    private void addCurrentCountWords() {
        view.setCurrentCountWords(view.getCurrentCountWords() + 10);
    }

    private void logicTakeAwayTenSecondButton() {
        if (view.getCurrentTimeMinute() == 0 & view.getCurrentTimeSecond() == 40) {
            takeAwayCurrentTime();
            view.setEnabledTakeAwayTenSecondButton(false);
        } else if (view.getCurrentTimeMinute() == 5 & view.getCurrentTimeSecond() == 0) {
            view.setEnabledAddTenSecondButton(true);
            takeAwayCurrentTime();

        } else {
            takeAwayCurrentTime();
        }
    }

    private void logicAddTenSecondButton() {
        if (view.getCurrentTimeMinute() == 4 & view.getCurrentTimeSecond() == 50) {
            addCurrentTime();
            view.setEnabledAddTenSecondButton(false);
        } else if (view.getCurrentTimeMinute() == 0 & view.getCurrentTimeSecond() == 30) {
            view.setEnabledTakeAwayTenSecondButton(true);
            addCurrentTime();
        } else {
            addCurrentTime();
        }
    }

    private void addCurrentTime() {
        if (view.getCurrentTimeSecond() == 50) {

            view.setCurrentTimeMinute(view.getCurrentTimeMinute() + 1);
            view.setCurrentTimeSecond(0);
            view.setVisibleTimeSecond(false);

        } else if (view.getCurrentTimeSecond() == 0) {

            view.setCurrentTimeSecond(10);
            view.setVisibleTimeSecond(true);

        } else {

            view.setCurrentTimeSecond(view.getCurrentTimeSecond() + 10);
        }
    }

    private void takeAwayCurrentTime() {

        if (view.getCurrentTimeSecond() == 0) {
            view.setCurrentTimeMinute(view.getCurrentTimeMinute() - 1);
            view.setCurrentTimeSecond(50);
            view.setVisibleTimeSecond(true);
        } else if (view.getCurrentTimeSecond() == 10) {
            view.setCurrentTimeSecond(0);
            view.setVisibleTimeSecond(false);

        } else {
            view.setCurrentTimeSecond(view.getCurrentTimeSecond() - 10);
        }
    }

    @Override
    public void stop() {
        subscription.dispose();
        subscription = null;
    }

    @Override
    public void setNavigation(Navigator navigator) {
        this.navigator = navigator;

    }

    @Override
    public void startGame() {
        Bundle bundle = new Bundle();
        String teamsNames = "";
        String teamsAvatars = "";

        for (TeamItem item : teamItemList) {
            teamsNames += item.getNameTeam() + ",";
            teamsAvatars += item.getImageTeam() + ",";

        }

        SettingItem settingItem = new SettingItem(teamsNames, teamsAvatars, view.getCurrentVocabularyName(), 90, 100);

        bundle.putSerializable(Constants.SETTING, settingItem);

        navigator.navigateTo(Screen.GAMING, ScreenType.ACTIVITY, bundle);
    }

    @Override
    public void setBackNavigator(BackNavigator backNavigator) {
        this.backNavigator = backNavigator;
    }

    private void initVocabularyList() {

        VocabularyItem item1 = new VocabularyItem("book 1");
        VocabularyItem item2 = new VocabularyItem("book 2");
        VocabularyItem item3 = new VocabularyItem("book 3");
        VocabularyItem item4 = new VocabularyItem("book 4");
        vocabularyItemList.add(item1);
        vocabularyItemList.add(item2);
        vocabularyItemList.add(item3);
        vocabularyItemList.add(item4);

    }

    private void initAvatarItemList() {
        TeamAvatarItem item = new TeamAvatarItem(R.mipmap.cat);
        TeamAvatarItem item1 = new TeamAvatarItem(R.mipmap.cat_like);
        TeamAvatarItem item2 = new TeamAvatarItem(R.mipmap.edin);
        TeamAvatarItem item3 = new TeamAvatarItem(R.mipmap.givot);
        TeamAvatarItem item4 = new TeamAvatarItem(R.mipmap.gnom);
        TeamAvatarItem item5 = new TeamAvatarItem(R.mipmap.hanter);
        TeamAvatarItem item6 = new TeamAvatarItem(R.mipmap.rac);
        TeamAvatarItem item7 = new TeamAvatarItem(R.mipmap.pic);

        avatarItemList.add(item);
        avatarItemList.add(item1);
        avatarItemList.add(item2);
        avatarItemList.add(item3);
        avatarItemList.add(item4);
        avatarItemList.add(item5);
        avatarItemList.add(item6);
        avatarItemList.add(item7);
    }

    void makeList() {

        TeamItem item1 = new TeamItem();
        item1.setNameTeam(CollectionTeamName.team3);
        item1.setImageTeam(CollectionImage.test3);


        TeamItem item2 = new TeamItem(CollectionImage.test1, CollectionTeamName.team2);
        TeamItem item3 = new TeamItem(CollectionImage.test2, CollectionTeamName.test);

        teamItemList.add(item1);
        teamItemList.add(item2);
        teamItemList.add(item3);
    }


}
