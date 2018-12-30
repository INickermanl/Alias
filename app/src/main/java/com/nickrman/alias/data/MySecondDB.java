package com.nickrman.alias.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.nickrman.alias.base.App;
import com.nickrman.alias.data.db.dao.VocabularyDAO;
import com.nickrman.alias.data.db.model.Vocabulary;

import static com.nickrman.alias.data.MySecondDB.DATABASE_VERSION;

@Database(entities = {Vocabulary.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class MySecondDB extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB";

    public abstract VocabularyDAO vocabularyDAO();

    private static MySecondDB INSTANCE;

    public static MySecondDB getInstance() {
        if (INSTANCE == null) { // <= ERROR HERE, should be == null
            INSTANCE = Room.databaseBuilder(App.getInstance().getApplicationContext(),
                    MySecondDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;

    }

}
