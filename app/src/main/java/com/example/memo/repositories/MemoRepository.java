package com.example.memo.repositories;

import android.content.Context;

import com.example.memo.database.AppDatabase;
import com.example.memo.database.Memo;
import com.example.memo.database.MemoDao;

import java.util.List;

public class MemoRepository {
    private MemoDao mMemoDao;

    public MemoRepository(Context context) {
        mMemoDao = AppDatabase.getInstance(context).memoDao();
    }

    public List<Memo> getAll() {
        return mMemoDao.getAll();
    }

    public void insert(final Memo memo) {
        mMemoDao.insert(memo);
    }

    public void update(final Memo memo) {
        mMemoDao.update(memo);
    }

    public void delete(String memoId) {
        mMemoDao.delete(memoId);
    }
}
