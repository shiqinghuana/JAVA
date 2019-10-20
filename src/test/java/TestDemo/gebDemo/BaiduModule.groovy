package TestDemo.gebDemo

import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class BaiduModile extends Module{

    static content = {
        Header  {$("div.head_wrapper")}
        BaiduMap  {$("#u1 > a:nth-child(3)")}
        form {$("form")}
        button {form.$("input.s_ipt")}
    }

    void open(){
        BaiduMap.click()
        log.info("打开百度地图")
    }

}