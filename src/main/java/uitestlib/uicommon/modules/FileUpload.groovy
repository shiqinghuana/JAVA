package uitestlib.uicommon.modules

import geb.navigator.Navigator
import groovy.util.logging.Slf4j

/**
 * 建模上传文件
 * Created by QingHuan on 2019/11/1 22:59
 */
@Slf4j
class FileUpload extends StableEnhancedMoudle{
    static content = {
        file {String uploadButtonName,Integer buttonIndex = 0 ->
            $('button',text:contains(uploadButtonName),buttonIndex).siblings('input',type:'file')}
    }
    def upLoadFile(String uploadButtonName,String filePath,Integer buttonIndex = 0){
        log.info("上传文件：uploadButtonName：${uploadButtonName},buttonIndex:${buttonIndex},filePath:${filePath}")
        File f = new File(filePath)
        String filename = f.name
        String absPath = f.absolutePath
        //上传文件
        try{
            file(uploadButtonName,buttonIndex) << absPath
            //等待上传完成，页面上能看到文件名
            waitFor(message:"文件${filename}上传"){$('span',text: filename)||$('div',text: filename)}
        }
        catch (Throwable throwable){
            Navigator fElements = $('input[type="file"]')
            if (fElements?.size() == 1){
                log.warn("当前页面上没有找到名称为：${uploadButtonName}的文件上传按钮，但是仅有一个input[type='file']元素,尝试向其上传文件")
                fElements <<absPath
            }
            else {
                throw throwable
            }
        }
    }
}
