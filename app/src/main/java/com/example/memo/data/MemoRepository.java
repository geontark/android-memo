package com.example.memo.data;

import com.example.memo.data.database.Memo;
import com.example.memo.data.datasource.MemoLocalDataSource;

import java.util.ArrayList;

/**
 *  메모 데이터 처리
 *  로컬에서 해당 로직을 처리할 것인지 서버에 요청할것인지에 대해서 분기하는 부분
 *  현재는 로컬부분만있음
 */
public class MemoRepository implements MemoDataSource {
    private MemoLocalDataSource mMemoLocalDataSource;
    private static MemoRepository INSTANCE = null;

    public static MemoRepository getInstance(MemoLocalDataSource memoLocalDataSource)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new MemoRepository(memoLocalDataSource);
        }
        return INSTANCE;
    }

    public MemoRepository(MemoLocalDataSource memoLocalDataSource) {
        this.mMemoLocalDataSource = memoLocalDataSource;
    }

    @Override
    public void getMemos(getMemosCallback callback) {
        mMemoLocalDataSource.getMemos(new getMemosCallback() {
            @Override
            public void onGetMemos(ArrayList<Memo> memos) {
                callback.onGetMemos(memos);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getMemo(String memoId, getMemoCallback callback) {
        mMemoLocalDataSource.getMemo(memoId, new getMemoCallback() {
            @Override
            public void onGetMemo(Memo memo) {
                callback.onGetMemo(memo);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveMemo(Memo memo, saveMemoCallback callback) {
        mMemoLocalDataSource.saveMemo(memo, new saveMemoCallback() {
            @Override
            public void onSaveMemo(Boolean isSuccess) {
                callback.onSaveMemo(isSuccess);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void updateMemo(Memo memo, updateMemoCallback callback) {
        mMemoLocalDataSource.updateMemo(memo, new updateMemoCallback() {
            @Override
            public void onUpdateMemo(Boolean isSuccess) {
                callback.onUpdateMemo(isSuccess);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteMemo(String memoId, deleteMemoCallback callback) {
        mMemoLocalDataSource.deleteMemo(memoId, new deleteMemoCallback() {
            @Override
            public void onDeleteMemo(Boolean isSuccess) {
                callback.onDeleteMemo(isSuccess);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteMemos(deleteMemosCallback callback) {
        mMemoLocalDataSource.deleteMemos(new deleteMemosCallback() {
            @Override
            public void onDeleteMemos(Boolean isSuccess) {
                callback.onDeleteMemos(isSuccess);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
