package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.*;

public class MainActivity extends AppCompatActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    TextView textView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        new Thread(new Runnable(){
            public void run(){
                send();
            }
        }).start();
    }

    public void printClientLog(final String data){
        Log.d("MainActivity", data);
        handler.post(new Runnable(){
            public void run(){
                textView.append(data + "\n");
            }
        });
    }

    public void printClientRestaurant (final ResultSet data) throws SQLException {
        String dataStatement;
        while (data.next()) {
            dataStatement = data.getString("Rname") + ", " + data.getInt("Rnumber") + ","
                    + data.getString("Raddress") + ", " + data.getInt("Rdoor");
            final String dataStr = dataStatement;
            Log.d("MainActivity", dataStr);
            final String finalDataStatement = dataStatement;
            handler.post(new Runnable() {
                public void run() {
                    textView.append("식당");
                    textView.append(finalDataStatement + "\n");
                }
            });
        }
    }
    public void printClientUser(final ResultSet data) throws SQLException {
        String dataStatement;
        while (data.next()) {
            dataStatement = data.getString("Uname") + ", " + data.getInt("Usid") + ","
                    + data.getInt("Ufdoor");
            final String dataStr = dataStatement;
            Log.d("MainActivity", dataStr);
            handler.post(new Runnable() {
                public void run() {
                    textView.append("유저");
                    textView.append(dataStr + "\n");
                }
            });
        }
    }
    public void printClientMenu(final ResultSet data) throws SQLException {
        String dataStatement;
        while (data.next()) {
            dataStatement = data.getInt("Rnum") + "," + data.getString("Mname") + data.getInt("Mprice");
            final String dataStr = dataStatement;
            Log.d("MainActivity", dataStr);
            handler.post(new Runnable() {
                public void run() {
                    textView.append("메뉴");
                    textView.append(dataStr + "\n");
                }
            });
        }
    }
    public void send(){
        try{
            int portNumber = 5050;
            ResultSet rs = null;
            Socket sock = new Socket("192.168.0.16", portNumber);
            printClientLog("소켓 연결함");

            ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
            outstream.writeObject("SELECT * from USER WHERE Usid = 2018000001");
            outstream.flush();
            printClientLog("유저 데이터 요청");

            ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
            printClientLog("서버로부터 받은 데이터");
            rs = (ResultSet)(instream.readObject());
            printClientUser(rs);

            outstream.writeObject("SELECT * from RESTAURANT WHERE Rdoor = 1");
            outstream.flush();
            printClientLog("레스토랑 데이터 요청");

            printClientLog("서버로부터 받은 데이터");
            rs = (ResultSet)(instream.readObject());
            printClientRestaurant(rs);

            outstream.writeObject("SELECT * from RESTAURANT_MENU WHERE Rnum = 1");
            outstream.flush();
            printClientLog("메뉴 데이터 요청");

            printClientLog("서버로부터 받은 데이터");
            rs = (ResultSet)(instream.readObject());
            printClientMenu(rs);

            sock.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}