package uitestlib.uicommon.modules

import geb.navigator.Navigator
import org.openqa.selenium.WebElement

/**
 * Created by QingHuan on 2019/11/9 21:57
 */
@SuppressWarnings('unused')
class ScrollableDiv extends StableEnhancedMoudle{
    static content = {
        theDiv {String cssSelector,Object textOrIndex = null ->
            if (textOrIndex && textOrIndex instanceof  String){
                $(cssSelector,text:contains(textOrIndex))
            }
            else if (textOrIndex && textOrIndex instanceof Integer){
                $(cssSelector,textOrIndex)
            }
            else {
                $(cssSelector)
            }

        }
    }

    def scrollFromTopToDown(WebElement element){
        scrollFromTopToDown(transformWebElementToNavigator(element))
    }

    def scrollFromTopToDown(Navigator navigator){
        scrollFromTopToDownByPercent(navigator,100)
    }

    /**
     * 从上到下按百分比移动滚动条
     * @param element
     * @param percent
     * @return
     */
    def scrollFromTopToDownByPercent(WebElement element,Integer percent){
        scrollFromTopToDownByPercent(transformWebElementToNavigator(element),percent)
    }

    /**
     * 从上到下按百分比移动滚动条
     * @param element
     * @param percent 移动的百分比
     * @return
     */
    def scrollFromTopToDownByPercent(Navigator element,Integer percent){
        Integer scrollHeight = js.exec(navigator.firstElement(),"return arguments[0].scrollHeigth;") as Integer
        Integer percentHeight = (Integer) (scrollHeight * 1.0 * percent / 100)
        js.exec(navigator.firstElement(),"argument[0].scrollTop = ${percentHeight}")
    }


    def scrollFromTopToDown(String cssSelector,Object textOrIndex = null){
        Navigator div = theDiv(cssSelector,textOrIndex)
        assert div,"没有定位到可滚动的div元素：cssSelector：${cssSelector},textOrIndex:${textOrIndex}"
        scrollFromLeftToRight(div)
    }

    def scrollFromTopToDownByPercent(String cssSelector,Object textOrIndex = null,Integer percent){
        Navigator div = theDiv(cssSelector,textOrIndex)
        assert div,"没有定位到可滚动的div元素：cssSelector：${cssSelector},textOrIndex:${textOrIndex}"
        scrollFromLeftToRight(div)
    }


    def scrollFromLeftToRight(WebElement element){
        scrollFromLeftToRight(transformWebElementToNavigator(element))
    }
    def scrollFromLeftToRight(Navigator element){
        scrollFromLeftToRightByPercent(element,100)
    }

    /**
     * 从左到右滚动给定div元素的滚动条
     * @param element div对应的WebElement元素
     * @param percent 百分比
     * @return
     */
    def scrollFromLeftToRightByPercent(WebElement element,Integer percent){
        scrollFromLeftToRightByPercent(transformWebElementToNavigator(element),percent)
    }
    /**
     * ，从左到右滚动给定div元素的滚动条
     * @param navigator div对应的导航
     * @param percent  移动的百分比
     * @return
     */
    def scrollFromLeftToRightByPercent(Navigator navigator,Integer percent){
        Integer scrollwidth = js.exec(navigator.firstElement(),"return arguments[0].scrollWidth;") as Integer
        Integer percentWidth = (Integer) (scrollwidth * 1.0 * percent / 100)
        js.exec(navigator.firstElement(),"arguments[0].scrollLeft=${percentWidth};")

    }

    def scrollFromLeftToRight(String cssSelector,Object textOrIndex = null){
        Navigator div = theDiv(cssSelector,textOrIndex)
        assert div,"没有定位到可滚动的div元素：cssSelector：${cssSelector},textOrIndex:${textOrIndex}"
        scrollFromLeftToRight(div)
    }


    def  scrollFromLeftToRightByPercent(String cssSelector,Object textOrIndex = null,Integer percent){
        Navigator div = theDiv(cssSelector,textOrIndex)
        assert div,"没有定位到可滚动的div元素：cssSelector：${cssSelector},textOrIndex:${textOrIndex}"
        scrollFromLeftToRight(div)
    }



    private  Navigator transformWebElementToNavigator(WebElement element){
        creatNavigatorfromElement(element)
    }
}
