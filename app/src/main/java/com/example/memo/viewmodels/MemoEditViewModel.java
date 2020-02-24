package com.example.memo.viewmodels;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;

import com.example.memo.R;
import com.example.memo.database.Memo;
import com.example.memo.listener.ResultListener;
import com.example.memo.provider.ResourceProvider;
import com.example.memo.repositories.MemoRepository;
import com.example.memo.repositories.NetworkRepository;
import com.example.memo.repositories.Repository;
import com.example.memo.resultManager.ResultMangerCode;
import com.example.memo.usecase.Usecase;
import com.example.memo.utilities.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * 메모 자세히 보기
 * 메모 수정 및 추가 가능
 * 수정 및 추가 데이터 관리
 */
public class MemoEditViewModel {
    final static String TAG = "MemoEditViewModel";

    // subscribe 라이프 사이클에 맞게 해
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * 새로운 메모 작성 모드
     * isAdd = T
     * 메모 읽기 모드
     * isAdd = F
     * isEdit = F
     * 메모 수정 모드
     * isEdit = T
     * isAdd = F
     * <p>
     * 플래그값에 따라 보이는 뷰가 다름
     */
    // edit 모드
    public ObservableField<Boolean> isEdit = new ObservableField<>(false);
    // add 모드
    public ObservableField<Boolean> isAdd = new ObservableField<>(false);

    // data
    public ObservableField<String> id = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    public ObservableField<String> description = new ObservableField<>();
    public ObservableArrayList<String> imgUrls = new ObservableArrayList<>();

    // url을 이용한 이미지 추가용도
    public ObservableField<Boolean> urlAddView = new ObservableField<>(false);
    public ObservableField<String> inputUrl = new ObservableField<>();

    private ResultListener mResultListener;
    private Usecase mUsecase;
    private MemoRepository mMemoRepository;
    private NetworkRepository mNetworkRepository;
    private ResourceProvider mResourceProvider;

    // 카메라 작동시 임시로 파일 경로 소유할 변수
    private String tempImagePath;

    // 메모 추가시 호출
    public MemoEditViewModel(ResourceProvider resProvier, Usecase usecase, Repository repository) {
        this.mResourceProvider = resProvier;
        this.mUsecase = usecase;
        this.mMemoRepository = repository.getMemoRepository();
        this.mNetworkRepository = repository.getNetworkRepository();
        mUsecase = usecase;
        setAdd(true);
        setEdit(false);
        init();
    }

    // 메모 수정시 호출
    public MemoEditViewModel(ResourceProvider resProvier, Usecase usecase, Repository repository, Memo memo) {
        this.mResourceProvider = resProvier;
        id.set(memo.getId());
        title.set(memo.getTitle());
        date.set(memo.getDate());
        description.set(memo.getDescription());

        this.mMemoRepository = repository.getMemoRepository();
        this.mNetworkRepository = repository.getNetworkRepository();

        for (String url : memo.getImgs()) {
            imgUrls.add(url);
        }
        mUsecase = usecase;
        setAdd(false);
        setEdit(false);
        init();
    }

    private void init() {
        mResultListener = new ResultListener() {
            @Override
            public void callback(int requestCode, int resultCode, Intent data) {

                if (resultCode == RESULT_OK) {
                    switch (requestCode) {
                        case ResultMangerCode.CAMERA: // 카메라 사진 선택

                            if (tempImagePath != null) {
                                imgUrls.add(tempImagePath);
                            }
                            break;
                        case ResultMangerCode.ALBUM:  // 앨범 사진 선택
                            imgUrls.add(data.getData().toString());
                            break;

                        default:
                    }
                }
            }
        };
    }

    //    url 이미지 다운로드
    public void onClickImageUrl() {

        if (mUsecase.reqAlbumPermission()) {

            String result = inputUrl.get();
            if (result == null || result.equals("")) {
                setUrlAddView(false);
                return;
            }

            if (!Utils.hasUrlRegex(result)) {
                mUsecase.getToastMessage(mResourceProvider.getString(R.string.toast_url_error));
                inputUrl.set("");
                setUrlAddView(false);
                return;
            }

            if (!Utils.hasImageFiletype(result)) {// 확장자명 검사
                mUsecase.getToastMessage(mResourceProvider.getString(R.string.toast_filetype_error));
                inputUrl.set("");
                setUrlAddView(false);
                return;
            }

//            url 이미지 요청
            mNetworkRepository.reqBitmapFromUrl(result)
                    .subscribe(new Consumer<Bitmap>() {
                        @Override
                        public void accept(Bitmap bitmap) throws Throwable {
                            String FileName = Utils.createFileName();
                            File tempFile = mUsecase.createImageFile(FileName);
                            tempImagePath = Utils.getAbsPath(tempFile);
                            FileOutputStream out = new FileOutputStream(tempFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.close();
                            imgUrls.add(tempImagePath);
                        }
                    });
            inputUrl.set("");
            setUrlAddView(false);
        }
    }

    public void onClickAlbum() {
        if (mUsecase.reqAlbumPermission()) {
            mUsecase.callAlbemApp();
        }
    }

    public void onClickPicture() {
        if (mUsecase.reqCameraPermission()) {
            String fileNme = Utils.createFileName();
            File file = mUsecase.createImageFile(fileNme);
            tempImagePath = Utils.getAbsPath(file);
            mUsecase.callCameraApp(file);
        }
    }

    public ResultListener getmResultListener() {
        return mResultListener;
    }

    public void setUrlAddView(boolean isVisible) {
        urlAddView.set(isVisible);
    }

    //    이미지 제거
    public void deleteImg(int position) {
        imgUrls.remove(position);
    }


    public void eventDone() {
        if (isAdd.get()) {  // 메모 추가 모드
            add();
            return;
        }

        if (isEdit.get()) { // 메모 수정모드
            modify();
            return;
        }
    }

    //    메모 추가하기
    public void add() {
        ArrayList<String> urls = new ArrayList<>();
        urls.addAll(imgUrls);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String id = "memo_" + timeStamp;
        String date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
        Memo memo = new Memo(id, title.get(), description.get(), date, urls);

        Completable.fromAction(() -> mMemoRepository.insert(memo))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    Log.d(TAG, "save success");
                    mUsecase.finish();
                }, throwable -> {
                    // Error
                    Log.d(TAG, "save fail");
                    mUsecase.getToastMessage(mResourceProvider.getString(R.string.toast_save_error));
                });
    }

    //    메모 수정하기
    public void modify() {
        ArrayList<String> urls = new ArrayList<>();
        for (String url : imgUrls) {
            urls.add(url);
        }
        Memo memo = new Memo(id.get(), title.get(), description.get(), date.get(), urls);
        Completable.fromAction(() -> mMemoRepository.update(memo))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    // Next Step
                    Log.d(TAG, "update success");
                    mUsecase.finish();
                }, throwable -> {
                    // Error handling
                    Log.d(TAG, "modify fail");
                    mUsecase.getToastMessage(mResourceProvider.getString(R.string.toast_update_error));
                });
    }

    //    메모 삭제
    public void eventDelete() {
        Completable.fromAction(() -> mMemoRepository.delete(id.get()))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    Log.d(TAG, "delete success");
                    mUsecase.finish();
                }, throwable -> {
                    // Error
                    Log.d(TAG, "delete fail");
                    mUsecase.getToastMessage(mResourceProvider.getString(R.string.toast_delete_error));
                });
    }

    public void setEdit(boolean isEdit) {
        this.isEdit.set(isEdit);
    }

    public void setAdd(Boolean isAdd) {
        this.isAdd.set(isAdd);
    }
}
