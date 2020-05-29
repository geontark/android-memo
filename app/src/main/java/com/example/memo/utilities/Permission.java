package com.example.memo.utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 앱 권한 요청
 */
public class Permission {
    final Integer PERMISSION_SUCCESS = 1000;
    final Integer CAMERA_PERMISSION_SUCCESS = 1001;
    final Integer ALBUM_PERMISSION_SUCCESS = 1002;

    ArrayList<String> mPermissions = new ArrayList<>();

    Context mContext;

    public Permission(Context context) {
        mContext = context;
    }
    public Permission(Context context, ArrayList<String> permissions) {

        this.mContext = context;
        permissionCheck(permissions, PERMISSION_SUCCESS);
    }

    public boolean permissionCheck(ArrayList<String> permissions, Integer successCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로 이상
            List<String> reqPermissions = new ArrayList<>();

            for (String permission : permissions) {
                if (!hasPermission(permission)) {
                    reqPermissions.add(permission);
                }
            }
            int reqPermissionLength = reqPermissions.size();
            if (reqPermissionLength != 0) {    // 권한 요청 팝업창 호출

                String[] permission = reqPermissions.toArray(new String[reqPermissionLength]);

                ActivityCompat.requestPermissions((AppCompatActivity) mContext, permission, successCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //    해당 권한 앱이 소유하고 있는지
    public Boolean hasPermission(String permission) {
        if (ContextCompat.checkSelfPermission(mContext, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //    카메라 권한 요청
    public boolean cameraPermission() {
        mPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mPermissions.add(Manifest.permission.CAMERA);
        return permissionCheck(mPermissions, CAMERA_PERMISSION_SUCCESS);
    }

    //    앨범 권한 요청
    public boolean albumPermission() {
        mPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionCheck(mPermissions, ALBUM_PERMISSION_SUCCESS);
    }

}
