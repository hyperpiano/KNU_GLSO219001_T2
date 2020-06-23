/* UTF-8 */

package server;

import java.net.*;
import java.io.*;
import java.sql.*;

public class AppServer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public ServerSocket serverSocket = null;
	public Socket clientSocket = null;
	
	public ObjectInputStream inStream;
	public ObjectOutputStream outStream;
	public DBQuery DB;
	public String requestLine;
	public ResultSet result;
	
	public void connect() throws IOException {		
		try {
			serverSocket = new ServerSocket(5050);
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		System.out.println("연결 완료");
		
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("accept() 실패");
			System.exit(1);
		}
		
		System.out.println("클라이언트 연결 완료");
		System.out.println(clientSocket);
		
		inStream = new ObjectInputStream(clientSocket.getInputStream());
		outStream = new ObjectOutputStream(clientSocket.getOutputStream());
		DB = new DBQuery();
		
		try {
			Object clientRequest = inStream.readObject();
			
			while((requestLine = (String)clientRequest) != null) {
				System.out.println(requestLine);
				if ("quit".equals(requestLine))
					break;
				
				result = DB.DataQuery(requestLine);
				outStream.writeObject(result);
				outStream.flush();
				
				clientRequest = inStream.readObject();
			} 
		}
		catch (Exception e) {
			System.exit(1);
		}
		outStream.close();
		inStream.close();
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
