package com.lebron.leetcode;

import java.util.Arrays;

/**
 * 有个马戏团正在设计叠罗汉的表演节目，一个人要站在另一人的肩膀上。出于实际和美观的考虑，
 * 在上面的人要比下面的人矮一点且轻一点。已知马戏团每个人的身高和体重，请编写代码计算叠罗汉最多能叠几个人。
 * 输入：height = [65,70,56,75,60,68] weight = [100,150,90,190,95,110]
 *   输出：6
 *   解释：从上往下数，叠罗汉最多能叠 6 层：(56,90), (60,95), (65,100), (68,110), (70,150), (75,190)
 */
public class CircusTowerLCCI {

    public static int bestSeqAtIndex(int[] height, int[] weight) {






        int hLen = height.length;
        int[][] H2W = new int[height.length][2];
        for(int i=0,j=0;i<hLen;i++,j++){
            H2W[i][0] = height[i];
            H2W[i][1] = weight[i];
        }
        //如果身高一样，体重从大到小排序
        Arrays.sort(H2W,(o1, o2)->(o1[0]==o2[0]?o2[1]-o1[1]:o1[0]-o2[0]));

        //LIS二分查找法
        int max = 0;
        int[] top = new int[hLen];
        for(int i=0;i<hLen;i++){
            int value = H2W[i][1];
            int left = 0, right = max;
            while(left < right){
                int mid = (left + right)/2;
                if(value > top[mid]){
                    left = mid + 1;
                }else if(value <= top[mid]){
                    right = mid;
                }
            }
            if(left==max){
                max ++;
            }
            top[left] = value;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] height = {65,70,56,75,60,68};
        int[] weight = {120,92,90,190,95,110};
        System.out.println(bestSeqAtIndex(height,weight));
    }

}
