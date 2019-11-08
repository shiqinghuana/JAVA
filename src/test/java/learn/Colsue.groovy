package learn

/**
 * Created by QingHuan on 2019/10/29 23:22
 */
class Colsue {

    static  show(int num ,final Closure closure){
        (1..num).collect{
            return closure(it)
        }
    }


    def static templateMethod(list, common1, diff1, diff2, common2) {
        common1 list
        diff1 list
        diff2 list
        common2 list
    }

    def static common1 = { list -> list.sort() }
    def static common2 = { println it }
    def static diff1 = { list -> list.unique() }
    def static diff2 = { list -> list }



    static void main(String[] args) {

        println show(5) {x->x*x}
        templateMethod([2,6,1,9,8,2,4,5], common1, diff1, diff2, common2)


    }








}
