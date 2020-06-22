package com.example.test;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SignupActivity extends AppCompatActivity {

    Button button_back, button_checkbox, button_join, button_check;
    EditText id, name;
    Socket member_socket;
    boolean isConnect;
    boolean isRunning;
    //선택 항목 표시
    String[] items = {"북문", "동문", "정문", "쪽문", "서문"};
    boolean[] state = {false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toast.makeText(SignupActivity.this, "가입창", Toast.LENGTH_SHORT).show();

        //back button - go to login Activity
        button_back = (Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //door check box - show dialog
        button_checkbox = (Button)findViewById(R.id.button_checkboxDoor);
        button_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiChoiceDialog();
            }
        });

        //sign up button - pass to DB, go to login Activity
        button_join = (Button)findViewById(R.id.button_join);
        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("가입 버튼 눌림");
                //btnMethod(button_join);

                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //check button - check ID
        button_check = (Button)findViewById(R.id.button_checkAvail);
        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 중복체크됨.
                if(IDcheckAvail() == 1) {
                    Toast.makeText(SignupActivity.this, "아이디 중복!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "아이디 사용 가능!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void showMultiChoiceDialog(){
        //문을 선택하는 custom dialog 창 생성
        final boolean[] backup = new boolean[state.length];
        System.arraycopy(state, 0, backup, 0, state.length);

        boolean[] test = state;

        for(int j=0; j<test.length; j++){
            Log.d("test", String.valueOf(test[j]));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

        builder.setTitle("자주 방문하는 문을 하나 선택하세요.");
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setMultiChoiceItems(items, state, new DialogInterface.OnMultiChoiceClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                state[i] = isChecked;
                String msg = items[i];
                msg += isChecked ? " 체크됨" : " 체크안됨";
                Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = "";
                for(int i=0; i<items.length; i++){
                    if(state[i]){
                        result += items[i] + " ";
                    }
                }
                Toast.makeText(SignupActivity.this, result, Toast.LENGTH_SHORT).show();
                //textView.setText(result);
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener(){
            @Override
            public void onCancel(DialogInterface dialog) {
                System.arraycopy(backup, 0, state, 0, state.length);
            }
        });

        builder.create();
        builder.show();
    }


    void passInfoToDB(){
        //가입 버튼 누를시, 입력된 정보들 DB로 넘김
        if(IDcheckAvail() == 1){

        }
    }

    int IDcheckAvail(){
        //아이디 중복체크

        return 1;
    }

}

