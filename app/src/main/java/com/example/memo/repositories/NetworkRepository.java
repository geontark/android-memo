package com.example.memo.repositories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.memo.R;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

public class NetworkRepository {
    private static final String TAG = NetworkRepository.class.getSimpleName();
    private Context mContext;

    public NetworkRepository(Context context) {
        mContext = context;
    }

    public Single reqBitmapFromUrl(String url) {
        return Single.create(new SingleOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Bitmap> emitter) throws Throwable {

                Glide.with(mContext)
                        .asBitmap()
                        .load(url)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_image_callback_error), Toast.LENGTH_LONG).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@androidx.annotation.NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                emitter.onSuccess(resource);
                            }
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }
        });
    }
}
