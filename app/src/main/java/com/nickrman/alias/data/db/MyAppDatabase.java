package com.nickrman.alias.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nickrman.alias.base.App;
import com.nickrman.alias.data.db.dao.VocabularyDAO;
import com.nickrman.alias.data.db.dao.WordDao;
import com.nickrman.alias.data.db.model.Vocabulary;
import com.nickrman.alias.data.db.model.Word;

import static com.nickrman.alias.data.db.MyAppDatabase.DATABASE_VERSION;


@Database(entities = {Vocabulary.class, Word.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB";

    public abstract VocabularyDAO vocabularyDAO();

    public abstract WordDao wordDao();

    private static MyAppDatabase INSTANCE;


    public static MyAppDatabase getINSTANCE(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MyAppDatabase.class,
                    DATABASE_NAME
            ).fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
