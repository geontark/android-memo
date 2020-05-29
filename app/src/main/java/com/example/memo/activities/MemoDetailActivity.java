package com.example.memo.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.example.memo.R;
import com.example.memo.data.MemoRepository;
import com.example.memo.data.datasource.MemoLocalDataSource;
import com.example.memo.databinding.ActivityMemoDetailBinding;
import com.example.memo.viewmodels.MemoEditViewModel;

public class MemoDetailActivity extends AppCompatActivity {
    final static String TAG = "MemoDetailActivity";

    //    viewmodels
    private MemoEditViewModel mMemoEditViewModel;
    private MemoRepository mMemoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);

        mMemoRepository = MemoRepository.getInstance(MemoLocalDataSource.getInstance(this));

        String memoId = getIntent().getStringExtra("memoId");
        if(memoId == null) {    // 메모 추가하기
            mMemoEditViewModel = new MemoEditViewModel(mMemoRepository);
        }else { // 메모 수정하기
            mMemoEditViewModel = new MemoEditViewModel(memoId, mMemoRepository);
        }

        ActivityMemoDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_memo_detail);
        binding.setLifecycleOwner(this);
        binding.setActivity(this);
        binding.setMemoEditViewModel(mMemoEditViewModel);

    }

    public void memoDelete() {
        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.alert_memo_delete));
        builder.setPositiveButton(context.getString(R.string.yse),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mMemoEditViewModel.deleteMemo(context);
                    }
                });
        builder.setNegativeButton(context.getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
}
