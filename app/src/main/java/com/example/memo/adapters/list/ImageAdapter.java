package com.example.memo.adapters.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.R;
import com.example.memo.adapters.list.viewholder.BindingViewHolder;
import com.example.memo.databinding.MemoImgItemBinding;
import com.example.memo.viewmodels.listitems.MemoImgItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 한개의 메모에 첨부되어 있는 이미지를 보여주는 어댑터
 * 이미지 부분 구현하기 전까지는 Deprecated 처리 사용하지 않음
 */
@Deprecated
public class ImageAdapter extends RecyclerView.Adapter<BindingViewHolder<MemoImgItemBinding>> {

    private List<String> mUrls = new ArrayList<>();
    private List<MemoImgItemViewModel> mItemViewModels = new ArrayList<>();
    private Context mContext;

    private boolean mEditMode = false;   // 이미지 삭제 버튼을 표시 할지에 대한 유무 플래그

    @NonNull
    @Override
    public BindingViewHolder<MemoImgItemBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.memo_img_item, parent, false);
        return new BindingViewHolder<>(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<MemoImgItemBinding> holder, int position) {
        MemoImgItemViewModel viewmodel = new MemoImgItemViewModel(mUrls.get(position), position);
        viewmodel.editMode.set(mEditMode);
        mItemViewModels.add(viewmodel);

//        holder.binding().setMemoImgItemViewModel(viewmodel);
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    public void addItems(List<String> urls) {
        if (urls != null) {
            this.mUrls = urls;
        }
    }

    public void setEditMode(boolean isEdit) {
        mEditMode = isEdit;

        for (MemoImgItemViewModel viewModel : mItemViewModels) {
            viewModel.editMode.set(isEdit);
        }
    }
}
