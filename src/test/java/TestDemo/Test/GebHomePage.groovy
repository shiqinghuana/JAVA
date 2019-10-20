package TestDemo.Test

import geb.Browser
import geb.Page
import org.openqa.selenium.chrome.ChromeDriver

class GebHomePage extends Page {

    static url = "http://gebish.org"                                     //5

    static at = { title == "Geb - Very Groovy Browser Automation" }      //6

    static content = {
        manualsMenu { module ManualsMenuModule }                        //7
    }

    public static void main(String[] args) {
            print("111")
            Browser.drive (new Browser(driver: new ChromeDriver())){
            to GebHomePage
            manualsMenu.open()
            quit()
            print("执行结束")

        }
    }

    }

