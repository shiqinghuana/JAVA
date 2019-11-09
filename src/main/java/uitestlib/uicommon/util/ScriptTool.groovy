package uitestlib.uicommon.util

import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.codehaus.groovy.runtime.InvokerHelper
import sun.font.Script

/**
 * 代码执行工具
 * Created by QingHuan on 2019/11/9 16:40
 */
@Slf4j
class ScriptTool {

    static String formatScript(String script){
        String otherImports = ''
        String remainedScript = ''
        script.eachLine {String line ->
            if (StringUtils.strip(line).startsWith('import ')){
                otherImports +=StringUtils.strip(line) + "\n"
            }
            else {
                remainedScript +=line + "\n"
            }
        }

        """
        import uitestlib.uicommon.util.ScriptTool
        ${otherImports}

        userRoleOutsideOfScript.with{
        ${remainedScript}
        """
    }
    /**
     * 执行脚本
     * @param userRole
     * @param script
     * @param uiContent
     * @param args
     * @return
     */
    static def runScript(def userRole,String script,Map<String,Object> uiContent = null,Object[] args = null){
        Binding binding =  new Binding()
        binding.setVariable('userRoleOutsideOfScript',userRole)
        binding.setVariable('uiContent',uiContent)
        binding.setVariable('args',args)
        try{
            Script shell
            shell = new GroovyShell().parse(script)
            InvokerHelper.createScript(shell.getClass(),binding).run()
        }
        catch (Exception e){
            log.error("执行脚本出错：${e.message}")
        }
    }
    /**
     * 执行脚本
     * @param script
     * @param uiContent
     * @param args
     * @return
     */

    static def runNormalScript(String script,Map<String,Object> uiContent = null,Object[] args = null){
        Binding binding =  new Binding()
        binding.setVariable('uiContent',uiContent)
        binding.setVariable('args',args)
        try{
            Script shell
            shell = new GroovyShell().parse(script)
            InvokerHelper.createScript(shell.getClass(),binding).run()
        }
        catch (Exception e){
            log.error("执行脚本出错：${e.message}")
        }
    }


}
