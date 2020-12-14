package controlador;

import vista.VentanaGestionClientes;
import vista.VentanaMenuPrincipal;
//Clase de servira de union entre las distintas ventanas, ademas sera la encargada de que la informacion introducida en la ventana llegue a la clase logica
//y de hay a la clase dao del objeto correspondiente 
public class coordinador_clientes {

	private VentanaMenuPrincipal miVentanaMenuPrincipal;
	private VentanaGestionClientes miVentanaGestionClientes;
	
	
	public VentanaMenuPrincipal muestraVentanaMenuPrincipal() {
		return miVentanaMenuPrincipal;
	}

	public void estableceVentanaMenuPrincipal(VentanaMenuPrincipal VentanaMenuPrincipal) {
		this.miVentanaMenuPrincipal = VentanaMenuPrincipal;
	}
	
	
	public void mostrarVentanaGestionClientes(String[] args) {
		miVentanaGestionClientes = new VentanaGestionClientes(args);
		miVentanaGestionClientes.setVisible(true);
	}
	
	public void mostrarVentanaMenuPrincipal(String[] args) {
		miVentanaMenuPrincipal = new VentanaMenuPrincipal(args);
		miVentanaMenuPrincipal.setVisible(true);
	}
	
}
