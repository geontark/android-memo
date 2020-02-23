package com.example.memo.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.memo.usecase.Usecase;

public class FloatbtnViewModel {
    Usecase mUsecase;

    public FloatbtnViewModel(Usecase usecase) {
        this.mUsecase = usecase;
    }

    public void onClick() {
        mUsecase.detailScreen();
    }
}
