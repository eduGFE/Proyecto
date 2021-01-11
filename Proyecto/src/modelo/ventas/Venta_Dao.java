package modelo.ventas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import modelo.conexion.Conexion;

//Clase que contendra las sentancias SQL necesarias para llevar la gestion de las ventas
public class Venta_Dao{

	public boolean insertaVenta(Venta_Dto ventas, String tipoConexion){
		//La variable que devuelve el metodo en funcion de si se inserta o no el registro
		boolean seInserta=false;
		//Objeto tipo java date que se crea con el parametro vacío, de manera que almacena la fecha actual del sistema en milisegundos
		java.util.Date fechaActual = new java.util.Date();
		//Objeto tipo sql date que almacenará la fecha para introducirla a la base de datos. Se le pasa por parametro la fecha actual del sitema mediante el metodo .getTime()
		java.sql.Date fechaActualSQL = new java.sql.Date(fechaActual.getTime());

		Conexion conex = new Conexion(tipoConexion);
		if(existeIDCliente(ventas.getIdCliente(),tipoConexion) && existeIDProducto(ventas.getIdProducto(), tipoConexion)) {
			try {
				String sentenciaInsertarVenta = "INSERT INTO ventas VALUES(null,?,?,?,?)";
				PreparedStatement insertaVenta=conex.getConexion().prepareStatement(sentenciaInsertarVenta);
				//el id se autoincrementa en la base de datos y la fecha se pone automaticamente con la funcion CURRENT_DATE() de MySQL para introducir la fecha actual
				insertaVenta.setDate(1, fechaActualSQL);
				insertaVenta.setInt(2, ventas.getIdCliente());
				insertaVenta.setInt(3, ventas.getIdProducto());
				insertaVenta.setInt(4, ventas.getCantidad());
				insertaVenta.executeUpdate();
				seInserta=true;
			} catch (SQLException e) {
				System.out.println("No se ha podido conectar con la base de datos. (Al intentar introducir un registro de venta)");
			}
		}else if(!existeIDCliente(ventas.getIdCliente(), tipoConexion) && !existeIDProducto(ventas.getIdProducto(), tipoConexion)){
			JOptionPane.showMessageDialog(null, "No existen ni la ID del Cliente ni la ID del producto.");
			seInserta=false;
		}else if(!existeIDCliente(ventas.getIdCliente(), tipoConexion)){
			JOptionPane.showMessageDialog(null, "No existe la ID del Cliente.");
			seInserta=false;
		}else if(!existeIDProducto(ventas.getIdProducto(), tipoConexion)){
			JOptionPane.showMessageDialog(null, "No existe la ID del Producto.");
			seInserta=false;
		}
		conex.desconectar();
		return seInserta;

	}

	public boolean borraVentaPorID(int idVenta, String tipoConexion) {
		boolean seHaBorrado=false;
		Conexion conex = new Conexion(tipoConexion);
		if(existeIDVenta(idVenta, tipoConexion)) {
			try {
				String sentenciaBorrarVenta = "DELETE FROM ventas WHERE idVenta = ?";
				PreparedStatement borrarVenta = conex.getConexion().prepareStatement(sentenciaBorrarVenta);
				borrarVenta.setInt(1, idVenta);
				borrarVenta.executeUpdate();
				seHaBorrado=true;
			}catch(SQLException e) {
				JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos");
			}
		}else{
			JOptionPane.showMessageDialog(null, "La id de venta no existe en la base de datos");
			seHaBorrado = false;
		}
		conex.desconectar();
		return seHaBorrado;
	}

	public boolean borrarVentaPorNIF(String nifCliente, String tipoConexion) {
		boolean seBorra=false;
		Conexion conex = new Conexion(tipoConexion);
		if(existeNIFCliente(nifCliente, tipoConexion)) {
			String sentenciaBorrarVentaNIF = "DELETE FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = ?)";
			try {
				PreparedStatement borrarVentaNif=conex.getConexion().prepareStatement(sentenciaBorrarVentaNIF);
				borrarVentaNif.setString(1, nifCliente);
				borrarVentaNif.executeUpdate();
				seBorra=true;
			} catch (SQLException e) {
				System.out.println("No se ha podido conectar con la base de datos (Error al intentar borrar venta por NIF)");
			}
			conex.desconectar();
			seBorra = true;
		}
		return seBorra;
	}

	public ArrayList<Venta_Dto> mostrarVentasPorNIF(String nifCliente, String tipoConexion) {
		ArrayList<Venta_Dto> listadoVentas = new ArrayList<Venta_Dto>();
		Conexion conex = new Conexion(tipoConexion);
		ResultSet resultado;
		String consultaSQL = "SELECT * FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = ?)";
		try {
			PreparedStatement consultaVentasPorNif = conex.getConexion().prepareStatement(consultaSQL);
			consultaVentasPorNif.setString(1, nifCliente);
			resultado = consultaVentasPorNif.executeQuery();
			while(resultado.next()){
				Venta_Dto venta = new Venta_Dto();
				venta.setIdVenta(resultado.getInt(1));
				venta.setFechaVenta(resultado.getDate(2));
				venta.setIdCliente(resultado.getInt(3));
				venta.setIdProducto(resultado.getInt(4));
				venta.setCantidad(resultado.getInt(5));
				listadoVentas.add(venta);
			}
		} catch (SQLException e) {
			System.out.println("No se ha podido conectar con la base de datos (Error al intentar borrar venta por NIF)");
		}

		return listadoVentas;
	}

	public ArrayList<Venta_Dto> mostrarVentasPorFecha(String fechaMinima, String fechaMaxima, String tipoConexion){          
	
		ArrayList<Venta_Dto> listadoVentas = new ArrayList<Venta_Dto>();
		Conexion conex = new Conexion(tipoConexion);
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet resultado;
		String muestraVentasPorFecha = "SELECT * FROM ventas WHERE fechaventa BETWEEN ? AND ?";
		try {
			java.util.Date fechaMinimaUtil = formatoFecha.parse(fechaMinima);
			java.sql.Date fechaMinimaSQL = new java.sql.Date(fechaMinimaUtil.getTime());

			java.util.Date fechaMaximaUtil = formatoFecha.parse(fechaMaxima);
			java.sql.Date fechaMaximaSQL = new java.sql.Date(fechaMaximaUtil.getTime());

			PreparedStatement sentenciaMuestraVentasPorFecha = conex.getConexion().prepareStatement(muestraVentasPorFecha);
			sentenciaMuestraVentasPorFecha.setDate(1, fechaMinimaSQL);
			sentenciaMuestraVentasPorFecha.setDate(2, fechaMaximaSQL);
			resultado = sentenciaMuestraVentasPorFecha.executeQuery();
			while(resultado.next()) {
				Venta_Dto venta = new Venta_Dto();
				venta.setIdVenta(resultado.getInt(1));
				venta.setFechaVenta(resultado.getDate(2));
				venta.setIdCliente(resultado.getInt(3));
				venta.setIdProducto(resultado.getInt(4));
				venta.setCantidad(resultado.getInt(5));
				listadoVentas.add(venta);
			}
		} catch (ParseException e1) {
			System.out.println("Error al parsear");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido conectar a la base de datos");
		}

		return listadoVentas;
	}
	
	
	
	
	/*
	public boolean exportarXMLporFechas(Date fechaMin, Date fechaMax, String tipoConexion) {					//PENDIENTE

	}
	 
	public boolean exportarXMLporCliente(String nifCliente, String tipoConexion) {								//PENDIENTE
		boolean seExporta=false;
		Conexion conex = new Conexion(tipoConexion);
		Calendar fechaActual = new GregorianCalendar();
		String dia = Integer.toString(fechaActual.get(Calendar.DATE));
		String mes = Integer.toString(fechaActual.get(Calendar.MONTH));
		String ano = Integer.toString(fechaActual.get(Calendar.YEAR));

		return seExporta;
	}
	*/

	public boolean exportarCSVporCliente(String nifCliente, String tipoConexion){
		boolean seExporta= false;
		Conexion conex = new Conexion(tipoConexion);
		//fechaActual almacena la fecha actual del sistema
		Calendar fechaActual = new GregorianCalendar();
		String dia = Integer.toString(fechaActual.get(Calendar.DATE));
		String mes = Integer.toString(fechaActual.get(Calendar.MONTH)+1);
		String ano = Integer.toString(fechaActual.get(Calendar.YEAR));
		int cont = 1;
		String contadorDiario="0"+cont;
		String nombreFichero = "VENTAS"+dia+mes+ano+contadorDiario;
		String consultaVentas = "SELECT * FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = ?)";
		ResultSet resultado = null;
		try {
			PreparedStatement consultarVentasPorNif= conex.getConexion().prepareStatement(consultaVentas);
			consultarVentasPorNif.setString(1, nifCliente);
			//En resultado se almacena el resultset con todas las ventas del cliente
			resultado = consultarVentasPorNif.executeQuery();

		} catch (SQLException e) {
			System.out.println("No se ha podido conectar con la base de datos (Error al intentar importar venta por NIF/CSV)");
		}
		try {
			File ficheroCSV = new File(nombreFichero+".csv");
			FileWriter flujoEscritura = new FileWriter(ficheroCSV);
			BufferedWriter flujoEscrituraBuffer = new BufferedWriter(flujoEscritura);
			while(resultado.next()){
				flujoEscrituraBuffer.write(resultado.getInt(1)+";");
				flujoEscrituraBuffer.write(String.valueOf(resultado.getDate(2))+";");
				flujoEscrituraBuffer.write(String.valueOf(resultado.getInt(3))+";");
				flujoEscrituraBuffer.write(String.valueOf(resultado.getInt(4))+";");
				flujoEscrituraBuffer.write(String.valueOf(resultado.getInt(5)));
				flujoEscrituraBuffer.newLine();
			}
			flujoEscrituraBuffer.close();
			seExporta=true;
		} catch (SQLException e) {
			System.out.println("Error de acceso a BD");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de acceso al fichero");
		}

		return seExporta;
	}

	public boolean exportarCSVporFechas(String fechaMinima, String fechaMaxima, String tipoConexion) {					
		boolean seExporta;
		Conexion conex = new Conexion(tipoConexion);
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet resultado=null;
		String muestraVentasPorFecha = "SELECT * FROM ventas WHERE fechaventa BETWEEN ? AND ?";
		Calendar fechaActual = new GregorianCalendar();
		String dia = Integer.toString(fechaActual.get(Calendar.DATE));
		String mes = Integer.toString(fechaActual.get(Calendar.MONTH));
		String ano = Integer.toString(fechaActual.get(Calendar.YEAR));
		int cont = 1;
		String contadorDiario="0"+cont;
		String nombreFichero = "VENTAS"+dia+mes+ano+contadorDiario;
		String consultaVentas = "SELECT * FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = ?)";
		try {
			PreparedStatement consultarVentasPorFecha = conex.getConexion().prepareStatement(muestraVentasPorFecha);
			java.util.Date fechaMinimaUtil = formatoFecha.parse(fechaMinima);
			java.sql.Date fechaMinimaSQL = new java.sql.Date(fechaMinimaUtil.getTime());

			java.util.Date fechaMaximaUtil = formatoFecha.parse(fechaMaxima);
			java.sql.Date fechaMaximaSQL = new java.sql.Date(fechaMaximaUtil.getTime());
			
			consultarVentasPorFecha.setDate(1, fechaMinimaSQL);
			consultarVentasPorFecha.setDate(2, fechaMaximaSQL);
			resultado = consultarVentasPorFecha.executeQuery();
			
			File ficheroCSV = new File(nombreFichero+".csv");
			FileWriter flujoEscritura = new FileWriter(ficheroCSV);
			BufferedWriter flujoEscrituraBuffer = new BufferedWriter(flujoEscritura);
			while(resultado.next()){
				flujoEscrituraBuffer.write(resultado.getInt(1)+";");
				flujoEscrituraBuffer.write(String.valueOf(resultado.getDate(2))+";");
				flujoEscrituraBuffer.write(String.valueOf(resultado.getInt(3))+";");
				flujoEscrituraBuffer.write(String.valueOf(resultado.getInt(4))+";");
				flujoEscrituraBuffer.write(String.valueOf(resultado.getInt(5)));
				flujoEscrituraBuffer.newLine();
			}
			flujoEscrituraBuffer.close();
			seExporta=true;
			
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos");
		} catch (ParseException e) {
			System.out.println("Error al parsear");
		} catch (IOException e) {
			System.out.println("Error al crear ficharo");
			e.printStackTrace();
		}
		
		

		return false;
	}

	//auxiliares
	private static Boolean existeIDCliente(int idCliente, String tipoConexion){
		boolean existeID=false;
		Conexion conex = new Conexion(tipoConexion);
		//1º declaro string con la consulta
		String consultaMuestraClientes= "SELECT id FROM CLIENTES";
		try {
			//2º instancio un objeto statement donde almacenaré la consulta.
			Statement muestraClientes = conex.getConexion().createStatement();
			//3º instancio un resultSet para almacenar el resultSet que devuelve la consulta.
			ResultSet resultSetClientes;
			//4º ejecuto la consulta y almaceno el resultSet
			resultSetClientes=muestraClientes.executeQuery(consultaMuestraClientes);
			while(resultSetClientes.next()&&existeID==false){
				if(idCliente==resultSetClientes.getInt(1)) {
					existeID=true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos AQUI!!!!");
		}
		conex.desconectar();
		return existeID;
	}

	private static Boolean existeIDProducto(int idProducto, String tipoConexion){
		Conexion conex = new Conexion(tipoConexion);
		boolean existeID=false;
		//1º declaro string con la consulta
		String consultaMuestraProductos= "SELECT id FROM PRODUCTOS";
		try {
			Statement muestraProductos = conex.getConexion().createStatement();
			ResultSet resultSetProductos = muestraProductos.executeQuery(consultaMuestraProductos);
			while(resultSetProductos.next()&&existeID==false) {
				if(idProducto==resultSetProductos.getInt(1)) {
					existeID=true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos (Al comprobar si existe ID producto)");
		}
		return existeID;
	}

	private static Boolean existeIDVenta(int idVenta, String tipoConexion){
		Conexion conex = new Conexion(tipoConexion);
		boolean existeID=false;
		String consultaMuestraVentas= "SELECT idventa FROM ventas";
		try {
			Statement muestraVentas = conex.getConexion().createStatement();
			ResultSet resultSetVentas=muestraVentas.executeQuery(consultaMuestraVentas);;
			while(resultSetVentas.next()&&existeID==false) {
				if(idVenta==resultSetVentas.getInt(1)) {
					existeID=true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos(Al comprobar si existe ID venta)");
			e.printStackTrace();
		}
		return existeID;
	}

	private static Boolean existeNIFCliente(String nifCliente, String tipoConexion) {
		boolean existeNIF=false;
		Conexion conex = new Conexion(tipoConexion);
		String consultaMuestraClientes= "SELECT nif FROM clientes";
		try {
			Statement muestraClientes = conex.getConexion().createStatement();
			ResultSet resultSetClientes;
			resultSetClientes=muestraClientes.executeQuery(consultaMuestraClientes);
			while(resultSetClientes.next()&&existeNIF==false) {
				if(nifCliente.equals(resultSetClientes.getString(1))) {
					existeNIF=true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos(Al comprobar si existe NIF cliente)");
			e.printStackTrace();
		}
		return existeNIF;
	}

}