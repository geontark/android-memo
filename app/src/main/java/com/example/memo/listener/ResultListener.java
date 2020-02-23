package com.example.memo.listener;

import android.content.Intent;

public interface ResultListener {
    public void callback(int requestCode, int resultCode, Intent data);
}
