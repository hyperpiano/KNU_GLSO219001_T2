package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText login_id, login_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toast.makeText(getApplicationContext(), "Login 화면", Toast.LENGTH_SHORT).show(); //확인용 토스트

        login_id = (EditText) findViewById(R.id.editText_id);
        login_name = (EditText) findViewById(R.id.editText_name);

        //가입 페이지로 전환
        Button button_join = (Button) findViewById(R.id.button_join);
        button_join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        //login button - check id, name with DB, go to List Activity
        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkLoginInfo();

                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void checkLoginInfo(){
        //check login Info with DB
        Toast.makeText(getApplicationContext(), "로그인 정보 확인함.", Toast.LENGTH_SHORT).show(); //확인용 토스트

    }
}
