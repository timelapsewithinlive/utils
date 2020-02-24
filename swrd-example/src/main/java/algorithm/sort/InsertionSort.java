package algorithm.problem.sort;

import com.alibaba.fastjson.JSON;

public class InsertionSort {

    public static int[] insertionSort(int[] arr){

        int length = arr.length; //数据长度
        //i初始化为0和1均可
        for (int i=1;i<arr.length;i++){ //从摸第一张牌开始
            int get =arr[i];
            int j=i-1;
            while (j>=0&&arr[j]>get){
                arr[j+1]=arr[j];
                j--;
            }
            arr[j + 1] = get;
        }
        return arr;
    }

    public static int[] insertionSort2(int[] a){

        int N = a.length;
        int i, j;
        for (i = 1; i < N; i++) {  //i初始化为0和1均可
            for (j = i - 1; j >= 0 && a[i] < a[j]; j--) {
                //当摸的牌比手牌小的时候，继续执行
            }
            //这里跳出内层循环，a[i]应被插入到a[j]后
            int tmp = a[i];
            for (int k = i; k > j + 1; k--) {
                a[k] = a[k-1];
                //将手牌移到摸牌的后面
            }
            a[j+1] = tmp;
        }
        return a;
    }

    public static void main(String[] args){
        int arr[] = { 6, 5, 3, 1, 8, 7, 2, 4,4 };// 从小到大插入排序
        int arr1[] = insertionSort(arr);
        //int arr1[] = insertionSort2(arr);
        System.out.println(JSON.toJSONString(arr1));
    }
}
