/* UTF-8 */

package server;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class AppServer {
	
	private ServerSocket server;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new AppServer();
	}	
    //메인메소드가 static으로 되어있기 때문에 다른것들을 다 static 으로 하기 귀찮기 때문에
	// 따로 생성자를 만들어서 진행 - > 메인에서는 호출정도의 기능만 구현하는게 좋다.
	public AppServer() {
		try {
			// 서버 가동
			server=new ServerSocket(30000);
			// 사용자 접속 대기 스레드 가동
			ConnectionThread thread= new ConnectionThread();
			thread.start();
		}catch(Exception e) {e.printStackTrace();}
	}
	
	// 사용자 접속 대기를 처리하는 스레드 클래스
	class ConnectionThread extends Thread{
				
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true) {
					System.out.println("사용자 접속 대기");
					Socket socket=server.accept();
					System.out.println("사용자가 접속하였습니다.");
					// 사용자 닉네임을 처리하는 스레드 가동
					NickNameThread thread=new NickNameThread(socket);
					thread.start();
					
						
				}
			}catch(Exception e) {e.printStackTrace();}
		}
	}
		
	// 닉네임 입력처리 스레드
	class NickNameThread extends Thread{
		private Socket socket;
		boolean login = false;
		
		public NickNameThread(Socket socket) {
			this.socket=socket;
		}
		public void run() {
			try {
				// 스트림 추출
				InputStream is = socket.getInputStream();
				OutputStream os= socket.getOutputStream();
				DataInputStream dis=new DataInputStream(is);
				DataOutputStream dos=new DataOutputStream(os);
				
				//닉네임(학번), 이름 수신
				String nickName=dis.readUTF();
				String usrName=dis.readUTF();
				System.out.println("read //" + nickName + usrName);
				//가입된 닉네임인지 DB에서 확인 후 true false 설정
				login = true; //이 부분 수정하면됨! 
				
				if(login == true)//로그인 성공
					dos.writeUTF(nickName+" 님 환영합니다.");
				else	//로그인 실패
					dos.writeUTF("Login info error");
				
				
			}catch(Exception e) {e.printStackTrace();}
		}
	}
		
	
	
	
}