package com.example.mobileprogramming_termproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mobileprogramming_termproject.data.FoodCategory;
import com.example.mobileprogramming_termproject.data.FoodItem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListTestActivity extends AppCompatActivity {

    private ListView listView; //아이템 목록 나타낼 레이아웃
    private FoodItemAdapter adapter; //
    private LinearLayout selectedItem; //선택된 아이템의 레이아웃
    private Button addBtn, removeBtn;
    /*
        addBtn : 아이템 추가 버튼,
        removeBtn : 아이템 제거 버튼 (선택된 아이템)
    */
    private EditText inputName, inputDate, inputCategory;
    /*
        각 EditText 레이아웃
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtest);

        eachLayoutSet();
    }

    protected void eachLayoutSet(){
        /*
        각 레이아웃 초기 설정 함수
         */
        listView = (ListView) findViewById(R.id.listView);

        addBtn = (Button) findViewById(R.id.addButton);
        removeBtn = (Button) findViewById(R.id.deleteButton);

        inputName = (EditText) findViewById(R.id.name_input);
        inputDate = (EditText) findViewById(R.id.date_input);
        inputCategory = (EditText) findViewById(R.id.category_input);

        adapter = new FoodItemAdapter();
        adapter.addItem(new FoodItem());
        adapter.addItem(new FoodItem());
        /*
            테스트 용 아이템 추가
         */
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*
                아이템 목록의 각 아이템 클릭 시 처리 함수.
                    -> 선택한 아이템의 배경 색 변경
                    -> 선택된 아이템의 isClicked(Boolean) 값 변경
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int idx, long l) {
                selectedItem = view.findViewById(R.id.selected_item);

                if(adapter.getItemClicked(idx)){
                    /*
                        클릭한 아이템 항목이 이미 선택된 경우,
                     */
                    adapter.setItemClicked(idx,false);
                    selectedItem.setBackgroundColor(Color.parseColor("#330000BB"));

                } else{
                    /*
                        아이템을 선택한 경우,
                     */
                    adapter.setItemClicked(idx,true);
                    selectedItem.setBackgroundColor(Color.parseColor("#FFCC00"));
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            /*
                각 EditText에 입력한 값을 반영하여 아이템을 추가한 후, 레이아웃 업데이트
             */
            @Override
            public void onClick(View view) {
                String returnName = inputName.getText().toString();
                Calendar returnDate = chagneStringToCalendar(inputDate.getText().toString());
                if(returnDate == null){
                    Toast.makeText(getApplicationContext(), "Date is wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }
                FoodCategory returnCategory = FoodCategory.fromString(inputCategory.getText().toString());
                /*
                    returnName : String -> (EditText)inputName 에 입력된 텍스트
                    returnDate : Calendar -> (EditText)inputDate 에 입력된 날짜
                    returnCategory : FoodCategory -> (EditText)inputCategory 에 입력된 카테고리
                 */

                adapter.addItem(new FoodItem(returnName, returnDate, returnCategory));
                listView.setAdapter(adapter);
                /*
                    입력된 정보를 가진 아이템 추가 후 리스트 업데이트
                 */

                adapter.setItemClickedAll(false);   //선택되어 있던 아이템 선택 초기화
                setEditText("","","");  //각 EditText 에 입력된 값 초기화
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            /*
                선택된 아이템 삭제.
             */
            @Override
            public void onClick(View view) {
                if(adapter.removeItem()){
                    /*
                        adapter.removeItem() : Boolean -> 선택된 아이템 삭제 진행,
                            삭제가 성공적 -> true
                                        -> false
                     */
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "There is empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void setEditText(String name, String date, String category){
        /*
            각 (EditText)의 값 입력한 매개변수로 설정
         */
        inputName.setText(name); inputDate.setText(date); inputCategory.setText(category);
    }

    public Calendar chagneStringToCalendar(String insertedDate){
        try {
            if(insertedDate.isEmpty() || insertedDate.length() != "yyyyMMdd".length()){
                return null;
            }
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            Date returnDate = dataFormat.parse(insertedDate);

            Calendar returnCalendar = Calendar.getInstance();
            returnCalendar.setTime(returnDate);

            return returnCalendar;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}