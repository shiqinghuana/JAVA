package TestDemo.gebDemo

import geb.Browser
import geb.Page
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.chrome.ChromeDriver

@Slf4j
class BaiduPage extends Page{

    static url = "https://www.baidu.com"

    static content = {
        Baidu {module(BaiduModile)}
    }
    static at ={Baidu.Header}

    public static void main(String[] args) {

        def drivers =   new Browser(driver: new ChromeDriver())
        Browser.drive (drivers){
            to BaiduPage
           // Baidu.open()
            waitFor {Baidu.Header}
           // at = {$("title",text:contains("百度地图"))}
            log.info("打开百度地图成功")
            Baidu.form.with {
                wd = "hahah"
            }
            Navigator nv = $("input",value:"百度一下")
            nv.click()
            print(nv.firstElement())
            sleep(3000000)
          /*
            Navigator nv = $("div>a",text:contains("新闻"))
            nv.click()*/
            close()

       }
    }



}