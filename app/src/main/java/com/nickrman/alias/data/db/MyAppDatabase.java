package com.nickrman.alias.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.nickrman.alias.data.db.dao.VocabularyDAO;
import com.nickrman.alias.data.db.model.Vocabulary;

import static com.nickrman.alias.data.db.AppDatabase.DATABASE_VERSION;


@Database(entities = {Vocabulary.class/*, Word.class*/}, version = DATABASE_VERSION,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB";

    public static VocabularyDAO vocabularyDAO;

    /*private static AppDatabase INSTANCE;


    public abstract VocabularyDAO booksDao();
    public abstract WordDao wordsDao();

    public static AppDatabase getInMemoryDatabase(Context context){
        if(INSTANCE == null){

            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return  INSTANCE;
    }

    public static AppDatabase getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
    return INSTANCE;
    }*/
}
