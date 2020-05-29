package com.example.memo.utilities;

import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    //    2020.01.20 11:20 형식-> 202001201120으로 변경
    public static long parsingDate(String date){
        String result = "";
        result += date.substring(0,4);
        result += date.substring(5,7);
        result += date.substring(8,10);
        result += date.substring(11,13);
        result += date.substring(14,16);
        result += date.substring(17,19);
        return Long.valueOf(result);
    }

    //    정규식(url 요청)
    public static boolean hasUrlRegex(String url) {
        String urlRegex = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.matches(urlRegex);
    }
//    확장자
    public static boolean hasImageFiletype(String text) {
        String fileTypeRegex = "^([\\S]+(\\.(?i)(jpeg|jpg|png|bmp))$)";
        return text.matches(fileTypeRegex);
    }
}

