package uitestlib.uicommon.util

import geb.Browser
import groovy.util.logging.Slf4j
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.openqa.selenium.support.events.WebDriverEventListener

import java.util.concurrent.TimeUnit

/**
 * 初始化浏览器
 * Created by QingHuan on 2019/11/6 23:32
 */
@Slf4j
class BrowserUtil {
    private static String isBusy = System.getProperty('ui.busy','NO')
    private static osName = System.getProperty('os.name')toLowerCase()
    private static Boolean serverExist = null

    static Browser initBrowser(String baseUrl,boolean headless = false){
        WebDriver driver = initAndMaximizeChrome(headless)
        new Browser(driver:driver,baseUrl:baseUrl)
    }
    static Browser initEventFiringBrowser(String baseUrl,boolean headless =false){
        WebDriver driver = initAndMaximizeChrome(headless)
        WebDriverEventListener enventListener=new ScreenShotWebDriverEventListener()
        driver = new EventFiringWebDriver(driver).register(enventListener)
        new Browser(driver:driver,baseUrl:baseUrl)
    }

    /**
     * driver.manage().window().maximize()
     * 这里为了解决failed to change window state to maximized current state is normal 导致实例化测试类失败的问题：NoClassDefFound
     * @param headless
     * @return
     */

    static WebDriver initAndMaximizeChrome(boolean headless = false){
        WebDriver driver
        ChromeOptions option = new ChromeOptions()
        option.addArguments("start-maximized")

        if (headless){
            log.info('使用Chrome Headless 模式')
            option.addArguments("--headless")
            option.addArguments("-disable-gpu")
            option.addArguments("--no-sandbos")
            option.addArguments("--window-size=1440,900")
        }
        if (isBusy.toLowerCase() =='yes'){
            //客户机资源不足，走云服务器测试
            //云账号密码填自己的
            DesiredCapabilities desiredCapabilities= DesiredCapabilities.chrome()
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY,option)
            desiredCapabilities.setCapability('f2etest.userid','xxx')
            desiredCapabilities.setCapability('f2etest.apiKey','xxx')
            driver = new RemoteWebDriver('xxx'.toURL(),desiredCapabilities)
            driver.setFileDetector(new LocalFileDetector())
        }
        else{
            //本地测试
            if (chromeDriverServiceExists()){
                log.warn("存在已打开的ChromeDriver Server 直接通过 RemoteWebDriver使用")
                DesiredCapabilities desiredCapabilities= DesiredCapabilities.chrome()
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY,option)
                driver = new RemoteWebDriver('http://127.0.0.1:9515'.toURL(),desiredCapabilities)
                //远程上传文件支持
                driver.setFileDetector(new LocalFileDetector())
            }
            else{
                driver = new ChromeDriver(option)
            }
        }
        //在mac上执行一次最大化
        if (!osName.contains('windows')){
            driver.manage().window().maximize()
        }
        return driver
    }

    static boolean chromeDriverServiceExists(){
        if (serverExist !=null){
            return serverExist
        }
        boolean result
        try{

            String commandOne = "netstat -an"
            String commandTwo = osName.contains("windows")?"findstr 127.0.0.1:9515":"grep 127.0.0.1.9515"
            log.warn("查看 ChromeDriver Server 是否存在：${commandOne+"|"+commandTwo}")

            def processOne = commandOne.execute()
            def processTwo = commandTwo.execute()
            processTwo.consumeProcessOutput(System.out,System.err)
            processOne |processTwo
            processOne.consumeProcessErrorStream(System.err)
            processTwo.waitFor(10L, TimeUnit.SECONDS)

            if (processTwo.exitValue()){
                result = false
            }
            else {
                result = true
            }
        }
        catch (Exception e){
            log.error("${e.message}")
            result = false
        }
        serverExist = result
        return result
    }
    static {
        System.setProperty("webdriver.chrome.verboseLogging","false")
        String os = System.getProperty('os.name').toLowerCase()
        if (os.contains('windows')){
            System.setProperty('webdriver.chrome.driver',"Driver/chromedriver.exe")
        }
        else{
            System.setProperty('webdriver.chrome.driver',"Driver/chromedriver")
        }
    }


}
