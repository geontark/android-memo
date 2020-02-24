package com.example.memo.repositories;

import android.content.Context;

public class Repository {
    private Context mContext;

    public Repository(Context context) {
        this.mContext = context;
    }

    public MemoRepository getMemoRepository() {
        return new MemoRepository(mContext);
    }

    public NetworkRepository getNetworkRepository() {
        return new NetworkRepository(mContext);
    }
}
