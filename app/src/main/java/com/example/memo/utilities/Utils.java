package com.example.memo.utilities;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Utils {
    final static String TAG = "Utils";

    //    파일명 생성
    public static String createFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = timeStamp;
        return fileName;
    }

    //    file로 부터 절대경로 가져오기
    public static String getAbsPath(File file) {
        return file.getAbsolutePath();
    }

    //    정규
    public static boolean hasUrlRegex(String url) {
        String urlRegex = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.matches(urlRegex);
    }

    public static boolean hasImageFiletype(String text) {
        String fileTypeRegex = "^([\\S]+(\\.(?i)(jpeg|jpg|png|bmp))$)";
        return text.matches(fileTypeRegex);
    }

}

