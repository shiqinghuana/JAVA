package uitestlib.uicommon.modules

import geb.Module
import geb.content.Navigable
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

/**
 * Created by QingHuan on 2019/10/20 23:48
 */

@Slf4j
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
     * 完全依赖文本点击，没有依赖css选择器限制，大数据下会比较慢
     * @param text
     * @return
     */
    def clickElementByTest(String text){
        stableClick($(" ",text:text))

    }

    /**
     * 强制清除当前文本，并设置成新值
     * @param element
     * @param value
     * @return
     */

    def forceClearAndSetValue(WebElement element,String value){
        Navigator niv = creatNavigatorfromElement(element)
        forceClearAndSetValue niv,value
    }



    /**
     * 强制清除当前文本，并设置成新值
     * @param niv
     * @param value
     * @param sendRightArrowKey 是否需要发生向右方向键，如果文本框被鼠标点击过，则需要，否则可能清理不完
     * @return
     */
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

    /**
     * 通过js脚本获取常规方法无法获取到的文本
     * @param niv
     * @return
     */
    String getTextByJavaScript(Navigator niv){
        getTextByJavaScript(niv.firstElement())

    }

    /**
     * 通过执行js脚本获取常规方法无法获取到的文本
     * @param element
     * @return
     */
    String getTextByJavaScript(WebElement element){
       String text =  js.exec(element,"return arguments[0].innerText")
        return text
    }

    /**
     * 将element对象转换成Navigator对象
     * @param element
     * @return
     */
    Navigator creatNavigatorfromElement(WebElement element){
        Navigator niv =   browser.navigatorFactory.createFromWebElements([element])
        return niv
    }

    /**
     * 将css字符串表达式转换成Navigator
     * @param cssString
     * @return
     */
    Navigator elementsToNavigator(String cssString){
        log.debug("将webelement转换成Navigator：${cssString}")
        List<WebElement> elements =  browser.driver.findElements(By.cssSelector(cssString))
        Navigator niv =  browser.navigatorFactory.createFromWebElements(elements)
        log.debug("转化后的Navigator中包含${niv.size()} 个element ")
        return niv
    }


    /**
     * 执行闭包中的代码 ，忽略 stale element reference：element is not attached to the page document异常
     * @param closure
     */
    def ignoreStaleElement(Closure closure){
        for (int i in 0..9){
            try{
                closure.call()
            }catch(Throwable throwable){
                log.info("执行操作发生异常：${throwable}")
                if (!throwable.message.contains('stale element reference')){
                    throw throwable
                }
            }
            sleep(500)
        }

    }
    /**
     * 递归获取 base元素下所有子元素
     * @param base
     * @return
     */
    List<Navigator> getChildrenRecursively(Navigator base){
        List<Navigator> childList = []
        List<Navigator> stack = []
        if (base?.isEmpty()){
            return childList
        }
        else {
            base.each {
                childList.add(it)
                Navigator child = it.children()
                if (child){
                    stack.push(child)
                }
            }
            while (stack.size()>0){
                childList.addAll(getChildrenRecursively(stack.pop()))
            }
        }
        log.debug("共找到${base}元素的${base.size()}个子元素")
        return childList


    }
    /**
     * 递归获取base下所有子元素
     * @param base
     * @return
     */
    List<Navigator> getChildrenRecursively(WebElement base){
        Navigator niv = creatNavigatorfromElement(base)
        return getChildrenRecursively(niv)
    }

    /**
     * 点击下拉按钮直到展开弹出框 ，最多点4此
     * @param trigger 下拉按钮
     */
    def triggerUntilOpened(Navigator trigger){
        //弹出层 css选择器
        Navigator overlays = $('')
        Integer previousCount = overlays.size()
        log.debug("点击下拉按钮前打开的弹出层数量：${previousCount}")
        for (i in 0..3) {
            stableClick(trigger)
            overlays = $("")
            Integer currentCount = overlays.size()
            if (currentCount>= previousCount){
                if (currentCount == 0 ){
                    log.info("一个弹出框也没有，继续点击")
                    continue
                }
                //已经打开
                break
            }
        }
    }
    /**
     * 执行必包动作后，查看是否有新的弹出层，如果有，就返回
     * @param closure 闭包
     * @return
     */

    Navigator findNewOpendOverlay(Closure closure){
        Navigator overlays = $('')
        Integer previousCount = overlays.size()
        closure.call()
        overlays = $('')
        Integer currentCount = overlays.size()
        if (currentCount>previousCount){
            log.info("执行必包前打开的弹出层${previousCount}")
            log.info("执行必包后打开的弹出层${currentCount}")
            return overlays.last()
        }else {
            return null
        }

    }

    /**
     * 点击下拉按钮，直到闭合，最多点击4次
     * @param trigger 下拉按钮
     */
    def triggerUntilClosed(Navigator trigger){
        for (i in 0..3){
            if (!trigger.attr('class').contains('next-inco-')){
                //如果当前按钮是乡下的，就点击闭合，否则说明已经闭合了
                stableClick(trigger)
            }
        }
    }

    /**
     * 执行代码查找元素出错后，自动到iframe中查找，最多支持两层iframe
     * @param closure
     * @return
     */
    def autowWithFrame(Closure closure){
        Throwable throwable = null
        boolean success = false
        def result = null

        try{
            result = closure.call()
            success = true
        }catch(Throwable throwable1){
            throwable = throwable1
            success = false
        }
        if (success){
            return result
        }else {
            log.info("在当前页面执行代码失败，尝试在第一城iFrame中执行：${throwable}")
            String currentUrl = browser.currentUrl
            String currentDomain = currentUrl.split("://")[1].split("/")[0]
            String domainWithPrefix = currentUrl.split("://")[0]+"://"+currentDomain
            Navigator  iframes = theRelatedIframes (domainWithPrefix)
            log.info("Navigator iframes = theRelatedIframes (domainWithPrefix):${iframs.size()}")
            for (Navigator frame : iframes) {
                String frameinfo = "${frame.attr("src")}"
                browser.withFrame(frame){
                    log.info("尝试在iframe(${frameinfo})中执行代码")
                    try{
                        result = closure.call()
                        success = true
                    }catch(Throwable throwable1){
                        throwable = throwable1
                        success = false
                    }
                }
                if (success){
                    break
                }
            }
            if (success){
            return result
            }else {
                log.error("当前页面上所有第一层iframe中代码都没有执行成功：${throwable?.message}")3
                iframes = theRelatedIframes (domainWithPrefix)
                for (Navigator frame : iframes) {
                    browser.withFrame(frame){

                        Navigator  secondLayerIframe = $("ifram")?.findAll {
                            //过滤出domain下ifram
                            String src = it.attr("src")
                            return src.startsWith("/")||src.startsWith('#')||src.startsWith(domainWithPrefix)
                        }
                        for (Navigator secondeIframe : secondLayerIframe) {
                            String secondedIfifo  = "${secondeIframe.attr('src')}"
                            browser.withFrame(secondeIframe){
                                log.info("尝试在第二层iframe(${secondedIfifo})中执行代码")
                                try{
                                    result = closure.call()
                                    success = true
                                }catch(Throwable throwable1){
                                    throwable = throwable1
                                    success = false
                                }
                            }
                            if (success){
                                return result
                            }

                        }
                    }
                    if (success){
                        return result
                    }
                }

                //两层全部遍历结束
                if (success){
                    return result
                }
                else {
                    throw throwable
                }
            }
        }
    }

    /**
     *
     * @param domainWithPrefix
     * @return
     */
    private Navigator theRelatedIframes(String domainWithPrefix){
        Navigator  frame = $("ifram")?.findAll {
            //过滤出domain下ifram
            String src = it.attr("src")
            return src.startsWith("/")||src.startsWith('#')||src.startsWith(domainWithPrefix)

        }
        return frame

    }

    /**
     * 在base元素下查找<input type = text 的输入元素。为了做兼容，如果查不到
     * 就查找input标签，但是无显示type属性的元素
     * @param base
     * @param index
     */
    Navigator findTextTypeInputElement(Navigator base = null , int index = 0){
        if (base){
            Navigator ctrlDiv = base
            Navigator normalInput = ctrlDiv.$('input',type:'text',index)
            if (normalInput){
                return normalInput
            }
            else {
                log.debug("没有找到type='text'的input元素。退而查找所有标签为input且无显示type属性的元素")
                Navigator allInputs = ctrlDiv.$("input").findAll {
                    it.attr('type' ) ==  ''|| it.attr('type' ) ==  null|| it.attr('type' ) ==  'text'
                }
                return allInputs[index]
            }
        }
        else {
            Navigator normalInput = $('input', type: 'text', index)
            if (normalInput) {
                return normalInput
            } else {
                log.debug("没有找到type='text'的input元素。退而查找所有标签为input且无显示type属性的元素")
                Navigator allInputs = $("input").findAll {
                    it.attr('type') == '' || it.attr('type') == null || it.attr('type') == 'text'
                }
                return allInputs[index]
            }
        }
    }

    /**
     * 有多个满足条件的按钮|checkbox|Radio 时，如何精确选择
     * @param niv
     * @param testValue
     * @param testedAttribute
     * @return
     */
    Navigator chooseWhenMoreThanOneMatch(Navigator niv,String testValue,String testedAttribute ="text"){
        Navigator target = niv
        if (niv.size()<=1){
            return target
        }
        else {
            //有多个匹配，则完全匹配优先级高于包含匹配
            //元素按照优先级排序后，重新返回，组件中依据排列优先级后的顺序来引用索引
            List<Navigator> fullMatch = []
            List<Navigator> partMartch = []
            for (Navigator item : niv) {
                if (testedAttribute=="text"){
                    if (item.text()==testValue){
                        fullMatch.add(item)
                    }
                    else {
                        partMartch.add(item)
                    }
                }
                else{
                    if (item.attr(testedAttribute)==testValue){
                        fullMatch.add(item)
                    }
                    else {
                        partMartch.add(item)
                    }
                }
            }
            //排序，全匹配的在前面
            fullMatch.addAll(partMartch)
            return browser.navigatorFactory.createFromNavigators(fullMatch)
        }
    }

    /**
     * 获取祖先div里的文本，通过名称定为某些组件，如checkbox时。组件自身没有文本，文本实在附近的DIV中的
     * @param niv 待获取祖先文本的元素
     * @param expectedString
     * @param generationCount 获取到第几级祖先为止
     * @return
     */
    static Boolean isAncestorDivsContainsText(Navigator niv,String expectedString,int generationCount = 3){
        if (niv){
            for (int i in 0..<generationCount) {
                Navigator div =niv.closest('div')
                if (div){
                    if (div.text().contains(expectedString)){
                        log.info("在第${i+1}级祖先div中成功找到了文本：${expectedString}")
                        return true
                    }
                    niv = div
                }
                else {
                    return false
                }
            }
        }
        return false
    }
    /**
     * 点击元素附近的空白处
     * @param niv
     * @return
     */
    def clickWhiteArea(Navigator niv){
        try{
            interact {
                moveToElement(niv,-1,-1)
                click()
            }
        }
        catch (Throwable ignored){

        }
    }


}
