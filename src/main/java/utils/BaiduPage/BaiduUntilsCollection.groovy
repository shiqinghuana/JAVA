package utils.BaiduPage

import geb.Browser
import geb.Page
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.chrome.ChromeDriver
import utils.BaiduMoudle.NameButton


/**
 * Created by shiqingahuna on 2019/10/20 23:35
 */
@Slf4j
class BaiduUntilsCollection extends Page{


    /**
     *   y用户脚本中使用的导航，查找元素后，找到的元素都存在这里
     * */
    Navigator navigatorInScript = null

    /**
     *   切换窗口前用户浏览器窗口
     * */
    String originalWindow = null
    static at = {true}
    static url = "https://www.baidu.com"
    static content = {

        nameButton {module(NameButton)}

    }


    void ClickName(String Name,Integer index=0){


        //先查buttom

        /*    button = $("Button",text)*/
        Navigator niv =  nameButton.clickWebElementByText(Name,index)
        if (niv){nameButton.stableClick(niv)}
        else {log.info("页面没找到元素")}
    }

    public static void main(String[] args) {
        Browser browser = new Browser(driver: new ChromeDriver())

        Browser.drive (browser){
            to BaiduUntilsCollection
            sleep(5)
            ClickName("新闻",)
           // quit()
        }
    }
}
