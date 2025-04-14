package com.example.mobileprogramming_termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobileprogramming_termproject.data.FoodItem;
import com.example.mobileprogramming_termproject.data.FoodList;
import java.text.SimpleDateFormat;

public class FoodItemAdapter extends BaseAdapter {
    /*
        adapter_item.xml 에 맞춘 어댑터 클래스
        참고 블로그 : https://kumgo1d.tistory.com/entry/Android-Custom-ListView-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BB%A4%EC%8A%A4%ED%85%80-%EB%A6%AC%EC%8A%A4%ED%8A%B8%EB%B7%B0-%EB%A7%8C%EB%93%A4%EA%B8%B0
     */
    public FoodList foodList = new FoodList();
    public Context context;

    @Override
    public int getCount() {
        /*
            adapter에 있는 아이템 수 반환
         */
        return foodList.getNumOfFoods();
    }

    @Override
    public Object getItem(int idx) {
        /*
            adapter의 idx 번째 아이템 반환
         */
        return foodList.getFoodItem(idx);
    }

    @Override
    public long getItemId(int idx) {
        return idx;
    }

    @Override
    public View getView(int idx, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        FoodItem foodItem = foodList.getFoodItem(idx);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_item, viewGroup, false);
        }

        ImageView image = (ImageView) view.findViewById(R.id.item_image);
        TextView date = view.findViewById(R.id.item_date);
        TextView name = view.findViewById(R.id.item_name);
        TextView bacode = view.findViewById(R.id.item_bacode);
        TextView category = view.findViewById(R.id.item_category);
        /*
            adapter_item.xml 의 각 레이아웃 할당
         */


        image.setImageResource(foodItem.getImageSrc());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        date.setText(dateFormat.format(foodItem.getCalendar().getTime()));
        name.setText(foodItem.getName());
        bacode.setText(foodItem.getBarcode());
        category.setText(foodItem.getCategory().toString());
        /*
            adapter_item.xml 의 각 레이아웃에 값 할당
         */

        return view;
    }
    public void addItem(FoodItem foodItem){
        /*
            FoodItem 추가
         */
        foodList.addNewFood(foodItem);
    }

    public Boolean removeItem(){
        /*
            isClicked == false 인 아이템 제거
            return 1 -> 제거 완료
                    2 -> 선택된 아이템 X인 경우
         */
        Boolean somethingHappen = false;
        for (int i = foodList.getNumOfFoods() - 1; i >= 0 ; i--){
            if(foodList.getFoodItem(i).isClicked){
                foodList.removeFoodItem_Idx(i);
                somethingHappen = true;
            }
        }
        return somethingHappen;
    }

    public Boolean getItemClicked(int idx){
        /*
            adapter의 idx번째 FoodItem의 isClicked 값 반환
         */
        return foodList.getFoodItem(idx).isClicked;
    }

    public void setItemClicked(int idx, Boolean state){
        /*
            adapter의 idx번째 FoodItem의 isClicked 값을 state로 설정
         */
        foodList.getFoodItem(idx).isClicked = state;
    }

    public void setItemClickedAll(Boolean state){
        /*
            adapter의 모든 FoodItem의 isClicked 값을 state로 설정
         */
        for (int i = 0; i < foodList.getNumOfFoods(); i++){
            setItemClicked(i,state);
        }
    }
}
