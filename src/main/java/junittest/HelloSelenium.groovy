package junittest

import org.junit.BeforeClass
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver


class HelloSelenium {

    @BeforeClass
    static void clear(){

    }
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",".\\Driver\\chromedriver.exe")
        WebDriver driver =   new ChromeDriver();
        driver.get("https://www.baidu.com")
        def title = driver.getTitle()
        println(title)
        driver.quit()
    }


}
