package uitestlib.uicommon.util

import groovy.util.logging.Slf4j
import org.junit.runner.Description
import org.openqa.selenium.WebDriver

/**
 * Created by QingHuan on 2019/11/9 0:08
 */
@Slf4j
class TestCaseExecuteInfoUtil {
    static String testCaseName
    static String whoIsExcuting(WebDriver driver){
        return '-'
    }
    static String generateScreenShotFileNamePrefix(WebDriver driver){
        return testCaseName +'-'+System.currentTimeMillis()+'-'+whoIsExcuting(driver)
    }


    static String generateScreenShotFileNamePrefixForClick(WebDriver driver){
        return 'click-'+generateScreenShotFileNamePrefix(driver)
    }
    /**
     * 将测试用哦管理名称和石化成下面的格式，以便其他插件调用
     * @param description
     * @return
     */
    static String reportTestCaseInfo(Description description){
        String testCaseName = description.displayName.split(/\(/)[0]
        String testClassName =  description.className
        log.info("testCaseName:${testCaseName},testClassName:${testClassName}")
        TestCaseExecuteInfoUtil.testCaseName = testClassName + '.' + testCaseName
    }
}
