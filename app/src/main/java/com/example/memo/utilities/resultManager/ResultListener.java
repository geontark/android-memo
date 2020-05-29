package com.example.memo.utilities.resultManager;

import android.content.Intent;

public interface ResultListener {
    public void callback(int requestCode, int resultCode, Intent data);
}
