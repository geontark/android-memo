package com.example.memo.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.example.memo.listener.LifecycleListener;

import java.util.ArrayList;
import java.util.List;

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
