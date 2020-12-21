package controlador;

import modelo.productos.Producto_Logica;
import vista.VentanaGestionProductos;
import vista.VentanaMenuPrincipal;
//Clase de servira de union entre las distintas ventanas, ademas sera la encargada de que la informacion introducida en la ventana llegue a la clase logica
//y de hay a la clase dao del objeto correspondiente 
public class Coordinador_Productos {

	private Producto_Logica miProducto_Logica;
	private VentanaMenuPrincipal miVentanaMenuPrincipal;
	private VentanaGestionProductos miVentanaGestionProductos;


	public VentanaGestionProductos mostrarVentanaGestionProductos(String[] args) {
		miVentanaGestionProductos = new VentanaGestionProductos(args);
		miVentanaGestionProductos.setVisible(true);
		return miVentanaGestionProductos;
	}

	public void mostrarVentanaMenuPrincipal(String[] args) {
		miVentanaMenuPrincipal = new VentanaMenuPrincipal(args);
		miVentanaMenuPrincipal.setVisible(true);
	}
	public void escribirproductosenfichero() {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.escribirproductosenfichero();
		
	}

}

