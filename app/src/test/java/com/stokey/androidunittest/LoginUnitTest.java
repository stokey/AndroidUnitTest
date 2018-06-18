package com.stokey.androidunittest;

import android.text.TextUtils;
import com.stokey.androidunittest.model.User;
import com.stokey.androidunittest.service.LoginManagerImp;
import com.stokey.androidunittest.util.LoginUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by stokey on 2018/6/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginUtil.class,TextUtils.class})
public class LoginUnitTest {

    private LoginManagerImp loginManagerImp;

    @BeforeClass
    public static void setUpClass() {
        RxUnitTestTools.openRxTools();
    }

    @Before
    public void setUp() {
        loginManagerImp = mock(LoginManagerImp.class);
    }

    @Test
    public void loginTest() {
        when(loginManagerImp.login("xxxx", "xxxx")).thenReturn(Observable.just(new User(1, "stokey", "xxxxxx", true)));
        loginManagerImp.login("xxxx", "xxxx")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> System.out.print(user.toString()));

    }

    @Test
    public void updateToken() {
        when(loginManagerImp.updateToken()).thenReturn(Observable.error(new Exception("input error")));
        // TODO 可以测试 具体逻辑
        loginManagerImp.updateToken().observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> System.out.print("token:" + result), throwable -> System.out.print("update token error:" + throwable.getMessage()));
    }


    @Test
    public void loginNameStaticCheckTest() {
        // TODO 演示静态方法调用
        PowerMockito.mockStatic(LoginUtil.class);
        PowerMockito.mockStatic(TextUtils.class);

        when(LoginUtil.checkLoginName(anyString())).thenCallRealMethod();
        if (LoginUtil.checkLoginName("")) {
            System.out.print("input user name is illegal");
        } else {
            System.out.print("input user name is not correct");
        }
    }

    @Test
    public void loginNameCheckTest() {
        // PowerMockito 可以测试static/final/private方法
        PowerMockito.mockStatic(LoginUtil.class);
        PowerMockito.mockStatic(TextUtils.class);
        when(LoginUtil.checkLoginName(anyString())).thenCallRealMethod();
        if (LoginUtil.checkLoginName("correct name")) {
            System.out.println("\'correct name\' is ok");
        } else {
            System.out.println("\'correct name\' is not ok");
        }

        if (LoginUtil.checkLoginName("xxxe")) {
            System.out.println("\'xxxe\' is ok");
        } else {
            System.out.println("\'xxxe\' is not ok");
        }

        if (LoginUtil.checkLoginName("xxx")) {
            System.out.println("\'xxx\' is ok");
        } else {
            System.out.println("\'xxx\' is not ok");
        }

        if (LoginUtil.checkLoginName("")) {
            System.out.println("\'\' is ok");
        } else {
            System.out.println("\'\' is not ok");
        }

        if (LoginUtil.checkLoginName(null)) {
            System.out.println("\'null\' is ok");
        } else {
            System.out.println("\'null\' is not ok");
        }
    }

    @Test
    public void loginPswCheckTest() {
        PowerMockito.mockStatic(LoginUtil.class);
        PowerMockito.mockStatic(TextUtils.class);
        when(LoginUtil.checkLoginPsw(anyString())).thenCallRealMethod();
        if (LoginUtil.checkLoginPsw("correct psw")) {
            System.out.println("\'correct psw\' is ok");
        } else {
            System.out.println("\'correct psw\' is not ok");
        }

        if (LoginUtil.checkLoginPsw("xxx")) {
            System.out.println("\'xxx\' is ok");
        } else {
            System.out.println("\'xxx\' is not ok");
        }

        if (LoginUtil.checkLoginPsw("")) {
            System.out.println("\'\' is ok");
        } else {
            System.out.println("\'\' is not ok");
        }

        if (LoginUtil.checkLoginPsw(null)) {
            System.out.println("\'null\' is ok");
        } else {
            System.out.println("\'null\' is not ok");
        }
    }

    @AfterClass
    public static void destroy() {
        RxUnitTestTools.destroyRxTools();
    }
}
