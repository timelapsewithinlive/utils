package algorithm.search;

import java.util.Stack;


public class 前序遍历栈实现 {
    public static TreeNode init(){
        TreeNode J = new TreeNode(8, null, null);
        TreeNode H = new TreeNode(4, null, null);
        TreeNode G = new TreeNode(2, null, null);
        TreeNode F = new TreeNode(7, null, J);
        TreeNode E = new TreeNode(5, H, null);
        TreeNode D = new TreeNode(1, null, G);
        TreeNode C = new TreeNode(9, F, null);
        TreeNode B = new TreeNode(3, D, E);
        TreeNode A = new TreeNode(6, B, C);
        return A;  //返回根节点
    }

    public static void preOrderStack(TreeNode root){//先序遍历

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode node = root;

        while(node != null || stack.size()>0){//将所有左孩子压栈
            if(node != null){//压栈之前先访问
                System.out.println(node.val);
                stack.push(node);
                node = node.left;
            }else{
                node = stack.pop();
                node = node.right;
            }
        }
    }

    public static void main(String[] args){
        TreeNode init = init();
        preOrderStack(init);
    }
}
