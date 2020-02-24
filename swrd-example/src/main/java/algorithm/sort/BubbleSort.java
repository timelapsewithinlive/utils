package algorithm.problem.sort;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static int[] bubbleSortAsc(int[] arr){
        int length = arr.length;

        for (int i=0;i<length-1;++i){
            for (int j=0;j<length-i-1;++j){
                if(arr[j]>arr[j+1]){
                    exchange(arr,j,j+1);
                    //swap(arr,j,j+1);
                }
            }
        }
        return  arr;
    }

    public static int[] bubbleSortDes(int[] arr){
        int length = arr.length;

        for (int i=0;i<length-1;++i){
            for (int j=length-i-1;j>0;--j){
                if(arr[j]<arr[j-1]){
                    //exchange(arr,j,j+1);
                    swap(arr,j,j+1);
                }
            }
        }
        return  arr;
    }

    public static void exchange(int[] arr,int a,int b){
        int temp = arr[b];
        arr[b] = arr[a];
        arr[a]=temp;
    }

    public static void swap(int[] arr,int a,int b){
        int temp = arr[a]^arr[b];
        arr[a]=temp^arr[b];
        arr[b]=temp^arr[a];

    }

    public static void main(String[] args){
        int arr[] = { 6, 5, 3, 1, 8, 7, 2, 4,4 };
        int arrAsc[] = bubbleSortAsc(arr);
        int arrDes[] = bubbleSortDes(arrAsc);
        System.out.println(JSON.toJSONString(arrDes));
    }
}
