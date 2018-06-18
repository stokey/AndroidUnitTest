package com.stokey.androidunittest;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by stokey on 2018/6/17.
 */
@RunWith(RobolectricTestRunner.class)
public class LoginUiUnitTest {

    private MainActivity mainActivity;
    private EditText mNameEdt;
    private EditText mPswEdt;
    private Button mLoginBtn;

    // Robolectric支持单元测试范围从Activity的跳转、Activity展示View（包括菜单）和Fragment到View的点击触摸及事件响应
    // 同时支持Toast和Dialog的测试
    // 可以模拟网络请求的Response
    @Before
    public void setUp() {
        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create().resume().get();
        mNameEdt = mainActivity.findViewById(R.id.login_name_edt);
        mPswEdt = mainActivity.findViewById(R.id.login_psw_edt);
        mLoginBtn = mainActivity.findViewById(R.id.login_btn);
    }

    @Test
    public void login() {
        mLoginBtn.performClick();
        Toast.makeText(mainActivity, "xxxx", Toast.LENGTH_LONG).show();
    }


}
