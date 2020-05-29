package com.example.memo.adapters.list.viewholder;

import android.content.Intent;
import android.view.View;

import com.example.memo.activities.MemoDetailActivity;
import com.example.memo.data.database.Memo;
import com.example.memo.databinding.MemoListItemBinding;
/**
 *  main 엑티비테에서 사용되는 메모 리스트의 뷰홀더
 */
public class MemoViewHolder extends BindingViewHolder {
    MemoListItemBinding mBinding;
    public MemoViewHolder(View view) {
        super(view);
        mBinding = ((MemoListItemBinding)binding());
    }

//    각각 메모 데이터 및 이벤트를 바인딩함
    public void bind(Memo memo) {
        mBinding.setMemo(memo);
        mBinding.setClickListener((View) -> {
            Intent intent = new Intent(View.getContext(), MemoDetailActivity.class);
            intent.putExtra("memoId",memo.id);
            View.getContext().startActivity(intent);
        });
        mBinding.executePendingBindings();
    }
}
