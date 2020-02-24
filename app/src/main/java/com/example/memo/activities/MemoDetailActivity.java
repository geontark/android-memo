package com.example.memo.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.memo.R;
import com.example.memo.adapters.list.ImageAdapter;
import com.example.memo.database.Memo;
import com.example.memo.databinding.ActivityMemoDetailBinding;
import com.example.memo.eventbus.EventBus;
import com.example.memo.eventbus.EventBusCode;
import com.example.memo.repositories.Repository;
import com.example.memo.resultManager.ResultManager;
import com.example.memo.provider.ResourceProvider;
import com.example.memo.usecase.Usecase;
import com.example.memo.viewmodels.MemoEditViewModel;
import com.example.memo.viewmodels.ToolbarViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;


/**
 * 메모 추가, 수정, 읽기가 가능한 엑티비티
 * 전 엑티비티에서 발생한 이벤트에따라 분기함
 */
public class MemoDetailActivity extends BaseActivity {

    final static String TAG = MemoDetailActivity.class.getSimpleName();

    // 이벤트버스에 등록한 구독 취소하기 위한 변수
    private Disposable mDisposalble;

    //    viewmodels
    private ToolbarViewModel mToolbarViewModel;
    private MemoEditViewModel mMemoEditViewModel;
    private ImageAdapter mAdapter;

    private ResourceProvider mResProvider;
    private Usecase mUsecase;
    private ResultManager mResultManager;
    private Repository mRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResProvider = new ResourceProvider(this);
        mUsecase = new Usecase(this);
        mResultManager = new ResultManager();

        mToolbarViewModel = new ToolbarViewModel(mResProvider, mUsecase);

        Memo memo = (Memo) getIntent().getSerializableExtra(mResProvider.getString(R.string.memo));
        mRepository = new Repository(this);

        if (memo != null) {   // 메모 읽기 and 수정
            mMemoEditViewModel = new MemoEditViewModel(mResProvider, mUsecase, mRepository, memo);
            mToolbarViewModel.setVisibleModifyBtn(true);
            mToolbarViewModel.setVisibleDoneBtn(false);
        } else {  // 새로운 메모 작성
            mMemoEditViewModel = new MemoEditViewModel(mResProvider, mUsecase, mRepository);
            mToolbarViewModel.setVisibleDoneBtn(true);
            mToolbarViewModel.setVisibleModifyBtn(false);
            mToolbarViewModel.setTitle(mResProvider.getString(R.string.add));
        }

        mResultManager.register(mMemoEditViewModel.getmResultListener());

        ActivityMemoDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_memo_detail);

        binding.setMemoEditViewModel(mMemoEditViewModel);
        binding.toolBar.setToolViewModel(mToolbarViewModel);

//        이미지 리사이클러뷰
        mAdapter = new ImageAdapter();
        binding.imageList.setAdapter(mAdapter);

//      viewmodel간의정 eventBus를 통한 통신
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject data) throws Throwable {

                int code = 0;
                try {

                    code = data.getInt(EventBusCode.CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (code) {
                    case EventBusCode.MEMO_IMG_ITEM_DELETE:
                        try {
                            mMemoEditViewModel.deleteImg(data.getInt(EventBusCode.DATA));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EventBusCode.TOOL_BAR_DONE:  // 툴바의 완료 버튼
                        mMemoEditViewModel.eventDone();
                        break;

                    case EventBusCode.TOOL_BAR_MODIFY:  // 수정모드로 변경
                        mMemoEditViewModel.setEdit(true);
                        mAdapter.setEditMode(true);
                        break;

                    case EventBusCode.TOOL_BAR_DELETE:  // 메모 삭제하기
                        mMemoEditViewModel.eventDelete();
                        break;
                }
            }
        };

        mDisposalble = EventBus.getInstance()
                .getObservable()
                .subscribe(consumer);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mResultManager.onResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//       해당 페이지에서 등록한  eventbus 구독 해
        if (mDisposalble != null) {
            mDisposalble.dispose();
        }
    }
}
