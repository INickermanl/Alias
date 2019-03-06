package com.nickrman.alias.screens.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.data.models.TeamAvatarItem;
import com.nickrman.alias.data.models.VocabularyItem;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.data.models.TeamItem;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class SettingsPresenter implements SettingsContract.Presenter {
    private int counter = 2;
    private boolean checkBook = false;

    private SettingsContract.View view;
    private CompositeDisposable subscription;
    private Navigator navigator;
    private BaseActivity activity;
    private BackNavigator backNavigator;
    private Handler handler;
    private TeamItem teamItemOkDialogAction = new TeamItem();

    private List<TeamItem> teamItemList = new ArrayList<>();
    private List<VocabularyItem> vocabularyItemList = new ArrayList();
    private List<TeamAvatarItem> avatarItemList = new ArrayList<>();
    private List<String> teamNameList = new ArrayList<>();

    private SharedPreferences mSettings;


    public SettingsPresenter(BaseActivity activity, SettingsContract.View view) {
        this.handler = new Handler(Looper.getMainLooper());
        this.view = view;
        this.activity = activity;
        initList();
        mSettings = activity.getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE);
        setupView();


    }

    private void initList() {
        initVocabularyList();
        initAvatarItemList();
        initTeamNameList();
        makeTeamList();
    }


    @Override
    public void start() {

        subscription = new CompositeDisposable();
        setupAction();
    }

    private void setupView() {
        view.setStartButtonClickable(false);
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

                            teamNameList.add(0, item.getNameTeam());
                            --counter;
                            opportunityCheck();

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
                if (avatarItemList.size() != 0)
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


                            if (teamItemOkDialogAction.getImageTeam() == 0) {
                                for (TeamAvatarItem avatarItem : avatarItemList) {
                                    if (avatarItem.isBackground()) {
                                        itemForItemList.setImageTeam(avatarItem.getAvatar());

                                    }
                                }
                            } else {
                                itemForItemList.setImageTeam(teamItemOkDialogAction.getImageTeam());
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
                            teamNameList.remove(0);

                            avatarItemList.remove(avatarItemMain);
                            counter++;
                            opportunityCheck();
                            view.hideDialog(view.getDialogAddTeam());
                        },
                        () ->//add teamName dialog
                                view.showChangeNameTeamDialog(
                                        () -> {
                                            if (!view.getTeamNameFromDialogChangeUserName().equals("")) {
                                                view.setTeamNameDialogField(view.getTeamNameFromDialogChangeUserName());
                                            }

                                            view.hideDialog(view.getDialogChangeUserTeamName());
                                        }),

                        //setDialogNameTeam
                        teamNameList.get(0).trim());


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
                        checkBook = true;
                        opportunityCheck();
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


        String teamsNames = "";
        String teamsAvatars = "";
        String teamScores = "";

        for (TeamItem item : teamItemList) {
            teamsNames += item.getNameTeam() + ",";
            teamsAvatars += item.getImageTeam() + ",";
            teamScores += 0 + ",";

        }

        int countSeconds = view.getCurrentTimeSecond() + (view.getCurrentTimeMinute() * 60);


        SharedPreferences.Editor editor = mSettings.edit();

        editor.putString(Constants.SETTING_TEAM_NAMES, teamsNames);
        editor.putString(Constants.SETTING_TEAM_AVATARS, teamsAvatars);
        editor.putString(Constants.SETTING_SCORES, teamScores);
        editor.putString(Constants.SETTING_VOCABULARY, view.getCurrentVocabularyName());

        editor.putInt(Constants.SETTING_COUNT_SECONDS, countSeconds);
        editor.putInt(Constants.SETTING_COUNT_WORDS, view.getCurrentCountWords());
        editor.putInt(Constants.SETTING_ROUND, 1);
        editor.putInt(Constants.SETTING_PLAY_TEAM, 0);


        editor.apply();

        navigator.navigateTo(Screen.GAMING, ScreenType.ACTIVITY);
    }

    @Override
    public void setBackNavigator(BackNavigator backNavigator) {
        this.backNavigator = backNavigator;
    }

    private void initVocabularyList() {

        VocabularyItem item1 = new VocabularyItem("Idiom dictionary");
        VocabularyItem item2 = new VocabularyItem("Animal Dictionary");
        VocabularyItem item3 = new VocabularyItem("Former mage dictionary");
        VocabularyItem item4 = new VocabularyItem("Dictionary of feelings\nand emotions");
        vocabularyItemList.add(item1);
        vocabularyItemList.add(item2);
        vocabularyItemList.add(item3);
        vocabularyItemList.add(item4);

    }

    private void initAvatarItemList() {
        TeamAvatarItem item = new TeamAvatarItem(R.mipmap.cat);
        TeamAvatarItem item2 = new TeamAvatarItem(R.mipmap.edin);
        TeamAvatarItem item3 = new TeamAvatarItem(R.mipmap.givot);
        TeamAvatarItem item4 = new TeamAvatarItem(R.mipmap.gnom);
        TeamAvatarItem item5 = new TeamAvatarItem(R.mipmap.hanter);
        TeamAvatarItem item6 = new TeamAvatarItem(R.mipmap.pic);


        avatarItemList.add(item);
        avatarItemList.add(item2);
        avatarItemList.add(item3);
        avatarItemList.add(item4);
        avatarItemList.add(item5);
        avatarItemList.add(item6);

    }

    void makeTeamList() {


        teamItemList.add(new TeamItem(avatarItemList.get(0).getAvatar(), teamNameList.get(0)));
        teamItemList.add(new TeamItem(avatarItemList.get(1).getAvatar(), teamNameList.get(1)));

        avatarItemList.remove(0);
        avatarItemList.remove(0);
        teamNameList.remove(0);
        teamNameList.remove(0);
    }

    private void initTeamNameList() {
        teamNameList.add("Vasya");
        teamNameList.add("bem");
        teamNameList.add("Nik");
        teamNameList.add("Armen");
        teamNameList.add("Cat");
        teamNameList.add("mamka");
        teamNameList.add("team");
        teamNameList.add("test");
        teamNameList.add("");


    }

    void opportunityCheck() {

        if (counter >= 2 && checkBook) {
            view.setStartButtonClickable(true);
        } else /*if (counter < 2)*/ {
            view.setStartButtonClickable(false);
        }
    }


}
