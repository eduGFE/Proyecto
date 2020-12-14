package controlador;

import vista.VentanaGestionProductos;
import vista.VentanaMenuPrincipal;
//Clase de servira de union entre las distintas ventanas, ademas sera la encargada de que la informacion introducida en la ventana llegue a la clase logica
//y de hay a la clase dao del objeto correspondiente 
public class coordinador_productos {


	private VentanaMenuPrincipal miVentanaMenuPrincipal;
	private VentanaGestionProductos miVentanaGestionProductos;
	
	public VentanaMenuPrincipal muestraVentanaMenuPrincipal() {
		return miVentanaMenuPrincipal;
	}

	public void estableceVentanaMenuPrincipal(VentanaMenuPrincipal VentanaMenuPrincipal) {
		this.miVentanaMenuPrincipal = VentanaMenuPrincipal;
	}
	
	public void mostrarVentanaGestionProductos(String[] args) {
		miVentanaGestionProductos = new VentanaGestionProductos(args);
		miVentanaGestionProductos.setVisible(true);
	}
	
	public void mostrarVentanaMenuPrincipal(String[] args) {
		miVentanaMenuPrincipal = new VentanaMenuPrincipal(args);
		miVentanaMenuPrincipal.setVisible(true);
	}
	
}
