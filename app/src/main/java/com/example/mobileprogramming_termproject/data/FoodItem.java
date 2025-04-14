package com.example.mobileprogramming_termproject.data;

import androidx.annotation.DrawableRes;

import com.example.mobileprogramming_termproject.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class FoodItem {
    /*
        각 아이템 클래스
     */
    private String name;
    private Calendar expirationDate;
    private int imageSrc;
    private FoodCategory category;
    private String barcode;
    public Boolean isClicked = false;

    public FoodItem(
            String name,
            Calendar expirationDate,
            int imageSrc,
            FoodCategory category,
            String barcode
    ){
        /*
        parameter:
            name,
            expirationDate,
            imageSrc,
            category,
            barcode,
        */
        this.name = name.isEmpty()? "Unknown" : name;
        this.expirationDate = expirationDate;
        this.imageSrc = imageSrc;
        this.category = category;
        this.barcode = barcode.isEmpty()? "00000000" : barcode;
    }
    public FoodItem(){
        /*
        parameter:

        */
        this(
                "",
                new GregorianCalendar(2000,1,1),
                R.drawable.ic_launcher_background,
                FoodCategory.UNKNWON,
                ""
        );
    }

    public FoodItem(String name){
        /*
        parameter:
            name,
        */
        this(
                name,
                new GregorianCalendar(2000,1,1),
                R.drawable.ic_launcher_background,
                FoodCategory.UNKNWON,
                ""
        );
    }

    public FoodItem(String name, Calendar date, FoodCategory category){
        /*
        parameter:
            name,
            expirationDate,

            category,

        */
        this(
                name,
                date,
                R.drawable.ic_launcher_background,
                category,
                ""
        );
    }

    public String getName(){
        return name;
    }
    public Calendar getCalendar(){
        return expirationDate;
    }

    public int getImageSrc(){
        return imageSrc;
    }

    public FoodCategory getCategory(){
        return category;
    }

    public String getBarcode(){
        return barcode;
    }
    public void print(){
        /*
            테스트용
         */
        System.out.println("name : " + name);
        System.out.println("expiration date : " + expirationDate.getTime());
        System.out.println("image source id : " + imageSrc);
        System.out.println("Category : " + category);
        System.out.println("barcode id : " + barcode);
    }
}
