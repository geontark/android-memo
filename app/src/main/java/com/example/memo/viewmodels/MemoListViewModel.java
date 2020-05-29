package com.example.memo.viewmodels;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memo.data.MemoDataSource;
import com.example.memo.data.MemoRepository;
import com.example.memo.data.database.Memo;
import com.example.memo.utilities.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 간단한 메모 리스트에 관련된 뷰모델
 *  메모 목록들 시간순으로 정렬해서 가져와서 뷰에 뿌려줌
 */
public class MemoListViewModel extends ViewModel {

    final static String TAG = "MemoListViewModel";

    private MemoRepository mMemoRepository;
    public MutableLiveData<ArrayList<Memo>> mMemos = new MutableLiveData<>();
    public MemoListViewModel(MemoRepository memoRepository) {
        mMemoRepository = memoRepository;
    }

//    메모 목록들 시간순으로 정렬해서 가져와서 뷰에 뿌려줌
    public void getMemos() {
        mMemoRepository.getMemos(new MemoDataSource.getMemosCallback() {
            @Override
            public void onGetMemos(ArrayList<Memo> memos) {
//                시간순으로 정렬
                Collections.sort(memos, new Comparator<Memo>() {
                    @Override
                    public int compare(Memo o1, Memo o2) {
                        long result1 = Utils.parsingDate(o1.getDate());
                        long result2 = Utils.parsingDate(o2.getDate());
                        return result1 < result2 ? 1 : -1;
                    }
                });
                mMemos.setValue(memos);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
