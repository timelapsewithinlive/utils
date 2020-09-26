package algorithm.problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class 求是否能完成依赖课程学习 {

    public static void main(String[] args){
        int numCourses=5;
        int[][] prerequisites ={{5,4},{5,3},{3,2},{4,1},{4,2}};
        System.out.println(canFinish(numCourses,prerequisites));
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        List<List<Integer>> adjacency = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < numCourses; i++)
            adjacency.add(new ArrayList<>());

        // Get the indegree and adjacency of every course.
        for(int[] cp : prerequisites) {
            int a = cp[0];
            int b = indegrees[a]++;
            adjacency.get(cp[1]).add(cp[0]);
        }

        // Get all the courses with the indegree of 0.
        for(int i = 0; i < numCourses; i++)
            if(indegrees[i] == 0) queue.add(i);

        // BFS TopSort.
        while(!queue.isEmpty()) {
            int pre = queue.poll();
            numCourses--;
            for(int cur : adjacency.get(pre))
                if(--indegrees[cur] == 0) queue.add(cur);
        }
        return numCourses == 0;
    }


}
