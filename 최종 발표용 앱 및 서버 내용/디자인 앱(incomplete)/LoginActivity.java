package com.example.myapplication1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    EditText login_id, login_name;
    Button button_join, button_login;

    // 서버 접속 여부를 판별하기 위한 변수
    boolean isConnect = false;
    ProgressDialog pro;
    // 어플 종료시 스레드 중지를 위해...
    boolean isRunning = false;
    // 서버와 연결되어있는 소켓 객체
    Socket member_socket;
    // 사용자 닉네임
    String user_id;
    // 로그인 성공 여부
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toast.makeText(getApplicationContext(), "Login 화면", Toast.LENGTH_SHORT).show(); //확인용 토스트

        login_id = (EditText) findViewById(R.id.editText_id);
        login_name = (EditText) findViewById(R.id.editText_name);

        //가입 페이지로 전환
        button_join = (Button) findViewById(R.id.button_join);
        button_join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        //login button - check id, name with DB, go to List Activity
        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("로그인 버튼 눌림");
                btnMethod(button_login);
            }
        });
    }

    // 버튼과 연결된 메소드
    public void btnMethod(View v) {
        System.out.println("버튼 메소드 실행");
        if (isConnect == false) {   //접속전
            //사용자가 입력한 닉네임을 받는다.
            String nickName = login_id.getText().toString();
            System.out.println("접속 전 //" + nickName);///
            if (nickName.length() > 0 && nickName != null) {
                //서버에 접속한다.
                pro = ProgressDialog.show(this, null, "접속중입니다");
                // 접속 스레드 가동
                ConnectionThread thread = new ConnectionThread();
                thread.start();

            }
            // 닉네임이 입력되지않을경우 다이얼로그창 띄운다.
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("닉네임을 입력해주세요");
                builder.setPositiveButton("확인", null);
                builder.show();
            }

        } else {                  // 접속 후
            // 입력한 문자열을 가져온다.
            String msg = login_id.getText().toString();
            System.out.println("접속완료//" + msg);
            // 송신 스레드 가동
            SendToServerThread thread = new SendToServerThread(member_socket, msg);
            thread.start();
        }
    }

    // 서버접속 처리하는 스레드 클래스 - 안드로이드에서 네트워크 관련 동작은 항상
    // 메인스레드가 아닌 스레드에서 처리해야 한다.
    class ConnectionThread extends Thread {
        @Override
        public void run() {
            try {
                // 접속한다.
                final Socket socket = new Socket("192.168.0.8", 30000);
                member_socket = socket;
                System.out.println("소켓 생성 " +  member_socket);
                // 미리 입력했던 닉네임을 서버로 전달한다.
                String nickName = login_id.getText().toString();
                String usrName = login_name.getText().toString();
                user_id = nickName;
                // 스트림을 추출
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                // 닉네임을 송신한다.
                dos.writeUTF(nickName);
                dos.writeUTF(usrName);
                System.out.println("학번, 이름 서버로 보냄 //" + nickName +  usrName);
                // ProgressDialog 를 제거한다.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pro.dismiss();
                        // 접속 상태를 true로 셋팅한다.
                        isConnect = true;
                        // 메세지 수신을 위한 스레드 가동
                        isRunning = true;
                        System.out.println("접속완료, 수신 스레드 가동//");
                        MessageThread thread = new MessageThread(socket);
                        thread.start();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MessageThread extends Thread {
        Socket socket;
        DataInputStream dis;

        public MessageThread(Socket socket) {
            try {
                this.socket = socket;
                InputStream is = socket.getInputStream();
                dis = new DataInputStream(is);
                System.out.println("inputStream 수신 스트림 생성//");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    // 서버로부터 데이터를 수신받는다.
                    final String msg = dis.readUTF();
                    System.out.println("서버로부터 수신된 데이터는 //" + msg);
                    // 화면에 출력
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 메세지의 시작 이름이 내 닉네임과 일치한다면
                            if (msg.startsWith(user_id)) {
                                isLogin = true;
                                System.out.println("시작 이름과 일치");
                                //check login Info with DB
                                Toast.makeText(getApplicationContext(), "로그인 정보 확인함.", Toast.LENGTH_SHORT).show(); //확인용 토스트
                                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 정보 오류", Toast.LENGTH_SHORT).show();
                                System.out.println("시작 이름과 일치X");
                            }

                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 서버에 데이터를 전달하는 스레드
    class SendToServerThread extends Thread {
        Socket socket;
        String msg;
        DataOutputStream dos;

        public SendToServerThread(Socket socket, String msg) {
            try {
                this.socket = socket;
                this.msg = msg;
                OutputStream os = socket.getOutputStream();
                dos = new DataOutputStream(os);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // 서버로 데이터를 보낸다.
                dos.writeUTF(msg);
                System.out.println("서버로 데이터 보냄 //" + msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        login_id.setText("");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            member_socket.close();
            isRunning = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 */
}
