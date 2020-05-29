package com.example.memo.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memo.data.MemoDataSource;
import com.example.memo.data.MemoRepository;
import com.example.memo.data.database.Memo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 메모 자세히 보기
 * 메모 수정 및 추가 가능
 * 수정 및 추가 데이터 관리
 */
public class MemoEditViewModel extends ViewModel {
    final static String TAG = "MemoEditViewModel";
    private MemoRepository mMemoRepository;
    private String mMemoId;

    /**
     * 플래그값에 따라 보이는 뷰가 다름
     *  mAddMode true 새로운 메모 추가
     *  mModifyMode true 메모 수정
     *
     *  mAddMode와 mModifyMode에 따른
     *  각각 read와 write모드
     */
    public MutableLiveData<Boolean> mAddMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> mModifyMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> mReadMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> mWriteMode = new MutableLiveData<>();


    public MutableLiveData<String> mTitle = new MutableLiveData<>();
    public MutableLiveData<String> mDescription = new MutableLiveData<>();
    public MutableLiveData<String> mDate = new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> mImgUrls = new MutableLiveData<ArrayList<String>>();

//    메모 추가하기
    public MemoEditViewModel(MemoRepository memoRepository) {
        mMemoRepository = memoRepository;
        mAddMode.postValue(true);
        mModifyMode.postValue(false);
        mWriteMode.postValue(true);
        mReadMode.postValue(false);
    }

    //    수정하기
    public MemoEditViewModel(String memeoId, MemoRepository memoRepository) {
        mMemoId = memeoId;
        mMemoRepository = memoRepository;

        mAddMode.postValue(false);
        mModifyMode.postValue(true);
        mWriteMode.postValue(false);
        mReadMode.postValue(true);

        getMemo(mMemoId);
    }

//    메모 정보 가져오기
    public void getMemo(String memoId) {
        mMemoRepository.getMemo(memoId, new MemoDataSource.getMemoCallback() {
            @Override
            public void onGetMemo(Memo memo) {
//                뮤테이블 라이브 데이터 값 변경
                mTitle.postValue(memo.title);
                mDescription.postValue(memo.description);
                mDate.postValue(memo.date);
                mImgUrls.postValue(memo.imgs);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

//    새로운 메모 추가하기
    public void addMemo(Context context) {
        String id = makeMemoId();
        String title = mTitle.getValue();
        String description = mDescription.getValue();
        ArrayList<String> imgs = mImgUrls.getValue();
        String date = makeDate();
        Memo memo = new Memo(id,title, description, date, imgs);
        mMemoRepository.saveMemo(memo, new MemoDataSource.saveMemoCallback() {
            @Override
            public void onSaveMemo(Boolean isSuccess) {
                ((Activity)context).finish();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

//    메모 수정하기
    public void modifyMemo(Context context) {
        String id = mMemoId;
        String title = mTitle.getValue();
        String description = mDescription.getValue();
        ArrayList<String> imgs = mImgUrls.getValue();
        String date = makeDate();
        Memo memo = new Memo(id,title, description, date, imgs);
        mMemoRepository.updateMemo(memo, new MemoDataSource.updateMemoCallback() {
            @Override
            public void onUpdateMemo(Boolean isSuccess) {
                mWriteMode.postValue(false);
                mReadMode.postValue(true);
                ((Activity)context).finish();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

//    메모 삭제
    public void deleteMemo(Context context) {
        mMemoRepository.deleteMemo(mMemoId, new MemoDataSource.deleteMemoCallback() {
            @Override
            public void onDeleteMemo(Boolean isSuccess) {
                if(isSuccess) {
                    ((Activity)context).finish();
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    //    완료 버튼
    public void doneBtn(Context context) {
        if(mAddMode.getValue()) { // 추가모드
            addMemo(context);
        }  else {    // 수정모드
            modifyMemo(context);
        }
    }

    private String makeDate() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
    }

    private String makeMemoId() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return ("memo_" + timeStamp);
    }

//    write 가능한 모드 켜기
    public void onModifyMode() {
        mWriteMode.postValue(true);
        mReadMode.postValue(false);
    }
}
