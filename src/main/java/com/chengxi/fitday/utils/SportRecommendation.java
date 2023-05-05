package com.chengxi.fitday.utils;

import java.util.*;

//基于运动（即推荐物品）的协同过滤算法
public class SportRecommendation {  
    // 运动项目兴趣评分矩阵
    private double[][] Amn;
    // 运动项目相似矩阵
    private double[][] Bmn;

    // 初始化兴趣评分矩阵和相似矩阵
    public SportRecommendation(double[][] Amn, double[][] Bmn) {
        this.Amn = Amn;
        this.Bmn = Bmn;
    }

    // 获取用户i对运动j的最终推荐评分
    public double getRecommendationScore(int xid, int sportId) {
        // 获取用户i喜欢的运动项目
        List<Integer> userLikes = getUserLikes(xid);
        double interestScore = Amn[xid][sportId];
        double similarityScore = 0;
        // 计算用户i和运动j之间的相似度得分
        for (int sport : userLikes) {
            similarityScore += Bmn[sport][sportId] * Amn[xid][sport];
        }
        // 根据公式计算最终推荐评分
        double recommendationScore = interestScore * 0.6 + similarityScore * 0.4;
        return recommendationScore;
    }

    // 获取用户i喜欢的运动项目
    private List<Integer> getUserLikes(int xid) {
        List<Integer> userLikes = new ArrayList<>();
        // 获取用户i的兴趣评分矩阵
        double[] userInterests = Amn[xid];
        // 获取用户i兴趣评分最高的前K个运动项目
        int K = 3;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(K, (a, b) -> Double.compare(Amn[xid][b], Amn[xid][a]));
        for (int i = 0; i < Amn[0].length; i++) {
            if (userInterests[i] > 0) {
                if (maxHeap.size() < K) {
                    maxHeap.offer(i);
                } else {
                    if (Amn[xid][i] > Amn[xid][maxHeap.peek()]) {
                        maxHeap.poll();
                        maxHeap.offer(i);
                    }
                }
            }
        }
        // 将用户i兴趣评分最高的前K个运动项目添加到喜欢列表中
        while (!maxHeap.isEmpty()) {
            userLikes.add(maxHeap.poll());
        }
        return userLikes;
    }

    // 获取运动推荐列表
    public List<Integer> getRecommendationList(int xid) {
        List<Integer> recommendationList = new ArrayList<>();
        // 遍历所有运动项目，获取用户i未评分的运动项目的推荐评分
        for (int i = 0; i < Amn[0].length; i++) {
            if (Amn[xid][i] == 0) {
                double recommendationScore = getRecommendationScore(xid, i);
                if (recommendationScore > 0) {
                    recommendationList.add(i);
                }
            }
        }
        // 根据推荐评分对运动项目进行排序
        Collections.sort(recommendationList, (a, b) -> Double.compare(getRecommendationScore(xid, b), getRecommendationScore(xid, a)));
        return recommendationList;
    }
}


