package com.example.memo.eventbus;

import org.json.JSONObject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class EventBus {
    private static EventBus mEventBus;
    private PublishSubject<JSONObject> mSubject;

    public EventBus() {
        this.mSubject = PublishSubject.create();
    }

    public static EventBus getInstance(){
        if(mEventBus == null){
            mEventBus= new EventBus();
        }
        return mEventBus;
    }

    public void sendEvent(JSONObject object){
        mSubject.onNext(object);
    }

    public Observable<JSONObject> getObservable(){
        return mSubject;
    }

}
