package controlador;

import java.sql.Date;
import java.util.ArrayList;

import modelo.ventas.Venta_Dto;
import modelo.ventas.Venta_Logica;
import vista.VentanaGestionVentas;
import vista.VentanaMenuPrincipal;
//Clase de servira de union entre las distintas ventanas, ademas sera la encargada de que la informacion introducida en la ventana llegue a la clase logica
//y de hay a la clase dao del objeto correspondiente 
public class Coordinador_Ventas {

	private VentanaMenuPrincipal miVentanaMenuPrincipal;
	private VentanaGestionVentas miVentanaGestionVentas;
	private Venta_Logica miLogicaVentas;
	
	//SETTERS
	public void setMiLogicaVentas(Venta_Logica miLogicaVentas) {
		this.miLogicaVentas = miLogicaVentas;
	}
	
	public void setMiVentanaMenuPrincipal(VentanaMenuPrincipal miVentanaMenuPrincipal) {
		this.miVentanaMenuPrincipal = miVentanaMenuPrincipal;
	}
	
	public void setMiVentanaGestionVentas(VentanaGestionVentas miVentanaGestionVentas) {
		this.miVentanaGestionVentas = miVentanaGestionVentas;
	}
	
	
	
	//GETTERS
	public VentanaMenuPrincipal getMiVentanaMenuPrincipal() {
		return miVentanaMenuPrincipal;
	}
	
	public VentanaGestionVentas getMiVentanaGestionVentas() {
		return miVentanaGestionVentas;
	}
	
	public Venta_Logica getMiLogicaVentas() {
		return miLogicaVentas;
	}

	

	//MOSTRAR VENTANAS
	public void mostrarVentanaGestionVentas() {
		miVentanaGestionVentas.setVisible(true);
	}
	public void mostrarVentanaMenuPrincipal() {
		miVentanaMenuPrincipal.setVisible(true);
	}
	
	//OCULTAR VENTANAS
	
	public void ocultarVentanaGestionVentas() {
		miVentanaGestionVentas.setVisible(false);
	}
	public void ocultarVentanaMenuPrincipal() {
		miVentanaMenuPrincipal.setVisible(false);
	}
	
	//METODOS VALIDACION LOGICA
	public void insertaVenta(Venta_Dto venta, String tipoConexion) {
		miLogicaVentas.insertaVenta(venta, tipoConexion);
	}

	public void borrarVentaPorID(String idVenta, String tipoConexion) {
		miLogicaVentas.borrarVentaPorID(idVenta, tipoConexion);
		
	}

	public void borrarVentaPorNIF(String nifCliente, String tipoConexion) {
		miLogicaVentas.borrarVentaPorNIF(nifCliente,tipoConexion);
	}
	
	public ArrayList<Venta_Dto> mostrarVentasPorNIF(String nifCliente, String tipoConexion){
		return miLogicaVentas.mostrarVentasPorNIF(nifCliente, tipoConexion);
	}
	
	public void exportarCSVporCliente(String nifCliente, String tipoConexion) {
		miLogicaVentas.exportarCSVporCliente(nifCliente, tipoConexion);
	}

	public ArrayList<Venta_Dto> mostrarVentasPorFecha(String fechaMin, String fechaMax, String tipoConexion) {
		
		return miLogicaVentas.mostrarVentasPorFecha(fechaMin, fechaMax, tipoConexion);
	}

	public void exportarCSVporFecha(String fechaMin, String fechaMax, String tipoConexion) {
		miLogicaVentas.exportarCSVporFecha(fechaMin, fechaMax, tipoConexion);		
	}
	
}