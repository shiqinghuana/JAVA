package uitestlib.uicommon.config

/**
 * Created by QingHuan on 2019/11/9 17:08
 */
class TestLibConfig {
    /**
     * 默认环境
     */
    public static String CURRENT_ENV = System.getProperty('test.environment','daily')

    /**
     * selenium 配置
     */

    static String WEBDRIVER_PATH_CHROME = '/Driver/chromedriver'
    static String WINDOWS_WEBDRIVER_PATH_CHROME = '/Driver/chromedriver.exe'
}
