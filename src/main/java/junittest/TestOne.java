package junittest;



import java.util.Arrays;

public class TestOne {

    void show(int[]arr){
        while(true){
            int m = 0;
            for(int i=0;i<arr.length-1;i++){
                int n;
                if(arr[i]>arr[i+1]){
                    n=arr[i];
                    arr[i]=arr[i+1];
                    arr[i+1]=n;
                    m++;
                }
            }
                if(m==0){
                    System.out.println(Arrays.toString(arr));
                    break;
                }
        }

    }

    public static void main(String[] args) {
        TestOne testOne = new TestOne();
        int[]arr={1,6,90,7,700};
        testOne.show(arr);
    }


}
