/* UTF-8 */

package server;

import java.sql.*;
import java.io.*;

public class DBQuery implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	public static Connection DBConnect() {
		String url = "jdbc:mysql://localhost?serverTimezone=UTC";
		String id = "root";
		String password = "dltjgus07_";
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 적재 성공");
			con = DriverManager.getConnection(url, id, password);
			System.out.println("데이터베이스 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}
		
		return con;
	}
	
	public ResultSet DataQuery(String request){
		ResultSet rs = null;
		Connection con = null;
		Statement st = null;
		
		try {
			con = DBQuery.DBConnect();
			
			st = con.createStatement();
			
			st.executeQuery("USE APP");
			rs = st.executeQuery(request);
			System.out.println(request);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return rs;
	}
	
	public DBQuery() {
		
	}
}
