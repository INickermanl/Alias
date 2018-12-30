package com.nickrman.alias.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nickrman.alias.base.App;
import com.nickrman.alias.data.db.dao.VocabularyDAO;
import com.nickrman.alias.data.db.model.Vocabulary;

import static com.nickrman.alias.data.db.MyAppDatabase.DATABASE_VERSION;


@Database(entities = {Vocabulary.class/*, Word.class*/}, version = DATABASE_VERSION, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB";

    public abstract VocabularyDAO vocabularyDAO();

    private static MyAppDatabase INSTANCE;

    /*
        public abstract VocabularyDAO booksDao();
        public abstract WordDao wordsDao();
        */


    public static MyAppDatabase getInMemoryDatabase() {
        if (INSTANCE == null) {

            INSTANCE =
                    Room.inMemoryDatabaseBuilder(App.getInstance().getApplicationContext(), MyAppDatabase.class)
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static MyAppDatabase getInstance() {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(App.getInstance().getApplicationContext(), MyAppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
