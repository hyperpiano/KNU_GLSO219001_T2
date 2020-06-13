package jdbcTest;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;



public class jdbcTest {

	public static void main(String[] args) {

		try {

			Connection con = null;



			con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC",

					"store_manager", "student2018");



			java.sql.Statement st = null;

			ResultSet rs = null;
			ResultSet a = null;
			st = con.createStatement();
			a= st.executeQuery("use knusw");
			rs = st.executeQuery("SELECT * FROM store");



			if (st.execute("SELECT * FROM store")) {

				rs = st.getResultSet();

			}



			while (rs.next()) {

				String number = rs.getString("_number");
				String name = rs.getString("name");
				String menu = rs.getString("menu");
				
				System.out.println("Number: "+ number + "\nName: " + name + "\nMenu: " + menu); 

			}

		} catch (SQLException sqex) {

			System.out.println("SQLException: " + sqex.getMessage());

			System.out.println("SQLState: " + sqex.getSQLState());

		}



	}

}


