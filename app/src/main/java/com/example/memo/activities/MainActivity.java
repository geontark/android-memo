package com.example.memo.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.memo.R;
import com.example.memo.adapters.list.MemoAdapter;
import com.example.memo.data.MemoRepository;
import com.example.memo.data.datasource.MemoLocalDataSource;
import com.example.memo.databinding.ActivityMainBinding;
import com.example.memo.viewmodels.MemoListViewModel;

/**
 * 첫 진입하는 엑티비티
 * 작성한 모든 메모를 확인 할 수 있음
 */
public class MainActivity extends AppCompatActivity {

    final static String TAG = MainActivity.class.getSimpleName();

//    viewmodels
    private MemoListViewModel mMemoListViewModel;
    private MemoAdapter mAdapter;
    private MemoRepository mMemoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMemoRepository = MemoRepository.getInstance(MemoLocalDataSource.getInstance(this));
        mMemoListViewModel = new MemoListViewModel(mMemoRepository);

//        databinding
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setActivity(this);
        binding.setMemoListViewModel(mMemoListViewModel);
        mAdapter = new MemoAdapter();
        binding.memoList.setAdapter(mAdapter);
    }


//    메모 목록들 갱신하여 뿌려준다
    @Override
    protected void onStart() {
        super.onStart();
        mMemoListViewModel.getMemos();
    }

    //   메모 작성하기 페이지로 이동
    public void addMemoBtn() {
        Intent intent = new Intent(this, MemoDetailActivity.class);
        startActivity(intent);
    }
}

