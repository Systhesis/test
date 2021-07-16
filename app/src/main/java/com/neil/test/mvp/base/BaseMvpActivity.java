package com.neil.test.mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;

import autodispose2.AutoDispose;
import autodispose2.AutoDisposeConverter;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;

/**
 * @author zhongnan1
 * @time 2021/7/16 14:44
 *
 */
public abstract class BaseMvpActivity<VDB extends ViewDataBinding, P extends BasePresent> extends BaseBindingActivity<VDB> implements BaseView {

    protected P mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        if(mPresent != null) {
            mPresent.detachView();
        }
        super.onDestroy();
    }


    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }
}
