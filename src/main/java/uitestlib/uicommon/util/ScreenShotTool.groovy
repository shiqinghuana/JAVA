package uitestlib.uicommon.util

import com.alibaba.fastjson.JSON
import groovy.transform.ToString
import groovy.util.logging.Slf4j
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver


import java.text.SimpleDateFormat

/**
 * Created by QingHuan on 2019/11/7 0:51
 */
@SuppressWarnings('unused')
@Slf4j
class ScreenShotTool {

    @ToString
    static class ScreenShotRequest{
        //需要截图的driver
        WebDriver driver
        //截图命名前缀
        String fileNamePrefix
        //截图保存的目录
        String dir
    }
    /**
     * 系统是否繁忙 ，繁忙的时候会走f2etest,截图先Base64再图片，否则走本地测试，直接保存文件
     */
    private static String isBusy = System.getProperty('ui.busy','No')
    /**
     * 上一次实际的执行截图动作的时间戳
     */
    static long lastTaskActionTime = 0
    /**
     * 两次截图之间的时间间隔
     */
    static int actionInterval = 500

    /**
     * 截图功能是否启用
     */
    static String enable = System.getProperty('ui.screen.shot.enabled','true')

    static Map<WebDriver,Long>lastOperated=new HashMap<>()

    /**
     * 纪录截图请求，只有一个，后面的截图请求会覆盖前面还会完成的截图请求
     */
    private static ScreenShotRequest screenShotRequest = new ScreenShotRequest()


    /**
     * 生成截图文件前缀
     * @return
     */
    static String generateScreenShotFileNamePrefix(){
        String testCaseName = System.getProperty('ui.test.case.name')
        if (!testCaseName){
            log.warn("获取测试用例名称失败，请在@before方法中使用UitestNameRule 的 logTestCaseName 方法来生成")
            testCaseName = 'unKnownTestCaseName'
        }
        return testCaseName+"-"+System.currentTimeMillis()+'--'
    }

    static String takeScreenShot(WebDriver driver,String fileNamePrefix,String dir = null){
        if (!enable.toLowerCase().contains('true')){
            return
        }
        //创建存放截图的路径
        if (dir){
            def imageDir = new File(dir)
            if (!imageDir.exists()){
                imageDir.mkdirs()
            }
        }
        String currentSep = File.separator
        if (null !=dir && !dir.endsWith(currentSep)){
            dir +=currentSep
        }
        if (fileNamePrefix ==null){
            fileNamePrefix = ''
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS")
        String filename = fileNamePrefix + "-"+df.format(new Date())+'.jpg'
        String filePath = dir =null? filename:dir+filename
        log.debug("开始截图")
        log.debug("开始截图：${filePath}")
        try {
            if (isBusy.toLowerCase()=='no'){
                File screenShot =  ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE)
                FileUtils.copyFile(screenShot,new File(filePath))
            }
            else{
                String base64Image =  ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64)
                Base64Utils.Base64ToImage(base64Image,filePath)
            }
        }catch(IOException e){e.printStackTrace()}
        creatScreenShotInfoTextFile(filePath)
        return filePath
    }

    /**
     * 接受截图请求，主要是接受after click web driver 实践触发的截图请求
     * 3秒最多执行一次截图，以铺货一些服务异常月面气泡提示信息，可疑的错误
     * 后面可能会用到Python脚本进行分析
     */
    static void takeScreenShotTask(){
        long time =System.currentTimeMillis()
        if (time -lastTaskActionTime <actionInterval){
            //截图频繁，忽略
            log.debug("截图太频繁，距离上一次截图不${actionInterval}ms，稍后再试")
            return
        }
        if (screenShotRequest.driver){
            log.debug("处理截图请求，${screenShotRequest}")
            takeScreenShot(screenShotRequest.driver,screenShotRequest.fileNamePrefix,screenShotRequest.dir)
            lastTaskActionTime = System.currentTimeMillis()
        }
    }
    /**
     * 请求对参数指定的webdriver代表的浏览器进行截图
     * @param driver1
     * @param fileNamePrefix
     * @param dir
     */
    synchronized static void requestToTakScreenShotForWebDriver(WebDriver driver1,String fileNamePrefix,String dir =null){
        try{
            screenShotRequest.driver = driver1
            screenShotRequest.fileNamePrefix = fileNamePrefix
            screenShotRequest.dir = dir
            log.debug("接受截图请求${screenShotRequest}")
            takeScreenShotTask()
        }catch(Exception ignored){}
    }

    static void clearSimilarScreenShots(String currentCaseName , String exclude = "notExcludeAnything",String imageDirStr = 'src/image'){
        //去除后缀名
        String formattedExclude = exclude.contains('.jpg')?exclude[0..-5]:exclude
        File imageDir = new File(imageDirStr)
        imageDir.eachFile { File f ->
            if (f.name.contains(currentCaseName)&&f.name.endsWith('jpg')&&!f.name.contains(formattedExclude)){
                if (f.size()<50*1000){
                    log.debug("删除小于50K的截图${f.name}，可能是个空白页")
                    f.delete()
                }
            }
            if (f.name.contains(currentCaseName)&&f.name.endsWith('txt')&&!f.name.contains(formattedExclude)){
                if (f.size()<3){
                    log.debug("清理空的截图说明文件：${f.name}")
                    f.delete()
                }
            }
        }
        List<File> screenShots = []
        imageDir.eachFile {
            if (it.name.contains(currentCaseName)&& it.name.endsWith('jpg')){
                screenShots.add(it)
            }
        }
        List<File> needToDelete = []
        for (File f:screenShots){
            for (File other : screenShots){
                if (f==other){
                    continue
                }
                if (Math.abs(f.size() - other.size())<3*1000){
                    if (f.lastModified()<other.lastModified()){
                        log.debug("截图：${f.name}和${other.name} 大小相差在3k以内，且创建时间小于后者，应移除")
                        needToDelete.add(f)
                        break
                    }
                }
            }
        }
        needToDelete.each {
            if (!it.name.contains(formattedExclude)){
                log.debug("删除截图${it.name}")
                it.delete()
                deleteRelatedScreenShotInfoTextFile(it.absolutePath)
            }
        }
    }

    static void addTestDataInfoForTestCase(String currentCaseName,String abnormalFildName ,Map testDate,List<String> errorInfo =null){
        File imageDir = new File('src/images')
        File[] f =imageDir.listFiles()
        if (!f){
            log.info("src/images没有文件")
            return
        }
        List<File> targets = f.findAll{it.name.contains(currentCaseName)&&it.name.contains(".jpg")}
        targets.sort()
        if (targets.size()==0){
            log.error("用例${currentCaseName}执行过程中没有产生截图信息，没法添加执行用例数据到图片")
            return
        }

        File  targetImage = targets[0]
        File targetImageInfoFile = new File(targetImage.absolutePath[0..-5]+'.txt')
        if (!targetImageInfoFile.exists()){
            //图片信息文件不存在，穿件一个
            targetImageInfoFile.createNewFile()
        }
        //读取已有数据
        List<String> currentLines = []
        targetImageInfoFile.eachLine ('UTF-8'){
            currentLines.add(it)
        }
        //写入测试数据
        targetImageInfoFile.withWriter ("UTF-8"){writer ->
            if (abnormalFildName){
                writer.writeLine('本次测试异常字段'+abnormalFildName)
                writer.writeLine(' ')
            }
            if (testDate){
                writer.writeLine('本次测试数据：')
                writer.writeLine(JSON.toJSONString(testDate,true))
                writer.writeLine()
            }
            if (errorInfo){
                writer.writeLine('本次异常信息：')
                errorInfo.each {String errorLine ->
                    writer.writeLine(errorLine)
                }
                writer.writeLine(" ")
            }
            //追加原有数据
            currentLines.each {String line ->
                writer.writeLine(line)
            }
        }
    }


    static List<String> findTestErrorInfoTestCase(String currentCaseName){
        File imageDir =  new File('src/images')
        File[] f = imageDir.listFiles()
        if (!f){
            log.info('src/images 没有文件')
            return
        }
        List<File> targets = f.findAll{it.name.contains(currentCaseName)&&it.name.contains('.jpg')}
        targets.sort()
        //向用例执行过程中产生的第一张图的说明文件中追加信息
        if (targets.size() ==0){
            log.error("用例${currentCaseName}执行过程中没有产生截图信息。没法添加用例执行数据到图片")
            return
        }
        File targetImage = targets[-1]
        File targetImageInfoFile = new File(targetImage.absolutePath[0..-5]+'.txt')
        if (!targetImageInfoFile.exists()){
            return null
        }
        //读取错误信息
        List<String> errorLines = []
        targetImageInfoFile.eachLine  ("UTF-8"){
            errorLines.add(it)
        }
        return errorLines
    }

    //创建和截图同名的.txt文件，用于后续添加截图信息
    static void creatScreenShotInfoTextFile(String imageFilePath){
        new File(imageFilePath[0..-5]+ '.txt').withWriter ('UTF-8'){writer ->
            writer <<' '
        }
    }




    /**
     * 删除相关的截图信文件
     * @param imageFilePath
     */
    static void deleteRelatedScreenShotInfoTextFile(String imageFilePath){
        String infoFileName = imageFilePath[0..-5]+".txt"
        File f = new File(infoFileName)
        if (f.exists()){
            f.delete()
        }
    }

}
