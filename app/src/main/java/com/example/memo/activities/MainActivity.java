package com.example.memo.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.memo.R;
import com.example.memo.adapters.list.MemoAdapter;
import com.example.memo.databinding.ActivityMainBinding;
import com.example.memo.repositories.Repository;
import com.example.memo.provider.ResourceProvider;
import com.example.memo.usecase.Usecase;
import com.example.memo.viewmodels.FloatbtnViewModel;
import com.example.memo.viewmodels.ToolbarViewModel;
import com.example.memo.viewmodels.MemoListViewModel;

public class MainActivity extends BaseActivity {

    final static String TAG = MainActivity.class.getSimpleName();

    //    viewmodels
    private MemoListViewModel mMemoListViewModel;
    private ToolbarViewModel mToolbarViewModel;
    private FloatbtnViewModel mFloatBtnViewModel;

    private MemoAdapter mAdapter;

    private ResourceProvider mResProvider;
    private Usecase mUsecase;
    private Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResProvider = new ResourceProvider(this);
        mUsecase = new Usecase(this);
        mRepository = new Repository(this);

        mMemoListViewModel = new MemoListViewModel(mResProvider, mUsecase, mRepository);
        mToolbarViewModel = new ToolbarViewModel(mResProvider, mUsecase);
        mToolbarViewModel.setTitle(mResProvider.getString(R.string.notepad));
        mFloatBtnViewModel = new FloatbtnViewModel(mUsecase);

        register(mMemoListViewModel.getmLifecycleListener());

//        databinding
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMemoListViewModel(mMemoListViewModel);
        mAdapter = new MemoAdapter();
        binding.memoList.setAdapter(mAdapter);
        binding.setFloatBtnViewModel(mFloatBtnViewModel);
        binding.toolBar.setToolViewModel(mToolbarViewModel);
    }
}

