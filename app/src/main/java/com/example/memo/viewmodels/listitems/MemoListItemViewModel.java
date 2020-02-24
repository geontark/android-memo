package com.example.memo.viewmodels.listitems;

import com.example.memo.database.Memo;
import com.example.memo.usecase.Usecase;

import java.util.ArrayList;

public class MemoListItemViewModel {
    private Usecase mUsecase;

    public String id;
    public String title;
    public String date;
    public String description;
    public ArrayList<String> imgs;

    public MemoListItemViewModel(Memo memo, Usecase usecase) {
        id = memo.getId();
        title = memo.getTitle();
        date = memo.getDate();
        description = memo.getDescription();
        imgs = memo.getImgs();
        mUsecase = usecase;
    }

    public void onClick() {
        mUsecase.detailScreen(new Memo(id, title, description, date, imgs));
    }
}
