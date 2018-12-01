package com.nickrman.alias.screens.setting;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nickrman.alias.R;
import com.nickrman.alias.base.App;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.dialogs.BaseDialog;
import com.nickrman.alias.base.dialogs.DialogShower;
import com.nickrman.alias.base.dialogs.events.HideDialogEvent;
import com.nickrman.alias.base.dialogs.events.ShowDialogEvent;
import com.nickrman.alias.data.db.AppDatabase;
import com.nickrman.alias.data.models.CollectionImage;
import com.nickrman.alias.data.models.CollectionTeamName;
import com.nickrman.alias.data.models.VocabularyItem;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.data.models.TeamItem;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.squareup.otto.Bus;

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
    private AppDatabase db;
    private String CON = "TEST";
    private DialogShower dialogVocabulary;
    private List<VocabularyItem> vocabularyItemList = new ArrayList();
    private BaseDialog baseDialog;

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

    private static int hello = 0;
    //create listItems and set him to adapter and create callback for item in adapter
    private List<TeamItem> list = new ArrayList<>();
    private Handler handler;

    public SettingsPresenter(BaseActivity activity) {
        this.handler = new Handler(Looper.getMainLooper());
        initVocabularyList();
        this.activity = activity;

        this.dialogVocabulary = new DialogShower(activity);
        //db = AppDatabase.getInMemoryDatabase(App.getInstance());


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
                        view.hideVocabularyDialog();
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
        view.addTeamButtonAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(Object o) {

                TeamItem item1 = new TeamItem(R.mipmap.cat, "test3");
                TeamItem item2 = new TeamItem(R.mipmap.pic, "test3");
                if (hello == 0) {
                    list.add(item1);
                    hello++;
                } else {
                    list.add(item2);
                }

                view.updateItemList(list);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        view.setTeamList(list, new TeamCallback() {
            @Override
            public void deleteTeam(int position, TeamItem item) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() > 2) {
                            list.remove(item);
                            view.updateItemList(list);
                        }
                    }
                }, 40);


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
        navigator.navigateTo(Screen.GAMING, ScreenType.ACTIVITY);
    }

    @Override
    public void setBackNavigator(BackNavigator backNavigator) {
        this.backNavigator = backNavigator;
    }

    void makeList() {

        TeamItem item1 = new TeamItem();
        item1.setNameTeam(CollectionTeamName.team3);
        item1.setImageTeam(CollectionImage.test3);


        TeamItem item2 = new TeamItem(CollectionImage.test1, CollectionTeamName.team2);
        TeamItem item3 = new TeamItem(CollectionImage.test2, CollectionTeamName.test);

        list.add(item1);
        list.add(item2);
        list.add(item3);
    }


}
