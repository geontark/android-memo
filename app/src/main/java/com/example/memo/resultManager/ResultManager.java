package com.example.memo.resultManager;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.memo.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity result에 대한 결과값을 viewmodel로 보내주는 역할을 함
 * 결과를 listen할 viewmodel의 리스너를 등록해주어야함
 */
public class ResultManager {
    private List<ResultListener> mListeners = new ArrayList<>();

    // result 이벤트 받아서 listen하고 있는 대상에게 보내줌
    private void notifyResult(int requestCode, int resultCode, Intent data) {
        int length = mListeners.size();
        for (int i = 0; i < length; i++) {
            mListeners.get(i).callback(requestCode, resultCode, data);
        }
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        notifyResult(requestCode, resultCode, data);
    }

    public void register(ResultListener listener) {
        mListeners.add(listener);
    }

    public void unReigster(ResultListener listener) {
        mListeners.remove(listener);
    }

}
