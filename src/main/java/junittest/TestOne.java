package junittest;



import java.util.Arrays;

public class TestOne {

    int show(int low,int col){
        if (col==0||low==col){
            return 1;
        }else {
            return show(low-1,col-1)+show(low-1,col);
        }
    }
    //要打印几行
    void or(int x){
        for (int i = 1; i <=x ; i++) {
            for (int j = 1; j <=2*x-1 ; j++) {
                if (j<x+i && j>x-i) {
                    for (int z = 1; z <=i ; z++) {
                        System.out.print(show(i,z)+" ");
                    }

                }else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TestOne testOne = new TestOne();

        testOne.or(3);
    }


}
