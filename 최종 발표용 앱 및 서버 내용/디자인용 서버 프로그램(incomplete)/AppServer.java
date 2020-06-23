/* UTF-8 */

package server;

import java.net.*;
import java.io.*;
import java.sql.*;

public class AppServer {
	
	public ServerSocket serverSocket = null;
	public Socket clientSocket = null;
	
	public BufferedReader clientRequest;
	public ObjectOutputStream outStream;
	public DBQuery DB;
	public String requestLine;
	public ResultSet result;
	public boolean login;
	
	public void connect() throws IOException {		
		try {
			serverSocket = new ServerSocket(30000);
			
		} catch (IOException e) {
			System.err.println("다음의 포트 번호에 연결할 수 없습니다: 30000");
			System.exit(1);
		}
		
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("accept() 실패");
			System.exit(1);
		}
		
		InputStream is = clientSocket.getInputStream();
		OutputStream os= clientSocket.getOutputStream();
		DataInputStream dis=new DataInputStream(is);
		DataOutputStream dos=new DataOutputStream(os);
		
		//닉네임(학번), 이름 수신
		String nickName=dis.readUTF();
		String usrName=dis.readUTF();
		System.out.println("read //" + nickName + usrName);
		//가입된 닉네임인지 DB에서 확인 후 true false 설정
		this.login = true; //이 부분 수정하면됨! 
		
		if(this.login == true)//로그인 성공
			dos.writeUTF(nickName+" 님 환영합니다.");
		else	//로그인 실패
			dos.writeUTF("Login info error");
		
		/*
		clientRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outStream = new ObjectOutputStream(clientSocket.getOutputStream());
		DB = new DBQuery();
		
		while((requestLine = clientRequest.readLine()) != null) {
			result = DB.DataQuery(requestLine);
			outStream.writeObject(result);
			outStream.flush();
		}*/
		
		outStream.close();
		clientRequest.close();
		clientSocket.close();
		serverSocket.close();
	}
	
	public AppServer() {
		try {
			this.connect();
		} catch (IOException e) {
			System.err.println("서버 생성 실패");
			System.exit(1);
		}
	}
}
