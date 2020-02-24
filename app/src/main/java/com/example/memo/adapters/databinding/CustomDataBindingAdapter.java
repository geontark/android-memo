package com.example.memo.adapters.databinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memo.adapters.list.ImageAdapter;
import com.example.memo.adapters.list.MemoAdapter;
import com.example.memo.database.Memo;
//import com.example.memo.models.Memo;

import java.util.ArrayList;

public class CustomDataBindingAdapter {

    final static String TAG = "dataBindingAdapter";

    //    이미지 넣기
    @BindingAdapter("image_load")
    public static void setImageUrl(ImageView view, String imageUrl) {
        Glide.with(view)
                .load(imageUrl)
                .fitCenter()
                .into(view);
    }

    @BindingAdapter("images_load")
    public static void setImageUrls(ImageView view, ArrayList<String> imageUrls) {
        if (imageUrls != null && imageUrls.size() != 0) {
            Glide.with(view)
                    .load(imageUrls.get(0))
                    .centerCrop()
                    .into(view);
        }else{
            view.setImageResource(0);
        }
    }

    @BindingAdapter("bind_items")
    public static <T> void setBindiItems(RecyclerView view, ArrayList<Memo> items) {
        MemoAdapter adapter = (MemoAdapter) view.getAdapter();

        adapter.addItems(items);
        adapter.notifyDataSetChanged();
    }

    @BindingAdapter("is_visible")
    public static void setIsVisible(View view, Boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }


    @BindingAdapter("is_gone")
    public static void setIsGone(View view, Boolean isGone) {
        if (isGone) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }


    @BindingAdapter("bind_img_items")
    public static <T> void setBindImgItems(RecyclerView view, ArrayList<String> items) {
        ImageAdapter adapter = (ImageAdapter) view.getAdapter();

        adapter.addItems(items);
        adapter.notifyDataSetChanged();
    }

    @BindingAdapter("app:add_scroll")
    public static void setScroll(TextView view, boolean isScroll) {
        if (isScroll) {
            view.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    // EditText 2way 바이딩
    @InverseBindingAdapter(attribute = "android:text", event = "editTextChangeEvent")
    public static String getText(EditText view) {
        return view.getText().toString();
    }

    @BindingAdapter("editTextChangeEvent")
    public static void setTextEvent(EditText view, final InverseBindingListener listener) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onChange();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
