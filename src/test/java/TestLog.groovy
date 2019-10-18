import utils.LogResult

class TestLog {
  static   File result1 = LogResult.creatTestResult("文件纪录测试")

    public static void main(String[] args) {


        result1.append("这是纪录测试文本的")
    }

}
