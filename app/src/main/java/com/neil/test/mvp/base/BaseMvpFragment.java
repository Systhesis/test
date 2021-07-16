package com.neil.test.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

/**
 * @author zhongnan1
 * @time 2021/7/16 14:59
 *
 */
public abstract class BaseMvpFragment<VDB extends ViewDataBinding, P extends BasePresent<BaseView>> extends BaseBindingFragment<VDB> implements BaseView {
    protected P mPresent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if(mPresent != null) {
            mPresent = null;
        }
        super.onDestroyView();
    }
}
