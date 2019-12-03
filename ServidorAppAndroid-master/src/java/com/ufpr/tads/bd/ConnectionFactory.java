package com.ufpr.tads.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
/*	CONEX�ES
Gabriel:*/
private static final String[] dbConn = {"jdbc:postgresql://localhost:5432/oscar_app", "postgres", "root"};
/*
Gustavo: 
private static final String[] dbConn = {"jdbc:postgresql://localhost:5432/teste", "postgres", "123"};
*/
//private static final String[] dbConn = {"jdbc:postgresql://localhost:5432/teste", "postgres", "123"};

	public static Connection getConnection() {

		try {
			Class.forName("org.postgresql.Driver");
			//Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dbConn[0], dbConn[1], dbConn[2]);
			//return DriverManager.getConnection("jdbc:mysql://localhost:3306/tcc","root","root");
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException (e);
		}
	}
}
