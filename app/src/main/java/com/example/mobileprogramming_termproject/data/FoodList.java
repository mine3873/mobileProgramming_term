package com.example.mobileprogramming_termproject.data;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodList {
    private ArrayList<FoodItem> foodList;
    /*
        FoodItem 배열
     */

    private int numOfFoods;

    public FoodList(
            ArrayList<FoodItem> foodList,
            int numOfFoods
    ){
        this.foodList = foodList;
        this.numOfFoods = numOfFoods;
    }

    public FoodList(){
        this(new ArrayList<>(), 0);
    }

    public FoodList(ArrayList<FoodItem> foodList){
        this(foodList, foodList.size());
    }

    public ArrayList<FoodItem> getFoodList(){
        return foodList;
    }


    public FoodItem getFoodItem(int idx){
        /*
            idx 인덱스의 FoodItem 객체 반환
         */
        return foodList.get(idx);
    }

    public int getNumOfFoods(){
        return numOfFoods;
    }

    public void addNewFood(FoodItem foodItem){
        /*
            새로운 FoodItem 객체 추가
         */
        foodList.add(foodItem);
        numOfFoods += 1;
    }

    public Boolean removeFoodItem_Idx(int idx){
        /*
            idx 번째 인덱스의 FoodItem 객체 삭제
         */
        try {
            if(numOfFoods <= 0 || idx >= numOfFoods || idx < 0) {
                /*
                    배열이 빈 경우, 또는 idx가 범위를 벗어난 경우
                 */
                throw new ArrayIndexOutOfBoundsException();
            }
            foodList.remove(idx);
            numOfFoods -= 1;
            return true;
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());
            return false;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
