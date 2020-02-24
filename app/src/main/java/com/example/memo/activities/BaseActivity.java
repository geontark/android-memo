package com.example.memo.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.example.memo.listener.LifecycleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 라이프 사이클 이벤트를 등록되어 있는 리스너에게 알리기 위한 엑티비티
 * 모든 엑티비티는 BaseActivity를 상속받고, viewmodel에서는 리스너를 등록하여 라이프 사이클 이벤트를 알수 있음
 */
public class BaseActivity extends Activity {

    private LifecycleOwner mLifecycleOwner =new LifecycleOwner();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifyEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notifyEvent(Lifecycle.Event.ON_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        notifyEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        notifyEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notifyEvent(Lifecycle.Event.ON_DESTROY);
    }

    private void notifyEvent(Enum event){
        mLifecycleOwner.notifyEvent(event);
    }

    protected void register(LifecycleListener listener){
        mLifecycleOwner.register(listener);
    }

    protected void unReigster(LifecycleListener listener){
        mLifecycleOwner.unregister(listener);
    }

    class LifecycleOwner {
        private List<LifecycleListener> mListeners = new ArrayList<>();
        private Enum lastEvent = Lifecycle.Event.ON_CREATE;

        public void register(LifecycleListener listener) {
            listener.callback(lastEvent.toString());
            mListeners.add(listener);
        }

        public void unregister(LifecycleListener listener){
            mListeners.remove(listener);
        }

        public void notifyEvent(Enum event){

            String strEvent = event.toString();
            for (LifecycleListener listener : mListeners) {
                listener.callback(strEvent);
            }
        }
    }
}
