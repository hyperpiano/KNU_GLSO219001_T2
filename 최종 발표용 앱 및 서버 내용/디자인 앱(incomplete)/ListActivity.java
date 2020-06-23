package com.example.myapplication1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    View dialogView;
    String reName, reInfo, reImage;
    ListView listView;
    Context context;
    ArrayList<RestaurantsModel> restaurantsData;
    CustomAdapter customAdapter;
    RestaurantsModel restaurantsModel;
    private Spinner spinner;    //door spinner
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_list);
        Toast.makeText(getApplicationContext(), "식당 리스트", Toast.LENGTH_SHORT).show();

        //추천 dialog view
        recommend();
        showDialog();

        //door spinner
        selectDoor();

        //getviews
        listView = findViewById(R.id.listView);
        restaurantsData = new ArrayList<>();

        //add Restaurants Data
        InfoRestaurantsData();

        customAdapter = new CustomAdapter(context, restaurantsData);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(context,restaurantsData.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    void selectDoor(){
        arrayList = new ArrayList<>();
        arrayList.add("북문");
        arrayList.add("동문");
        arrayList.add("정문/쪽문");
        arrayList.add("서문");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),arrayList.get(i)+"이 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }


    void showDialog(){
        dialogView = (View) View.inflate(ListActivity.this, R.layout.dialog1, null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(ListActivity.this);
        dlg.setView(dialogView);
        dlg.setPositiveButton("보러가기", new DialogInterface.OnClickListener(){
            // go to the recommended restaurant info Activity
            public void onClick(DialogInterface dialog, int which){
                Intent intent = new Intent(ListActivity.this, InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dlg.setNegativeButton("다음에 갈래요", null); //left button
        dlg.show();
    }

    void recommend(){
        //random recommend
        //error!!!!!!!!!!!!!
        /*
        reName = "식당이름";
        reInfo = "#뫄뫄뫄#솨솨솨";
        reImage = "R.drawable.sample";

        TextView tvName, tvInfo;
        tvName = (TextView) findViewById(R.id.tv_Name);
        tvInfo = (TextView) findViewById(R.id.tv_Info);
        tvName.setText(reName);
        tvInfo.setText(reInfo);

        ImageView iv;
        iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageResource(Integer.parseInt(reImage));
        */
    }

    private void InfoRestaurantsData() {
        //식당 1
        restaurantsModel = new RestaurantsModel();
        restaurantsModel.setId(1);
        restaurantsModel.setName("떡볶이잘하는집");
        restaurantsModel.setImage(R.drawable.dduk);
        restaurantsModel.setInfo("떡볶이");
        restaurantsModel.setInfo2("111");
        restaurantsData.add(restaurantsModel);

        //식당 2
        restaurantsModel = new RestaurantsModel();
        restaurantsModel.setId(2);
        restaurantsModel.setName("아이스크림잘하는집");
        restaurantsModel.setImage(R.drawable.br);
        restaurantsModel.setInfo("아이스크림");
        restaurantsModel.setInfo2("222");
        restaurantsData.add(restaurantsModel);

        //식당 3
        restaurantsModel = new RestaurantsModel();
        restaurantsModel.setId(1);
        restaurantsModel.setName("한식잘하는집");
        restaurantsModel.setImage(R.drawable.hansik);
        restaurantsModel.setInfo("한식");
        restaurantsModel.setInfo2("333");
        restaurantsData.add(restaurantsModel);

        //country 4
        restaurantsModel = new RestaurantsModel();
        restaurantsModel.setId(1);
        restaurantsModel.setName("짜파구리잘하는집");
        restaurantsModel.setImage(R.drawable.jjapa);
        restaurantsModel.setInfo("짜파구리");
        restaurantsModel.setInfo2("444");
        restaurantsData.add(restaurantsModel);

        //country 5
        restaurantsModel = new RestaurantsModel();
        restaurantsModel.setId(1);
        restaurantsModel.setName("피자잘하는집");
        restaurantsModel.setImage(R.drawable.pizza);
        restaurantsModel.setInfo("피자");
        restaurantsModel.setInfo2("555");
        restaurantsData.add(restaurantsModel);

        //country 6
        restaurantsModel = new RestaurantsModel();
        restaurantsModel.setId(1);
        restaurantsModel.setName("삼겹살잘하는집");
        restaurantsModel.setImage(R.drawable.sam);
        restaurantsModel.setInfo("삼겹살");
        restaurantsModel.setInfo2("666");
        restaurantsData.add(restaurantsModel);

        //country 7
        restaurantsModel = new RestaurantsModel();
        restaurantsModel.setId(1);
        restaurantsModel.setName("초밥잘하는집");
        restaurantsModel.setImage(R.drawable.sushi);
        restaurantsModel.setInfo("초밥");
        restaurantsModel.setInfo2("777");
        restaurantsData.add(restaurantsModel);

        //country 8
        restaurantsModel = new RestaurantsModel();
        restaurantsModel.setId(1);
        restaurantsModel.setName("호반우");
        restaurantsModel.setImage(R.drawable.ho);
        restaurantsModel.setInfo("그냥");
        restaurantsModel.setInfo2("넣어봄");
        restaurantsData.add(restaurantsModel);




    }
}
