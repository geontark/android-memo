package com.example.memo.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MemoDao {
    @Insert(onConflict = REPLACE)
    void insert(Memo memo);

    @Query("SELECT * FROM Memo")
    List<Memo> getAll();


    @Update(onConflict = REPLACE)
    void update(Memo memo);

    @Query("DELETE FROM Memo WHERE id = :memoId")
    void delete(String memoId);

}