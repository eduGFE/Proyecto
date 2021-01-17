package modelo.ventas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import modelo.conexion.Conexion;
import modelo.productos.Producto_Dto;

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
				JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR CON LA BASE DE DATOS");
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
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR CON LA BASE DE DATOS");
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

		if(nifCliente.isEmpty()) {
			
			if(existeNIFCliente(nifCliente, tipoConexion)) {
				
				String sentenciaBorrarVentaNIF = "DELETE FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = '')";
				try {
					PreparedStatement borrarVentaNif=conex.getConexion().prepareStatement(sentenciaBorrarVentaNIF);
					borrarVentaNif.executeUpdate();
					seBorra=true;
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR CON LA BASE DE DATOS");
				}
			}
		}
		else {
			if(existeNIFCliente(nifCliente, tipoConexion)) {
				String sentenciaBorrarVentaNIF = "DELETE FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = ?)";
				try {
					PreparedStatement borrarVentaNif=conex.getConexion().prepareStatement(sentenciaBorrarVentaNIF);
					borrarVentaNif.setString(1, nifCliente);
					borrarVentaNif.executeUpdate();
					seBorra=true;
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR CON LA BASE DE DATOS");
				}
				conex.desconectar();
				seBorra = true;
			}else {
				JOptionPane.showMessageDialog(null, "El NIF del cliente no existe en la base de datos");
			}
		}
		conex.desconectar();
		return seBorra;
	}

	public ArrayList<Venta_Dto> mostrarVentasPorNIF(String nifCliente, String tipoConexion) {
		ArrayList<Venta_Dto> listadoVentas = new ArrayList<Venta_Dto>();
		Conexion conex = new Conexion(tipoConexion);
		ResultSet resultado;
		//Si la cadena entra vacía, se buscan null
		if(nifCliente.isBlank()){
			String consultaSQL = "SELECT * FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = '')";
			try {
				PreparedStatement consultaVentasPorNif = conex.getConexion().prepareStatement(consultaSQL);
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
				e.printStackTrace();			
				}
			//Si no esta vacía
		}else {
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
				e.printStackTrace();
			}
		}
		conex.desconectar();
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
			e1.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido conectar a la base de datos");
		}
		conex.desconectar();
		return listadoVentas;
	}

	public void exportarXMLporCliente(String nifCliente, String tipoConexion) {
		ArrayList<Venta_Dto> listaVentas = new ArrayList<Venta_Dto>();
		try {		
			listaVentas = almacenarEnArrayList(nifCliente, tipoConexion);
			if (listaVentas.size() != 0) {
				String nombreFichero = obtenerNombreFicheroXML();
				crearXML(nombreFichero, listaVentas);	
			}else {
				JOptionPane.showMessageDialog(null, "No hay Ventas con el Nif de Cliente especificado!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Venta_Dto> almacenarEnArrayList(String nifCliente, String tipoConexion) {
		ArrayList<Venta_Dto> listaVentas = new ArrayList<Venta_Dto>();
		Venta_Dto venta_Dto = null;
		Conexion conex = new Conexion(tipoConexion);

		if (nifCliente.isEmpty()) {
			// Aqui va el codigo para clientes sin NIF
			ResultSet clientes;
			ResultSet ventas = null;
			String consultaClientes = "SELECT id FROM CLIENTES WHERE nif = ''";
			String consultaVentas = "SELECT * FROM ventas WHERE idcliente = ?";
			try {
				PreparedStatement sentenciaClientes = conex.getConexion().prepareStatement(consultaClientes);
				clientes = sentenciaClientes.executeQuery();
				while (clientes.next()) {
					int idCliente = clientes.getInt("id");
					PreparedStatement sentenciaVentas = conex.getConexion().prepareStatement(consultaVentas);
					sentenciaVentas.setInt(1, idCliente);
					ventas = sentenciaVentas.executeQuery();
				
					while (ventas.next()) {
						venta_Dto = new Venta_Dto(ventas.getInt("idventa"), ventas.getDate("fechaventa"),
								ventas.getInt("idcliente"), ventas.getInt("idproducto"),
								ventas.getInt("cantidad"));
						listaVentas.add(venta_Dto);
					}
				}
					conex.desconectar();	
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {

			String consultaVentas = "SELECT * FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = ?)";
			ResultSet resultado = null;
			try {
				PreparedStatement consultarVentasPorNif = conex.getConexion().prepareStatement(consultaVentas);
				consultarVentasPorNif.setString(1, nifCliente);
				// En resultado se almacena el resultset con todas las ventas del cliente
				resultado = consultarVentasPorNif.executeQuery();
					while (resultado.next()) {
						venta_Dto = new Venta_Dto(resultado.getInt("idventa"), resultado.getDate("fechaventa"),
								resultado.getInt("idcliente"), resultado.getInt("idproducto"),
								resultado.getInt("cantidad"));
						listaVentas.add(venta_Dto);
					}
					conex.desconectar();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaVentas;
	}

	public static void crearXML(String nombreArchivo, ArrayList<Venta_Dto> listaVentas) throws TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, nombreArchivo, null);
			document.setXmlVersion("1.0");
			Element raiz = document.getDocumentElement();
			for (int i = 0; i < listaVentas.size(); i++) {
				Element itemNode = document.createElement("ventas");
				
				Element Idnode = document.createElement("id_venta");
				Text nodeIdvalue = document.createTextNode("" + listaVentas.get(i).getIdVenta());
				Idnode.appendChild(nodeIdvalue);

				Element fechanode = document.createElement("fecha_venta");
				Text nodefechavalue = document.createTextNode("" + listaVentas.get(i).getFechaVenta());
				fechanode.appendChild(nodefechavalue);
		
				Element idclientenode = document.createElement("id_cliente");
				Text nodeidclientevalue = document.createTextNode("" + listaVentas.get(i).getIdCliente());
				idclientenode.appendChild(nodeidclientevalue);
				
				Element idproductonode = document.createElement("id_producto");
				Text nodeidproductovalue = document.createTextNode("" + listaVentas.get(i).getIdProducto());
				idproductonode.appendChild(nodeidproductovalue);
				
				Element cantidadnode = document.createElement("cantidad");
				Text nodecantidadvalue = document.createTextNode("" + listaVentas.get(i).getCantidad());
				cantidadnode.appendChild(nodecantidadvalue);

				itemNode.appendChild(Idnode);
				itemNode.appendChild(fechanode);
				itemNode.appendChild(idclientenode);
				itemNode.appendChild(idproductonode);
				itemNode.appendChild(cantidadnode);
		
				raiz.appendChild(itemNode);

			}
			Source source = new DOMSource(document);
			StreamResult result = new StreamResult(new File("Ventas XML Exportadas\\" +nombreArchivo));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			JOptionPane.showMessageDialog(null, "SE HAN EXPORTADO LOS DATOS AL FICHERO -> "+nombreArchivo);

		} catch (ParserConfigurationException e) {
			Logger.getLogger(Venta_Dao.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	private static String obtenerNombreFicheroXML() {

		File carpetaVentas = new File("Ventas XML Exportadas");
		carpetaVentas.mkdir(); // Crea la carpeta (en el WorkSpace del proyecto) si no existe.
		String fechaActual = generarFechaActual();
		int contadorDiario = 1;
		String contadorDiarioStr = String.format("%02d", contadorDiario); // Formato de 2 dígitos.
		String nombreFichero = "VENTAS" + fechaActual + "_" + contadorDiarioStr+".xml"; // Provisional.

		// COMPROBACIÓN DE EXISTENCIA DE NOMBRE DE FICHERO IGUAL.
		// Recorremos el directorio guardando cada fichero en objeto ficheroEntrada.
		for (File ficheroEntrada : carpetaVentas.listFiles()) {
			// Si ya existe un fichero con el mismo nombre que el generado anteriormente...
			if ((ficheroEntrada.getName().equals(nombreFichero))) {
				contadorDiario++; // Aumentamos el contador diario.
				if (contadorDiario < 10) { // Si es menor a 10...
					// Fijamos formato en 2 dígitos y guardamos el nombre.
					contadorDiarioStr = String.format("%02d", contadorDiario);
					nombreFichero = "VENTAS" + fechaActual + "_" + contadorDiarioStr+".xml";
				} else { // Si NO es menor a 10 no es necesario fijar formato.
					nombreFichero = "VENTAS" + fechaActual + "_" + contadorDiario+".xml";
				}
			}
		}
		return nombreFichero;
	}

	public void exportarCSVporCliente(String nifCliente, String tipoConexion){

		Conexion conex = new Conexion(tipoConexion);
		//fechaActual almacena la fecha actual del sistema
		String nombreFichero = obtenerNombreFicheroCSV();
		if(nifCliente.isBlank()) {
			//Aqui va el codigo para clientes sin NIF
			ResultSet clientes;
			ResultSet ventas=null;

			String consultaClientes = "SELECT id FROM CLIENTES WHERE nif = ''";
			String consultaVentas = "SELECT * FROM ventas WHERE idcliente = ?";

			try {
				PreparedStatement sentenciaClientes = conex.getConexion().prepareStatement(consultaClientes);
				clientes = sentenciaClientes.executeQuery();
				while(clientes.next()) {
					int idCliente = clientes.getInt("id");
					PreparedStatement sentenciaVentas = conex.getConexion().prepareStatement(consultaVentas);
					sentenciaVentas.setInt(1, idCliente);
					ventas = sentenciaVentas.executeQuery();
				}
				if(ventas.next()==false) {
					JOptionPane.showMessageDialog(null, "NO HAY CONTENIDO EN LA TABLA. NO SE IMPORTARÁ EL FICHERO");
				}else {
					ventas.previous();
					File ficheroCSV = new File("Ventas CSV Exportadas\\"+nombreFichero);
					FileWriter flujoEscritura = new FileWriter(ficheroCSV);
					BufferedWriter flujoEscrituraBuffer = new BufferedWriter(flujoEscritura);
					while(ventas.next()){
						flujoEscrituraBuffer.write(ventas.getInt(1)+";");
						flujoEscrituraBuffer.write(String.valueOf(ventas.getDate(2))+";");
						flujoEscrituraBuffer.write(String.valueOf(ventas.getInt(3))+";");
						flujoEscrituraBuffer.write(String.valueOf(ventas.getInt(4))+";");
						flujoEscrituraBuffer.write(String.valueOf(ventas.getInt(5)));
						flujoEscrituraBuffer.newLine();
					}
					JOptionPane.showMessageDialog(null, "SE HAN EXPORTADO LOS DATOS DEL CLIENTE AL FICHERO CSV");
					flujoEscrituraBuffer.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else {
			String consultaVentas = "SELECT * FROM ventas WHERE idcliente = (SELECT id FROM clientes WHERE NIF = ?)";
			ResultSet resultado = null;
			try {
				PreparedStatement consultarVentasPorNif= conex.getConexion().prepareStatement(consultaVentas);
				consultarVentasPorNif.setString(1, nifCliente);
				//En resultado se almacena el resultset con todas las ventas del cliente
				resultado = consultarVentasPorNif.executeQuery();
				if(resultado.next()==false) {
					JOptionPane.showMessageDialog(null, "NO HAY CONTENIDO EN LA TABLA. NO SE IMPORTARÁ EL FICHERO");
				}else {
					resultado.previous();
					File ficheroCSV = new File("Ventas CSV Exportadas\\"+nombreFichero);
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
					JOptionPane.showMessageDialog(null, "SE HAN EXPORTADO LOS DATOS DEL CLIENTE AL FICHERO CSV");
					flujoEscrituraBuffer.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		conex.desconectar();

	}

	public void exportarCSVporFechas(String fechaMinima, String fechaMaxima, String tipoConexion) {					
		Conexion conex = new Conexion(tipoConexion);
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet resultado=null;
		String muestraVentasPorFecha = "SELECT * FROM ventas WHERE fechaventa BETWEEN ? AND ?";
		String nombreFichero = obtenerNombreFicheroCSV();
		try {
			PreparedStatement consultarVentasPorFecha = conex.getConexion().prepareStatement(muestraVentasPorFecha);
			java.util.Date fechaMinimaUtil = formatoFecha.parse(fechaMinima);
			java.sql.Date fechaMinimaSQL = new java.sql.Date(fechaMinimaUtil.getTime());

			java.util.Date fechaMaximaUtil = formatoFecha.parse(fechaMaxima);
			java.sql.Date fechaMaximaSQL = new java.sql.Date(fechaMaximaUtil.getTime());

			consultarVentasPorFecha.setDate(1, fechaMinimaSQL);
			consultarVentasPorFecha.setDate(2, fechaMaximaSQL);
			resultado = consultarVentasPorFecha.executeQuery();

			if(resultado.next()==false) {
				JOptionPane.showMessageDialog(null, "NO HAY CONTENIDO EN LA TABLA. NO SE IMPORTARÁ EL FICHERO");
			}else{
				//al hacer el if anterior salta el primer registro, hay que retroceder para que lea la tabla desde el principio
				resultado.previous();
				File ficheroCSV = new File("Ventas CSV Exportadas\\"+nombreFichero);
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
				JOptionPane.showMessageDialog(null, "SE HAN EXPORTADO LOS DATOS DEL CLIENTE AL FICHERO CSV");
				flujoEscrituraBuffer.close();
				conex.desconectar();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportarXMLporFechas(String fechaMinima, String fechaMaxima, String tipoConexion) {					
		Conexion conex = new Conexion(tipoConexion);
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet resultado=null;
		String muestraVentasPorFecha = "SELECT * FROM ventas WHERE fechaventa BETWEEN ? AND ?";
		try {
			PreparedStatement consultarVentasPorFecha = conex.getConexion().prepareStatement(muestraVentasPorFecha);
			java.util.Date fechaMinimaUtil = formatoFecha.parse(fechaMinima);
			java.sql.Date fechaMinimaSQL = new java.sql.Date(fechaMinimaUtil.getTime());

			java.util.Date fechaMaximaUtil = formatoFecha.parse(fechaMaxima);
			java.sql.Date fechaMaximaSQL = new java.sql.Date(fechaMaximaUtil.getTime());

			consultarVentasPorFecha.setDate(1, fechaMinimaSQL);
			consultarVentasPorFecha.setDate(2, fechaMaximaSQL);
			resultado = consultarVentasPorFecha.executeQuery();
			
			ArrayList<Venta_Dto> listaVentas = new ArrayList<Venta_Dto>();
			Venta_Dto venta_Dto = null;
			
			while (resultado.next()) {
				venta_Dto = new Venta_Dto(resultado.getInt("idventa"), resultado.getDate("fechaventa"),
						resultado.getInt("idcliente"), resultado.getInt("idproducto"),
						resultado.getInt("cantidad"));
				listaVentas.add(venta_Dto);
			}
			conex.desconectar();
			
			if (listaVentas.size() != 0) {
				String nombreFichero = obtenerNombreFicheroXML();
				crearXML(nombreFichero, listaVentas);
			}else {
				JOptionPane.showMessageDialog(null, "No hay Ventas en el periodo seleccionado!");
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		conex.desconectar();
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
			e.printStackTrace();
		}
		conex.desconectar();
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
			if(nifCliente.isBlank()){
				while(resultSetClientes.next()&&existeNIF==false) {

					if((resultSetClientes.getString("nif")).equals("")) {
						existeNIF=true;
					}
				}
			}else {
				while(resultSetClientes.next()&&existeNIF==false) {
					if(nifCliente.equals(resultSetClientes.getString(1))) {
						existeNIF=true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conex.desconectar();
		return existeNIF;
	}

	private static String obtenerNombreFicheroCSV() {

		File carpetaVentas = new File("Ventas CSV Exportadas");
		carpetaVentas.mkdir();	//Crea la carpeta (en el WorkSpace del proyecto) si no existe.	
		String fechaActual = generarFechaActual();
		int contadorDiario = 1;
		String contadorDiarioStr = String.format("%02d",contadorDiario); //Formato de 2 dígitos.
		String nombreFichero = "VENTAS"+ fechaActual +"_"+contadorDiarioStr+".csv"; //Provisional.

		//COMPROBACIÓN DE EXISTENCIA DE NOMBRE DE FICHERO IGUAL.
		//Recorremos el directorio guardando cada fichero en objeto ficheroEntrada.
		for (File ficheroEntrada : carpetaVentas.listFiles()) {
			//Si ya existe un fichero con el mismo nombre que el generado anteriormente...
			if ((ficheroEntrada.getName().equals(nombreFichero))) {
				contadorDiario++; //Aumentamos el contador diario.
				if (contadorDiario<10) { //Si es menor a 10...
					//Fijamos formato en 2 dígitos y guardamos el nombre.
					contadorDiarioStr = String.format("%02d",contadorDiario);
					nombreFichero = "VENTAS"+ fechaActual +"_"+ contadorDiarioStr +".csv";
				} else { //Si NO es menor a 10 no es necesario fijar formato.
					nombreFichero = "VENTAS"+ fechaActual +"_"+ contadorDiario +".csv";
				}	
			}	        
		}
		return nombreFichero;
	}
	
	/**
	 * Método que genera y devuelve la fecha actual en formato 'ddmmaa'.
	 */
	private static String generarFechaActual() {
		//Generamos fecha actual completa.
		Calendar fechaCompleta = Calendar.getInstance();
		
		//Guardamos el día de la fecha actual.
		String dia = Integer.toString(fechaCompleta.get(Calendar.DATE));
		//Si el día tiene menos de dos dígitos, le añadimos un cero a la izquierda.
		if (dia.length()<2) { 
			dia = String.format("%02d",Integer.valueOf(dia)); 
			}
		
		//Guardamos el mes de la fecha actual (+1 porque van de 0 a 11).
		String mes = Integer.toString(fechaCompleta.get(Calendar.MONTH)+1);
		//Si el mes tiene menos de dos dígitos, le añadimos un cero a la izquierda.
		if (mes.length()<2) { 
			mes = String.format("%02d",Integer.valueOf(mes)); 
			}
		
		//Guardamos las dos últimas cifras del año actual.
		String ano = Integer.toString((fechaCompleta.get(Calendar.YEAR))).substring(2,4);
		
		return dia+mes+ano; //Devolvemos concatenación de fecha (ddmmaa).
	}
}