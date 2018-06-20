package com.stokey.androidunittest;

import android.app.AlertDialog;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by stokey on 2018/6/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
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
        // RxJava切换线程时需要
        RxUnitTestTools.openRxTools();
        // 输出日志
        ShadowLog.stream = System.out;
        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create().resume().get();
        mNameEdt = mainActivity.findViewById(R.id.login_name_edt);
        mNameEdt.setText("User Name");
        mPswEdt = mainActivity.findViewById(R.id.login_psw_edt);
        mPswEdt.setText("User psw");
        mLoginBtn = mainActivity.findViewById(R.id.login_btn);
    }

    @Test
    public void login() {
        mLoginBtn.performClick();
        Toast toast = ShadowToast.getLatestToast();
        assertNotNull(toast);
        System.out.println("last toast content:" + ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void dialogTest() {
        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        assertNotNull(dialog);
        ShadowAlertDialog shadowAlertDialog = Shadows.shadowOf(dialog);
        assertEquals("Robolectric Test", shadowAlertDialog.getMessage());
    }

    @Test
    public void fragmentTest() {
        // Fragment测试需要添加：org.robolectric:shadows-support-v4:3.4-rc2
        Fragment fragment = new Fragment();
        // 添加Fragment到Activity中，会触发Fragment的onCreateView()
        SupportFragmentTestUtil.startFragment(fragment);
        assertNotNull(fragment.getView());
    }

    @Test
    public void resourceTest() {
        Application application = RuntimeEnvironment.application;
        String appName = application.getString(R.string.app_name);
        assertEquals("AndroidUnitTest", appName);
    }

    @After
    public void onDestroy() {
        RxUnitTestTools.destroyRxTools();
    }
}
