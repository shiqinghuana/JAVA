package uitestlib.uicommon.modules

import geb.navigator.Navigator

/**
 * 点击link<a 元素
 * Created by QingHuan on 2019/11/1 23:52
 */
class NameLink extends StableEnhancedMoudle{

    static content = {
        link {String name,Integer index = 0 ->
            Navigator allRekatedLinks = $('a',text:contains(name))
            Navigator sorted = chooseWhenMoreThanOneMatch(allRekatedLinks,name)
            return sorted[index]
        }
    }

    /**
     *点击给定文本的链接
     * @param linkText
     * @param index
     * @return
     */
    def clickNamedLink(String linkText,Integer index = 0){
        Navigator thelink = link(linkText,index)
        assert thelink,"页面上找不到文本${linkText}为，索引为${index}的链接"
        stableClick(thelink)
    }



}
