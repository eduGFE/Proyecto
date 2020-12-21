package controlador;

import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
	
	public void importarabbdd(String[] args,JTable table2,int fila) throws Exception {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.importarabbdd(args,table2,fila);
	}
	public void consultarproductos(String[] args,DefaultTableModel model1) throws Exception {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.consultarproductos(args,model1);
	}
	public DefaultTableModel eliminarproducto(String[] args,int id,DefaultTableModel model1) throws Exception {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.eliminarproducto(args,id,model1);
		return model1;
	}
	public DefaultTableModel consultarproductoporid(String[] args,int id,DefaultTableModel model1) throws Exception {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.consultarproductoporid(args,id,model1);
		return model1;
	}
	
	
	

}

