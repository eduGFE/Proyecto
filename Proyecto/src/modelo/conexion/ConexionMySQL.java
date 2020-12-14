package modelo.conexion;

import java.sql.*;

/**
 * Clase que permite conectar con la base de datos
 *
 */
public class ConexionMySQL {
	static String bd = "PRACTICA_1";
	static String login = "root";
	static String password = "";
	static String url = "jdbc:mysql://localhost/" + bd;

	Connection conexion = null;

	/** Constructor de DbConnection */
	public ConexionMySQL() {
		try {
			// obtenemos el driver de para mysql
			Class.forName("com.mysql.jdbc.Driver");
			// obtenemos la conexión
			conexion = DriverManager.getConnection(url, login, password);

			if (conexion != null) {
				System.out.println("Conección a base de datos " + bd + " OK");
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/** Permite retornar la conexión */
	public Connection getConnection() {
		return conexion ;
	}

	public void desconectar() {
		 conexion  = null;
	}

}