package com.example.memo.viewmodels.listitems;

import com.example.memo.database.Memo;
import com.example.memo.repositories.MemoRepository;
import com.example.memo.repositories.Repository;
import com.example.memo.usecase.Usecase;

import java.util.ArrayList;

public class MemoListItemViewModel {
    private Usecase mUsecase;
    public String id;
    public String title;
    public String description;
    public ArrayList<String> imgs;
    public String date;
    MemoRepository mMemoRepository;

    public MemoListItemViewModel(Memo memo, Usecase usecase, Repository repository) {
        id = memo.getId();
        title = memo.getTitle();
        description = memo.getDescription();
        imgs = memo.getImgs();
        date = memo.getDate();
        mUsecase = usecase;
        mMemoRepository = repository.getMemoRepository();
    }

    public void onClick(){
        mUsecase.detailScreen(new Memo(id, title, description, date, imgs));
    }
}
