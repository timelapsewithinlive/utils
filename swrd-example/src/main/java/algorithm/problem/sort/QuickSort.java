package algorithm.problem.sort;

import com.alibaba.fastjson.JSON;

public class QuickSort {

    public static void main(String[] args){
        int arr[] = { 6, 5, 3, 1, 8, 7, 2, 4,4 };// 从小到大插入排序
        int i=1;
        System.out.println(arr[++i]);
        System.out.println(i);
        sort( arr,0,  arr.length-1);
        System.out.println(JSON.toJSONString(arr));
    }

    public static int partition(int []array,int lo,int hi){
        //固定的切分方式
        int key=array[lo];
        while(lo<hi){
            while(array[hi]>=key&&hi>lo){//从后半部分向前扫描
                hi--;
            }
            array[lo]=array[hi];
            while(array[lo]<=key&&hi>lo){//从前半部分向后扫描
                lo++;
            }
            array[hi]=array[lo];
        }
        array[hi]=key;
        return hi;
    }

    public static void sort(int[] array,int lo ,int hi){
        if(lo>=hi){
            return ;
        }
        int index=partition(array,lo,hi);
        sort(array,lo,index-1);
        sort(array,index+1,hi);
    }


  /*  public static void exchange(int a[], int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }*/
}
