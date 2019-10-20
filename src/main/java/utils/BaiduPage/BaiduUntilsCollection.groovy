package utils.BaiduPage

import geb.Page
import geb.navigator.Navigator
import groovy.util.logging.Slf4j


/**
 * Created by shiqingahuna on 2019/10/20 23:35
 */
@Slf4j
class BaiduUntilsCollection extends Page{
    /**
     *   y用户脚本中使用的导航，查找元素后，找到的元素都存在这里
     * */
    Navigator navigatorInScript = null

    /**
     *   切换窗口前用户浏览器窗口
     * */
    String originalWindow = null
    static at = {true}




    void ClickName(String Name){

        //先查buttom

        button = $("Button",text)


    }


}
