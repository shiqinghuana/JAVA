package hahah



import org.junit.BeforeClass

class Hahah {
    def x;
    def y;

    Hahah(){

    }

    Hahah(x, y){
        this.x=x
        this.y = y
    }

    def jjj(){
            return x+y
        }


    def hahha(){
        def l = [0,1,'[["11"],["22"]]']
        def str = '0,1,[["11"],["22"]]'
        def l1 = /我是一个列表$l/
        def s1 = /我是二个字符串$str/
        def s2 = /我是三个字符串${l[1..-1]}/
        def s3 = /我是4个字符串${l.toString()[1..-2]}/
        def l2 = [0,1,[["11"],["22"]]]
        println(l)
        println(l1)
        println(s1)
        println(s2)
        println(s3)

        println(l.toString())
        println(l[2])
        String s = l2[2]
        println(s+s.class)


    }


    public static void main(String[] args) {
        Hahah sdf = new Hahah("411111",87)

        println(sdf.hahha())
    }


}
