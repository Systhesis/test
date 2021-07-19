package com.neil.test.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * @author zhongnan1
 * @time 2021/7/16 14:54
 *
 */
public abstract class BaseBindingFragment<VDB extends ViewDataBinding> extends Fragment {
    protected VDB mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View view = mBinding.getRoot();
        initView(view);
        return view;
    }

    public abstract int getLayoutId();

    public void initView(View view){

    }

    public void initData(Bundle savedInstanceState) {

    }

}
