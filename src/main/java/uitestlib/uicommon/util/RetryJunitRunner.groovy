package uitestlib.uicommon.util

import groovy.util.logging.Slf4j
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.internal.AssumptionViolatedException
import org.junit.internal.runners.model.EachTestNotifier
import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier
import org.junit.runner.notification.StoppedByUserException
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.InitializationError
import org.junit.runners.model.Statement


/**
 * 失败重跑功能
 * 这里不适合并发跑用例的场景，因为这里使用了TestCaseExecuteInfoUtil 来纪录当前正在跑的测试用例，只有一个
 * Created by QingHuan on 2019/11/8 23:58
 */
@Slf4j
class RetryJunitRunner extends BlockJUnit4ClassRunner{
    /**
     * 测试用例重跑次数
     */
    private final int retryCount = 1

    /**
     * 纪录每个测试用例执行的次数
     */

    private Map<String,Integer> testExecutedCounts =  new HashMap<>()

    /**
     *
     * @param klass
     * @throws InitializationError
     */
    RetryJunitRunner(Class<?> klass) throws InitializationError {
        super(klass)
    }



    void run(final  RunNotifier notifier){
        EachTestNotifier testNotifier =new EachTestNotifier(notifier,getDescription())
        Statement statement = classBlock(notifier)
        try{
            updataTestCount(description.displayName)
            TestCaseExecuteInfoUtil.reportTestCaseInfo(description)
            log.warn("testCaseName：${TestCaseExecuteInfoUtil.testCaseName},testExecutedCounts:${testExecutedCounts}")
            statement.evaluate()
        }catch(AssumptionViolatedException e){
            testNotifier.addFailedAssumption(e)
        }catch(StoppedByUserException e1){
            throw e1
        }catch(Throwable e){
            retry(testNotifier,statement,getDescription(),e)
        }
    }

    protected void runChild(final  FrameworkMethod method,RunNotifier notifier){
        Description description =    describeChild(method)
        if (method.getAnnotation(Ignore.class)!=null){
            notifier.fireTestIgnored(description)
        }else {
            runTestUntil(methodBlock(method),description,notifier)
        }
    }

    protected final void runTestUntil(Statement statement,Description description,
                                        RunNotifier notifier){
        EachTestNotifier eachNotifier = new EachTestNotifier(notifier,description)
        eachNotifier.fireTestStarted()
        try{
            updataTestCount(description.displayName)
            TestCaseExecuteInfoUtil.reportTestCaseInfo(description)
            log.warn("testCaseName:${TestCaseExecuteInfoUtil.testCaseName},testExecutedCounts:${testExecutedCounts}")
            statement.evaluate()
        }
        catch (AssumptionViolatedException e){
            eachNotifier.addFailedAssumption(e)
        }
        catch (Throwable e){
            retry(eachNotifier,statement,description,e)
        }
        finally{
            eachNotifier.fireTestFinished()
        }
    }

    void retry(EachTestNotifier notifier, Statement statement, Description description,Throwable currentThrowable){
        Throwable caughtThrowable = currentThrowable
        while (testExecutedCounts[description.displayName]<retryCount){
            try{
                log.error("重跑用例：${description.displayName}，上次异常信息${logStackTrace(caughtThrowable)}")
                updataTestCount(description.displayName)
                TestCaseExecuteInfoUtil.reportTestCaseInfo(description)
                log.warn("testCaseName:${TestCaseExecuteInfoUtil.testCaseName},testExecutedCounts:${testExecutedCounts}")
                statement.evaluate()
                return
            }
            catch (Throwable e){
                caughtThrowable = e
            }
        }
        log.error("测试用例最终执行失败：${description.displayName}异常信息：${logStackTrace(caughtThrowable)}")
        notifier.addFailure(caughtThrowable)
    }


    private static String logStackTrace(Throwable e){
        StringWriter stringWriter = new StringWriter()
        PrintWriter printWriter = new PrintWriter(stringWriter)
        e.printStackTrace(printWriter)
        return stringWriter.toString()
    }

    private void updataTestCount(String testName){
        if (testExecutedCounts[testName]!=null){
            testExecutedCounts[testName] = testExecutedCounts[testName]+1
        }else {
            testExecutedCounts[testName] = 0
        }
    }
}
