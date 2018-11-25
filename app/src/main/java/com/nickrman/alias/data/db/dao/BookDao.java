package com.nickrman.alias.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.nickrman.alias.data.db.model.Book;

import io.reactivex.Flowable;


@Dao
public interface BookDao {
    
    @Query("SELECT * FROM books WHERE id =:userId")
    Flowable<Book> getBooksById(int userId);


}
