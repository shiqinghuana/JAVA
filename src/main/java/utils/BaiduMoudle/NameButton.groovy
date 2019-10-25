package utils.BaiduMoudle

import geb.navigator.EmptyNavigator
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.WebElement

@Slf4j
class NameButton  extends StableEnhancedMoudle {
    HashMap historydata = new HashMap<String, ArrayList<Navigator>>()
    static content = {

        /**
         * 页面几种常见元素
         * <a href 链接
         * < input 输入框
         *  <button
         */
        Link { $("a") }

        Input { $("input") }
        Button { $("button") }

    }

    Navigator clickWebElementByText(String name, Integer index=0) {
        if (index == 0) {
            Navigator niv1 = (Navigator) Link.find { it.text()?.contains(name) }
            Navigator niv2 = (Navigator) Input.find { it.text()?.contains(name) }
            Navigator niv3 = (Navigator) Button.find { it.text()?.contains(name) }
            if (niv1 != null) {
                log.info("查询到Link类型${name}")
                return niv1
            }
            if (niv2 != null) {
                log.info("查询到Input类型${name}")
                return niv2
            }
            if (niv3 != null) {
                log.info("查询到Button类型${name}")
                return niv3
            }

        } else {
            Navigator niv4 = (Navigator) Link.findAll { it.text()?.contains(name) }
            Navigator niv5 = (Navigator) Input.findAll { it.text()?.contains(name) }
            Navigator niv6 = (Navigator) Button.findAll { it.text()?.contains(name) }
            if (niv4 != null) {
                log.info("查询到Link类型${name}")
                return niv4
            }
            if (niv5 != null) {
                log.info("查询到Input类型${name}")
                return niv5
            }
            if (niv6 != null) {
                log.info("查询到Button类型${name}")
                return niv6
            }

        }

    }
        Navigator returNoEmptyNavigator(Navigator...args){
            for(i in args) {
                def element =  browser.navigatorFactory.createFromNavigators(i)
                if (!isEmpty(element)){
                    return i
                }
            }
        }
           /* for (nameniv in Link) {
                if (nameniv.text()?.contains(name)) {
                    log.info("=查询到Link类型${name}")
                   return nameniv

                }

            }
            for (nameniv in Input) {
                if (nameniv.text()?.contains(name)) {
                    log.info("=查询到Input类型${name}")
                    return nameniv
                }

            }
            for (nameniv in Button) {
                if (nameniv.text()?.contains(name)) {
                    log.info("=查询到Button类型${name}")
                    return nameniv
                }

            }*/

}