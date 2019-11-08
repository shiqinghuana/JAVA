package TestDemo.Test;


import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.Thread.sleep;


/**
 * Created by QingHuan on 2019/10/22 22:31
 */
class BaiDuTest {
    private String url = "https://www.baidu.com/";
    private static WebDriver driver =  new ChromeDriver();

    @AfterClass
    public static void teardown(){
        driver.quit();

    }

    @Test
    void 百度登陆() throws InterruptedException {
        driver.get(url);
        sleep(3000);
         WebElement element = driver.findElement(By.cssSelector("#u1 > a.lb"));
         element.click();

        sleep(1000);
        driver.findElement(By.cssSelector("#TANGRAM__PSP_10__footerULoginBtn")).click();
        sleep(1000);
        driver.findElement(By.cssSelector("#TANGRAM__PSP_10__userName")).sendKeys("16657123946");
        sleep(1000);
        driver.findElement(By.cssSelector("#TANGRAM__PSP_10__password")).sendKeys("wsluyinbin123");
        sleep(1000);
        driver.findElement(By.cssSelector("#TANGRAM__PSP_10__submit")).click();
        sleep(100000000);
    }

}
