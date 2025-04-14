package com.example.mobileprogramming_termproject.data;

import java.util.Locale;

public enum FoodCategory {
    UNKNWON, SNACK, DRINK;

    public static FoodCategory fromString(String insertedStr){
        /*
            입력된 문자열을 enum 으로 반환.
            enum에 포함 x -> UNKNOWN (기본값)
         */
        try {
            return FoodCategory.valueOf(insertedStr.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e){
            return FoodCategory.UNKNWON;
        } catch (Exception e){
            return FoodCategory.UNKNWON;
        }
    }

}
