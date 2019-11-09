package uitestlib.uicommon.support

import groovy.util.logging.Slf4j
import org.junit.rules.TestWatcher
import org.junit.runner.Description


/**
 * Created by QingHuan on 2019/11/9 20:54
 */
@Slf4j
class UiTestNameRule extends TestWatcher{
    private String name
    private Description description

    protected void starting(Description d){
        name = d.getMethodName()
        description = d
    }


    String getMethodName(){
        return name
    }

    String getTestFullName(){
        String testCaseName = description.displayName.split(/\(/)[0]
        String testClassName = description.className
        log.debug("testCaseName:${testCaseName},yesyClasssName:${testClassName}")
        return testClassName + '.' + testCaseName
    }

    String logTestCaseName(){
        log.info("开始执行用例：${getMethodName()}")
        String testName = getTestFullName()
        log.debug("纪录当前用例名称到System 属性 ui.test.case.name中：${testName}")
        System.setProperty('ui.test.case.name',testName)
    }

}
