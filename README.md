# Android 单元测试

+ JUnit：运行在JVM上，只能对纯Java代码进行测试
+ AndroidJunitRunner：（Google官方Android单元测试框架之一）需要运行在Android真机或者模拟器环境
+ Mockito：
+ Robolectric：运行在JVM上，但是框架本身引入了Android依赖库，可以做Android单元测试。比运行在真机或者模拟器快（Shadow概念）。但是也有局限性：1. 不支持加载so
+ Espresso：（Google官方Android单元测试框架之一）运行在真机上，任何Android代码都能运行，但是不像Junit和mockito那样隔离依赖。相对于其他框架会慢一些


## Junit
## Mockito
## PowerMockito
+ 优点：支持static/final/private方法调用
+ 使用
	+ @RunWith(PowerMockRunner.class)：`普通Mock不需要加@RunWith和@PrepareForTest`
	+ @PrepareForTest：`需要Mock static、final、private等方法时`



+ Mocking框架
+ 单元测试——依赖隔离
+ 单元测试不是集成测试——`在单元测试的基础上，将相关模块组合成为子系统或系统进行测试`

## Robolectric
+ 缺点
	+ 仅支持API21及以下
	+ 不支持JNI库 

# Android测试
+ Monkey测试
+ MonkeyRunner框架
+ UiAutomator
+ Robotium
+ Appium


# Android测试
+ 单元测试 
+ 集成测试

## 单元测试
+ 原则
	+ 依赖隔离：尽可能少的依赖

	
+ 问题
	+ 如何解决Android依赖：`通过在test/java目录下，创建同包名、同类名、同方法的类` 
	+ 隔离Native方法：`依赖隔离——通过mock模拟出数据`
	+ 解决内部new对象：`依赖隔离——通过提供带参数构造函数进行初始化`
	+ 静态方法：`依赖隔离——1. 改成非静态类 2. SPY`
	+ RxJava异步转同步：`JVM没有Looper类——` 

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