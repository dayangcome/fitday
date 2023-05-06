package com.chengxi.fitday.utils;

import com.chengxi.fitday.entity.Userinfo;
import com.chengxi.fitday.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class FoodRecommendation {           //对于食物推荐的协同过滤算法
    private List<String> recommendedRecipes;

    private List<Userinfo> users;

    @Autowired
    private IFoodService foodService;

    public FoodRecommendation() {
    }

    public FoodRecommendation(List<String> recommendedRecipes, List<Userinfo> users) {
        this.recommendedRecipes = recommendedRecipes;
        this.users = users;
    }

    public List<String> getRecommendedRecipes() {
        return recommendedRecipes;
    }
    public List<Userinfo> findSimilarUsers(Userinfo targetUser, double weightThreshold) {
        List<Userinfo> similarUsers = new ArrayList<>();
        for (Userinfo user : users) {
            if (Math.abs(user.getWeight() - targetUser.getWeight()) <= weightThreshold) {
                similarUsers.add(user);
            }
        }
        return similarUsers;
    }

    public static double computeSimilarityScore(Userinfo userA, Userinfo userB) {
        double[] ratingsA = {userA.getHeight(), userA.getWeight(), userA.getBmi()};
        double[] ratingsB = {userB.getHeight(), userB.getWeight(), userB.getBmi()};

        // 计算用户A和用户B的平均评分
        double avgRatingA = Arrays.stream(ratingsA).average().getAsDouble();
        double avgRatingB = Arrays.stream(ratingsB).average().getAsDouble();

        // 计算用户A和用户B的评分向量之间的皮尔逊相关系数
        double numerator = 0;
        double denominatorA = 0;
        double denominatorB = 0;
        for (int i = 0; i < ratingsA.length; i++) {
            double ratingA = ratingsA[i];
            double ratingB = ratingsB[i];
            numerator += (ratingA - avgRatingA) * (ratingB - avgRatingB);
            denominatorA += Math.pow(ratingA - avgRatingA, 2);
            denominatorB += Math.pow(ratingB - avgRatingB, 2);
        }
        double denominator = Math.sqrt(denominatorA) * Math.sqrt(denominatorB);
        double similarityScore = (denominator == 0) ? 0 : numerator / denominator;

        return similarityScore;
    }


    public Map<Integer, Map<Integer, Double>> computeSimilarityMatrix(List<Userinfo> userList) {
        // 初始化结果矩阵
        Map<Integer, Map<Integer, Double>> result = new HashMap<>();
        for (Userinfo userA : userList) {
            result.put(Integer.parseInt(userA.getUserId()+""), new HashMap<>());
            for (Userinfo userB : userList) {
                result.get(userA.getId()).put(Integer.parseInt(userB.getUserId()+""), 0.0);
            }
        }
        // 计算相似度矩阵
        for (Userinfo userA : userList) {
            for (Userinfo userB : userList) {
                if (userA.getId() == userB.getId()) {
                    continue;
                }
                double similarity = computeSimilarityScore(userA, userB);
                result.get(userA.getId()).put(Integer.parseInt(userB.getUserId()+""), similarity);
            }
        }
        return result;
    }



    public FoodRecommendation getRecommendation(Userinfo targetUser, int numRecipes) {
        // 找到与目标用户体重相似的用户
        List<Userinfo> similarUsers = findSimilarUsers(targetUser, 5);

        // 计算相似度矩阵
        Map<Integer, Map<Integer, Double>> similarityMatrix = computeSimilarityMatrix(similarUsers);

        // 获取用户i兴趣评分最高的前K个运动项目
        // 基于相似度矩阵进行推荐
        Map<String, Double> scores = new HashMap<>();
        Map<String, Double> weights = new HashMap<>();
        for (Userinfo user : similarUsers) {
            for (Integer key : similarityMatrix.keySet()) {
                String food=foodService.getById(key).getFoodName();
                if (!similarityMatrix.keySet().toString().contains(food)) {
                    double score = similarityMatrix.get(user.getUserId()).get(targetUser.getUserId());
                    scores.put(food, scores.getOrDefault(food, 0.0) + score);
                    weights.put(food, weights.getOrDefault(food, 0.0) + 1.0);
                }
            }
        }

        // 计算得分
        List<Map.Entry<String, Double>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<String> recommendedRecipes = new ArrayList<>();
        for (Map.Entry<String, Double> entry : sortedScores) {
            if (recommendedRecipes.size() >= numRecipes) {
                break;
            }
            String recipe = entry.getKey();
            double score = entry.getValue();
            double weight = weights.get(recipe);
            double weightedScore = score / weight;
            recommendedRecipes.add(recipe);
        }

        return new FoodRecommendation(recommendedRecipes,users);
    }
}


