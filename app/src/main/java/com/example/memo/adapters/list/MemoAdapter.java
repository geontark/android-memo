package com.example.memo.adapters.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.R;
import com.example.memo.adapters.list.viewholder.BindingViewHolder;
import com.example.memo.database.Memo;
import com.example.memo.databinding.MemoListItemBinding;
import com.example.memo.usecase.Usecase;
import com.example.memo.viewmodels.listitems.MemoListItemViewModel;

import java.util.ArrayList;
import java.util.List;

// 간략하게 메모 목록을 보여주는 리스트의 어댑터
public class MemoAdapter extends RecyclerView.Adapter<BindingViewHolder<MemoListItemBinding>> {

    private static final String TAG = MemoAdapter.class.getName();

    private List<Memo> mMemos = new ArrayList<>();
    private Context mContext;
    private Usecase mUsecase;

    @NonNull
    @Override
    public BindingViewHolder<MemoListItemBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mUsecase = new Usecase(mContext);

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.memo_list_item, parent, false);
        return new BindingViewHolder<>(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<MemoListItemBinding> holder, int position) {
        Memo memo = mMemos.get(position);
        holder.binding().setMemoListItemViewModel(new MemoListItemViewModel(memo, mUsecase));
    }

    @Override
    public int getItemCount() {
        return mMemos.size();
    }

    public void addItems(List<Memo> memos) {
        if (memos != null) {
            mMemos = memos;
        }
    }
}
