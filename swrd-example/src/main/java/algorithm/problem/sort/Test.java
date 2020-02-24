package algorithm.problem.sort;

public class Test {


    public static void main(String[] args){
        int arr[] ={};
        //冒泡
        for(int i=0;i<arr.length-1;++i){
            for (int j=0;j<arr.length-1-i;++j){
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }

        //选择
        for(int i=0;i<arr.length;++i){
            int minIndex=i;
            for (int j=i;j<arr.length;++j){
                if(arr[j]<arr[minIndex]){
                    minIndex=j;
                }
            }
            int temp =arr[minIndex];
            arr[minIndex]=arr[i];
            arr[i]=temp;
        }

        //插入
        int current;
        for(int i=0;i<arr.length;++i){
            current= arr[i+1];
            int preIndex=i;

            while(preIndex>=0&&arr[preIndex]>current){
                arr[preIndex+1]=arr[preIndex];
                preIndex--;
            }
            arr[preIndex+1]=current;
        }

        //
    }


}
