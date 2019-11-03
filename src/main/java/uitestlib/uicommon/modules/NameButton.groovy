package uitestlib.uicommon.modules

import geb.navigator.Navigator

/**
 * 根据按钮名称点击按钮
 * Created by QingHuan on 2019/11/1 22:48
 */
class NameButton extends StableEnhancedMoudle{

    static content = {
        button {String buttonName,Integer index=0->
            Navigator allRelatedBtns = $('button',text:contains(buttonName))
            Navigator sorted = chooseWhenMoreThanOneMatch(allRelatedBtns,buttonName)
            return sorted[index]
        }
    }

    def clickNameButton(String buttonName,Integer index = 0){

        Navigator btn = button(buttonName,index)
        assert btn,"当前页面找不到给定按钮：按钮名称：${buttonName},在同名按钮中的索引：${index})"
        stableClick(btn)
    }

}
