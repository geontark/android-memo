package com.example.memo.viewmodels;

import androidx.databinding.ObservableArrayList;

import com.example.memo.R;
import com.example.memo.database.Memo;
import com.example.memo.listener.LifecycleListener;
import com.example.memo.repositories.MemoRepository;
import com.example.memo.repositories.Repository;
import com.example.memo.provider.ResourceProvider;
import com.example.memo.usecase.Usecase;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 *  간단한 메모 리스트에 관련된 뷰모
 */
public class MemoListViewModel {

    final static String TAG = MemoListViewModel.class.getSimpleName();

    private Usecase mUsecase;
    private MemoRepository mMemoRepository;
    private ResourceProvider mResourceProvider;
    public ObservableArrayList<Memo> mMemos = new ObservableArrayList<>();


    private LifecycleListener mLifecycleListener;

    public MemoListViewModel(ResourceProvider resProvier, Usecase usecase, Repository repository) {
        mResourceProvider = resProvier;
        mUsecase = usecase;
        mMemoRepository = repository.getMemoRepository();

        init();
        initList();
    }

    private void initList() {

        Single.fromCallable(mMemoRepository::getAll)
                .subscribeOn(Schedulers.io())
                .subscribe(memos -> {
                    mMemos.clear();
                    mMemos.addAll(memos);
                }, throwable -> {
                    // Error
                    mUsecase.getToastMessage(mResourceProvider.getString(R.string.toast_memos_read_error));
                });
    }

    private void init() {
//        라이프 사이클 리스너
        if (mLifecycleListener == null) {
            mLifecycleListener = new LifecycleListener() {
                @Override
                public void callback(String event) {

                    switch (event) {
                        case "ON_CREATE":
                            break;

                        case "ON_START":
                            initList();
                            break;
                    }
                }
            };
        }
    }

    public LifecycleListener getmLifecycleListener() {
        return mLifecycleListener;
    }
}
