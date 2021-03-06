package controlador;

import vista.VentanaGestionVentas;
import vista.VentanaMenuPrincipal;
//Clase de servira de union entre las distintas ventanas, ademas sera la encargada de que la informacion introducida en la ventana llegue a la clase logica
//y de hay a la clase dao del objeto correspondiente 
public class Coordinador_Ventas {


	private VentanaMenuPrincipal miVentanaMenuPrincipal;
	private VentanaGestionVentas miVentanaGestionVentas;
	
	public VentanaMenuPrincipal muestraVentanaMenuPrincipal() {
		return miVentanaMenuPrincipal;
	}

	public void estableceVentanaMenuPrincipal(VentanaMenuPrincipal VentanaMenuPrincipal) {
		this.miVentanaMenuPrincipal = VentanaMenuPrincipal;
	}
	
	public void mostrarVentanaGestionVentas(String[] args) {
		miVentanaGestionVentas = new VentanaGestionVentas(args);
		miVentanaGestionVentas.setVisible(true);
	}
	
	public void mostrarVentanaMenuPrincipal(String[] args) {
		miVentanaMenuPrincipal = new VentanaMenuPrincipal(args);
		miVentanaMenuPrincipal.setVisible(true);
	}
	
	
}
