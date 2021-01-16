package modelo.ventas;

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
		boolean seInserta=false;
		Venta_Dao miVenta = new Venta_Dao();
		if (venta.getCantidad()<=9999){
			seInserta = miVenta.insertaVenta(venta, tipoConex);
		}else{
			JOptionPane.showMessageDialog(null, "La cantidad introducida excede el limite");
		}
		if(seInserta) {
			JOptionPane.showMessageDialog(null, "SE HA INSERTADO EL REGISTRO CORRECTAMENTE");
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HA INSERTADO EL REGISTRO");
		}
	}

	public void borrarVentaPorID(String cadenaIdVenta, String tipoConex){
		int idVenta;
		Venta_Dao venta = new Venta_Dao();
		String patronNumeros =  "[0-9]*";
		
		if(cadenaIdVenta.isBlank()) {
			JOptionPane.showMessageDialog(null, "No ha completado la informacion en el campo");
		}else if(Pattern.matches(patronNumeros, cadenaIdVenta)) {
			idVenta = Integer.parseInt(cadenaIdVenta);
			if(venta.borraVentaPorID(idVenta, tipoConex)){
				JOptionPane.showMessageDialog(null, "SE HA ELIMINADO EL REGISTRO CORRECTAMENTE");
			}else {
				JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO EL REGISTRO");
			}	
		}else {
			JOptionPane.showMessageDialog(null, "La ID que se ha introducido es incorrecta");
		}
	}

	public void borrarVentaPorNIF(String nifCliente, String tipoConex) {
		Venta_Dao venta = new Venta_Dao();
		String patronNif = "\\d{8}[A-Z a-z]{1}";
		if(Pattern.matches(patronNif, nifCliente)) {
			if(venta.borrarVentaPorNIF(nifCliente, tipoConex)){
				JOptionPane.showMessageDialog(null, "SE HA ELIMINADO EL REGISTRO CORRECTAMENTE");
			}else {
				JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO NINGUN REGISTRO");
			}
		}else if(nifCliente.isBlank()) {
			JOptionPane.showMessageDialog(null, "Se eliminarán las ventas de todos los clientes que no han introducido DNI");
			if(venta.borrarVentaPorNIF(nifCliente, tipoConex)) {
				JOptionPane.showMessageDialog(null, "SE HA ELIMINADO EL REGISTRO CORRECTAMENTE");
			}else {
				JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO NINGUN REGISTRO");
			}

		}else{
			JOptionPane.showMessageDialog(null, "El nif que se ha introducido es incorrecto");
		}
	}

	public ArrayList<Venta_Dto> mostrarVentasPorNIF(String nifCliente, String tipoConexion){
		boolean esDni =true;
		String patronNif = "\\d{8}[A-Z a-z]{1}";
		if(nifCliente.isBlank()){
			esDni= true;
		}else if(Pattern.matches(patronNif, nifCliente)) {
			esDni= true;
		}else{
			esDni = false;
		}

		if(esDni) {
			Venta_Dao venta= new Venta_Dao();
			ArrayList<Venta_Dto> listadoVentas;
			listadoVentas =  venta.mostrarVentasPorNIF(nifCliente, tipoConexion);
			return listadoVentas;
		}else{
			JOptionPane.showMessageDialog(null, "El DNI introducido no es valido");
		}
		return null;
	}

	public void exportarCSVporCliente(String nifCliente, String tipoConexion) {
		boolean esDni;
		String patronNif = "\\d{8}[A-Z a-z]{1}";
		
		if(nifCliente.isBlank()){
            esDni = true;
        }else if(!Pattern.matches(patronNif, nifCliente)) {
			esDni= false;
		}else{
			esDni = true;
		}

		if(esDni) {
			Venta_Dao venta = new Venta_Dao();
			venta.exportarCSVporCliente(nifCliente, tipoConexion);
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HAN PODIDO EXPORTAR LOS DATOS DEL CLIENTE AL FICHERO CSV");
		}

	}

	public void exportarXMLporCliente(String nifCliente, String tipoConexion) {
		boolean esDni;
		String patronNif = "\\d{8}[A-Z a-z]{1}";

		if(nifCliente.isEmpty()){
			esDni = true;
		}else if(!Pattern.matches(patronNif, nifCliente)) {
			esDni= false;
		}else{
			esDni = true;
		}

		if(esDni) {
			Venta_Dao venta = new Venta_Dao();
			venta.exportarXMLporCliente(nifCliente, tipoConexion);
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HAN PODIDO EXPORTAR LOS DATOS DEL CLIENTE AL FICHERO XML");
		}

	}

	public ArrayList<Venta_Dto> mostrarVentasPorFecha(String fechaMin, String fechaMax, String tipoConexion) {
		boolean esFechaValida = true;
		boolean esOrdenCorrecto = true;
		//Defino un patron para comprobar la cadena. \\d -> indica que es un digito y lo que hay en las llaves indica la cantidad de digitos a introducir.
		String formatoDePatronFecha = "\\d{4}-\\d{1,2}-\\d{1,2}";
		//COMPRUEBA EL FORMATO DE LA FECHA
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