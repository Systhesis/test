package com.neil.test.mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author zhongnan1
 * @time 2021/7/16 14:37
 *
 */
public abstract class BaseBindingActivity<VDB extends ViewDataBinding> extends AppCompatActivity {
    protected final static String TAG = BaseBindingActivity.class.getSimpleName();
    protected VDB mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initData(savedInstanceState);
        initView();
    }

    protected abstract int getLayoutId();

    protected void initView() {

    }

    protected void initData(Bundle savedInstanceState) {

    }
}
