package com.stokey.androidunittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stokey.androidunittest.service.LoginManagerImp;
import com.stokey.androidunittest.util.LoginUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.login_name_edt)
    EditText mNameEdt;

    @BindView(R.id.login_psw_edt)
    EditText mPswEdt;

    @BindView(R.id.login_btn)
    Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_btn})
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn: {
                if (checkLoginName()) {
                    if (checkLoginPsw()) {
                        LoginManagerImp.getInstance()
                                .login(mNameEdt.getText().toString(), mPswEdt.getText().toString())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(user ->
                                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show(),
                                        throwable -> Toast.makeText(MainActivity.this, "Login Failed:" + throwable.getMessage(), Toast.LENGTH_LONG).show());
                    } else {
                        Toast.makeText(MainActivity.this, "Password is illegal", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User name is illegal", Toast.LENGTH_LONG).show();
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private boolean checkLoginName() {
        if (TextUtils.isEmpty(mNameEdt.getText().toString())) {
            Toast.makeText(this, "User Name is null", Toast.LENGTH_SHORT).show();
            return false;
        }

        return LoginUtil.checkLoginName(mNameEdt.getText().toString());
    }

    private boolean checkLoginPsw() {
        if (TextUtils.isEmpty(mPswEdt.getText().toString())) {
            Toast.makeText(this, "Password is null", Toast.LENGTH_SHORT).show();
            return false;
        }

        return LoginUtil.checkLoginPsw(mPswEdt.getText().toString());
    }
}
