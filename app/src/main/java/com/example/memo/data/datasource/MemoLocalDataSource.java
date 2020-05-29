package com.example.memo.data.datasource;

import android.content.Context;
import com.example.memo.data.MemoDataSource;
import com.example.memo.data.database.AppDatabase;
import com.example.memo.data.database.Memo;
import com.example.memo.data.database.MemoDao;
import com.example.memo.utilities.AppExecutors;

import java.util.ArrayList;
import java.util.List;

/**
 *  로컬부분에서 Memo 데이터에 관한 처리
 *  ROOM DB에 CRUD 가능
 */
public class MemoLocalDataSource implements MemoDataSource {

    private static MemoLocalDataSource INSTANCE = null;
    private MemoDao mMemoDao;

    public static MemoLocalDataSource getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new MemoLocalDataSource(context);
        }
        return INSTANCE;
    }

    public MemoLocalDataSource(Context context) {
        mMemoDao = AppDatabase.getInstance(context).memoDao();
    }

    @Override
    public void getMemos(getMemosCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Memo> memos = mMemoDao.getAll();
                AppExecutors executors = new AppExecutors();
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(memos != null) {
                            callback.onGetMemos((ArrayList)memos);
                        }else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(runnable);
    }

    @Override
    public void getMemo(String memoId, getMemoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Memo memo = mMemoDao.getMemo(memoId);
                AppExecutors executors = new AppExecutors();
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(memo != null) {
                            callback.onGetMemo(memo);
                        }else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(runnable);
    }

    @Override
    public void saveMemo(Memo memo, saveMemoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mMemoDao.insert(memo);
                try {
                    callback.onSaveMemo(true);
                } catch (Exception e) {
                    callback.onDataNotAvailable();
                }
            }
        };
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(runnable);
    }

    @Override
    public void updateMemo(Memo memo, updateMemoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mMemoDao.update(memo);
                    callback.onUpdateMemo(true);
                } catch (Exception e) {
                    callback.onDataNotAvailable();
                }
            }
        };
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(runnable);
    }

    @Override
    public void deleteMemo(String memoId, deleteMemoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mMemoDao.delete(memoId);
                    callback.onDeleteMemo(true);
                } catch (Exception e) {
                    callback.onDataNotAvailable();
                }
            }
        };
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(runnable);
    }

    @Override
    public void deleteMemos(deleteMemosCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mMemoDao.deleteAll();
                    callback.onDeleteMemos(true);
                } catch (Exception e) {
                    callback.onDataNotAvailable();
                }
            }
        };
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(runnable);
    }
}
