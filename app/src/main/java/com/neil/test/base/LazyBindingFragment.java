package com.neil.test.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * @author zhongnan1
 * @time 2021/7/22 18:27
 *
 */
public abstract class LazyBindingFragment<VDB extends ViewDataBinding> extends BaseBindingFragment<VDB> {
    /**
     * View是否已经被创建出来
     */
    private boolean isViewCreated = false;

    /**
     * 当前Fragment是否是首次可见
     */
    private boolean isFirstVisible = true;

    /**
     * 当前真正的可见状态
     */
    private boolean currentVisibleState = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //在View创建完毕之后，isViewCreate 要变为true
        isViewCreated = true;
        if (!isHidden() && getUserVisibleHint()) {
            dispatchVisibleState(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstVisible) {
            if (!isHidden() && !currentVisibleState && getUserVisibleHint()) {
                dispatchVisibleState(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (currentVisibleState && getUserVisibleHint()) {
            dispatchVisibleState(false);
        }
    }

    /**
     * 当第一次可见的时候(此方法，在View的一次生命周期中只执行一次)
     * 如果Fragment经历了onDestroyView，那么整个方法会再次执行
     * 重写此方法时，对Fragment全局变量进行 初始化
     * 具体的参照github demo
     */
    protected void onFragmentFirstVisible() {
        Log.d(TAG, "第一次可见,进行当前Fragment初始化操作");
    }

    /**
     * 当fragment变成可见的时候(可能会多次)
     */
    protected void onFragmentResume() {
        Log.d(TAG, "onFragmentResume 执行网络请求以及，UI操作");
    }

    /**
     * 当fragment变成不可见的时候(可能会多次)
     */
    protected void onFragmentPause() {
        Log.d(TAG, "onFragmentPause 中断网络请求，UI操作");
    }


    protected void dispatchVisibleState(boolean isVisible) {
        //为了兼容内嵌ViewPager的情况,分发时，还要判断父Fragment是不是可见
        if (isVisible && isParentInvisible()) {
            //如果当前可见，但是父容器不可见，那么也不必分发
            return;
        }
        if (isVisible == currentVisibleState) {
            return;//如果目标值，和当前值相同，那就别费劲了
        }

        //更新状态值
        currentVisibleState = isVisible;

        if (isVisible) {
            //如果可见
            //那就区分是第一次可见，还是非第一次可见
            if (isFirstVisible) {
                isFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentResume();
            dispatchChildVisibilityState(true);
        } else {
            onFragmentPause();
            dispatchChildVisibilityState(false);
        }
    }

    protected boolean isParentInvisible() {
        Fragment parent = getParentFragment();
        Log.d(TAG, "getParentFragment:" + parent + "");
        if (parent instanceof LazyBindingFragment) {
            LazyBindingFragment lz = (LazyBindingFragment) parent;
            return !lz.currentVisibleState;
        }
        return false;// 默认可见
    }

    protected void dispatchChildVisibilityState(boolean isVisible) {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> list = fragmentManager.getFragments();
        if (list != null) {
            for (Fragment fg : list) {//遍历子
                if (fg instanceof LazyBindingFragment
                        && !fg.isHidden() && fg.getUserVisibleHint()) {
                    ((LazyBindingFragment) fg).dispatchVisibleState(isVisible);
                }
            }
        }
    }
}
