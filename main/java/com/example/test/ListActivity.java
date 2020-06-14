package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class ListActivity extends AppCompatActivity {
    View dialogView;
    String reName, reInfo, reImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toast.makeText(getApplicationContext(), "식당 리스트", Toast.LENGTH_SHORT).show();

        //추천 dialog view
        recommend();
        showDialog();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
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

}
