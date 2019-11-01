import utils.LogResult

class TestLog {
    static   File result1 = LogResult.creatTestResult("文件纪录测试")

    def static laal(Map<String,Object> stringObjectMap,String hahha,String hahhas){
        println("字典"+stringObjectMap)
        println("字符串1"+hahha)
        println("字符串2"+hahhas)
    }
    public static void main(String[] args) {
        laal("123","hahha",o:"ss")

        result1.append("这是纪录测试文本的")
    }

}
