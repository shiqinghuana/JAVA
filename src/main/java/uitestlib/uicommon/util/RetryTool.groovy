package uitestlib.uicommon.util

import groovy.util.logging.Slf4j

/**
 * 重跑用例，case放入闭包中
 * Created by QingHuan on 2019/11/9 15:23
 */
@Slf4j
class RetryTool {
    /**
     * 最新错误信息
     */
    static String latestErrorInfo

    static def runWithRetry(int retryCount = 1,int retryInterval = 5,Closure closure) {
        Throwable throwable = null
        boolean success = false
        try {
            latestErrorInfo = null
            closure.call()
            success = true
        } catch (Throwable e) {
            log.info("用例执行失败：${e.message}")
            throwable = e
        }
        //执行失败
        if (!success) {
            for (int i in 0..retryCount) {
                Integer sleepTime = new Random().nextInt(retryInterval)
                log.info("执行重试：${sleepTime}秒后重试")
                sleep(sleepTime * 1000)

                try {
                    latestErrorInfo = null
                    closure.call()
                    success = true
                }
                catch (Throwable e) {
                    log.info("用例执行失败${e.getMessage()}")
                    throwable = e
                }
                if (success) {
                    return
                }
        }
    }
    if (throwable){
        latestErrorInfo = throwable.message
        throw throwable
    }
    else {
        latestErrorInfo = null
        }
    }
}
