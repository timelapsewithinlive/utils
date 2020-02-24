package algorithm.problem.sort;

import com.alibaba.fastjson.JSON;

public class SelectionSort {

    public static void selectionSort(int[] arr){
        int length =arr.length;

        for(int i =0;i<length-1;++i){
            int min =i;
            for (int j=1+i;j<length;++j){
                if(arr[j]<arr[min]){
                    min=j;
                }
            }
            exchange(arr,i,min);
        }
    }

    public static void exchange(int[] arr,int i,int min){
        int temp = arr[i];
        arr[i] = arr[min];
        arr[min]=temp;
    }

    public static void main(String[] args){
        int arr[] = { 6, 5, 3, 1, 8, 7, 2, 4,4 };// 从小到大插入排序
         selectionSort(arr);
        System.out.println(JSON.toJSONString(arr));
    }
}
