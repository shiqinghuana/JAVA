package learn

import javax.print.DocFlavor

class Parret {


    static void show(){
        print( "我是静态方法")
    }

    Integer s = innit()

    def P(){

        print("我是常规方法"+s)
    }

    Integer innit(){
        return 1
    }


    public static void main(String[] args) {
        show()
        print("我是主线程")

    }
}
