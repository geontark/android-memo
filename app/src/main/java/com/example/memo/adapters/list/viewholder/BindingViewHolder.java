package com.example.memo.adapters.list.viewholder;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Generic으로 선언 시 모든 RecyclerViewAdapter에서 공통으로 사용할 수 있다.
 */
public class BindingViewHolder<T> extends RecyclerView.ViewHolder {

    private final T binding;

    public BindingViewHolder(View itemView) {
        super(itemView);
        this.binding = (T) DataBindingUtil.bind(itemView);
    }

    public T binding() {
        return binding;
    }
}