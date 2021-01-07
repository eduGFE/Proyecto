package modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	//atributo donde se almacena la conexion creada
	Connection conexion;
	//atributo que determina el tipo de conexion en funcion del argumento que se pase al main
	String tipo;
	public Conexion(String argumento){
		tipo = argumento;
		try {
			//Si es 1 se conecta a MySQL
			if(tipo.equals("1")) {
				Class.forName("com.mysql.jdbc.Driver");
				this.conexion=DriverManager.getConnection("jdbc:mysql://localhost/PRACTICA_1");
				//Si es 2 se conecta a SQLite
			}else if(tipo.equals("2")) {
				Class.forName("org.sqlite.JDBC");
				this.conexion=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\vlagu\\Desktop\\sqlite-tools-win32-x86-3330000"
						+ "\\sqlite-tools-win32-x86-3330000\\sqlite-tools-win32-x86-3330000\\PRACTICA_1");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver");
		} catch (SQLException e) {
			System.out.println("No se ha podido acceder a la base de datos.");
		}
	}
	
	public Connection getConexion() {
		return conexion;
	}
	
	
}
