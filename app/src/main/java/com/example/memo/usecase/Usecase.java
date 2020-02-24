package com.example.memo.usecase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.memo.R;
import com.example.memo.activities.MemoDetailActivity;
import com.example.memo.database.Memo;
import com.example.memo.resultManager.ResultMangerCode;
import com.example.memo.utilities.Permission;

import java.io.File;
import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

public class Usecase {
    final static String TAG = "Usecase";

    private Context mContext;

    public Usecase(Context context) {
        mContext = context;
    }

    public File createImageFile(String fileName) {
//        external에 파일 저장

        String suffix = ".jpg";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    fileName,  /* prefix */
                    suffix,         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    //    카메라 앱 호출
    public void callCameraApp(File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {

            if (file != null) {
                Uri photoURI = FileProvider.getUriForFile(mContext,
                        "com.example.memo.provide",
                        file);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                ((Activity) mContext).startActivityForResult(intent, ResultMangerCode.CAMERA);
            }

        }
    }

    //    앨범 앱 호출
    public void callAlbemApp() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT); // ACTION_PICK은 사용하지 말것, deprecated + formally
        intent.setType("image/*");
        ((Activity) mContext).startActivityForResult(intent, ResultMangerCode.ALBUM);
    }

    //    메모 자세히보기 엑티비티로 이동
    public void detailScreen() {
        Intent intent = new Intent(mContext, MemoDetailActivity.class);
        mContext.startActivity(intent);
    }

    public <T> void detailScreen(Memo data) {
        Intent intent = new Intent(mContext, MemoDetailActivity.class);
        intent.putExtra("memo", data);
        mContext.startActivity(intent);
    }

    public void getToastMessage(String text) {
        Toast.makeText(mContext, text + "", Toast.LENGTH_SHORT).show();
    }

    public Single getBitmapFromUrl(String url) {

        return Single.create(new SingleOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Bitmap> emitter) throws Throwable {

                Glide.with(mContext)
                        .asBitmap()
                        .load(url)
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

//    카메라 권한 요청
    public boolean reqCameraPermission() {
        Permission permission = new Permission(mContext);
        return permission.cameraPermission();
    }

//    앨범 권한 요청
    public boolean reqAlbumPermission() {
        Permission permission = new Permission(mContext);
        return permission.albumPermission();
    }

//    알림 다이얼로그 띄움
    public Single callAlertDialog(String message) {

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Boolean> emitter) throws Throwable {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(message);
                builder.setPositiveButton(mContext.getString(R.string.yse),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                emitter.onSuccess(true);
                            }
                        });
                builder.setNegativeButton(mContext.getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                emitter.onSuccess(false);
                            }
                        });
                builder.show();
            }
        });
    }

    public void finish() {
        ((Activity) mContext).finish();
    }
}
