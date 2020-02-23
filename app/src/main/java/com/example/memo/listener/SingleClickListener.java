package com.example.memo.listener;

import android.os.SystemClock;
import android.view.View;

// 중복 클릭을 방지하기 위한 용도(적용 안됨)
public abstract class SingleClickListener implements View.OnClickListener{
    //클릭 가능한 시간 차이
    private static final long MIN_CLICK_INTERVAL=600;
    //마지막으로 클릭한 시간
    private long mLastClickTime;
    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        long currentClickTime= SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;

        if(elapsedTime<=MIN_CLICK_INTERVAL)
            return;
        //중복클릭시간 아니면 이벤트 발생
        onSingleClick(v);
    }
}
