package modelo.productos;

import javax.swing.JFrame;

import vista.VentanaGestionProductos;
import vista.VentanaMenuPrincipal;

//Clase que controlara que los datos introducidos son validos para la insercion en la BBDD
public class Producto_Logica {

	
	public void escribirproductosenfichero() {
		Producto_Dao producto = new Producto_Dao();
		producto.escribirproductosenfichero();
	}
	
	
	

}
