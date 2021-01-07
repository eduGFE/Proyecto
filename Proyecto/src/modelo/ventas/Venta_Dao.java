package modelo.ventas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;

//Clase que contendra las sentancias SQL necesarias para llevar la gestion de las ventas
public class Venta_Dao{

	public static boolean insertaVenta(Venta_Dto ventas, String tipoConexion){
		boolean seInserta=false;
		Conexion conexionEstablecida = new Conexion(tipoConexion);
		if(existeIDCliente(conexionEstablecida, ventas.getIdCliente()) && existeIDProducto(conexionEstablecida, ventas.getIdProducto())) {
			try {
				String sentenciaInsertarVenta = "INSERT INTO ventas VALUES(?,?,?,?,?)";
				PreparedStatement insertaVenta=conexionEstablecida.getConexion().prepareStatement(sentenciaInsertarVenta);
				insertaVenta.setInt(1, ventas.getIdVenta());
				insertaVenta.setDate(2, ventas.getFechaVenta());
				insertaVenta.setInt(3, ventas.getIdCliente());
				insertaVenta.setInt(4, ventas.getIdProducto());
				insertaVenta.setInt(5, ventas.getCantidad());
				insertaVenta.executeUpdate();
				seInserta=true;
				conexionEstablecida.getConexion().close();
			} catch (SQLException e) {
				System.out.println("No se ha podido conectar con la base de datos");
			}
		}else {
			seInserta=false;
		}
		
		return seInserta;

	}

	public static boolean borraVentaPorID(Connection conexionEstablecida, int idVenta) {
		boolean seHaBorrado=false;
		if(existeIDVenta(conexionEstablecida, idVenta)) {
			try {
				String sentenciaBorrarVenta = "DELETE FROM ventas WHERE idVenta = ?";
				PreparedStatement borrarVenta = conexionEstablecida.prepareStatement(sentenciaBorrarVenta);
				borrarVenta.setInt(1, idVenta);
				borrarVenta.executeUpdate();
				seHaBorrado=true;
			}catch(SQLException e) {
				System.out.println("No se ha podido conectar con la base de datos");
			}
		}else{
			seHaBorrado = false;
		}
		return seHaBorrado;
	}

	public static boolean borraVentaPorIntervalo(Connection conexionEstablecida, Date fechaMinima, Date fechaMaxima) {
		boolean seHaBorrado = false;
		String sentenciaBorrarVentaIntervalo = "DELETE FROM ventas WHERE fecha BETWEEN ? AND ?";
		try {
			PreparedStatement borrarVentaIntervalo = conexionEstablecida.prepareStatement(sentenciaBorrarVentaIntervalo);
			borrarVentaIntervalo.setDate(1, fechaMinima);
			borrarVentaIntervalo.setDate(2, fechaMaxima);
			borrarVentaIntervalo.executeUpdate();
		} catch (SQLException e) {
			System.out.println("No se ha podido conectar con la base de datos");
		}
		
		return seHaBorrado;
		
	}
	
	public static boolean borrarVentaPorNIF(Connection conexionEstablecida, String nif) {
		boolean seHaBorrado=false;
		String sentenciaBorrarVentaNIF = "DELETE FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = ?)";
		try {
			PreparedStatement borrarVentaNif=conexionEstablecida.prepareStatement(sentenciaBorrarVentaNIF);
			borrarVentaNif.setString(1, nif);
			borrarVentaNif.executeUpdate();
			
			
		} catch (SQLException e) {
			
			System.out.println("No se ha podido conectar con la base de datos");
		}
		return seHaBorrado;
	}
	
	//auxiliares
	private static Boolean existeIDCliente(Conexion conexionEstablecida, int idCliente){
		boolean existeID=false;
		//1º declaro string con la consulta
		String consultaMuestraClientes= "SELECT id FROM CLIENTES";
		try {
			//2º instancio un objeto statement donde almacenaré la consulta.
			Statement muestraClientes = conexionEstablecida.getConexion().createStatement();
			//3º instancio un resultSet para almacenar el resultSet que devuelve la consulta.
			ResultSet resultSetClientes;
			//4º ejecuto la consulta y almaceno el resultSet
			resultSetClientes=muestraClientes.executeQuery(consultaMuestraClientes);
			while(resultSetClientes.next()&&existeID==false) {
				if(idCliente==resultSetClientes.getInt(1)) {
					existeID=true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos");
		}
		return existeID;
	}

	private static Boolean existeIDProducto(Conexion conexionEstablecida, int idProducto){
		boolean existeID=false;
		//1º declaro string con la consulta
		String consultaMuestraProductos= "SELECT id FROM CLIENTES";
		try {
			//2º instancio un objeto statement donde almacenaré la consulta.
			Statement muestraProductos = conexionEstablecida.getConexion().createStatement();
			//3º instancio un resultSet para almacenar el resultSet que devuelve la consulta.
			ResultSet resultSetProductos;
			//4º ejecuto la consulta y almaceno el resultSet
			resultSetProductos=muestraProductos.executeQuery(consultaMuestraProductos);
			while(resultSetProductos.next()&&existeID==false) {
				if(idProducto==resultSetProductos.getInt(1)) {
					existeID=true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos");
		}
		return existeID;
	}

	private static Boolean existeIDVenta(Connection conexion, int idVenta){
		boolean existeID=false;
		//1º declaro string con la consulta
		String consultaMuestraVentas= "SELECT id FROM ventas";
		try {
			//2º instancio un objeto statement donde almacenaré la consulta.
			Statement muestraVentas = conexion.createStatement();
			//3º instancio un resultSet para almacenar el resultSet que devuelve la consulta.
			ResultSet resultSetVentas;
			//4º ejecuto la consulta y almaceno el resultSet
			resultSetVentas=muestraVentas.executeQuery(consultaMuestraVentas);
			while(resultSetVentas.next()&&existeID==false) {
				if(idVenta==resultSetVentas.getInt(1)) {
					existeID=true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos");
		}
		return existeID;
	}

}

