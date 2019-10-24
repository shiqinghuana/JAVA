package utils.BaiduMoudle

import geb.navigator.EmptyNavigator
import geb.navigator.Navigator
import groovy.util.logging.Slf4j

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

            for (nameniv in Link) {
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

            }
    }
}