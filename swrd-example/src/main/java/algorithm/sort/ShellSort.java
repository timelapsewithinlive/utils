package algorithm.sort;

import com.alibaba.fastjson.JSON;

public class ShellSort {
    public static void shellSort(int[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1; //h的取值序列为1, 4, 13, 40, ...
        }
        while (h >= 1) {
            int n, i ,j, k;
            //分割后，产生n个子序列
            for (n = 0; n < h; n++) {
                //分别对每个子序列进行插入排序
                for (i = n + h; i < N; i += h) {
                    for (j = i - h; j >= 0 && a[i] < a[j]; j -= h) {

                    }
                    int tmp = a[i];
                    for (k = i; k > j + h; k -= h) {
                        a[k] = a[k-h];
                    }
                    a[j+h] = tmp;
                }
            }
            h = h / 3;
        }
    }

    public static void main(String[] args){
        int arr[] = { 6, 5, 3, 1, 8, 7, 2, 4,4 };// 从小到大插入排序
        shellSort(arr);
        System.out.println(JSON.toJSONString(arr));
    }
}
