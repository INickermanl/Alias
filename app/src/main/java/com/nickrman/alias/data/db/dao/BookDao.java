package com.nickrman.alias.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nickrman.alias.data.db.model.Book;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface BookDao {

    @Query("SELECT * FROM books WHERE id = :userId")
    Flowable<Book> getBooksById(int userId);

    @Query("SELECT * FROM books")
    Flowable<List<Book>> getAllBooks();

    @Insert
    void insertBook(Book ... books);

    @Update
    void updateBook(Book ... books);

    @Delete
    void deleteBooks(Book book);

    @Query("DELETE FROM books")
    void deleteAllBooks();
}
