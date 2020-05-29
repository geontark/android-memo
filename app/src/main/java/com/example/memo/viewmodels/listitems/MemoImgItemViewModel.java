package com.example.memo.viewmodels.listitems;

import androidx.databinding.ObservableField;

@Deprecated
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
    }

    public void setEditMode(Boolean isEditMode) {
        this.editMode.set(isEditMode);
    }
}
