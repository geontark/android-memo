package com.example.memo.viewmodels.listitems;

import androidx.databinding.ObservableField;

import com.example.memo.eventbus.EventBus;
import com.example.memo.eventbus.EventBusCode;

import org.json.JSONException;
import org.json.JSONObject;

public class MemoImgItemViewModel {
    final static String TAG = MemoImgItemViewModel.class.getSimpleName();
    public ObservableField<Boolean> editMode;
    public String imgUrl;
    private int position;


    public MemoImgItemViewModel(String imgUrl,int position) {
        this.imgUrl = imgUrl;
        this.position = position;
        this.editMode = new ObservableField<>(false);
    }

    public void onClikDelete(){
        JSONObject json =  new JSONObject();
        try {
            json.put("code", EventBusCode.MEMO_IMG_ITEM_DELETE);
            json.put("data",position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EventBus.getInstance().sendEvent(json);
    }

    public void setEditMode(Boolean isEditMode) {
        this.editMode.set(isEditMode);
    }
}
