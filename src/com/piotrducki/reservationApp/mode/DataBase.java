package com.piotrducki.reservationApp.mode;


import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//import com.mysql.jdbc.Connection;
public class DataBase
{

	private Connection conn;
	private static DataBase instance;
	private Statement statement;

	private DataBase()
	{
		String url = "jdbc:mysql://127.0.0.1:3306/Kino?useSSL=false";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "2997";
		try
		{
			Class.forName(driver).newInstance();
			this.conn = (Connection) DriverManager.getConnection(url, userName, password);
		} catch (Exception sqle)
		{
			sqle.printStackTrace();
		}
	}

	public static DataBase getInstance()
	{
		if (instance == null)
		{
			instance = new DataBase();
		}
		return instance;
	}

	public ResultSet query(String query) throws SQLException
	{
		statement = instance.conn.createStatement();
		ResultSet res = statement.executeQuery(query);
		return res;
	}
	
	public int insert(String insertQuery) throws SQLException
	{
		statement = instance.conn.createStatement();
		int result = statement.executeUpdate(insertQuery);
		return result;
	}
}
