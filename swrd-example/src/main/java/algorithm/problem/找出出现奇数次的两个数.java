package algorithm.problem;

public class 找出出现奇数次的两个数 {

    public static void main(String[] args){
      int[] arr=   { 1, 2, 3, 4, 1, 2, 7, 3 };

      int 异或结果=0;
      for (int i=0;i<arr.length;i++){
          异或结果^=arr[i];
      }

      int 第几位=0;

        int a=1;
      /*  while(true){
            int b = 异或结果 & 1;
            if(b==0){
                a=a<<1;
                第几位++;
            }else{

            }
        }*/

        int x=1;
        System.out.println(++x);


    }
}
