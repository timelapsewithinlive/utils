package algorithm.problem.search;

public class MiddleSearch {

    public static void main(String[] args) throws InterruptedException {
        Object obj=new Object();

        synchronized (obj){
            while (true){
                System.out.println(3);
                obj.wait(1000);
                System.out.println(4);
            }
        }
    }

    public static int search (int meta,int[] arr,int begin,int end){
        int middle = (begin+end)/2;

        if(meta>arr[middle]){
            return search(meta,arr,middle+1,end);
        }else if(meta<arr[middle]){
            return search(meta, arr, begin, middle-1);
        }else{
            return middle;
        }

    }
}
