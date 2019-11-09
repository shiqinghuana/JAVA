package uitestlib.uicommon.modules

import geb.navigator.Navigator

/**
 * Created by QingHuan on 2019/11/9 21:52
 */
class UnNamedTextInput extends StableEnhancedMoudle {
    static content = {
        unNamedInput {String placeHolderText ,int index = 0 ->
            $('input',type:"text",placeHolderText:contains(placeHolderText))
        }
    }

    def inputTextToUnNamedTextInputField(String placeHolderText,String value,int index =0){
        Navigator input = unNamedInput(placeHolderText,index)
        assert input,"页面上没有找到placeHolderText属性为 ${placeHolderText},index为:${index}的元素"
        input.firstElement().clear()
        input << value
    }



}
