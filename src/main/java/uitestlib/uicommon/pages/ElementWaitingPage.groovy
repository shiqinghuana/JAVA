package uitestlib.uicommon.pages

import geb.Page
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.By

/**
 * 提供元素查找基本能力
 * Created by QingHuan on 2019/11/2 0:00
 */
@Slf4j
class ElementWaitingPage extends Page {
    static at = { true }

    /**
     * 查找元素使用默认等待
     * @param attributes
     * @param selector
     * @param index
     * @return
     */
    Navigator element(Map<String, Object> attributes, String selector, int index) {
        waitFor(message: "元素定位失败：selector：${selector},attributes:${attributes},index:${index}") {
            super.$(attributes, selector, index)
        }
    }
    /**
     * 查找元素使用默认等待
     * @param attributes
     * @param selector
     * @return
     */
    Navigator element(Map<String, Object> attributes, String selector) {
        waitFor(message: "元素定位失败：selector：${selector},attributes:${attributes}") { super.$(attributes, selector) }
    }

    /**
     * 查找元素使用默认等待
     * @param selector
     * @param index
     * @return
     */
    Navigator element(String selector, int index) {
        waitFor(message: "元素定位失败：selector：${selector},index:${index}") { super.$(selector, index) }
    }

    /**
     * 查找元素使用默认等待
     * @param selector
     * @return
     */
    Navigator element(String selector) {
        waitFor(message: "元素定位失败：selector：${selector}") { super.$(selector) }
    }

    /**
     * 查找元素使用默认等待
     * @param by
     * @return
     */
    Navigator element(By by) {
        waitFor(message: "元素定位失败：${by}") { super.$(by) }
    }
    /**
     * 显示等待元素
     * @param attributes
     * @param timeout
     * @param selector
     * @param index
     * @return
     */

    Navigator waitForElement(Map<String, Object> attributes, double timeout = 10, String selector, int index) {
        if (timeout <= 0) {
            element(attributes, selector, index)
        } else {
            waitFor(message: "元素定位失败：selector：${selector},attributes:${attributes},index:${index}") {
                super.$(attributes, selector, index)
            }
        }
    }

    /**
     * 显示等待元素
     * @param attributes
     * @param timeout
     * @param selector
     * @return
     */
    Navigator waitForElement(Map<String, Object> attributes, double timeout = 10, String selector) {
        if (timeout <= 0) {
            element(attributes, selector)
        } else {
            waitFor(message: "元素定位失败：selector：${selector},attributes:${attributes}") { super.$(attributes, selector) }
        }
    }

    /**
     * 显示等待元素
     * @param selector
     * @param timeout
     * @param index
     * @return
     */


    Navigator waitForElement(String selector, double timeout = 10, int index) {
        if (timeout <= 0) {
            element(selector, index)
        } else {
            waitFor(message: "元素定位失败：selector：${selector},index:${index}") { super.$(selector, index) }
        }
    }
    /**
     * 显示等待元素
     * @param selector
     * @return
     */
    Navigator waitForElement(String selector,double timeout = 10) {
        if (timeout <= 0) {
            element(selector,)
        } else {
        waitFor(message: "元素定位失败：selector：${selector}") { super.$(selector) }
    }}

    /**
     * 显示等待元素
     * @param by
     * @return
     */
    Navigator waitForElement(By by,double timeout = 10) {
        if (timeout <= 0) {
            element(by)
        } else {
        waitFor(message: "元素定位失败：${by}") { super.$(by) }
    }}

    /**
     * 刷新页面并查找元素，使用显示等待
     * @param attributes
     * @param timeout
     * @param selector
     * @param index
     * @return
     */

    Navigator refreshWaitForElement(Map<String, Object> attributes, double timeout , String selector, int index) {
        if (timeout <= 0) {
            refreshWaitFor(message: "元素定位失败：selector：${selector},attributes:${attributes},index:${index}") {
                super.$(attributes, selector, index)
            }
        }else{
            refreshWaitFor(message: "元素定位失败：selector：${selector},attributes:${attributes},index:${index}",timeout) {
                super.$(attributes, selector, index)
            }
        }
    }

    /**
     * 刷新页面并查找元素，使用显示等待
     * @param attributes
     * @param timeout
     * @param selector
     * @return
     */

    Navigator refreshWaitForElement(Map<String, Object> attributes, double timeout , String selector) {
        if (timeout <= 0) {
            refreshWaitFor(message: "元素定位失败：selector：${selector},attributes:${attributes}") {
                super.$(attributes, selector)
            }
        }else{
            refreshWaitFor(message: "元素定位失败：selector：${selector},attributes:${attributes}",timeout) {
                super.$(attributes, selector,)
            }
        }
    }

    /**
     * 刷新页面并查找元素，使用显示等待
     * @param timeout
     * @param selector
     * @param index
     * @return
     */

    Navigator refreshWaitForElement( double timeout , String selector, int index) {
        if (timeout <= 0) {
            refreshWaitFor(message: "元素定位失败：selector：${selector},index:${index}") {
                super.$( selector, index)
            }
        }else{
            refreshWaitFor(message: "元素定位失败：selector：${selector},,index:${index}",timeout) {
                super.$(selector, index)
            }
        }
    }

    /**
     * 刷新页面并查找元素，使用显示等待
     * @param timeout
     * @param selector
     * @return
     */
    Navigator refreshWaitForElement( double timeout , String selector) {

        refreshWaitFor(message: "元素定位失败：selector：${selector},,index:${index}", timeout) {
            super.$(selector)
        }
    }

    Navigator refreshWaitForElement(double timeout ,By by) {
        refreshWaitFor(message: "元素定位失败：by：${by}", timeout) {
            super.$(by)
        }
    }

    /**
     * 判断元素是否存在
     * @param attributes
     * @param index
     * @param selector
     * @return
     */

    Navigator elementExists(Map<String, Object> attributes,  String selector,int index){
        boolean exist
        try{
            if (attributes){
                waitForElement(attributes,3,selector,index)
            }
            else{
                waitForElement(3,selector,index)
            }
            exist = true
        }catch(Throwable throwable){
            exist = false
        }
        return exist
    }
    /**
     *判断元素是否存在
     * @param attributes
     * @param selector
     * @return
     */

    boolean elementExists(Map<String, Object> attributes, String selector){
        elementExists(attributes,selector,0)
    }


    /**
     * 判断元素是否存在
     * @param selector
     * @param index
     * @return
     */
    boolean elementExists( String selector,int index){
        elementExists(null,selector,0)
    }

    /**
     * 判断元素是否存在
     * @param selector
     * @return
     */
    boolean elementExists( String selector){
        elementExists(null,selector,0)
    }

    /**
     * 重复执行，知道成功或者超时
      * @param timeout
     * @param interval
     * @param closure
     */
    def waitUntilNoError(int timeout = 30,int interval = 2,Closure closure){
        int timeSpent = 0
        Throwable lastError = null
        while (timeSpent<timeout){
            try{
                closure.call()
                //调用成功后清除错误纪录
                lastError = null
            }
            catch (Throwable throwable){
                log.error("操作中发生错误${throwable.message}")
                lastError = throwable
            }

            if (lastError ==null){
                break
            }

            timeSpent +=interval
            sleep(interval * 1000)
        }
        if (lastError !=null){
            throw lastError
        }
    }


    /**
     * 等待页面不存在目标元素
     * @param cssSelector 目标元素
     * @param timeout
     */
    void waitUntilElementDisappear(String cssSelector,double timeout = 10){
        int preLoginWait = timeout * 0.2
        if (preLoginWait <2){
            preLoginWait = 2
        }
        sleep(preLoginWait * 1000)
        waitFor(timeout){!$(cssSelector)}
    }

    /**
     * 不断查找某元素，知道找到或者超时
     * @param attributes
     * @param selector
     * @param timeout
     * @return
     */
    Navigator keepFindingElementUntilTimeOut(Map<String, Object> attributes = null,  String selector,Integer timeout ){
        Navigator navigator = null
        long starttime = System.currentTimeMillis()
        long endTime = startTime + timeout*1000
        while(starttime<endTime){
            if (attributes){
                navigator = $(attributes,selector)
            }
            else{
                navigator = $(selector)
            }
            if (navigator){
                break
            }
            sleep(200)
            starttime +=200
        }
        return navigator

    }


}