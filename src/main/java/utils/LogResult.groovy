package utils

import groovy.util.logging.Slf4j

import java.text.SimpleDateFormat


@Slf4j
class LogResult {
    static File creatTestResult(String prefix ,String suffix = '.txt'){
        // 存放目录
        String dir = 'src/result'
        if (dir){
            def imageDir = new File(dir)
            if (!imageDir.exists()){
                imageDir.mkdirs()
            }
        }

        String currentSep = File.separator
        if (null !=dir && !dir.endsWith(currentSep)){
            dir += currentSep
        }
        if (prefix == null){
            prefix = ''
        }
        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS")
        String fileName = prefix + '-' + df.format(new Date()) +suffix
        String filePath = dir == null? fileName:dir+fileName
        File f = new File(filePath)
        f.createNewFile()
        log.info("本次结果纪录在文件：${filePath}")
        log.debug("hahha")
        return  f
    }

    static append(File f, String text, String charest = 'utf-8'){
        f.append(text+'\n',charest)
    }


}
