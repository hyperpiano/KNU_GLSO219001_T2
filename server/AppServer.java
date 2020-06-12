/* UTF-8 */

package server;

import java.net.*;
import java.io.*;
import java.sql.*;

public class AppServer {
	
	public final static String select = "SELECT";
	public final static String delete = "DELETE";
	public final static String update = "UPDATE";
	
	public ServerSocket serverSocket = null;
	public Socket clientSocket = null;
	
	public BufferedReader clientRequest;
	public ObjectOutputStream outStream;
	public String requestLine;
	public ResultSet result;
	
	public void connect() throws IOException {		
		try {
			serverSocket = new ServerSocket(5555);
			
		} catch (IOException e) {
			System.err.println("다음의 포트 번호에 연결할 수 없습니다: 5555");
			System.exit(1);
		}
		
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("accept() 실패");
			System.exit(1);
		}
		
		clientRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outStream = new ObjectOutputStream(clientSocket.getOutputStream());
		
		while((requestLine = clientRequest.readLine()) != null) {
			if(requestLine.indexOf(select) != -1) {
				result = DBQuery.selectData(requestLine);
			}
			else if (requestLine.indexOf(delete) != -1) {
				result = DBQuery.deleteData(requestLine);
			}
			else if (requestLine.indexOf(update) != -1) {
				result = DBQuery.updateData(requestLine);
			}
			else {
				result = null;
			}
			outStream.writeObject(result);
			outStream.flush();
		}
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
