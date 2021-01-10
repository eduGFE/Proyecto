package modelo.ventas;

import java.sql.Date;
import java.util.ArrayList;

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
		if(seBorra) {
			JOptionPane.showMessageDialog(null, "SE HA ELIMINADO EL REGISTRO CORRECTAMENTE");
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO EL REGISTRO");
		}
		
	}
	
	public ArrayList<Venta_Dto> mostrarVentasPorNIF(String nifCliente, String tipoConexion){
		Venta_Dao venta= new Venta_Dao();
		ArrayList<Venta_Dto> listadoVentas;
		listadoVentas =  venta.mostrarVentasPorNIF(nifCliente, tipoConexion);
		return listadoVentas;
	}

	public void exportarCSVporCliente(String nifCliente, String tipoConexion) {
		boolean seExporta;
		Venta_Dao venta = new Venta_Dao();
		seExporta=venta.exportarCSVporCliente(nifCliente, tipoConexion);
		if(seExporta) {
			JOptionPane.showMessageDialog(null, "SE HAN EXPORTADO LOS DATOS DEL CLIENTE AL FICHERO CSV");
		}else {
			JOptionPane.showMessageDialog(null, "NO SE HAN PODIDO EXPORTAR LOS DATOS DEL CLIENTE AL FICHERO CSV");
		}
		
	}
	
}
