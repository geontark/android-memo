package com.example.memo.adapters.databinding;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.adapters.list.MemoAdapter;
import com.example.memo.data.database.Memo;

import java.util.ArrayList;

/**
 *  전체적인 메모 리스트에 관련있는 바인딩 어댑터
 */
public class MemoListBindingAdapter {
//        메모 리스트 어댑터에 메모 정보 주기
    @BindingAdapter("set_memos")
    public static void setMemos(RecyclerView view, MutableLiveData<ArrayList<Memo>> memos) {
        if(memos != null) {
            MemoAdapter adapter = ((MemoAdapter)view.getAdapter());
            adapter.addItems(memos.getValue());
            adapter.notifyDataSetChanged();
        }
    }
}
