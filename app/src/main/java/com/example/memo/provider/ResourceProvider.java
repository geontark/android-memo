package com.example.memo.provider;

import android.content.Context;

import com.example.memo.R;

public class ResourceProvider {
    Context mContext;

    public ResourceProvider(Context context) {
        mContext = context;
    }

    public String getString(int resourceId){
        return mContext.getResources().getString(resourceId);
    }
}
