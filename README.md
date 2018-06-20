# Android 单元测试
+ 单元测试`重要概念`
	+ 单元测试——依赖隔离
	+ 单元测试不是集成测试——`在单元测试的基础上，将相关模块组合成为子系统或系统进行测试`
+ 用于Android平台的单元测试框架 
	+ JUnit：运行在JVM上，只能对纯Java代码进行测试
	+ AndroidJunitRunner：（Google官方Android单元测试框架之一）需要运行在Android真机或者模拟器环境
	+ Mockito：运行在JVM上，用于与JUnit配合模拟真实数据
	+ PowerMockito：Mockito加强框架，支持static、final、private方法调用
	+ Robolectric：运行在JVM上，但是框架本身引入了Android依赖库，可以做Android单元测试。比运行在真机或者模拟器快（Shadow概念）。但是也有局限性：`不支持加载so`
	+ Espresso：（Google官方Android单元测试框架之一）运行在真机上，任何Android代码都能运行，但是不像Junit和mockito那样隔离依赖。相对于其他框架会慢一些

# 初步使用
## Junit
## Mockito
## PowerMockito
+ 优点：支持static/final/private方法调用
+ 关键方法说明
	+ @RunWith(PowerMockRunner.class)：`普通Mock不需要加@RunWith和@PrepareForTest`
	+ @PrepareForTest：`该注释用于需要Mock static、final、private等方法时`
	
	```java
	@RunWith(PowerMockRunner.class)
   	@PrepareForTest({TextUtils.class}) 
    public class LoginTest {
    	@Before
    	public void setup(){
    		// 处理初始化
    	}
    	@Test
    	public void testLogin() {
    		// static方法处理
    		PowerMockito.mockStatic(TextUtils.class);
    		// 处理逻辑
    	}     
   }
	```

## Robolectric
+ 缺点
	+ 不支持JNI库：[解决方案](https://rocko.xyz/2016/11/27/Android-Robolectric-%E5%8A%A0%E8%BD%BD%E8%BF%90%E8%A1%8C%E6%9C%AC%E5%9C%B0-so-%E5%8A%A8%E6%80%81%E5%BA%93/)
+ 关键方法说明：[详情参见](https://blog.csdn.net/qq_17766199/article/details/78710177)

	```java
	// 注明单元测试环境
	@RunWith(RobolectricTestRunner.class)
	@Config(constants= BuildConfig.class, sdk=26)
	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
		// 如果有RxJava
		RxUnitTestTools.openRxTools();
	}
	
	@Test
	public void toastTest() {
		//检测是否有弹出toast，如果有打印toast内容
		Toast toast = ShadowToast.getLastToast();
		assertNotNull(toast);
		System.out.println("toast content:" + ShadowToast.getTextOfLatestToast());
	}
	
	@Test
	public void buttonTest() {
		Activity activity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
		Button mLoginBtn = activity.findViewById(R.id.login_btn);
		// 模拟点击事件
		mLoginBtn.performClick();
		toastTest();
	}
	
	@Test
	public void activityTest() {
		Activity activity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
		Button mLoginBtn = activity.findViewById(R.id.login_btn);
		// 模拟点击事件
		mLoginBtn.performClick();
		Intent intent = new Intent(activity, TestActivity.class);
		activity.startActivity(intent);
		assertThat(ShadowActivity.getNextStartedActivity(), equalTo(intent);
	}
	
	@Test
	public void dialogTest() {
		AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		// 判断Dialog尚未弹出
		assertNull(dialog);
		ShadowAlertDialog shadowDialog = Shadows.shadowOf(dialog);
		assertEquals("Robolectric Test", shadowDialog.getMessage());
	}
	
	@Test
	public void fragmentTest() {
		Fragment fragment = new Fragment();
		// 添加Fragment到Activity中，会触发Fragment的onCreateView()
		SupportFragmentTestUtils.startFragment(fragment);
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
		RxUnitTestTools.onDestroy();
	}
	```
+ 自定义Shadow


# Android测试
+ Monkey测试
+ MonkeyRunner框架
+ UiAutomator
+ Robotium
+ Appium

## 单元测试常见问题及解决
+ 如何解决Android依赖：`通过在test/java目录下，创建同包名、同类名、同方法的类` 
+ 隔离Native方法：`依赖隔离——通过mock模拟出数据`
+ 解决内部new对象：`依赖隔离——通过提供带参数构造函数进行初始化`
+ 静态方法：`依赖隔离——1. 改成非静态类 2. SPY`
+ RxJava异步转同步：`JVM没有Looper类，需要将线程都转到同一个线程中` 

```java
public class RxUnitTestTools {
	private static boolean isInitRxTools = false;
    /**
    * 把异步变成同步，方便测试
    */
    public static void openRxTools() {
        if (isInitRxTools) {
            return;
        }
        isInitRxTools = true;
        RxJavaPlugins.setIoSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
    }
    public static void reset() {
        RxJavaPlugin.reset();
        RxAndroidPlugin.reset();
    }
}
// 待测试代码
public class RxPresenterTest {
    RxPresenter rxPresenter;
    @Before
    public void setUp() throws Exception {
        RxUnitTestTools.openRxTools();
        rxPresenter = new RxPresenter();
    }
}
```