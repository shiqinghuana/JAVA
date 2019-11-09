package uitestlib.uicommon.support

import geb.Browser
import geb.Page
import geb.content.TemplateDerivedPageContent
import geb.frame.WithFrameDelegate
import geb.navigator.Navigator
import org.openqa.selenium.NoSuchFrameException
import org.openqa.selenium.WebElement


/**
 * Created by QingHuan on 2019/11/9 21:01
 */
class CustomizedFrameSupport {
    Browser browser
    CustomizedFrameSupport(Browser browser1){
        this.browser = browser1
    }

    public <T> T withIFrame(frame, Class<? extends Page> page = null, Closure<T> block){
        executeWithFrame(frame,page,block)
    }

    public <T> T withIFrame(Navigator frame, Class<? extends Page> page = null, Closure<T> block){
        executeWithFrame(frame,page,block)
    }

    public <T> T withIFrame(TemplateDerivedPageContent frame, Class<? extends Page> page = null, Closure<T> block){
        executeWithFrame(frame,page,block)
    }

    private <T> T executeWithFrame(frame,def page,Closure<T> block){
        def originalPage = browser.page
        browser.driver.switchTo().frame(frame)
        if (page){
            if (page.at){
                browser.at(page)
            }else{
                browser.page(page)
            }
        }
        try{
            Closure cloned = block.clone()
            cloned.delegate = new WithFrameDelegate(browser)
            cloned.resolveStrategy =Closure.DELEGATE_FIRST
            cloned.call()
        }finally{
            browser.page(originalPage)
            browser.driver.switchTo().parentFrame()
        }
    }

    private <T> T executeWithFrame(Navigator frameNavigator, def page, Closure<T> block){
        WebElement element = frameNavigator.firstElement()
        if (element == null){
            throw new NoSuchFrameException("No elements for given content:${frameNavigator}")
        }
        executeWithFrame(element, page,block)
    }


}
