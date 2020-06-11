package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class RecommendActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Button button_look = (Button)findViewById(R.id.button_look);
        button_look.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(RecommendActivity.this, ListActivity.class); //수정 - 세부사항 클래스로 이동
                startActivity(intent);
                finish();
            }
        });

        Button button_goList = (Button)findViewById(R.id.button_nolook);
        button_goList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(RecommendActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}


