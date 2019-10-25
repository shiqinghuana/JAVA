package utils.BaiduMoudle

import geb.Module
import geb.content.Navigable
import geb.navigator.Navigator
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

/**
 * Created by QingHuan on 2019/10/20 23:48
 */
class StableEnhancedMoudle extends Module{

    def stableClick(Navigator navigable){
        stableClick(navigable.firstElement())

    }

    def stableClick(WebElement element) {
        try {
            // 元素标签为a，且href为javascript ,普通点击不到，调用js执行
            if (element.tagName == "a") {
                String href = element.getAttribute("href")
                if (href == null || href.contains("javscript")) {
                    js.exec(element, "argument[0].click();")
                } else {
                    element.click()
                }
            }
            else {
                element.click()
            }
    }catch(Throwable throwable){
            //处理异常，如果因为元素遮挡，调用js再点击一次
            if (throwable.message.contains("Other element would receive the click")){
               Navigator niv =  creatNavigatorfromElement(element)
                interact {
                    moveToElement(niv)
                    sleep(500)
                    click()
                }
            }

        }

    }

    /**
     * 强制清除当前文本，并设置成新值
     */

    def forceClearAndSetValue(WebElement element,String value){

        Navigator niv = creatNavigatorfromElement(element)
        forceClearAndSetValue niv,value
    }

    def forceClearAndSetValue(Navigator niv, String value, boolean sendRightArrowKey = false) {
        String currentValue = niv.attr('value')
        WebElement element =  niv.firstElement()
        if (sendRightArrowKey){
            currentValue?.length()?.times {
                element.sendKeys(Keys.RIGHT)
            }
        }
        currentValue?.length()?.times {
            element.sendKeys(Keys.RIGHT)
        }
        if (value!=null && value !=''){
            element.sendKeys(value)
        }
    }

    String getTextByJavaScript(Navigator niv){
        getTextByJavaScript(niv.firstElement())

    }

    String getTextByJavaScript(WebElement element){
       String text =  js.exec(element,"return arguments[0].innerText")
        return text
    }



    /**
     * 将element对象转换成Navigator对象
     */
    Navigator creatNavigatorfromElement(WebElement element){
        Navigator niv =   browser.navigatorFactory.createFromWebElements([element])
        return niv
    }


}
