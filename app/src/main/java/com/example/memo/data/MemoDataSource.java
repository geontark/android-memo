package com.example.memo.data;

import com.example.memo.data.database.Memo;

import java.util.ArrayList;

public interface MemoDataSource {
    interface getMemosCallback {
        void onGetMemos(ArrayList<Memo> memos);
        void onDataNotAvailable();
    }

    interface getMemoCallback {
        void onGetMemo(Memo memo);
        void onDataNotAvailable();
    }

    interface saveMemoCallback {
        void onSaveMemo(Boolean isSuccess);
        void onDataNotAvailable();
    }

    interface updateMemoCallback {
        void onUpdateMemo(Boolean isSuccess);
        void onDataNotAvailable();
    }

    interface deleteMemoCallback {
        void onDeleteMemo(Boolean isSuccess);
        void onDataNotAvailable();
    }

    interface deleteMemosCallback {
        void onDeleteMemos(Boolean isSuccess);
        void onDataNotAvailable();
    }

    public void getMemos(getMemosCallback callback);
    public void getMemo(String memoId, getMemoCallback callback);
    public void saveMemo(Memo memo, saveMemoCallback callback);
    public void updateMemo(Memo memo, updateMemoCallback callback);
    public void deleteMemo(String memoId, deleteMemoCallback callback);
    public void deleteMemos(deleteMemosCallback callback);
}
