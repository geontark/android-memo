package com.example.memo.adapters.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.R;
import com.example.memo.adapters.list.viewholder.MemoViewHolder;
import com.example.memo.data.database.Memo;

import java.util.ArrayList;

// 간략하게 메모 목록을 보여주는 리스트의 어댑터
public class MemoAdapter extends RecyclerView.Adapter<MemoViewHolder> {

    private ArrayList<Memo> mMemos = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.memo_list_item, parent, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
        Memo memo = mMemos.get(position);
        holder.bind(memo);
    }

    @Override
    public int getItemCount() {
        return mMemos.size();
    }

    public void addItems(ArrayList<Memo> memos) {
        if (memos != null) {
            mMemos = memos;
        }
    }
}
