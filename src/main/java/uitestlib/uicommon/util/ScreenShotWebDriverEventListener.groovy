package uitestlib.uicommon.util

import groovy.util.logging.Slf4j
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * 截图事件处理
 * Created by QingHuan on 2019/11/7 0:43
 */

@Slf4j
class ScreenShotWebDriverEventListener extends WebDriverEventListenerAdapter{

    /**
     * 生成截图文件的前缀
     * 其中会用到当前正在运行的测试用例名称。改名称会从系统属性ui.test.case.name中获取
     * 测试用例名称来源是UiTestNameRule 的 logTestCaseName方法
     *
     */
    static String generateScreenShotFileNamePrefix(String action){
        return ScreenShotTool.generateScreenShotFileNamePrefix()+action+'-'
    }

    void afterClickOn(WebElement element,WebDriver driver){
        //有click生成的截图都带有click前缀
        String fileNamePrefix =  generateScreenShotFileNamePrefix('click')
        ScreenShotTool.requestToTakScreenShotForWebDriver(driver,fileNamePrefix,'src/iamges')
        logOperateTimeStamp(driver)
    }

    void afterScript(String var1,WebDriver driver){
        String fileNamePrefix =  generateScreenShotFileNamePrefix('afterScript')
        ScreenShotTool.requestToTakScreenShotForWebDriver(driver,fileNamePrefix,'src/iamges')
        logOperateTimeStamp(driver)
    }

    void beforeAlertAccept(WebDriver driver){logOperateTimeStamp(driver)}

    /**
     * This action will be performed each time after {@link org.openqa.selenium.Alert#accept()}
     *
     * @param driver WebDriver
     */
    void afterAlertAccept(WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * This action will be performed each time before {@link org.openqa.selenium.Alert#dismiss()}
     *
     * @param driver WebDriver
     */
    void afterAlertDismiss(WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * This action will be performed each time after {@link org.openqa.selenium.Alert#dismiss()}
     *
     * @param driver WebDriver
     */
    void beforeAlertDismiss(WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * Called before {@link org.openqa.selenium.WebDriver#get get(String url)} respectively
     * {@link org.openqa.selenium.WebDriver.Navigation#to navigate().to(String url)}.
     *
     * @param url URL
     * @param driver WebDriver
     */
    void beforeNavigateTo(String url, WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * Called after {@link org.openqa.selenium.WebDriver#get get(String url)} respectively
     * {@link org.openqa.selenium.WebDriver.Navigation#to navigate().to(String url)}. Not called, if an
     * exception is thrown.
     *
     * @param url URL
     * @param driver WebDriver
     */
    void afterNavigateTo(String url, WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#back navigate().back()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateBack(WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation navigate().back()}. Not called, if an
     * exception is thrown.
     *
     * @param driver WebDriver
     */
    void afterNavigateBack(WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#forward navigate().forward()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateForward(WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation#forward navigate().forward()}. Not called,
     * if an exception is thrown.
     *
     * @param driver WebDriver
     */
    void afterNavigateForward(WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#refresh navigate().refresh()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateRefresh(WebDriver driver){logOperateTimeStamp(driver)};

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation#refresh navigate().refresh()}. Not called,
     * if an exception is thrown.
     *
     * @param driver WebDriver
     */
    void afterNavigateRefresh(WebDriver driver){
        //refresh生成的截图都带有哦refresh前缀
        String fileNamePrefix =  generateScreenShotFileNamePrefix('refresh')
        ScreenShotTool.requestToTakScreenShotForWebDriver(driver,fileNamePrefix,'src/iamges')
        logOperateTimeStamp(driver)
    };




    /**
     * Called before {@link WebElement#clear WebElement.clear()}, {@link WebElement#sendKeys
     * WebElement.sendKeys(...)}.
     *
     * @param driver WebDriver
     * @param element the WebElement being used for the action
     */
    void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend){logOperateTimeStamp(driver)};

    /**
     * Called after {@link WebElement#clear WebElement.clear()}, {@link WebElement#sendKeys
     * WebElement.sendKeys(...)}}. Not called, if an exception is thrown.
     *
     * @param driver WebDriver
     * @param element the WebElement being used for the action
     */
    void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend){logOperateTimeStamp(driver)};



    /**
     * Called after {@link org.openqa.selenium.remote.RemoteWebDriver#executeScript(java.lang.String, java.lang.Object[]) }.
     * Not called if an exception is thrown
     *
     * @param driver WebDriver
     * @param script the script that was executed
     */

    /**
     * Called whenever an exception would be thrown.
     *
     * @param driver WebDriver
     * @param throwable the exception that will be thrown
     */
    void onException(Throwable throwable, WebDriver driver){logOperateTimeStamp(driver)};




    static void logOperateTimeStamp(WebDriver driver){
        //纪录浏览器最后操作时间
        Long time = System.currentTimeMillis()
        ScreenShotTool.lastOperated[driver] = time
        log.debug("${driver}对应浏览器最后操作时间为${time}")
    }

}
