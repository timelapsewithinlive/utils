package algorithm.problem;
// https://blog.csdn.net/qq_35206244/article/details/99673621
public class 爬楼梯几种办法 {

    public static void main(String[] args) {
        int start = (int) System.nanoTime();
        System.out.println(jumpStepFibonacci(10));
        int end = (int)System.nanoTime();
        System.out.println("time:"+(end-start)+"ns");
    }

    /**上台阶问题,递归求解**/
    public static int jumpStepFibonacci(int n) {
        if(n<0) return -1;
        if(n<=2) return n;
        //后一个台阶等于前两个台阶方法之和。 所以一直递归到第一台阶。依次用前边的台阶计算后边台阶的方法数量
        return jumpStepFibonacci(n-1)+jumpStepFibonacci(n-2);
    }

    /**上台阶问题,迭代法**/
    public static int jumpStepIter(int n) {
        if(n<0) return -1;
        if(n<=2) return n;
        int temp1 =1;
        int temp2 =2;
        int res = 0;
        for(int i =3;i<=n;i++) {
            res = temp1 + temp2;
            temp1 = temp2;
            temp2 = res;
        }
        return res;
    }

    /**上台阶问题，动态规划**/
    public static int climbStairs(int n) {
        if(n==0) return -1;
        if(n==1) return 1;
        int []dp = new int [n];
        dp[0] =1;
        dp[1] =2;
        for(int i=0;i<n-2;i++) {
            dp[i+2]=dp[i]+dp[i+1];
        }
        return dp[n-1];
    }

}
