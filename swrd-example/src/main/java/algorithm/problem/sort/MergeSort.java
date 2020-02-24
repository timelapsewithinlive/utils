package algorithm.problem.sort;

import java.util.Arrays;

public class MergeSort {
    public static void main(String []args){
        int []arr = {8,4,5,7,1,3,6,2};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void sort(int []arr){
        int []temp = new int[arr.length];//在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
        sort(arr,0,arr.length-1,temp);
    }
    private static void sort(int[] arr,int left,int right,int []temp){
        if(left<right){
            int mid = (left+right)/2;
            sort(arr,left,mid,temp);//左边归并排序，使得左子序列有序
            sort(arr,mid+1,right,temp);//右边归并排序，使得右子序列有序
            merge(arr,left,mid,right,temp);//将两个有序子数组合并操作
        }
    }
    private static void merge(int[] arr,int left,int mid,int right,int[] temp){
        int l = left;//左序列指针
        int r = mid+1;//右序列指针
        int t = 0;//临时数组指针
        while (l<=mid && r<=right){
            if(arr[l]<=arr[r]){
                temp[t++] = arr[l++];
            }else {
                temp[t++] = arr[r++];
            }
        }
        while(l<=mid){//将左边剩余元素填充进temp中
            temp[t++] = arr[l++];
        }
        while(r<=right){//将右序列剩余元素填充进temp中
            temp[t++] = arr[r++];
        }
        t = 0;
        //将temp中的元素全部拷贝到原数组中
        while(left <= right){
            arr[left++] = temp[t++];
        }
    }

}
