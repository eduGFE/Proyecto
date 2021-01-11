package modelo.ventas;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import controlador.Coordinador_Ventas;

//Clase que controlara que los datos introducidos son validos para la insercion en la BBDD
public class Venta_Logica {
	private Coordinador_Ventas miCoordinadorVentas;


	public void setCoordinadorVentas(Coordinador_Ventas miCoordinador) {
		this.miCoordinadorVentas=miCoordinador;
	}

	public void insertaVenta(Venta_Dto venta, String tipoConex ) {
		boolean seInserta;

		
		Venta_Dao miVenta = new Venta_Dao();
		seInserta = miVenta.insertaVenta(venta, tipoConex);
		if(seInserta) {
			JOptionPane.showMessageDialog(null, "SE HA INSERTADO EL REGISTRO CORRECTAMENTE");
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HA INSERTADO EL REGISTRO");
		}
	}

	public void borrarVentaPorID(int idVenta, String tipoConex) {
		boolean seBorra;
		Venta_Dao miVenta = new Venta_Dao();
		seBorra = miVenta.borraVentaPorID(idVenta, tipoConex);
		if(seBorra) {
			JOptionPane.showMessageDialog(null, "SE HA ELIMINADO EL REGISTRO CORRECTAMENTE");
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO EL REGISTRO");
		}
	}

	public void borrarVentaPorNIF(String nifCliente, String tipoConex) {
		boolean seBorra;
		Venta_Dao miVenta = new Venta_Dao();
		seBorra = miVenta.borrarVentaPorNIF(nifCliente, tipoConex);
		String patronNif = "\\d{8}[A-Z a-z]{1}";
		if(!Pattern.matches(patronNif, nifCliente)) {
			seBorra= false;
		}else {
			seBorra = true;
		}


		if(seBorra) {
			JOptionPane.showMessageDialog(null, "SE HA ELIMINADO EL REGISTRO CORRECTAMENTE");
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO EL REGISTRO");
		}

	}

	public ArrayList<Venta_Dto> mostrarVentasPorNIF(String nifCliente, String tipoConexion){

		boolean esDni;
		String patronNif = "\\d{8}[A-Z a-z]{1}";
		if(!Pattern.matches(patronNif, nifCliente)) {
			esDni= false;
		}else{
			esDni = true;
		}

		if(esDni) {
			Venta_Dao venta= new Venta_Dao();
			ArrayList<Venta_Dto> listadoVentas;
			listadoVentas =  venta.mostrarVentasPorNIF(nifCliente, tipoConexion);
			return listadoVentas;
		}else {
			JOptionPane.showMessageDialog(null, "El DNI introducido no es valido");
		}
		return null;
	}

	public void exportarCSVporCliente(String nifCliente, String tipoConexion) {
		boolean seExporta;
		String patronNif = "\\d{8}[A-Z a-z]{1}";
		if(!Pattern.matches(patronNif, nifCliente)) {
			seExporta= false;
		}else{
			seExporta = true;
		}

		if(seExporta) {
			Venta_Dao venta = new Venta_Dao();
			seExporta=venta.exportarCSVporCliente(nifCliente, tipoConexion);
			JOptionPane.showMessageDialog(null, "SE HAN EXPORTADO LOS DATOS DEL CLIENTE AL FICHERO CSV");
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HAN PODIDO EXPORTAR LOS DATOS DEL CLIENTE AL FICHERO CSV");
		}

	}

	public ArrayList<Venta_Dto> mostrarVentasPorFecha(String fechaMin, String fechaMax, String tipoConexion) {
		boolean esFechaValida = true;
		//Defino un patron para comprobar la cadena. \\d -> indica que es un digito y lo que hay en las llaves indica la cantidad de digitos a introducir.
		String formatoDePatronFecha = "\\d{4}-\\d{1,2}-\\d{1,2}";

		if(!Pattern.matches(formatoDePatronFecha, fechaMin)||!Pattern.matches(formatoDePatronFecha, fechaMax)) {
			esFechaValida = false;
		}

		if(esFechaValida == false){
			JOptionPane.showMessageDialog(null, "La fecha introducida es incorrecta");
		}else{
			Venta_Dao venta = new Venta_Dao();
			ArrayList<Venta_Dto> listadoVentas;
			listadoVentas = venta.mostrarVentasPorFecha(fechaMin, fechaMax, tipoConexion);
			return listadoVentas;
		}
		return null;
	}

	public void exportarCSVporFecha(String fechaMin, String fechaMax, String tipoConexion) {
		boolean esFechaValida=true;
		String formatoDePatronFecha = "\\d{4}-\\d{1,2}-\\d{1,2}";
		if(!Pattern.matches(formatoDePatronFecha, fechaMin)||!Pattern.matches(formatoDePatronFecha, fechaMax)) {
			esFechaValida = false;
		}

		if(esFechaValida == false) {
			JOptionPane.showMessageDialog(null, "El formato de la fecha debe ser: YYYY-MM-DD");
		}else{
			Venta_Dao venta = new Venta_Dao();
			venta.exportarCSVporFechas(fechaMin, fechaMax, tipoConexion);
		}


	}

}