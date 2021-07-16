package com.neil.test.mvp.view;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.neil.test.R;
import com.neil.test.databinding.ActivityLoginBinding;
import com.neil.test.mvp.base.BaseMvpActivity;
import com.neil.test.mvp.bean.BaseObjectBean;
import com.neil.test.mvp.bean.LoginBean;
import com.neil.test.mvp.contract.LoginContract;
import com.neil.test.mvp.present.LoginPresent;

/**
 * @author zhongnan1
 * @time 2021/7/16 15:51
 *
 */
public class LoginActivity extends BaseMvpActivity<ActivityLoginBinding, LoginPresent> implements LoginContract.View {
    @Override
    protected void initView() {
        super.initView();
        mPresent = new LoginPresent();
        mPresent.attachView(this);
        mBinding.btnSigninLogin.setOnClickListener(v -> {
            if (getUsername().isEmpty() || getPassword().isEmpty()) {
                Toast.makeText(LoginActivity.this, "帐号密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            mPresent.login(getUsername(), getPassword());
        });
    }

    /**
     * @return 帐号
     */
    private String getUsername() {
        return mBinding.etUsernameLogin.getText().toString().trim();
    }

    /**
     * @return 密码
     */
    private String getPassword() {
        return mBinding.etPasswordLogin.getText().toString().trim();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onSuccess(BaseObjectBean<LoginBean> bean) {
        Toast.makeText(this, bean.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        Log.e(TAG, "showLoading: ");
    }

    @Override
    public void hideLoading() {
        Log.e(TAG, "hideLoading: ");
    }

    @Override
    public void onError(String errorMsg) {
        Log.e(TAG, "onError: " + errorMsg );
    }
}
